insert into person(id,name,age) values(1000,'PERSON_1000',18);

insert into project(id,name,owner_id) values (1001,'PROJECT_1001',0);
insert into project(id,name,owner_id) values (2001,'PROJECT_2001',0);

-- insert into project(id,name) values (1001,'PROJECT_1001');
-- insert into project(id,name) values (2001,'PROJECT_2001');

insert into person_project(person_id,project_id) values (1000,1001);
insert into person_project(person_id,project_id) values (1000,2001);