-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
/*
create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);

 */

-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
create sequence client_SEQ start with 1 increment by 1;
create sequence phone_seq start with 1 increment by 1;
create sequence address_seq start with 1 increment by 1;

create table client
(
    id             bigint not null,
    name           varchar(255),
    client_address bigint
);

create table phone
(
    id  bigint not null,
    number    varchar(255),
    client_id bigint
);

create table address
(
    id     bigint not null,
    street varchar(255)
);