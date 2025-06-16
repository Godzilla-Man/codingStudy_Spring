CREATE USER spring_test IDENTIFIED BY 1234;

GRANT CONNECT, RESOURCE TO SPRING_TEST;

CREATE TABLE TBL_MEMBER (
    MEMBER_NO NUMBER PRIMARY KEY,
    MEMBER_ID VARCHAR2(30) UNIQUE NOT NULL,
    MEMBER_PW VARCHAR2(30) NOT NULL,
    MEMBER_NAME VARCHAR2(30) NOT NULL,
    MEMBER_EMAIL VARCHAR2(200) NOT NULL,
    MEMBER_PHONE CHAR(13) NOT NULL,
    MEMBER_ADDR VARCHAR2(200),
    MEMBER_LEVEL NUMBER DEFAULT 3,
    ENROLL_DATE DATE NOT NULL
);

CREATE SEQUENCE SEQ_MEMBER;

alter table tbl_member modify member_pw char(60);

delete from tbl_member;
commit;

SELECT * FROM TBL_MEMBER;

update tbl_member
   set member_level = 1
 where member_id = 'a';
 
 commit;
 
 update tbl_member
   set member_addr = '부산'
 where member_id = 'bb';
 
 commit;
 
CREATE TABLE TBL_LEVEL (
    level_code number primary key,
    level_name varchar2(30) not null
);

insert into tbl_level values (1, '관리자');
insert into tbl_level values (2, '정회원');
insert into tbl_level values (3, '준회원');

commit;

select * from tbl_level;
select * from tbl_member;

-- 게시판 테이블
CREATE TABLE TBL_NOTICE (
    NOTICE_NO NUMBER PRIMARY KEY,           -- 게시글 번호
    NOTICE_TITLE VARCHAR2(300) NOT NULL,    -- 게시글 제목
    NOTICE_CONTENT VARCHAR2(1000) NOT NULL, -- 게시글 내용
    NOTICE_WRITER NUMBER REFERENCES TBL_MEMBER(MEMBER_NO) ON DELETE CASCADE, -- 작성자(회원번호). 회원 정보 삭제 시, 해당 회원이 작성한 게시글도 자동 삭제 처리
    NOTICE_DATE DATE DEFAULT SYSDATE NOT NULL,  -- 게시글 작성일 (기본 값은 오늘 날짜)
    READ_COUNT NUMBER DEFAULT 0    -- 조회수 (기본값: 0)
);

-- 게시글 번호 생성 시퀀스
CREATE SEQUENCE SEQ_NOTICE;

-- 게시판 파일 테이블
CREATE TABLE TBL_NOTICE_FILE (
    FILE_NO NUMBER PRIMARY KEY, -- 파일 번호
    NOTICE_NO NUMBER REFERENCES TBL_NOTICE(NOTICE_NO) ON DELETE CASCADE,    -- 어떤 게시글의 파일인지! 게시글 번호. 게시글 삭제 시 파일 정보도 자동 삭제
    FILE_NAME VARCHAR2(100) NOT NULL,   -- 파일명 (사용자가 업로드한 실제 파일명)
    FILE_PATH VARCHAR2(100) NOT NULL   -- 파일명 (서버에 저장할 파일명. 중복 방지 차원)
);

-- 파일 번호 시퀀스
CREATE SEQUENCE SEQ_NOTICE_FILE;

INSERT INTO TBL_NOTICE VALUES (SEQ_NOTICE.NEXTVAL, '제목'||SEQ_NOTICE.CURRVAL, '내용'||SEQ_NOTICE.CURRVAL, 14, SYSDATE, 0);
INSERT INTO TBL_NOTICE VALUES (SEQ_NOTICE.NEXTVAL, '제목'||SEQ_NOTICE.CURRVAL, '내용'||SEQ_NOTICE.CURRVAL, 22, SYSDATE, 0);
INSERT INTO TBL_NOTICE VALUES (SEQ_NOTICE.NEXTVAL, '제목'||SEQ_NOTICE.CURRVAL, '내용'||SEQ_NOTICE.CURRVAL, 21, SYSDATE, 0);

COMMIT;

select * from tbl_notice;


select*
  from(
select rownum rnum, a.*
  from
(
select a.*
  from tbl_notice a
order by notice_writer
) a
) a 
where rnum between 41 and 50;