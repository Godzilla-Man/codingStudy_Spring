-- 회원 관리 테이블

create table tbl_member (
    member_id varchar2(20) primary key,
    member_pw char(60) not null,
    member_name varchar2(30) not null,
    member_phone char(13) not null,
    member_level number
);