# My Books 

create table BOOKS (
	ID bigint AUTO_INCREMENT primary key,
    TITLE varchar(255),
    EDITION varchar(20)
);

create table AUTHORS (
	ID bigint AUTO_INCREMENT primary key,
    FIRSTNAME varchar(40),
    LASTNAME varchar(40)
);

create table AUTHORS_BOOKS (
    AUTHOR_ID bigint,
	BOOK_ID bigint,
    primary key (AUTHOR_ID, BOOK_ID),
        foreign key (AUTHOR_ID) references AUTHORS(ID),
    foreign key (BOOK_ID) references BOOKS(ID)
);

SELECT * FROM BOOKS;
SELECT * FROM AUTHORS;
SELECT * FROM AUTHORS_BOOKS;

SHOW STATUS LIKE '%Threads_connected%';

set global max_connections = 250;