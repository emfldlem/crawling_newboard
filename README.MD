<H1>
루리웹 핫딜 게시판 , 뽐뿌 게시판 신규 게시글 알람 서비스
</H1>

ㅁ 개발 환경
  - 스프링 부트
  - JSON 웹 크롤링
  - FILE READ, CREATE
  - Gmail SMTP 사용
  
  
ㅁ 설명
  - MainController
    -> web1
      1. 메인 함수로써, URL 설정 된 값에서 JSOUP를 이용하여 DOM를 가져오고, 원하는 요소(table_body)를 Elements로 가져온다.
      2. Elements 의 자식 들을 상황에 맞게 가공한다. 여기서는 공지사항부분 제거, 최신 상위 글 5개 추출 작업을 진행
      3. 각 각 Elements에서 게시글의 아이디를 추출하고,
       외부 저장된 id.txt의 값을 가져와서(readFileId()) 비교하여 최신 글인지 판명
      4. 최신글일 경우 GmailSend()를 이용하여 Gmail SMTP로 메일 전송
      5. 그 후 id.txt 파일의 내용을 최신 게시글 id로 변경
      6. 1~5의 작업을 1분마다 진행
      
      ※ 게시글 아이디로 최신글이 아닐 경우 메일 전송 스킵
      
      
  
ㅁ Mail (javax.mail jar를 이용)
  - MyAuthentication
    1. 구글 id, 비밀번호를 입력 하여 사용자 인증
    
  - GmailSend
    1. 수신자, 내용, 제목 의 변수 값을 파라미터로 받음
    2. 프로퍼티 설정으로 Gmail SMTP 설정 값 지정 
    3. Authenticator 를 MyAuthentication을 이용하여 사용자 인증을 받음
    4. 구글 인증이 된 세션을 얻은 뒤 MimeMessage 변수를 생성
    5. 그 뒤 전송시간, 송신자, 수신자, 제목, 내용 입력 후 파일 전송
    
    
    해당 부분은 블로그 http://gongcha.tistory.com/27 참조
    
      
    
  
    