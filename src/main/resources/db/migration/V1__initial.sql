create table sector
(
  id          bigint       not null primary key,
  description varchar(255) null
);

create table employee
(
  cpf        varchar(255) not null primary key,
  birth_date date         not null,
  email      varchar(255) null,
  name       varchar(255) null,
  phone      varchar(255) null,
  sector_id  bigint       not null,
  constraint FKqxbo4fprgn1j40k2bgan17p4j
    foreign key (sector_id) references sector (id)
);

INSERT INTO sector (id, description)
VALUES (1, 'Sector 1');
INSERT INTO sector (id, description)
VALUES (2, 'Sector 2');
INSERT INTO sector (id, description)
VALUES (3, 'Sector 3');