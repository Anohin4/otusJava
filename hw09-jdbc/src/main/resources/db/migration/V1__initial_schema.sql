create table IF NOT EXISTS test
(
    id   int,
    name varchar(50)
);
create table IF NOT EXISTS client
(
    id   bigserial not null primary key,
    name varchar(50)
);

create table IF NOT EXISTS manager
(
    no   bigserial not null primary key,
    label varchar(50),
    param1 varchar(50)
);