create table users(
    id bigint auto_increment primary key,
    id_chat bigint not null,
    permission tinyint
);

create table complaints(
    id
    full_name
    phone_number
    text
    image
    audio
    date
);

create table audios();
create table images();

create table museums(
    id
    full_name
    phone_number
    count_of_people
    date
);