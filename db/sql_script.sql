CREATE TABLE Category(
id int IDENTITY(1,1) PRIMARY KEY,
name varchar(128)
);

CREATE TABLE Country(
id int IDENTITY(1,1) PRIMARY KEY,
name varchar(128)
);

CREATE TABLE Actor(
id int IDENTITY(1,1) PRIMARY KEY,
first_name varchar(128),
last_name varchar(128),
birthdate datetime,
id_country int not null FOREIGN KEY references Country(id)
);

CREATE TABLE Language(
id int IDENTITY(1,1) PRIMARY KEY,
name varchar (128)
);

/*
ToFix();
Not enough information from entity diagram, cannot fill all the fields.
*/

CREATE TABLE Film(
id int IDENTITY(1,1) PRIMARY KEY,
title text,
release_date datetime,
id_language int not null foreign key references Language(id),
lenght int,
description text
);

/*(end)*/

CREATE TABLE Trailer(
id int IDENTITY(1,1) PRIMARY KEY,
id_film int not null foreign key references Film(id),
link text
);

CREATE TABLE Screenshot(
id int IDENTITY(1,1) PRIMARY KEY,
id_film int not null foreign key references Film(id),
link text
);


CREATE TABLE City(
id int IDENTITY(1,1) PRIMARY KEY,
name varchar(128) not null,
id_country int not null foreign key references Country(id)
);


CREATE TABLE Address(
id int IDENTITY(1,1) PRIMARY KEY,
address text,
address2 text,
district varchar(128),
postal_code varchar(64),
id_city int not null foreign key references City(id)
);

/*
To discus.
Probably there is no sence to store client_id in the db, because each login must be unique, thus we can use login as a primary key and identifier.


ToFix();
Not enough information from entity diagram cannot fill all the fields.
*/
CREATE TABLE Client(
id int IDENTITY(1,1) PRIMARY KEY,
first_name varchar(128),
last_name varchar(128),
email varchar(128),
id_address int not null foreign key references Address(id),
creation_date datetime,
login varchar(128) not null unique,
passwordHash binary(64) not null,
salt UNIQUEIDENTIFIER
);
/*(end)*/

/*
To discus.
Probably should add such field as 'rating' for following algorithm work. Client not only checks what films he watched but how much did he like it. 
*/
CREATE TABLE Client_data(
id int IDENTITY(1,1) PRIMARY KEY,
id_client int not null foreign key references Client(id),
id_film int not null foreign key references Film(id)
);
/*(end)*/



