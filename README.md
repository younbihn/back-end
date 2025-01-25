# Racket Puncher
라켓 펀처는 20-30대를 중심으로 빠르게 성장하는 테니스 인기를 반영하여 기존 매칭 플랫폼들과
차별화된 기능을 제공하는 새로운 테니스 경기 매칭 플랫폼입니다.

## ✅ 라켓 펀처의 차별화된 기능
### 👇원클릭 매칭 신청
매칭 등록자에게 메시지를 보내는 번거로움 없이 버튼 클릭만으로 원클릭 참가 신청을 해보세요!

### 🗺 지도 기반 내 주변 매칭 조회
지도 기반 매칭 조회 시스템을 통해 직관적인 시각화 서비스를 제공합니다!

### 👀 경기장 예약 여부를 알 수 있는 매칭 플랫폼
매칭 등록시 경기장 예약 여부를 명기하기 때문에 해당 매칭의 경기장 예약 여부를 알 수 있어요!

### 📢 패널티 시스템
참여자의 무분별한 이탈 혹은 경기 주최자의 무분별한 매칭글 삭제/수정을 방지하기 위한 패널티 시스
템을 제공합니다!

### ☔ 우천 알림 기능
우천시 경기 취소가 가능하도록 경기 당일 우천이 예상되면 당일 경기 취소가 가능해지며, 경기 참여
자들에게 우천 알림이 보내집니다! 

![image](https://github.com/user-attachments/assets/f39041d7-96f0-458e-a160-0c0bf474fd8e)

![image](https://github.com/user-attachments/assets/5af26775-f0dd-4d9e-b322-8b683b5ab2c0)

### Tech Stack
- Language: Java 17
- Framework: Spring Boot (3.1.5)
- Build Tool: Gradle
- Deploy Tool: AWS EC2
- Auth: Spring security, JWT, OAuth 2.0 (Kakao)
- DB: MySQL
- Image Storage: S3
- Temporary data storage/caching : Redis
- Test : Postman, Junit, Mockito
- Weather Open API: Korea Meteorological Administration Short-Term Forecast Retrieval API
- Address Open API: Ministry of the Interior and Safety Address API
- HTTP Client Tool: OpenFeign
- Json Parsing: Custom serializer extends by JsonSerializer
- CI/CD tools: Docker, Jenkins
