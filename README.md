### DB 연동 관련 내용



Application.properties

```java
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/fantasyshop
spring.datasource.username=root
spring.datasource.password=root1234!!

spring.jpa.database=mysql
spring.jpa.hibernate.ddl-auto=update
```



현재 작성된 Application.properties에 맞게 MySQL에 fantasyshop 데이터베이스를 생성하고, root 계정을 만들어야 한다.



#### MacOS

MySQL 설치 명령어

``````
brew services start mysql
``````



첫 설치후에는 root계정이 비밀번호가 없다. 비밀번호 없는 root 계정으로 MySQL 접속

``````
mysql -u root
``````



아래와 같이 명령어가 바뀌어있으면 정상적으로 MySQL에 접속이 된 것이다.

``````bash
idonghyeon@DH-MacMini-2 supercoding % mysql -u root
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 9
Server version: 9.3.0 Homebrew

Copyright (c) 2000, 2025, Oracle and/or its affiliates.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> 
``````



이제  root 계정에 비밀번호를 root1234!!로 설정하겠다.

``````sql
ALTER USER 'root'@'localhost' IDENTIFIED BY 'root1234!!';
FLUSH PRIVILEGES;
``````



정상적으로 진행되었다면 quit 명령어를 입력하면 다시 MySQL에서 빠져나와질것이다.

그리고 root 계정에 대하여 정상적으로 비밀번호가 설정 되었는지 확인해보자. 

아래의 명령어를 입력한다

``````bash
mysql -u root -p
``````



그럼 아래와 같이 비밀번호가 입력되는 곳이 나오는데, 입력을 해도 아무 변화가 없는 것이 정상이다.

``````
idonghyeon@DH-MacMini-2 aws % mysql -u root -p 
Enter password: 
``````



root1234!!를 입력 후 엔터를 친다. 그리고 정상적으로 접속이 되었다면 아래의 화면이다.

``````
idonghyeon@DH-MacMini-2 aws % mysql -u root -p
Enter password: 
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 12
Server version: 9.3.0 Homebrew

Copyright (c) 2000, 2025, Oracle and/or its affiliates.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> 

``````



이제 fantasyshop 데이터베이스를 만들 명령어를 입력한다.

``````
CREATE DATABASE fantasy CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
``````



#### 위 명령어의 의미 (ChatGPT 답변)

##### CHARACTER SET utf8mb4

- 문자 집합(Character Set)을 지정합니다.

- `utf8mb4`는 MySQL에서 **유니코드 문자(이모지 포함)를 완전하게 저장할 수 있는 문자 인코딩**입니다.

  - 기존의 `utf8`은 최대 3바이트까지만 인코딩 → 일부 이모지나 특수 문자는 저장 불가

  - `utf8mb4`는 최대 4바이트까지 인코딩 → 모든 유니코드 문자 지원

- 요즘은 `utf8mb4`를 기본으로 사용하는 것이 권장됩니다.



##### COLLATE utf8mb4_general_ci

- **정렬 방식(Collation)** 을 지정합니다.
- 이건 "문자들을 어떻게 비교하고 정렬할 것인가?"를 결정합니다.
- `utf8mb4_general_ci`는 다음과 같은 특징이 있습니다:
  
  - **ci = case-insensitive**: 대소문자 구분 안 함 (`abc`와 `ABC`는 같다고 봄)
  - 비교와 정렬이 빠르지만, 언어별로 완전히 정확하지 않을 수도 있음
  
  

이제 프로젝트를 실행하면 정상적으로 실행이 될 것이다.
