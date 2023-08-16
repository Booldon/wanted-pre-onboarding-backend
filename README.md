# wanted-pre-onboarding-backend
원티드 프리온 보딩 과제

지원자  : 정병국

데이터 베이스 테이블 구조 :

![assignment_Entity](https://github.com/Booldon/wanted-pre-onboarding-backend/assets/99729203/848281cc-13bf-48a1-89e6-32de0ebd15fb)

구현 방법 및 이유 : 해당 애플리케이션은 JAVA/Spring Boot를 사용하여 개발하였으며, 

Spring Security-jwt를 통해 로그인 시 클라이언트에게 jwt를 반환하게 하였습니다.

반환한 jwt를 서버에서 판별하여 게시글의 수정 및 삭제의 권한을 부여하는 방식으로 구현하였습니다.


게시글의 pagenation기능은 JpaRepostitory의 pageable기능을 사용하여 구현하였습니다.

게시글의 수정 및 삭제의 권한을 판별할때는 로그인시 SecurityContext에 등록된 사용자의 Autorization을 get하여 게시글 작성자의 ID를 비교하도록 하였습니다.

API 명세 : https://documenter.getpostman.com/view/26437760/2s9Y5QzkjJ
