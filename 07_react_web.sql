-- 회원 관리 테이블

create table tbl_member (
    member_id varchar2(20) primary key,
    member_pw char(60) not null,
    member_name varchar2(30) not null,
    member_phone char(13) not null,
    member_level number
);

select * from tbl_member;

-- 게시판 테이블

CREATE TABLE TBL_BOARD (
    BOARD_NO NUMBER PRIMARY KEY,            -- 게시글 번호
    BOARD_TITLE VARCHAR2(300) NOT NULL,     -- 게시글 제목
    BOARD_THUMB VARCHAR2(300),              -- 썸네일 이미지 서버 저장 파일명
    BOARD_CONTENT VARCHAR2(3000) NOT NULL,  -- 게시글 내용
    BOARD_WRITER VARCHAR2(20) REFERENCES TBL_MEMBER(MEMBER_ID) ON DELETE CASCADE, -- 작성자 아이디
    BOARD_STATUS NUMBER,                    -- 공개, 비공개
    BOARD_DATE DATE                         -- 게시글 작성일
);

CREATE SEQUENCE SEQ_BOARD;

-- 파일 테이블

CREATE TABLE TBL_BOARD_FILE (
    BOARD_FILE_NO NUMBER PRIMARY KEY, -- 파일 번호
    BOARD_NO NUMBER REFERENCES TBL_BOARD(BOARD_NO) ON DELETE CASCADE,
    FILE_NAME VARCHAR2(300),
    FILE_PATH VARCHAR2(300)    
);

CREATE SEQUENCE SEQ_BOARD_FILE;


insert into tbl_board values (seq_board.nextval, '제목'||seq_board.currval, null, '내용'||seq_board.currval, 'aaaaaaaa', 2, sysdate);

commit;

select * from tbl_member;
select * from TBL_BOARD;
select * from TBL_BOARD order by board_no desc;
select * from tbl_board_file;

