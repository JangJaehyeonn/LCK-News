
![image](https://github.com/hanraeul/LCK-News/assets/98158546/07265021-0b13-4f84-8f08-9ed620c1411e) 
# LCK-News
## 목차

[1. 필수 구현 기능]( #1-필수-구현-기능 )

[2. 와이어 프레임]( #2-와이어-프레임 )

[3. API 명세서]( #3-api-명세서 )

[3. ERD]( #4-erd)

## 1. 필수 구현 기능
- **사용자 인증 기능**
    - 회원가입, 회원탈퇴, 로그인, 로그아웃
- **프로필 관리 기능**
    - 프로필 조회, 프로필 수정
- **뉴스피드 게시물 CRUD 기능**
    - 게시물 작성, 조회, 수정, 삭제

### 공통 조건
- 예외처리는 아래와 같은 형태로 처리하여 `Response` 합니다.


    | Http Status Code | Message |
    | --- | --- |
    | 400 | 잘못된 요청입니다. |
- Status Code 분류는 [링크](https://hongong.hanbit.co.kr/http-상태-코드-표-1xx-5xx-전체-요약-정리)를 참고합니다.
- 모든 엔티티에는 `생성일자`와 `수정일자`가 존재합니다.
- 클라이언트는 Postman이고 프론트엔드는 별도 구현하지 않습니다.
    - 추가 구현 단계에서 Swagger만 적용합니다.

### 사용자 인증 기능 - 회원 가입, 회원 탈퇴, 로그인, 로그아웃
- **사용자 Entity & Status**


    | 회원 | 타입 |
    | --- | --- |
    | ID | bigint |
    | 사용자 ID | varchar |
    | 비밀번호 | varchar |
    | 이름 | varchar |
    | 이메일 | varchar |
    | 한 줄 소개 | varchar |
    | 회원상태코드 | varchar |
    | refresh token | varchar |
    | 상태변경시간 | timestamp |
    | 생성일자 | timestamp |
    | 수정일자 | timestamp |
    
    | 회원상태코드 |
    | --- |
    | 정상 |
    | 탈퇴 |

- **사용자 인증 기능 공통 조건**
    - **Spring Security**와 **JWT**를 사용하여 설계 및 구현합니다.
    - JWT는 **Access Token**, **Refresh Token**을 구현합니다.
    - Access Token 만료 시 :  **유효한 Refresh Token**을 통해 새로운 Access Token과 Refresh Token을 발급
    - Refresh Token 만료 시 : **재로그인**을 통해 새로운 Access Token과 Refresh Token을 발급
    - API를 요청할 때는 Access Token을 사용합니다.
  - **회원가입 기능**

    신규 가입자는 `사용자 ID`, `비밀번호`를 입력하여 서비스에 가입할 수 있습니다.

      - 사용자 ID
          - 중복된 ID, 탈퇴한 ID로는 회원가입 할 수 없습니다.
          - 대소문자 포함 영문 + 숫자만을 허용합니다.
          - 사용자 ID는 최소 10글자 이상, 최대 20글자 이하여야 합니다.
      - 비밀번호
          - `Bcrypt`로 단방향-인코딩합니다.
          - 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함합니다.
          - 비밀번호는 최소 10글자 이상이어야 합니다.
      - ⚠️ 필수 예외처리
          - 중복된 `사용자 ID`로 가입하는 경우
          - `사용자 ID` 비밀번호 형식이 올바르지 않은 경우

- **회원탈퇴 기능**

  회원탈퇴는 가입된 사용자의 **회원 상태**를 변경하여 탈퇴처리 합니다.

  탈퇴 처리 시 `비밀번호`를 확인한 후 일치할 때 탈퇴처리 합니다.

    - 조건
        - 탈퇴한 사용자 ID는 재사용할 수 없고, 복구할 수 없습니다.
        - 탈퇴처리된 사용자는 **재탈퇴** 처리가 불가합니다.
    - ⚠️ 필수 예외처리
        - `사용자 ID`와 `비밀번호`가 일치하지 않는 경우
        - 이미 탈퇴한 `사용자 ID`인 경우

- **로그인 기능**

  사용자는 자신의 계정으로 서비스에 **로그인**할 수 있습니다.

    - 조건
        - 로그인 시 클라이언트에게 토큰을 발행합니다.


            | 토큰 종류 | 만료기간 |
            | --- | --- |
            | Access Token | 30분 |
            | Refresh Token | 2주 |
        - 회원가입된 사용자 ID와 비밀번호가 일치하는 사용자만 로그인할 수 있습니다.
        - 로그인 성공 시, **header**에 토큰을 추가하고 성공 상태코드와 메세지를 반환합니다.
        - 탈퇴했거나 로그아웃을 한 경우, `Refresh Token`이 유효하지 않은 상태가 되어야합니다.
    - ⚠️ 필수 예외처리
        - 유효하지 않은 사용자 정보로 로그인을 시도한 경우
            
            ex. 회원가입을 하지 않거나 회원 탈퇴한 경우
            
        - `사용자 ID`와 `비밀번호`가 일치하지 않는 사용자 정보로 로그인을 시도한 경우
- **로그아웃 기능**

  사용자는 로그인 되어 있는 본인의 계정을 **로그아웃** 할 수 있습니다.

    - 조건
        - 로그아웃 시, 발행한 토큰은 **초기화** 합니다.
        - 로그아웃 후 초기화 된 `Refresh Token`은 재사용할 수 없고, 재로그인해야 합니다.

### 프로필 관리 기능 - 프로필 조회, 프로필 수정
- **프로필 조회 기능**
    - **사용자 ID, 이름, 한 줄 소개, 이메일**을 볼 수 있습니다.
    - **ID(사용자 ID X), 비밀번호, 생성일자, 수정일자**와 같은 데이터는 노출하지 않습니다.


      - **프로필 수정 기능**

        로그인한 사용자는 본인의 사용자 정보를 수정할 수 있습니다.

        | 수정 가능한 사용자 정보 |
        | --- |
  -     | 이름 |
        | 이메일 |
        | 한 줄 소개 |
        | 비밀번호 |
      - 비밀번호 수정 조건
          - 비밀번호 수정 시, 본인 확인을 위해 현재 비밀번호를 입력하여 올바른 경우에만 수정할 수 있습니다.
          - 현재 비밀번호와 동일한 비밀번호로는 변경할 수 없습니다.
      - ⚠️ 필수 예외처리
          - 비밀번호 수정 시, 본인 확인을 위해 입력한 현재 비밀번호가 일치하지 않은 경우
          - 비밀번호 형식이 올바르지 않은 경우
          - 현재 비밀번호와 동일한 비밀번호로 수정하는 경우
### 뉴스피드 게시물 CRUD 기능 - 게시물 작성/조회/수정/삭제, 뉴스피드 조회 
- **뉴스피드 Entity**


    | 뉴스피드 | 타입 |
    | --- | --- |
    | ID | bigint |
    | 작성자 ID | bigint |
    | 내용 | longText |
    | 생성일자 | timestamp |
    | 수정일자 | timestamp |
- **게시물 작성, 조회, 수정, 삭제 기능**

  게시물 조회는 모든 사용자가 조회할 수 있습니다.

    - 조건
        - 게시물 작성, 수정, 삭제는 **인가(Authorization)**가 필요합니다.
        - 유효한 JWT 토큰을 가진 작성자 본인만 처리할 수 있습니다.
    - ⚠️ 필수 예외처리
        - 작성자가 아닌 다른 사용자가 게시물 작성, 수정, 삭제를 시도하는 경우

- **뉴스피드 조회 기능**

  모든 사용자가 전체 뉴스피드 데이터를 조회할 수 있습니다.

    - 조건
        - 모든 사용자는 전체 뉴스피드를 조회할 수 있습니다.
        - 기본 정렬은 **생성일자 기준으로 최신순**으로 정렬합니다.
        - 뉴스피드가 없는 경우, 아래와 같이 반환합니다.
---


프로필 조회 테스트 (ProfileServiceTest)
![profileTest](https://github.com/JangJaehyeonn/LCK-News/assets/96277705/baa766cf-7135-484a-8c85-fd220a857127)

---

로그아웃 테스트 (UserServiceTest)
![UserServiceTest](https://github.com/JangJaehyeonn/LCK-News/assets/96277705/9ce18c87-44ab-4e60-b391-bb1389551c6f)

---

게시물 작성 테스트 (PostServiceTest)
![PostServiceTest](https://github.com/JangJaehyeonn/LCK-News/assets/96277705/38535408-2c8d-4933-b1ae-e33b9c247525)



