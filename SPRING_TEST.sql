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
   set member_addr = '�λ�'
 where member_id = 'bb';
 
 commit;
 
CREATE TABLE TBL_LEVEL (
    level_code number primary key,
    level_name varchar2(30) not null
);

insert into tbl_level values (1, '������');
insert into tbl_level values (2, '��ȸ��');
insert into tbl_level values (3, '��ȸ��');

commit;

select * from tbl_level;
select * from tbl_member;

-- �Խ��� ���̺�
CREATE TABLE TBL_NOTICE (
    NOTICE_NO NUMBER PRIMARY KEY,           -- �Խñ� ��ȣ
    NOTICE_TITLE VARCHAR2(300) NOT NULL,    -- �Խñ� ����
    NOTICE_CONTENT VARCHAR2(1000) NOT NULL, -- �Խñ� ����
    NOTICE_WRITER NUMBER REFERENCES TBL_MEMBER(MEMBER_NO) ON DELETE CASCADE, -- �ۼ���(ȸ����ȣ). ȸ�� ���� ���� ��, �ش� ȸ���� �ۼ��� �Խñ۵� �ڵ� ���� ó��
    NOTICE_DATE DATE DEFAULT SYSDATE NOT NULL,  -- �Խñ� �ۼ��� (�⺻ ���� ���� ��¥)
    READ_COUNT NUMBER DEFAULT 0    -- ��ȸ�� (�⺻��: 0)
);

-- �Խñ� ��ȣ ���� ������
CREATE SEQUENCE SEQ_NOTICE;

-- �Խ��� ���� ���̺�
CREATE TABLE TBL_NOTICE_FILE (
    FILE_NO NUMBER PRIMARY KEY, -- ���� ��ȣ
    NOTICE_NO NUMBER REFERENCES TBL_NOTICE(NOTICE_NO) ON DELETE CASCADE,    -- � �Խñ��� ��������! �Խñ� ��ȣ. �Խñ� ���� �� ���� ������ �ڵ� ����
    FILE_NAME VARCHAR2(100) NOT NULL,   -- ���ϸ� (����ڰ� ���ε��� ���� ���ϸ�)
    FILE_PATH VARCHAR2(100) NOT NULL   -- ���ϸ� (������ ������ ���ϸ�. �ߺ� ���� ����)
);

-- ���� ��ȣ ������
CREATE SEQUENCE SEQ_NOTICE_FILE;

INSERT INTO TBL_NOTICE VALUES (SEQ_NOTICE.NEXTVAL, '����'||SEQ_NOTICE.CURRVAL, '����'||SEQ_NOTICE.CURRVAL, 14, SYSDATE, 0);
INSERT INTO TBL_NOTICE VALUES (SEQ_NOTICE.NEXTVAL, '����'||SEQ_NOTICE.CURRVAL, '����'||SEQ_NOTICE.CURRVAL, 22, SYSDATE, 0);
INSERT INTO TBL_NOTICE VALUES (SEQ_NOTICE.NEXTVAL, '����'||SEQ_NOTICE.CURRVAL, '����'||SEQ_NOTICE.CURRVAL, 21, SYSDATE, 0);

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