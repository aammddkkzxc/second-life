# 프로젝트 : Second - Life
<br/>이스트소프트에서 주관하는 부트캠프(명 : 오르미)에서 진행된 사이드 프로젝트 입니다.
<br/>Spring Boot를 활용하여 웹 커뮤니티 사이트를 구현하였습니다.
<br/>배포 URL : http://3.37.13.117:8080/


## 👨‍🏫 프로젝트 소개
- 대은퇴 시대, 이직이 잦은 문화, 청년 실업자 등등 무직자를 위한 커뮤니티입니다.
- 다양한 연령층의 공통 관심사가 될 수 있는 무직 상태 에서의 고단한 일상/재취업/자산 관리 등의 컨텐츠를 공유합니다.
- 인증된 사용자들에 한하여 지역모임 컨텐츠를 제공합니다.
- 궁극적으로 사회적 고립, 혐오 문화, 세대 간의 갈등의 문제를 소통을 통하여 해결하도록 독려합니다.

## 🧑‍🤝‍🧑 팀 구성
- **박준석**
- **김상형**
- **이지현**

## ⏲️ 개발 기간, 과정
- 2024.3.25(월) ~ 2023.04.5(금)
- 요구사항 분석
- 화면 구성 초안
- 데이터베이스 모델링 설계
- 구현
- 화면 디자인 완성
- 테스트
- 배포
- 발표 준비 

## ⚙️ 기술 스택 및 개발 환경
- **Language** : Java 17
- **Framework** : SpringBoot 3.2.3
- **Server** : AWS EC2
- **DataBase** : AWS RDS - MySQL
- **ORM** : JPA, Spring data JPA, QueryDsl
- **WS/WAS** : Tomcat
- **회의** : Discord, Zoom, Notion


