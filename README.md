## 실행 방법

전체 소스코드 풀 받아서 실행 하거나

docker Compose 로 빌드 한 Docker Image 풀받을걸로 실행 하면됨

실행 방법 

1. myapp.tar 를 다운로드
2. 아래의 명령어로 터미널에서 다음 명령어를 입력하여 이미지를 로드
 - docker load -i myapp.tar
3. 아래의 명령어로 이미지를 기반으로 컨테이너 실행
 - docker run -d --name myapp_container myapp:latest
4. 실행
 - docker-compose up

참고:
현재 DB(mysql) 은 이미지안에 포함 되어있으므로 별도로 빌드및 이미지화 진행은 안해도 됨
