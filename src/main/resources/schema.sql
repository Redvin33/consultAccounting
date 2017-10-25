CREATE TYPE phase_enum AS ENUM('urakointi', 'tarjous', 'aloittamaton');
CREATE TABLE IF NOT EXISTS projects(
	id SERIAl unique,
	name varchar,
	customer varchar,
	hourly_rate float,
	charged float,
	to_charge float,
	phase phase_enum,
	active boolean,
	primary key(id)
);
CREATE TABLE IF NOT EXISTS work_output(
	id SERIAL unique,
	date_ date,
	hours float,
	project_id int,
	paid phase_enum,
	description varchar,
	foreign key(project_id) references Projects(id)
);
CREATE TABLE IF NOT EXISTS driving_journal(
	id SERIAL unique,
	project_id int,
	departure_time timestamp,
	return_time timestamp,
	route varchar,
	description varchar,
	readout_departure int,
	readout_return int,
	foreign key(project_id) references Projects(id)
);
