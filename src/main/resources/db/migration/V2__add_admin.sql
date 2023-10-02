insert into users (date_of_created, dtype, email, name, password, role)
values (CURRENT_TIMESTAMP, 'CUSTOMER', 'emailcustomer@gmail.com', 'Igor',
        '$2a$12$2G5Eh08N1cvL6Z/JjSS3K.sLlBxiNbaf8luB8fMR3ghLeAyT83gXa', 'ROLE_CUSTOMER'),
       (CURRENT_TIMESTAMP, 'MANAGER', 'emailadmin@gmail.com', 'Vlad',
        '$2a$12$FNV2ubCQc8Yi5T.aakpnvuNgm7uftAD4aLZXz9vZXIQuNfdBJAjz.', 'ROLE_MANAGER');

insert into goods (price, quantity, date_of_created, name)
values ('10000', '20', CURRENT_TIMESTAMP, 'Iphone13'),
       ('8000', '14', CURRENT_TIMESTAMP, 'Iphone11'),
       ('6000', '10', CURRENT_TIMESTAMP, 'Iphone10'),
       ('3000', '7', CURRENT_TIMESTAMP, 'SamsungGalaxy5'),
       ('20000', '3', CURRENT_TIMESTAMP, 'PlayStation5');

