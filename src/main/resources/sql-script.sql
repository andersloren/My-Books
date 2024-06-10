# My Books 

create table BOOKS (
	ID bigint AUTO_INCREMENT primary key,
    TITLE varchar(255),
    EDITION varchar(20)
);

create table AUTHORS (
	ID bigint AUTO_INCREMENT primary key,
    FIRSTNAME varchar(40),
    LASTNAME varchar(40),
    BOOK_ID bigint,
    foreign key (BOOK_ID) references BOOKS(ID)
);

create table BOOKS_AUTHORS (
	BOOK_ID bigint,
    AUTHOR_ID bigint,
    primary key (BOOK_ID, AUTHOR_ID),
    foreign key (BOOK_ID) references BOOKS(ID),
    foreign key (AUTHOR_ID) references AUTHORS(ID)
);

SELECT * FROM BOOKS;
SELECT * FROM AUTHORS;
SELECT * FROM BOOKS_AUTHORS;
