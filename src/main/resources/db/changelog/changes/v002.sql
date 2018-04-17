create sequence Disability_Seq increment by 1 start with 100;

create table disability(
	id bigint primary key,
	person_id bigint references person(id),
	disability_option varchar(100)
);