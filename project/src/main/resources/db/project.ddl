CREATE TABLE team (id int auto_increment PRIMARY KEY, name text NOT NULL, dept varchar(32));

CREATE TABLE contract (id int auto_increment PRIMARY KEY, name text NOT NULL, type varchar(32),
    start_date char(10), end_date char(10), company varchar(32), status varchar(32));

CREATE TABLE project (id int auto_increment PRIMARY KEY, code varchar(32) NOT NULL, name text NOT NULL,
    domain varchar(32), issue text, online_date char(10));

CREATE TABLE subproject (id int auto_increment PRIMARY KEY, project_id int NOT NULL, system varchar(32),
    phase varchar(32) NOT NULL, dept varchar(32), pm int, pmo int, qa int, contract_id int,
    FOREIGN KEY (project_id) REFERENCES project(id),
    FOREIGN KEY (contract_id) REFERENCES contract(id));

CREATE TABLE process (id int auto_increment PRIMARY KEY, subproject_id int NOT NULL, phase varchar(32) NOT NULL,
    plan_start_date char(10), plan_end_date char(10), really_start_date char(10), really_end_date char(10),
    plan_man_days int, invest_rate double, current_process int default 0,
    FOREIGN KEY (subproject_id) REFERENCES subproject(id));

CREATE TABLE personnel (id int auto_increment PRIMARY KEY, code varchar(32) NOT NULL, name text NOT NULL,
    company varchar(32), level varchar(32), type varchar(32), phone text, dept varchar(32), team_id int,
    position varchar(32), charge int, status varchar(32), start_date char(10), end_date char(10),
    FOREIGN KEY (team_id) REFERENCES team(id));

CREATE TABLE resource (id int auto_increment PRIMARY KEY, subproject_id int, personnel_id int,
    current_ration double, next_subproject_id int, prev_subproject_id int, start_date char(10), end_date char(10),
    FOREIGN KEY (personnel_id) REFERENCES personnel(id),
    FOREIGN KEY (subproject_id) REFERENCES subproject(id),
    FOREIGN KEY (next_subproject_id) REFERENCES subproject(id),
    FOREIGN KEY (prev_subproject_id) REFERENCES subproject(id));

CREATE TABLE resource_his(id int auto_increment PRIMARY KEY, subproject_id int, personnel_id int, current_ration double,
    start_date char(10), end_date char(10));

CREATE TABLE risk (id int auto_increment PRIMARY KEY, subproject_id int NOT NULL, responsible_personnel_id int,
    measure text, type varchar(32), desc text,
    FOREIGN KEY (responsible_personnel_id) REFERENCES personnel(id),
    FOREIGN KEY (subproject_id) REFERENCES subproject(id));

CREATE TABLE dict (id int auto_increment PRIMARY KEY, code varchar(32) NOT NULL, name text NOT NULL,
    type varchar(32) NOT NULL);

CREATE TABLE dict_type (code varchar(32) PRIMARY KEY, name text NOT NULL);