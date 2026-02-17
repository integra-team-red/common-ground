create table Event
(
    id         uuid primary key,
    title      varchar(100) not null,
    start_time timestamp,
    end_time   timestamp
);
