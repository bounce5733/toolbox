create table TEAM (id int auto_increment primary key, name text not null);

create table SHORTAGE (id int auto_increment primary key, type varchar(32), amount double);

create table RISK (id int auto_increment primary key, subproject_id int not null, responsible_personnel_id int,
    measure text, type varchar(32), desc text);

create table CONTRACT (id int auto_increment primary key, subproject_id int, name text not null, type varchar(32),
    start_date char(10), end_date char(10), company varchar(32), status varchar(32));

create table PROJECT (id int auto_increment primary key, code varchar(32) not null, name text not null,
    domain varchar(32), issue text, online_date char(10));

create table SUBPROJECT (id int auto_increment primary key, project_id int not null, system varchar(32),
    phase varchar(32) not null, dept varchar(32), pm int, pmo int, qa int, contract_id int);

create table PROCESS (id int auto_increment primary key, subproject_id int not null, phase varchar(32) not null,
    plan_start_date char(10), plan_end_date char(10), really_start_date char(10), really_end_date char(10),
    plan_man_days int, invest_rate double, current_process int default 0);

create table RESOURCE (id int auto_increment primary key, subproject_id int, personnel_id int,
    current_ration double, next_subproject_id int, prev_subproject_id int, start_date char(10), end_date char(10));

create table PERSONNEL (id int auto_increment primary key, code varchar(32) not null, name text not null,
    company varchar(32), level varchar(32), type varchar(32), phone text, dept varchar(32),
    position varchar(32), charge int, status varchar(32), start_date char(10), end_date char(10));

create table DICT (id int auto_increment primary key, code varchar(32) not null, name text not null,
    type varchar(32) not null);

create table DICT_TYPE (code varchar(32) primary key, name text not null);