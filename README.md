# 음악 서비스

## 개발 환경

- Java 21
- Spring Boot Webflux 3.5.3
- Spring Data R2DBC 3.5.3
- PostgreSQL 16.0
- Gradle 8.14.2

## 프로젝트 구조

- domain : 도메인 모델과 리포지토리 인터페이스
- dataloder : 데이터 로더를 통한 초기 데이터 삽입
- api : REST API 구현

## 실행방법

### 초기 데이터 관리

1. postgreSQL 데이터베이스를 실행합니다.
    1. docker 폴더 안의 docker-compose.yml 파일을 사용하여 PostgreSQL 컨테이너를 실행합니다.
        - `docker compose up -d` 명령어를 사용합니다.
    2. docker 폴더 안의 initial_ddl.sql 파일을 사용하여 초기 데이터베이스 스키마를 생성합니다.
        - `docker exec -i <container_id> psql -U postgres -f /docker-entrypoint-initdb.d/initial_dml.sql` 명령어를 사용합니다.
    3. docker-compose 를 사용하지 않는 경우, PostgreSQL의 url, username, password 를 application.yml 파일에 설정합니다.
2. 데이터를 다운로드 받습니다.
    1. https://www.kaggle.com/datasets/devdope/900k-spotify?select=900k+Definitive+Spotify+Dataset.json 에서 다운 받을 수 있습니다.
        1. `curl -L -o dataloader/src/main/resources/data/900k-spotify.zip https://www.kaggle.com/api/v1/datasets/download/devdope/900k-spotify` 명령어를 사용하여 다운로드 합니다.
    2. 다운로드 받은 zip 파일을 압축 해재 후 `900k Definitive Spotify Dataset.json` 파일을 `dataloader/src/main/resources/data` 폴더에 위치시킵니다.
3. dataloader 어플리케이션을 실행합니다.
    - `./gradlew :dataloader:bootRun --args='"data/900k Definitive Spotify Dataset.json"'` 명령어를 사용하여 실행합니다.
