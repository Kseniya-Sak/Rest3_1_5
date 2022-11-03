insert into users(age, last_name, password, username)
VALUES (24, 'Sims', '$2a$12$xDnhJn9WgtjTafQ6fgwKnO.LD1YamGPooe5ba11zlnPuNHfb8tCjy', 'Max'),
       (32, 'Emelin', '$2a$12$xDnhJn9WgtjTafQ6fgwKnO.LD1YamGPooe5ba11zlnPuNHfb8tCjy', 'Elena');

insert into roles(name) value ('ROLE_USER'),
                                ('ROLE_ADMIN');

insert into users_roles(user_id, role_id) VALUES (1,2),
                                                 (2, 1),
                                                 (2, 2);
