insert into site_user (PASSWORD, NICKNAME, EMAIL, PHONE_NUMBER, GENDER, NTRP, LOCATION_SI, LOCATION_GU, AGE_GROUP, CREATE_DATE)
values ('1234', 'nickname12', 'email@naver.com', '010-1234-5678', 'MALE', 3.1, '안양시', '동안구', 'TWENTIES', '2023-10-31 10:00:00');

INSERT INTO MATCHING (SITE_USER_ID, TITLE, CONTENT, LOCATION, LOCATION_IMG,
                      DATE, START_TIME, END_TIME, RECRUIT_DUE_DATE, RECRUIT_NUM, APPLY_NUM,
                      COST, NTRP, AGE, RECRUIT_STATUS, MATCHING_TYPE, CREATE_TIME)
            VALUES ('1', '주말 테니스 모임', '주말에 함께 테니스를 즐길 사람들을 모집합니다. 초보자도 환영합니다!', '서울시 강남구 테니스장', '',
                    '2023-12-10', '14:00:00', '16:00:00', '2023-12-05 05:00:00', '2', '1',
                    '50000','BEGINNER', 'TWENTIES', 'OPEN', 'SINGLE',  '2023-11-06 07:18:44');
                    
insert into matching (SITE_USER_ID, TITLE, CONTENT, LOCATION, DATE, START_TIME, END_TIME, RECRUIT_NUM, COST, IS_RESERVED, NTRP, AGE, RECRUIT_STATUS, CREATE_TIME, MATCHING_TYPE)
values (1, 'oo구장 경기 모집', '같이 경기해요!', '구장 위치', '2023-11-10', '15:00:00', '17:00:00', 2, 20000, 1, '3.0~4.0', '10~40', 'OPEN', '2023-10-31 10:00:00', 'SINGLE');
