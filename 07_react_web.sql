-- ȸ�� ���� ���̺�

create table tbl_member (
    member_id varchar2(20) primary key,
    member_pw char(60) not null,
    member_name varchar2(30) not null,
    member_phone char(13) not null,
    member_level number
);

select * from tbl_member;

-- �Խ��� ���̺�

CREATE TABLE TBL_BOARD (
    BOARD_NO NUMBER PRIMARY KEY,            -- �Խñ� ��ȣ
    BOARD_TITLE VARCHAR2(300) NOT NULL,     -- �Խñ� ����
    BOARD_THUMB VARCHAR2(300),              -- ����� �̹��� ���� ���� ���ϸ�
    BOARD_CONTENT VARCHAR2(3000) NOT NULL,  -- �Խñ� ����
    BOARD_WRITER VARCHAR2(20) REFERENCES TBL_MEMBER(MEMBER_ID) ON DELETE CASCADE, -- �ۼ��� ���̵�
    BOARD_STATUS NUMBER,                    -- ����, �����
    BOARD_DATE DATE                         -- �Խñ� �ۼ���
);

CREATE SEQUENCE SEQ_BOARD;

-- ���� ���̺�

CREATE TABLE TBL_BOARD_FILE (
    BOARD_FILE_NO NUMBER PRIMARY KEY, -- ���� ��ȣ
    BOARD_NO NUMBER REFERENCES TBL_BOARD(BOARD_NO) ON DELETE CASCADE,
    FILE_NAME VARCHAR2(300),
    FILE_PATH VARCHAR2(300)    
);

CREATE SEQUENCE SEQ_BOARD_FILE;


insert into tbl_board values (seq_board.nextval, '����'||seq_board.currval, null, '����'||seq_board.currval, 'aaaaaaaa', 2, sysdate);

commit;

select * from tbl_member;
select * from TBL_BOARD;
select * from TBL_BOARD order by board_no desc;
select * from tbl_board_file;

