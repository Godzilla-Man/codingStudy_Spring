create user react_test identified by 1234;
grant connect, resource to react_test;

-- 05�� ������Ʈ
create table tbl_notice (
    notice_no number primary key,
    notice_title varchar2(300) not null,
    notice_content varchar2(300) not null,
    notice_writer varchar2(30) not null,
    notice_date date
);

-- �Խ��� ��ȣ ������
create sequence seq_notice;

insert into tbl_notice values (seq_notice.nextval, '����' || seq_notice.currval, '����' || seq_notice.currval,'user01', sysdate);

commit;

select count(*) from tbl_notice;

select * from tbl_notice order by notice_no desc;

delete from tbl_notice where notice_no = '23';
commit;

-- 06�� ������Ʈ

create table tbl_member (
    member_id varchar2(30) primary key, -- ȸ�� ���̵�
    member_name varchar2(30) not null,  -- ȸ�� �̸�
    member_phone char(13) not null,     -- ȸ�� ��ȭ��ȣ
    member_intro varchar2(200) not null,-- ȸ�� �Ұ���
    enroll_date date default sysdate    -- ������
);

insert into tbl_member values('user01', '����1', '010-5555-4444', '����1 �Ұ���', sysdate);
insert into tbl_member values('user02', '����2', '010-5555-4444', '����2 �Ұ���', sysdate);
insert into tbl_member values('user03', '����3', '010-5555-4444', '����3 �Ұ���', sysdate);
insert into tbl_member values('user04', '����4', '010-5555-4444', '����4 �Ұ���', sysdate);
insert into tbl_member values('user05', '����5', '010-5555-4444', '����5 �Ұ���', sysdate);
commit;

select * from tbl_member;