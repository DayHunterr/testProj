create table GOODS
(
    id              bigint not null auto_increment,
    date_of_created datetime(6),
    name            varchar(255),
    price           double precision,
    quantity        integer,
    primary key (id)
) engine = InnoDB;
create table order_item
(
    id              bigint not null auto_increment,
    date_of_created datetime(6),
    good_id         bigint,
    quantity        integer,
    primary key (id)
) engine = InnoDB;
create table ORDERS
(
    id              bigint not null auto_increment,
    date_of_created datetime(6),
    paid            bit,
    primary key (id)
) engine = InnoDB;
create table orders_order_items
(
    order_id       bigint not null,
    order_items_id bigint not null
) engine = InnoDB;
create table USERS
(
    dtype           varchar(31)  not null,
    id              bigint       not null auto_increment,
    date_of_created datetime(6),
    email           varchar(255) not null,
    name            varchar(255) not null,
    password        varchar(255) not null,
    role            varchar(255) not null,
    primary key (id)
) engine = InnoDB;
alter table orders_order_items
    add constraint UK_9d47gapmi35omtannusv6btu3 unique (order_items_id);
alter table orders_order_items
    add constraint FK7nw03p9mxq154wvbsonaq0qrw foreign key (order_items_id) references order_item (id);
alter table orders_order_items
    add constraint FK3l8rktw0f4w5t6tift31e2d7c foreign key (order_id) references orders (id);