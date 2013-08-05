USE ciscowt_db;

-- seed the admin
insert into user (id,username,admin) values (1,'admin',true);

-- seed user
insert into user (id,username,admin) values (2,'Bill Smith',false);
insert into user (id,username,admin) values (3,'Dwight Smithers',false);

-- seed approvertype
insert into approvertype (id,name) values (1,'Services/CA');
insert into approvertype (id,name) values (2,'Documentation');
insert into approvertype (id,name) values (3,'Strong Encryption');
insert into approvertype (id,name) values (4,'Marketing');
insert into approvertype (id,name) values (5,'Localization');
insert into approvertype (id,name) values (6,'Manufacturing');
insert into approvertype (id,name) values (7,'Regression & feature testing');
insert into approvertype (id,name) values (8,'Build team requirements');
insert into approvertype (id,name) values (9,'All features committed');
insert into approvertype (id,name) values (10,'DDTS updates');
insert into approvertype (id,name) values (11,'Proceed with release');


-- seed filetype
insert into filetype (id,name) values (1,'Manufacturing Media');
insert into filetype (id,name) values (2,'Public (cisco.com) Media');
insert into filetype (id,name) values (3,'Release notes/readme');
insert into filetype (id,name) values (4,'Locale');
insert into filetype (id,name) values (5,'OVA');
insert into filetype (id,name) values (6,'Recovery disk');
insert into filetype (id,name) values (7,'Other');


-- seed product
insert into product (id,name) values (1,'NotFound');
insert into product (id,name) values (2,'Jabber');
insert into product (id,name) values (3,'CUCM');
insert into product (id,name) values (4,'CUCM IM&P');
insert into product (id,name) values (5,'TelePresence Server');
insert into product (id,name) values (6,'UCCE');
insert into product (id,name) values (7,'UCCX');
insert into product (id,name) values (8,'CVP');
insert into product (id,name) values (9,'TC');
insert into product (id,name) values (10,'CWMS');

