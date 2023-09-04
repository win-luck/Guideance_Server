# 오직 글에만 집중하려는 당신을 위한 선택, 가이던쓰✏️

(앱 실행 화면 추후 추가예정)

가이던쓰는 글짓기를 좋아하거나 글을 잘 쓰고 싶은 사람들이 오직 작문 실력에만 초점에 맞출 수 있는 작문 플랫폼입니다.

글을 작성하여 좋아요나 피드백을 받고, 관심있는 특정 태그를 구독하여 새로운 글에 대한 알림을 실시간으로 받아보세요!

주요 API 목록은 다음과 같습니다.

### ArticleController

![image](https://github.com/win-luck/Guideance_Server/assets/53044069/cb69b94e-64fd-46b1-897b-7644290d2c55)

- 게시물 작성/수정/삭제
- 댓글 작성/수정/삭제
- 게시물 좋아요/좋아요 취소
- 게시물 목록 조회
- 특정 게시물 조회
- 게시물 검색 결과 조회

### UserController

![image](https://github.com/win-luck/Guideance_Server/assets/53044069/02a21feb-bede-4020-b8e8-abec9ea3fbc0)
- 회원정보 조회/수정/탈퇴
- 회원별 고유 정보 조회: 좋아요 누른 게시물, 작성한 게시물, 작성한 댓글, 수신한 알림, 구독한 태그
- 알림 읽음/삭제

### TagController

![image](https://github.com/win-luck/Guideance_Server/assets/53044069/31c59d12-9b33-415b-9cc3-7842f71ecbf5)
- 태그 생성/삭제
- 태그 목록 조회
- 태그 검색 결과 조회
- 특정 태그를 구독하는 유저 목록 조회
- 특정 태그가 포함된 게시물 목록 조회
- 태그 구독/구독 취소

서버는 아래와 같은 프레임워크 및 tool로 구성되어 있습니다.
- Springboot 2.7.13 + Java 11
- mySQL 8.0.32 + JPA
- Redis 6.2.5
- Swagger 3.0.0