## 📝 프로젝트 아키텍쳐
- ERD설계화면
 ![erd](https://github.com/aammddkkzxc/second-life/assets/105401500/76649f40-cac2-40be-937b-d8a9ddaf2efa)


## 📌 주요 기능
### 회원
  - [x] 회원 가입
    - [x] 회원 가입 필요사항
      - [x] 아이디 : 20자 이상 불가, 이미 존재하는 아이디 중복 불가, 필수 입력
      - [x] 비밀번호 : 필수 입력
      - [x] 닉네임 : 20자 이상 불가, 이미 존재하는 닉네임 중복 불가, 필수 입력
      - [x] 생일 : 필수 입력
      - [x] 지역 : 필수 입력
      - [x] 자기소개 : 선택 입력
      - [x] 회원 등급 : 회원 가입 시 일반 회원(L1) 자동 부여
  - [x] 로그인
  - [x] 로그 아웃
  - [x] 회원 탈퇴
  - [x] 회원 등급 
    - [x] 일반 회원(L1), 인증 회원(L2), 관리자(ADMIN)
      - [x] 관리자(ADMIN) : /admin 페이지에서 닉네임으로 회원 조회
      - [x] 관리자(ADMIN) : /admin 페이지에서 회원 등급 변경 가능
  - [x] 회원 정보
    - [x] 닉네임 / 지역 / 회원 등급 / 생일 / 이메일 인증여부 / 자기소개 게시
    - [x] 작성 게시물 수 / 작성 댓글 수 게시
    - [x] 본인이 작성한 게시물들 로딩, 해당 게시글로 이동 가능
    - [x] 회원 정보 수정
      - [x] 이메일 인증, 이메일 인증을 한다면 인증을 한다면 일반 회원에서 인증 회원으로 승격
      - [x] 회원 정보 수정 버튼을 통하여 정보 수정 가능
        - [x] 닉네임/비밀번호/지역/생년월일/자기소개 를 수정. 원하는 수정 사항만 기입하여 수정
### 게시글
  - [x] 작성
    - [x] 작성 권한
      - [x] 일반 회원 : 자유 게시판 가능
      - [x] 인증 회원 : 자유 게시판 + 지역 게시판 가능
    - [x] 생성시간, 수정시간 표시
    - [x] 인증회원은 자유 게시판과 지역 게시판 중 선택하여 작성
  - [x] 조회
    - [x] 조회 권한
      - [x] 비 회원 : 모든 게시글 조회 불가, 로그인 화면으로 이동
      - [x] 일반 회원 : 메인(인기글 포함) + 자유 게시판
      - [x] 인증 회원 : 메인(인기글 포함) + 자유 게시판 + 지역 게시판
      - [x] ADMIN : 모든 페이지(관리자 전용 페이지 포함), 모든 글 조회
      - [x] 권한 없는 회원이 조회 시도 시 권한 없음을 게시
    - [x] 조회 수
      - [x] 자정 기준 하루 동안 하나의 게시글에 대해 한 회원당 한 번의 조회수 증가
    - [x] 페이징
      - [x] 자유게시판, 지역게시판
  - [x] 수정
    - [x] 수정 권한 : 본인
    - [x] 자유 게시판, 지역 게시판 수정
    - [x] 게시글 상세 - 수정 시간 표시
  - [x] 삭제
    - [x] 삭제 권한 : 본인
  - [x] 게시판
    - [x] 메인페이지 인기 게시판
      - [x] 모든 게시글 중 게시글 추천 수 비교, 동률 시 게시글 조회 수 비교, 동률 시 댓글 수 비교 하여 내림차순 정렬
    - [x] 자유 게시판, 지역 게시판
      - [x] 최신 작성 게시글일 수록 상단에 위치
    - [x] 지역 게시판 조회 특수 기능
      - [x] 지역 버튼 클릭하여 해당 지역에 속하는 지역 게시판 게시글만 조회 가능
### 댓글
  - [x] 작성
    - [x] 작성 권한
      - [x] 일반 회원 : 메인(인기글 포함) + 자유 게시판
      - [x] 인증 회원 : 메인(인기글 포함) + 자유 게시판 + 지역 게시판
      - [x] 생성 시간 표시
  - [x] 조회
    - [x] 최신 작성 댓글일 수록 상단에 위치
  - [x] 수정
    - [x] 수정 권한 : 본인
    - [x] 수정 시간 표시
  - [x] 삭제
    - [x] 삭제 권한 : 본인
### 추천
  - [x] 게시글 추천
    - [x] 한 회원은 하나의 게시글에 대해 한번만 추천 가능
    - [x] 추천 버튼 클릭하여 추천
    - [x] 버튼 재 클릭 시 추천 내역 검증 후 추천 취소
  - [x] 댓글 추천
    - [x] 한 회원은 하나의 댓글에 대해 한번만 추천 가능 
    - [x] 추천 버튼 클릭하여 추천
    - [x] 버튼 재 클릭 시 추천 내역 검증 후 추천 취소
       

### 댓글 봇
  - [x] 게시글이 등록되면 댓글 봇이 게시글의 내용을 확인하고 그에 맞는 댓글을 작성.
  - [x] 게시글이 수정되면 댓글 봇이 게시글의 내용을 확인하고 그에 맞는 댓글을 작성.

### 인증
  - [x] 회원 이메일 인증을 요청 ->
        users Table 에 입력받은 이메일의 인증 내역이 없다면,
        인증 코드 만들어 입력받은 이메일 주소로 전송하고
        Verification Table 에 해당 회원의 PK, 인증 코드, 만료일을 저장.
  - [x] 코드 확인 버튼 클릭 ->
        입력받은 인증 코드가 유효한지 확인하고, 유효하다면 이메일 인증을 활성화.
        회원 등급이 인증 회원으로 바뀌고, 회원 정보에 이메일이 저장 + 인증 여부 true로 변경.

### AOP
  - [x] 특정 패키지 하위 메소드 성공 여부 로깅, 실행 시간 측정 로깅
  - [x] 특정 패키지 하위 메소드 시작 여부 종료 여부 로깅


        

        
       


