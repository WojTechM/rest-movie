create table Movie (id  bigserial not null, duration float4 not null, primary key (id))
create table Movie_categories (Movie_id int8 not null, categories int4)
create table Movie_Pornstar (Movie_id int8 not null, pornstars_id int8 not null)
create table Pornstar (id  bigserial not null, age int8 not null, firstName varchar(255), height int8 not null, lastName varchar(255), nickName varchar(255), sex int4, weight int8 not null, primary key (id))
alter table Movie_Pornstar add constraint UK_6e8we4ixpwfyah65f59aq0sm4 unique (pornstars_id)
alter table Movie_categories add constraint FK7mb1lnec97kwimjugva15r62f foreign key (Movie_id) references Movie
alter table Movie_Pornstar add constraint FKn3bgyeygwb51ho4epwvyci4u7 foreign key (pornstars_id) references Pornstar
alter table Movie_Pornstar add constraint FKhg5x8sv1ultgvjdkb1rhpvy89 foreign key (Movie_id) references Movie
create table Movie (id  bigserial not null, duration float4 not null, primary key (id))
create table Movie_categories (Movie_id int8 not null, categories int4)
create table Movie_Pornstar (Movie_id int8 not null, pornstars_id int8 not null)
create table Pornstar (id  bigserial not null, age int8 not null, firstName varchar(255), height int8 not null, lastName varchar(255), nickName varchar(255), sex int4, weight int8 not null, primary key (id))
alter table Movie_Pornstar add constraint UK_6e8we4ixpwfyah65f59aq0sm4 unique (pornstars_id)
alter table Movie_categories add constraint FK7mb1lnec97kwimjugva15r62f foreign key (Movie_id) references Movie
alter table Movie_Pornstar add constraint FKn3bgyeygwb51ho4epwvyci4u7 foreign key (pornstars_id) references Pornstar
alter table Movie_Pornstar add constraint FKhg5x8sv1ultgvjdkb1rhpvy89 foreign key (Movie_id) references Movie
create table Movie (id  bigserial not null, duration float4 not null, primary key (id))
create table Movie_categories (Movie_id int8 not null, categories int4)
create table Movie_Pornstar (Movie_id int8 not null, pornstars_id int8 not null)
create table Pornstar (id  bigserial not null, age int8 not null, firstName varchar(255), height int8 not null, lastName varchar(255), nickName varchar(255), sex int4, weight int8 not null, primary key (id))
alter table Movie_Pornstar add constraint UK_6e8we4ixpwfyah65f59aq0sm4 unique (pornstars_id)
alter table Movie_categories add constraint FK7mb1lnec97kwimjugva15r62f foreign key (Movie_id) references Movie
alter table Movie_Pornstar add constraint FKn3bgyeygwb51ho4epwvyci4u7 foreign key (pornstars_id) references Pornstar
alter table Movie_Pornstar add constraint FKhg5x8sv1ultgvjdkb1rhpvy89 foreign key (Movie_id) references Movie
