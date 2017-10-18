
CREATE TYPE phase_enum AS ENUM('urakointi', 'tarjous', 'aloittamaton');
CREATE TABLE Projects(
	id SERIAl unique,
	name varchar[100],
	customer varchar[100],
	hourly_rate float,
	charged float,
	to_charge float,
	phase phase_enum,
	active boolean,
	primary key(id)
);

CREATE TABLE work_output(
	id SERIAL unique,
	date_ date,
	hours float,
	project_id int,
	paid phase_enum,
	description varchar[100],
	foreign key(project_id) references Projects(id)
);

CREATE TABLE driving_journal(
	id SERIAL unique,
	project_id int,
	departure_time timestamp,
	return_time timestamp,
	route varchar[100],
	description varchar[100],
	readout_departure int,
	readout_return int,
	foreign key(project_id) references Projects(id)
);
