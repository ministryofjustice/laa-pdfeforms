create sequence Person_Seq increment by 1 start with 100;

create sequence Address_Seq increment by 1 start with 100;

create table Address(
	id bigint primary key,
	address_line1 varchar(100) not null,
	address_line2 varchar(100),
	address_line3 varchar(100),
	postcode varchar(8) not null
);

create table Person(
	id bigint primary key not null,
	ufn varchar(50) unique not null,
	title varchar(5) not null,
	surname varchar(100) not null,
	forename varchar(100) not null,
	address_id bigint not null references address(id),
	date_of_birth date not null,
	ni_number varchar(10),
	employment_status varchar(50),
	relationship_status varchar(50),
	telephone_number varchar(15),
	correspondence_address_id bigint not null references address(id),
	ethnicity varchar(50),
	source_of_business varchar(50),
	existing_client char(1),
	request_specific_solicitor char(1),
	request_specific_solicitor_text varchar(50),
	police_station_1 varchar(50),
	magistrate_court varchar(50),
	crown_court varchar(50),
	nationality varchar(50),
	venue varchar(50),
	allegation varchar(50),
	previous_conviction char(1),
	funding_date date,
	advice_and_assistance varchar(50),
	proof_of_benefits_requested varchar(50),
	police_station_2 varchar(50),
	pre_order_work varchar(50),
	rep_order_applied_for varchar(50),
	rep_order_granted varchar(50),
	crm3_advocate varchar(50),
	conflict_check char(1),
	conflict_check_name varchar(50),
	conflict_check_date date,
	risk_assessment char(1),
	co_accused varchar(100)
);
