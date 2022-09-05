create table TEAM (id int, name text not null, primary key (id));

create table SHORTAGE (id int, type varchar(32), amount double, primary key (id));

create table RISK (id int, project int, type varchar(32), risk_desc text, primary key (id));

create table CONTRACT (id int, name text not null, type varchar(32), end_date date, company varchar(32),
    status varchar(32), primary key (id));

create table PROJECT (id int, name text not null, phase varchar(32), end_date date,
    domain varchar(32), pmid int, is_project_group char(1), progress varchar(32), delay double, primary key (id));

create table SUBPROJECT (id int, project int, system int, team_name text, primary key(id));

create table SUBPROJECT_PERSONNEL (subprojectid int, personnelid int, current_ration double, end_date date,
    next_subproject_id int, prev_subproject_id int, start_date date);

create table PERSONNEL (id int, name text not null, company varchar(32), level varchar(32), class varchar(32),
    is_pm char(1), is_admin char(1), phone text, primary key (id));

create table DICT (id int, code varchar(32) not null, name text not null, type varchar(32) not null, primary key (id));

create table DICT_TYPE (id int, name text not null, primary key(id));