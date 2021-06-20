INSERT INTO specialties (name) VALUES('Alergologia');
INSERT INTO specialties (name) VALUES('Angiologia');
INSERT INTO specialties (name) VALUES('Buco maxilo');
INSERT INTO specialties (name) VALUES('Cardiologia clínca');
INSERT INTO specialties (name) VALUES('Cardiologia infantil');
INSERT INTO specialties (name) VALUES('Cirurgia cabeça e pescoço');
INSERT INTO specialties (name) VALUES('Cirurgia cardíaca');
INSERT INTO specialties (name) VALUES('Cirurgia de tórax');

INSERT INTO adresses (id, bairro, cep, complemento, gia, ibge, localidade, logradouro, siafi, uf) VALUES (1, 'Jardim Helena', '08090284', '(Ch Três Meninas)', '1004', '3550308', 'São Paulo', 'Rua 03 de Outubro', '7107', 'SP');

INSERT INTO doctors (active, cell_phone, crm, name, phone, address_id) VALUES ('TRUE', '11988100022', '1234567', 'José Silva Ferreira', '1130254013', 1);

INSERT INTO doctor_specialty (doctor_id, specialty_id) VALUES (1, 1);
INSERT INTO doctor_specialty (doctor_id, specialty_id) VALUES (1, 2);