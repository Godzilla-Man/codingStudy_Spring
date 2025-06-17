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