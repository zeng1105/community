create table USER
(
	ID INT auto_increment,
	NAME VARCHAR(50) not null,
	ACCOUNT_ID VARCHAR(100) not null,
	TOKEN CHAR(50) not null,
	GMT_CREATE BIGINT not null,
	GMT_MODIFIED BIGINT not null,
	constraint USER_PK primary key (ID)
);