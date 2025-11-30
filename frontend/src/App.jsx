import { useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import useAuthStore from './store/authStore';
import { Button, Input, Loading, Select, Modal, Header } from './components/common';
import { TEAM_OPTIONS } from './utils/constants';

function App() {
  const { isAuthenticated } = useAuthStore();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [inputValue, setInputValue] = useState('');
  const [selectedTeam, setSelectedTeam] = useState('');
  const [inputError, setInputError] = useState('');

  const handleInputChange = (e) => {
    setInputValue(e.target.value);
    if (e.target.value.length < 3) {
      setInputError('최소 3자 이상 입력해주세요');
    } else {
      setInputError('');
    }
  };

  return (
    <Router>
      <div className="min-h-screen bg-gray-50">
        {/* Header 테스트 */}
        <Header />

        <Routes>
          <Route
            path="/"
            element={
              <div className="max-w-7xl mx-auto p-8">
                <div className="text-center mb-12 mt-8">
                  <h1 className="text-4xl font-bold text-gray-900 mb-4">
                    ⚾ OKBO 공통 컴포넌트 테스트
                  </h1>
                  <p className="text-xl text-gray-600">
                    Phase 1.2: 공통 컴포넌트 개발 완료
                  </p>
                </div>

                <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
                  {/* Button 컴포넌트 */}
                  <div className="bg-white rounded-lg shadow-lg p-6">
                    <h2 className="text-2xl font-bold mb-4">1. Button</h2>

                    <div className="space-y-4">
                      <div>
                        <h3 className="text-sm font-semibold mb-2 text-gray-600">Variants</h3>
                        <div className="flex flex-wrap gap-2">
                          <Button variant="primary" size="sm">Primary</Button>
                          <Button variant="secondary" size="sm">Secondary</Button>
                          <Button variant="danger" size="sm">Danger</Button>
                          <Button variant="outline" size="sm">Outline</Button>
                          <Button variant="ghost" size="sm">Ghost</Button>
                        </div>
                      </div>

                      <div>
                        <h3 className="text-sm font-semibold mb-2 text-gray-600">Sizes</h3>
                        <div className="flex flex-wrap items-center gap-2">
                          <Button size="sm">Small</Button>
                          <Button size="md">Medium</Button>
                          <Button size="lg">Large</Button>
                        </div>
                      </div>

                      <div>
                        <h3 className="text-sm font-semibold mb-2 text-gray-600">States</h3>
                        <div className="flex flex-wrap gap-2">
                          <Button disabled>Disabled</Button>
                          <Button fullWidth>Full Width</Button>
                        </div>
                      </div>
                    </div>
                  </div>

                  {/* Input 컴포넌트 */}
                  <div className="bg-white rounded-lg shadow-lg p-6">
                    <h2 className="text-2xl font-bold mb-4">2. Input</h2>

                    <div className="space-y-4">
                      <Input
                        label="기본 입력"
                        placeholder="텍스트를 입력하세요"
                        fullWidth
                      />

                      <Input
                        label="필수 입력"
                        type="email"
                        placeholder="이메일을 입력하세요"
                        required
                        fullWidth
                      />

                      <Input
                        label="유효성 검사"
                        value={inputValue}
                        onChange={handleInputChange}
                        error={inputError}
                        helperText="최소 3자 이상 입력해주세요"
                        fullWidth
                      />

                      <Input
                        label="비활성화"
                        value="비활성화된 입력"
                        disabled
                        fullWidth
                      />
                    </div>
                  </div>

                  {/* Select 컴포넌트 */}
                  <div className="bg-white rounded-lg shadow-lg p-6">
                    <h2 className="text-2xl font-bold mb-4">3. Select</h2>

                    <div className="space-y-4">
                      <Select
                        label="응원팀 선택"
                        options={TEAM_OPTIONS}
                        value={selectedTeam}
                        onChange={(e) => setSelectedTeam(e.target.value)}
                        placeholder="팀을 선택하세요"
                        fullWidth
                      />

                      <Select
                        label="필수 선택"
                        options={TEAM_OPTIONS}
                        required
                        fullWidth
                      />

                      <Select
                        label="비활성화"
                        options={TEAM_OPTIONS}
                        disabled
                        fullWidth
                      />

                      {selectedTeam && (
                        <div className="p-3 bg-blue-50 rounded-lg">
                          <p className="text-sm text-blue-800">
                            선택된 팀: <strong>{TEAM_OPTIONS.find(t => t.value === selectedTeam)?.label}</strong>
                          </p>
                        </div>
                      )}
                    </div>
                  </div>

                  {/* Loading 컴포넌트 */}
                  <div className="bg-white rounded-lg shadow-lg p-6">
                    <h2 className="text-2xl font-bold mb-4">4. Loading</h2>

                    <div className="space-y-6">
                      <div>
                        <h3 className="text-sm font-semibold mb-2 text-gray-600">Sizes</h3>
                        <div className="flex items-center gap-8">
                          <div>
                            <p className="text-xs text-gray-500 mb-2">Small</p>
                            <Loading size="sm" />
                          </div>
                          <div>
                            <p className="text-xs text-gray-500 mb-2">Medium</p>
                            <Loading size="md" />
                          </div>
                          <div>
                            <p className="text-xs text-gray-500 mb-2">Large</p>
                            <Loading size="lg" />
                          </div>
                        </div>
                      </div>

                      <div>
                        <h3 className="text-sm font-semibold mb-2 text-gray-600">With Text</h3>
                        <Loading text="로딩 중..." />
                      </div>
                    </div>
                  </div>

                  {/* Modal 컴포넌트 */}
                  <div className="bg-white rounded-lg shadow-lg p-6 lg:col-span-2">
                    <h2 className="text-2xl font-bold mb-4">5. Modal</h2>

                    <div className="space-y-3">
                      <Button onClick={() => setIsModalOpen(true)}>
                        모달 열기
                      </Button>

                      <Modal
                        isOpen={isModalOpen}
                        onClose={() => setIsModalOpen(false)}
                        title="모달 제목"
                        size="md"
                      >
                        <div className="space-y-4">
                          <p className="text-gray-600">
                            모달 컴포넌트 테스트입니다.
                          </p>
                          <p className="text-sm text-gray-500">
                            - ESC 키로 닫을 수 있습니다<br />
                            - 오버레이 클릭으로 닫을 수 있습니다<br />
                            - X 버튼으로 닫을 수 있습니다
                          </p>
                          <div className="flex gap-2 justify-end">
                            <Button variant="secondary" onClick={() => setIsModalOpen(false)}>
                              취소
                            </Button>
                            <Button variant="primary" onClick={() => setIsModalOpen(false)}>
                              확인
                            </Button>
                          </div>
                        </div>
                      </Modal>
                    </div>
                  </div>
                </div>

                {/* 완료 안내 */}
                <div className="mt-12 bg-green-50 border border-green-200 rounded-lg p-6">
                  <h3 className="text-lg font-bold text-green-800 mb-2">
                    ✅ Phase 1.2 완료!
                  </h3>
                  <p className="text-green-700">
                    모든 공통 컴포넌트가 성공적으로 구현되었습니다. 이제 Phase 1.3 (인증 시스템)으로 넘어갈 수 있습니다.
                  </p>
                </div>
              </div>
            }
          />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
