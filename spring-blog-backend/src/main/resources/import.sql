insert into role (id, name) values (1, "ROLE_USER");
insert into role (id, name) values (2, "ROLE_ADMIN");

insert into user(id, name, username, password, email) values (1,"brian","brian123","$2a$10$kbE5NB0YFTNqRXXe062nd.E13Z1cRh0zQJ.gvPIDpGZmVMy88ymBq","email@gmail.com");
  
insert into user_role(user_id,role_id) values (1,2);
insert into user_role(user_id,role_id) values (1,1);