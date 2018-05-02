create sequence attendance_note_Seq increment by 1 start with 100;

create table attendance_note(
	id bigint primary key not null,
	ufn varchar(50) not null references person(ufn),
	attendance_date date,
	status char(1),
	free_earner varchar(50),
	venue varchar(50),
	risk_need_for_check char(1),
	conflict_check char(1),
	client_attend varchar(50),
	client_cps varchar(50),
	attend_witness varchar(50),
	attend_other varchar(50),
	prep varchar(50),
	calls_made varchar(50),
	travel varchar(50),
	advoc varchar(50),
	waiting varchar(50),
	mileage numeric,
	other_disabilities varchar(50),
	instruction_notes varchar(1000)
);