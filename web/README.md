feat: 새로운 기능을 추가
fix: 버그 수정
design: CSS 등 사용자 UI 디자인 변경
style: 코드 포맷 변경, 세미 콜론 누락, 코드 수정이 없는 경우
refactor: 프로덕션 코드 리팩토링
comment: 필요한 주석 추가 및 변경
docs: 문서 수정
test: 테스트 코드, 리팩토링 테스트 코드 추가, 프로덕션 코드 변경 없음
build: 빌드 업무 수정, 패키지 매니저 수정, 패키지 관리자 구성 등 업데이트, 프로덕션 코드 변경 없음
rename: 파일 혹은 폴더명을 수정하거나 옮기는 작업만 수행한 경우
remove: 파일을 삭제하는 작업만 수행한 경우
backup: 코드 백업하는 경우

---

Branch Naming
- `master`
    - default 브랜치
- `develop`
    - 개발 브랜치
- `feature`
- `release`
- `hotfixs`

Branch Naming 예시
feature/S09P31C108-112-login_form

---

feat(user): 회원 가입 기능 구현
SNS 회원가입 연동
Adds #S09P31C108-158
Closes #S09P31C108-001

---

git fetch upstream
git merge upstream/develop
git push origin develop
