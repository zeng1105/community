drop table user;

create table USER
(
	ID LONG auto_increment,
	NAME VARCHAR(50) not null,
	ACCOUNT_ID VARCHAR(100) not null,
	TOKEN CHAR(50) not null,
	GMT_CREATE BIGINT not null,
	GMT_MODIFIED BIGINT not null,
	BIO VARCHAR(256) not null,
	constraint USER_PK
		primary key (ID)
);

