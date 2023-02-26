-- create database sims;
drop table if exists t_mails;
create table t_mails
(
    id        SERIAL primary key,
    sent_to   varchar(64),
    topic     varchar(64),
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
values ('MAIL', false);
insert into t_notification_channels(type, is_active)
values ('FRONT', true);

drop table if exists t_notification_mail_recipients cascade;
create table t_notification_mail_recipients
(
    id    SERIAL primary key,
    email varchar(128)
);

insert into t_notification_mail_recipients(email)
values ('test1@test.pl');
insert into t_notification_mail_recipients(email)
values ('test2@test.pl');

drop table if exists t_notifications cascade;
create table t_notifications
(
    id           SERIAL primary key,
    timestamp    timestamp,
    message      text,
    message_type varchar(15),
    channel_id   SERIAL,
    seen         boolean,

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

drop table if exists t_users_habits cascade;
create table t_users_habits
(
    user_id            varchar(32),
    start_hour         SERIAL,
    start_minute       SERIAL,
    end_hour           SERIAL,
    end_minute         SERIAL,
    nr_of_days_learned SERIAL,
    ready_to_monitor   boolean
);

drop table if exists t_logs cascade;
create table t_logs
(
    id          SERIAL primary key,
    message     text,
    date_time   varchar(64),
    thread_name varchar(65),
    log_level   varchar(67),
    class_path  varchar(68),
    username    varchar(69)
);