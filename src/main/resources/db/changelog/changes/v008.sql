create sequence attendance_seq increment by 1 start with 100;

create table attendance(
	id bigint primary key not null,
	ufn varchar(50) not null references person(ufn),
	scope char(1),
	allegations char(1),
	custody varchar(50),
	bail varchar(50),
	allegations_text varchar(1000),
	instructions varchar(1000),
	advice varchar(1000),
	result varchar(50),
	action_text varchar(1000)
);

