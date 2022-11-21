-- create database potentialsi;
drop table if exists t_mails;
create table t_mails
(
    id        SERIAL primary key,
    sent_to   varchar(64),
    body      varchar(500),
    status    varchar(10),
    timestamp timestamp
);


--------------------------- notifications ---------------------------
drop table if exists t_notification_channels cascade;
create table t_notification_channels
(
    id        SERIAL primary key,
    type      varchar(64),
    is_active boolean
);

insert into t_notification_channels(type, is_active)
values ('MAIL', true);
insert into t_notification_channels(type, is_active)
values ('FRONT', true);

drop table if exists t_notification_mail_recipients cascade;
create table t_notification_mail_recipients
(
    id    SERIAL primary key,
    email varchar(128)
);

drop table if exists t_notifications cascade;
create table t_notifications
(
    id         SERIAL primary key,
    timestamp  timestamp,
    message    varchar(5000),
    error_type varchar(15),
    channel_id SERIAL,

    CONSTRAINT fk_channel FOREIGN KEY (channel_id) REFERENCES t_notification_channels (id)
);

drop table if exists t_notifications_recipients cascade;
create table t_notifications_recipients
(
    notification_id SERIAL,
    recipient_id    SERIAL,

    CONSTRAINT fk_notification FOREIGN KEY (notification_id) REFERENCES t_notifications (id),
    CONSTRAINT fk_recipient FOREIGN KEY (recipient_id) REFERENCES t_notification_mail_recipients (id)
);

