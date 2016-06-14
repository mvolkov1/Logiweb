create user if not exists 'logiweb'@'localhost' identified by 'logiweb';

create database if not exists logiweb;

grant all on logiweb.* to 'logiweb'@'localhost';


drop table if exists Cargo, Driver, Vehicle, OrderItems, Order1, Distance, City;


create table City(
	id bigint auto_increment primary key,
	city varchar(50) unique not null
);

create table Distance(
	id bigint auto_increment primary key,
	cityId1  bigint not null,
	cityId2  bigint not null,
	distance numeric(6,1) not null,
	foreign key (cityId1) references City(id),
	foreign key (cityId2) references City(id)
);

create table Order1(
	id bigint auto_increment primary key,
    uid varchar(12) unique not null,
	isCompleted smallint not null default 0,
	nItems integer not null
);

create table OrderItems(
	id bigint auto_increment primary key,
	orderId bigint not null,
	cityId bigint not null,
	itemNumber int not null,
	foreign key (cityId) references City(id),
	foreign key (orderId) references Order1(Id)
);


create table Vehicle(
	id bigint auto_increment primary key,
	vin varchar(7) unique not null,
	capacity numeric (3,1) not null,
	nDrivers integer (1) not null,
	isAvailable smallint not null default 1,
	cityId bigint not null,
    orderId bigint,
    foreign key (orderId) references Order1(id),
	foreign key (cityId) references City(id),
	check (vin like '[A-Z,a-z][A-Z,a-z][0-9][0-9][0-9][0-9][0-9]')
);


create table Driver(
	id bigint auto_increment primary key,
	uid varchar(12) unique not null,
	firstName varchar(50) not null,
	lastName varchar(50) not null,
	monthHours integer not null,
	status1  varchar(10) not null,
	cityId bigint not null,
	orderId bigint,
	foreign key (orderId) references Order1(id),
	check (Status in ('Driving', 'AtWork', 'Free')),
	foreign key (cityId) references City(id)
);

create table Cargo(
	id bigint auto_increment primary key,
	uid varchar(12) unique not null,
	title varchar(200) not null,
	mass numeric(6,1) not null,
	orderId bigint,
	status1 varchar(10) not null,
	cityIdStart bigint not null,
	cityIdEnd   bigint not null,
	check (status1 in ('Prepared', 'Loaded', 'Delivered')),
	foreign key (cityIdStart) references City(id),
	foreign key (cityIdEnd) references City(id),
	foreign key (orderId) references Order1(id)
);












