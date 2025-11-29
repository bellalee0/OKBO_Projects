# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

OKBO is a KBO (Korean Baseball Organization) fan community platform built with Spring Boot. Users can post about their favorite teams, follow other fans, comment, and like posts. The project was developed as a team project for Sparta Coding Camp's Kotlin & Spring Boot training program.

**Tech Stack:** Java 17, Spring Boot 3.5.8, MySQL 8.4, JPA, JWT

## Build & Run Commands

### Build
```bash
./gradlew build
```

### Run Application
```bash
./gradlew bootRun
```

### Run Tests
```bash
./gradlew test
```

### Run Specific Test Class
```bash
./gradlew test --tests "com.okbo_projects.domain.user.service.UserServiceTest"
```

### Clean Build
```bash
./gradlew clean build
```

## Architecture Overview

### Package Structure Philosophy

The project follows **domain-driven package structure** with clear separation of concerns:

```
com.okbo_projects/
├── common/              # Shared infrastructure
│   ├── entity/         # BaseEntity + all JPA entities (User, Board, Comment, Like, Follow)
│   ├── exception/      # Global exception handling
│   ├── filter/         # Authentication filter (JWT validation)
│   ├── model/          # Shared models (Team enum, LoginUser)
│   └── utils/          # JWT and password encoding utilities
│
└── domain/             # Business domains (each has controller/service/repository/model)
    ├── user/
    ├── board/
    ├── comment/
    ├── like/
    └── follow/
```

**Key Design Decision:** All JPA entities live in `common/entity` rather than in individual domain packages. This centralizes entity relationships and makes the data model easier to understand at a glance.

### Authentication Flow

1. **LoginCheckFilter** (OncePerRequestFilter) intercepts all requests
2. Whitelist pattern checks if endpoint requires authentication
3. JWT token extracted from `Authorization: Bearer <token>` header
4. Token validated via `JwtUtils.validateToken()`
5. User ID extracted and wrapped in `LoginUser` object
6. `LoginUser` injected into request attributes as `"loginUser"`
7. Controllers access via `@RequestAttribute(name = "loginUser") LoginUser loginUser`

**No Spring Security is used** - custom filter-based authentication.

### Soft Delete Pattern

All entities inherit from `BaseEntity` which includes:
- `createdAt` / `modifiedAt` (JPA Auditing with @CreatedDate/@LastModifiedDate)
- `isDeleted` flag (TINYINT(1), default 0)

When deleting entities:
- Set `isDeleted = true` via `updateIsDeleted()` method
- Repository queries filter with `WHERE entity.isDeleted = false`
- Related entities also soft-deleted (e.g., deleting board soft-deletes comments)

**Important:** Enable JPA Auditing is configured in `OkboProjectsApplication` with `@EnableJpaAuditing`.

### Repository Pattern Enhancement

Repositories use **default methods** to encapsulate exception handling:

```java
// Instead of Optional, throw CustomException directly
default User findUserById(Long id) {
    return findById(id).orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER));
}
```

This keeps service layer clean and consistent. All domain repositories follow this pattern.

### Team Enum System

The `Team` enum represents 10 KBO baseball teams (DOOSAN, HANHWA, KIA, KT, KIWOOM, LG, LOTTE, NC, SAMSUNG, SSG). It's used extensively:
- User's favorite team (`User.team`)
- Board's associated team (`Board.team`)
- Search/filter boards by team

Stored as `@Enumerated(EnumType.STRING)` in database.

## Important Implementation Details

### Count Management in Entities

`Board` and `Comment` entities maintain denormalized count fields:
- `Board.comments` (comment count)
- `Board.likes` / `Comment.likes` (like counts)

These are incremented/decremented via entity methods:
- `addComments()` / `minusComments()`
- `addLikes()` / `minusLikes()`

Called from service layer when creating/deleting related entities.

### Like Entity Polymorphism

`Like` entity supports both board likes and comment likes using nullable foreign keys:
```java
@ManyToOne(fetch = FetchType.LAZY)
private Board board;      // nullable

@ManyToOne(fetch = FetchType.LAZY)
private Comment comment;  // nullable
```

Constructor determines which type:
- `new Like(user, board)` for board likes
- `new Like(user, comment)` for comment likes

### Search Query Pattern

Complex search queries use `@Query` with conditional JPQL:
```java
WHERE (:title IS NULL OR :title = '' OR board.title LIKE %:title%)
  AND (:writer IS NULL OR :writer = '' OR board.writer.nickname LIKE %:writer%)
  AND (:startDate IS NULL OR board.createdAt >= :startDate)
  AND (:endDate IS NULL OR board.createdAt <= :endDate)
  AND board.isDeleted = false
```

Service layer checks if search params exist before choosing search vs. findAll:
```java
boolean searchCondition = (title != null && !title.isBlank()) || ...;
if (searchCondition) {
    // Use search query
} else {
    // Use simple findAll
}
```

### Date Format Conversion

Date parameters come as strings (`yyyyMMdd`) and are converted to `LocalDateTime`:
- `startDate` → LocalDate + `atTime(0, 0, 0)`
- `endDate` → LocalDate + `atTime(23, 59, 59)`

## Configuration Notes

### Database Configuration

**Location:** `src/main/resources/application.yml`

Current settings:
- `spring.jpa.hibernate.ddl-auto: update` (auto-schema update)
- `spring.jpa.show-sql: true` (SQL logging)
- MySQL 8 dialect

**Security Warning:** The current `application.yml` contains hardcoded credentials and JWT secret key. These should be externalized to environment variables or separate config files before production deployment.

### JWT Configuration

- Token expiration: 24 hours (`24 * 60 * 60 * 1000L`)
- Algorithm: HS256
- Secret key: Base64-encoded string from `application.yml`
- Claims: `userId` (Long)

## Domain-Specific Logic

### User Domain
- Unique constraints: `nickname`, `email`
- Password hashing: BCrypt (via `PasswordEncoder` utility)
- User deletion cascade: Deletes all follows (as follower and followee)

### Board Domain
- Board belongs to one `Team`
- Writer validation: Only writer can update/delete
- Board deletion cascade: Soft-deletes all comments, hard-deletes all likes

### Follow Domain
- `fromUser` (follower) → `toUser` (followee)
- Cannot follow yourself (validation in service)
- Used for personalized feed: "show boards from users I follow"

### Comment Domain
- Belongs to both `Board` and `User`
- Deletion: Only soft delete (doesn't cascade to likes)

### Like Domain
- Prevents duplicate likes (check before insert)
- Unlike: Hard delete from database

## Error Handling Pattern

All business errors throw `CustomException` with predefined `ErrorMessage` enum:
```java
throw new CustomException(ErrorMessage.NOT_FOUND_USER);
```

`GlobalExceptionHandler` catches and converts to `ErrorResponse` with appropriate HTTP status.

## Development Conventions

### Transaction Management
- Use `@Transactional` on service methods
- Read-only queries: `@Transactional(readOnly = true)` for optimization
- Dirty checking: Entity changes auto-persist in transaction (no need to call `save()` for updates)
- Only call `save()` for new entity creation

### Pagination
- Default: 10 items per page, sorted by `createdAt` DESC
- Use `@PageableDefault` in controllers for consistency
- Return `Page<ResponseDto>` for paginated endpoints

### Response Pattern
- Create: 201 Created with response body
- Update: 200 OK with response body
- Delete: 204 No Content (empty body)
- Get: 200 OK with response body

### Validation
- Use `@Valid` on request DTOs in controllers
- Validation errors caught by `GlobalExceptionHandler.handleMethodArgumentNotValidException()`
