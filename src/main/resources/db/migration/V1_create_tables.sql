create table bot.users(
	id bigint auto_increment primary key,
    chat_id bigint not null,
    permission tinyint,
    username varchar(50)
);

create table bot.complaints(
	id bigint auto_increment primary key,
    text text,
    phone_number varchar(20),
    full_name varchar(50),
    image blob,
    audio blob
);

create table bot.museums(
	id bigint auto_increment primary key,
    count_of_people int,
    phone_number varchar(20),
    full_name varchar(50),
    date datetime
);