DROP ALL OBJECTS ;

create table request (
ID INTEGER default 1 not null,
CODE varchar(255) not null,
MESSAGE VARCHAR(1024) not null,
RESPONSE VARCHAR(1014),

primary key (ID));