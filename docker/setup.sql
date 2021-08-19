create table programming_language
(
    id            bigserial primary key,
    language_name varchar(256) not null unique,
    language_key  varchar(256) not null unique
);

insert into programming_language (language_name, language_key)
values ('Java', 'java'),
       ('Python', 'Python');