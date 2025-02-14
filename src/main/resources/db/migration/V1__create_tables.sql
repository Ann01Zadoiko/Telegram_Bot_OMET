create table users(
	id bigint auto_increment primary key,
    chat_id bigint not null,
    permission tinyint,
    username varchar(50)
);

create table complaints(
	id bigint auto_increment primary key,
    text text,
    phone_number varchar(20),
    full_name varchar(50),
    image blob,
    audio blob,
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