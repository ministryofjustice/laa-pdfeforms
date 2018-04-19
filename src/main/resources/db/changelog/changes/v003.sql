alter table person add column risk_assessment_type varchar(50);

alter table person rename column risk_assessment to risk_assessment_done;