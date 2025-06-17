create user react_test identified by 1234;
grant connect, resource to react_test;

-- 05번 프로젝트
create table tbl_notice (
    notice_no number primary key,
    notice_title varchar2(300) not null,
    notice_content varchar2(300) not null,
    notice_writer varchar2(30) not null,
    notice_date date
);

-- 게시판 번호 시퀀스
create sequence seq_notice;

insert into tbl_notice values (seq_notice.nextval, '제목' || seq_notice.currval, '내용' || seq_notice.currval,'user01', sysdate);

commit;

select count(*) from tbl_notice;