create table organization
(
    id        bigint primary key,
    name      varchar(255),
    deleted boolean default false
);
create table department
(
    id        bigint primary key,
    name      varchar(255),
    deleted boolean default false,
    organization_id bigint,
    foreign key (organization_id) references organization (id)
);

create table employee
(
    id            bigint primary key,
    name          varchar(255),
    department_id bigint,
    deleted     boolean default false,
    organization_id bigint,
    foreign key (department_id) references department (id),
    foreign key (organization_id) references organization (id)
);

insert into organization
values (1, '金风细雨楼', false);

insert into department
values (1, '财务部', false, 1);
insert into department
values (2, '人事部', false, 1);
insert into department
values (3, '研发部', false, 1);

insert into employee
values (1, '张三', 1, false, 1);
insert into employee
values (2, '李四', 1, false, 1);
insert into employee
values (3, '王五', 1, false, 1);

insert into employee
values (4, '赵构', 2, false, 1);

insert into employee
values (5, '朱元璋', 3, false, 1);
