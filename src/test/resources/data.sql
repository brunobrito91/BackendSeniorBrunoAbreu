INSERT INTO sector (id, description)
VALUES (1, 'Sector 1');
INSERT INTO sector (id, description)
VALUES (2, 'Sector 2');
INSERT INTO sector (id, description)
VALUES (3, 'Sector 3');

INSERT INTO employee (cpf, birth_date, email, name, phone, sector_id)
VALUES ('12345678910', curdate() - interval 18 year, 'employee10@employee.com', 'Employee 10', '99999999910', 3);