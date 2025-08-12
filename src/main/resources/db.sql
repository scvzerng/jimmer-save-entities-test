create table test_record(
    id bigint primary key,
    name varchar(255) not null,
    member_count bigint default 0
);

insert into test_record values(1,'test',1)
