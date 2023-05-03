INSERT INTO t_actions(name)
VALUES ('insert'),
       ('update'),
       ('delete');

INSERT INTO t_modules(name, status)
VALUES ('Users', 'active'),
       ('Queries', 'active'),
       ('Tables', 'active'),
       ('Apps', 'active');

INSERT INTO t_roles(name)
VALUES ('query_authorizer'),
       ('query_creator'),
       ('user_creator');

-- role -> modules
-- query_authorizer(1) -> queries(2),tables(3),apps(4)
-- query_creator(2) -> queries(2),tables(3),apps(4)
-- user_creator(3) -> users(1)
INSERT INTO t_module_role
VALUES (1, 2),
       (1, 3),
       (1, 4),
       (2, 2),
       (2, 3),
       (2, 4),
       (3, 1);

-- modules = users(1), queries(2), tables(3), apps(4)
INSERT INTO t_screens(link, name, status, module_id)
VALUES ('/users-create', 'Create Users', 'active', 1),
       ('/users', 'List Users', 'active', 1),
       ('/queries', 'List Queries', 'active', 2),
       ('/queries-create', 'Create Queries', 'active', 2),
       ('/apps', 'List Apps', 'active', 4),
       ('/apps-create', 'Create Apps', 'active', 4),
       ('/tables', 'List Tables', 'active', 3),
       ('/tables-create', 'Create Tables', 'active', 3);
