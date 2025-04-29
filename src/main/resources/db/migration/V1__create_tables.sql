create table users(
	id bigint auto_increment primary key,
    chat_id bigint not null,
    permission tinyint,
    role varchar(50)
);

create table complaints(
	id bigint auto_increment primary key,
    text text,
    phone_number varchar(20),
    full_name varchar(50),
    image blob,
    chat_id bigint
);

create table museums(
	id bigint auto_increment primary key,
    count_of_people int,
    phone_number varchar(20),
    full_name varchar(50),
    date date,
    close bit,
    chat_id bigint
);

create table vacancies(
    id bigint auto_increment primary key,
    name text,
    specification varchar(50)
);

create table transports(
	id bigint auto_increment primary key,
    type varchar(20),
    number_of_track varchar(5),
    stops_start_end text,
    time_start_end text,
    link text,
    interval_time varchar(20)
);

create table stops(
	id bigint auto_increment primary key,
    stops_across text,
    stops_right_back text,
    id_transport bigint,
    foreign key (id_transport) references bot.transports (id)
);

create table notices(
	id bigint auto_increment primary key,
    date date,
    reason text,
    relevance bit,
    id_transport bigint,
    foreign key (id_transport) references bot.transports (id)
);