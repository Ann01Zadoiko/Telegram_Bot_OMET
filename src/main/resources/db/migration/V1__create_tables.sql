create table users (
    id bigserial primary key,
    chat_id bigint not null,
    permission smallint,
    role varchar(50)
);

create table complaints (
    id bigserial primary key,
    text text,
    phone_number varchar(20),
    full_name varchar(50),
    chat_id bigint
);

create table museums (
    id bigserial primary key,
    count_of_people integer,
    phone_number varchar(20),
    full_name varchar(50),
    date date,
    close boolean,
    chat_id bigint
);

create table vacancies (
    id bigserial primary key,
    name text,
    specification varchar(50)
);

create table transports (
    id bigserial primary key,
    type varchar(20),
    number_of_track varchar(5),
    stops_start_end text,
    time_start_end text,
    link text,
    interval_time varchar(20)
);

create table stops (
    id bigserial primary key,
    stops_across text,
    stops_right_back text,
    id_transport bigint,
    foreign key (id_transport) references transports (id)
);

create table notices (
    id bigserial primary key,
    date date,
    time time,
    reason text,
    relevance boolean,
    id_transport bigint,
    foreign key (id_transport) references transports (id)
);
