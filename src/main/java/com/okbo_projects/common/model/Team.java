package com.okbo_projects.common.model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public enum Team {
    // 필드 -----------------------------------------------------
    // 팀 필드
    DOOSAN("두산 베어즈"),
    HANHWA("한화 이글스"),
    KIA("기아 타이거즈"),
    KT("KT 위즈"),
    KIWOOM("키움 히어로즈"),
    LG("LG 트윈스"),
    LOTTE("롯데 자이언츠"),
    NC("NC 다이노스"),
    SAMSUNG("삼성 라이온즈"),
    SSG("SSG 랜더스");

    // 인스턴스 필드
    private String teamName;


    // 생성자 ---------------------------------------------------
    Team(String teamName) {
        this.teamName = teamName;
    }


    // Getter --------------------------------------------------
    public String getTeamName() {
        return teamName;
    }

}

