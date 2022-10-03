drop table if exists t_mails;
create table t_mails
(
    id   SERIAL primary key,
    sent_to varchar(64),
    body   varchar(500),
    status varchar(10),
    timestamp timestamp
);

