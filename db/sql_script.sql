CREATE TABLE Category(
id int IDENTITY(1,1) PRIMARY KEY,
name varchar(128)
);

CREATE TABLE Country(
id int IDENTITY(1,1) PRIMARY KEY,
name varchar(32)
);

CREATE TABLE Actor(
id int IDENTITY(1,1) PRIMARY KEY,
first_name varchar(64),
last_name varchar(64),
birthdate datetime,
id_country int not null FOREIGN KEY references Country(id)
);

CREATE TABLE Language(
id int IDENTITY(1,1) PRIMARY KEY,
name varchar (32)
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
name varchar(64) not null,
id_country int not null foreign key references Country(id)
);


CREATE TABLE Address(
id int IDENTITY(1,1) PRIMARY KEY,
address text,
address2 text,
district varchar(64),
postal_code varchar(32),
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
first_name varchar(64),
last_name varchar(64),
email varchar(128),
id_address int not null foreign key references Address(id),
creation_date datetime,
login varchar(64) not null unique,
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

/* To fix 
Not enough information from entity diagram
*/
CREATE TABLE Comment_rating(
id int IDENTITY(1,1) PRIMARY KEY,
comment_text text,
id_client int not null foreign key references Client(id),
Film_rating float,
comment_date datetime,
id_film int not null foreign key references Film(id)
);

/*(end)*/

CREATE TABLE Film_Category(
id int IDENTITY(1,1) PRIMARY KEY,
id_film int not null foreign key references Film(id),
id_category int not null foreign key references Category(id)
);


CREATE TABLE Film_Actor(
id int IDENTITY(1,1) PRIMARY KEY,
id_film int not null foreign key references Film(id),
id_actor int not null foreign key references Actor(id),
role varchar(64)
);

CREATE TABLE Studio(
id int IDENTITY(1,1) PRIMARY KEY,
studio_name varchar(64)
);

CREATE TABLE Film_studio(
id int IDENTITY(1,1) PRIMARY KEY,
id_film int not null foreign key references Film(id),
id_studio int not null foreign key references Studio(id)
);

CREATE TABLE Network(
id int IDENTITY(1,1) PRIMARY KEY,
network_name varchar(64),
network_logo text
);


CREATE TABLE Link_to_network(
id int IDENTITY(1,1) PRIMARY KEY,
id_network int not null foreign key references Network(id),
link text
);

CREATE TABLE Film_network(
id int IDENTITY(1,1) PRIMARY KEY,
id_film int not null foreign key references Film(id),
id_link_to_network int not null foreign key references Link_to_network(id)
);

CREATE TABLE Director(
id int IDENTITY(1,1) PRIMARY KEY,
first_name varchar(64),
last_name varchar(64),
id_country int not null foreign key references Country(id)
);

CREATE TABLE Film_director(
id int IDENTITY(1,1) PRIMARY KEY,
id_film int not null foreign key references Film(id),
id_director int not null foreign key references Director(id)
);

CREATE TABLE Award(
id int IDENTITY(1,1) PRIMARY KEY,
award_name varchar(64),
award_year int,
id_film int not null foreign key references Film(id)
);