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

select * from tbl_notice order by notice_no desc;

delete from tbl_notice where notice_no = '23';
commit;

-- 06번 프로젝트

create table tbl_member (
    member_id varchar2(30) primary key, -- 회원 아이디
    member_name varchar2(30) not null,  -- 회원 이름
    member_phone char(13) not null,     -- 회원 전화번호
    member_intro varchar2(200) not null,-- 회원 소개글
    enroll_date date default sysdate    -- 가입일
);

insert into tbl_member values('user01', '유저1', '010-5555-4444', '유저1 소개글', sysdate);
insert into tbl_member values('user02', '유저2', '010-5555-4444', '유저2 소개글', sysdate);
insert into tbl_member values('user03', '유저3', '010-5555-4444', '유저3 소개글', sysdate);
insert into tbl_member values('user04', '유저4', '010-5555-4444', '유저4 소개글', sysdate);
insert into tbl_member values('user05', '유저5', '010-5555-4444', '유저5 소개글', sysdate);
commit;

select * from tbl_member;