insert into user (created,modified,email,name,password) values(now(),now(),'admin@gmail.com','admin','$2a$12$t0KrYTJEy4N5LV6OA62YvOVoxq7tmBeaR2LoUO8rxp9ObdBlxjlN.');
insert into role(created, modified,name) values (now(),now(),'ADMIN');
insert into user_roles (users_id,roles_id) values(1,1);

insert into user (created,modified,email,name,password) values(now(),now(),'user@gmail.com','user','$2a$12$t0KrYTJEy4N5LV6OA62YvOVoxq7tmBeaR2LoUO8rxp9ObdBlxjlN.');
insert into role(created, modified,name) values (now(),now(),'USER');
insert into user_roles (users_id,roles_id) values(2,2);
