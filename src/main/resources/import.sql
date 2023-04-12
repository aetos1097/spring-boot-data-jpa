/*populate tables */
-- Archivo importante para introducir datos al inicio, el archivo se debe llamar igual que este ademas no se introduce el campo id
-- porque este es autoincrementable en la entidad.
INSERT INTO clientes(nombre, apellido, email, create_at,foto) VALUES ('Juan', 'Medina','aetos@gmail.com','2018-08-21','');
INSERT INTO clientes(nombre, apellido, email, create_at,foto) VALUES ('Andre', 'Pena','aetos23@gmail.com','2018-08-22','');
INSERT INTO clientes(nombre, apellido, email, create_at,foto) VALUES ('Sofia', 'Garcia', 'sofiagarcia@gmail.com', '2022-01-15','');
INSERT INTO clientes(nombre, apellido, email, create_at,foto) VALUES ('Carlos', 'Rodriguez', 'carlosr@hotmail.com', '2021-12-04','');
INSERT INTO clientes(nombre, apellido, email, create_at,foto) VALUES ('Laura', 'Perez', 'laurapz@gmail.com', '2022-02-19','');
INSERT INTO clientes(nombre, apellido, email, create_at,foto) VALUES ('Ricardo', 'Gonzalez', 'rgonzalez@yahoo.com', '2022-03-05','');
INSERT INTO clientes(nombre, apellido, email, create_at,foto) VALUES ('Ana', 'Vazquez', 'anavzqz@hotmail.com', '2022-02-11','');
INSERT INTO clientes(nombre, apellido, email, create_at,foto) VALUES ('Diego', 'Sanchez', 'dsanchez@gmail.com', '2021-11-28','');
INSERT INTO clientes(nombre, apellido, email, create_at,foto) VALUES ('Maria', 'Torres', 'mtorres@yahoo.com', '2022-03-12','');
INSERT INTO clientes(nombre, apellido, email, create_at,foto) VALUES ('Pedro', 'Lopez', 'plopez@hotmail.com', '2022-02-05','');
INSERT INTO clientes(nombre, apellido, email, create_at,foto) VALUES ('Julia', 'Castillo', 'juliacstll@yahoo.com', '2021-12-18','');
INSERT INTO clientes(nombre, apellido, email, create_at,foto) VALUES ('Gabriel', 'Ruiz', 'gruiz@gmail.com', '2022-01-22','');
INSERT INTO clientes(nombre, apellido, email, create_at,foto) VALUES ('Fernanda', 'Gomez', 'fgomez@hotmail.com', '2022-02-26','');
INSERT INTO clientes(nombre, apellido, email, create_at,foto) VALUES ('Oscar', 'Ramirez', 'oscar.ramirez@yahoo.com', '2021-11-21','');
INSERT INTO clientes(nombre, apellido, email, create_at,foto) VALUES ('Natalia', 'Flores', 'natiflores@hotmail.com', '2022-03-19','');
INSERT INTO clientes(nombre, apellido, email, create_at,foto) VALUES ('Andres', 'Hernandez', 'andreshdz@gmail.com', '2021-12-11','');
INSERT INTO clientes(nombre, apellido, email, create_at,foto) VALUES ('Valentina', 'Diaz', 'vdiaz@yahoo.com', '2022-01-29','');
INSERT INTO clientes(nombre, apellido, email, create_at,foto) VALUES ('Luis', 'Santos', 'lsantos@hotmail.com', '2022-03-26','');
INSERT INTO clientes(nombre, apellido, email, create_at,foto) VALUES ('Carmen', 'Morales', 'carmenmorales@gmail.com', '2021-11-14','');
INSERT INTO clientes(nombre, apellido, email, create_at,foto) VALUES ('Jorge', 'Cruz', 'jcruz@hotmail.com', '2022-02-12','');
INSERT INTO clientes(nombre, apellido, email, create_at,foto) VALUES ('Isabel', 'Jimenez', 'isabeljimenez@yahoo.com', '2022-03-05','');
INSERT INTO clientes(nombre, apellido, email, create_at,foto) VALUES ('Mario', 'Martinez', 'mariomartinez@gmail.com', '2021-12-25','');
INSERT INTO clientes(nombre, apellido, email, create_at,foto) VALUES ('Alejandro', 'Guzman', 'alejandroguzman@yahoo.com', '2022-01-08','');

-- Tabla productos
INSERT INTO productos(nombre,precio,create_at) VALUES   ('Panasonic Pantalla LCD',529000, NOW());
INSERT INTO productos(nombre, precio, create_at) VALUES ('Sony PlayStation 5', 749900, NOW());
INSERT INTO productos(nombre, precio, create_at) VALUES ('Apple AirPods Pro', 259900, NOW());
INSERT INTO productos(nombre, precio, create_at) VALUES ('Samsung Galaxy S21 Ultra', 1449900, NOW());
INSERT INTO productos(nombre, precio, create_at) VALUES ('LG OLED TV', 799000, NOW());
INSERT INTO productos(nombre, precio, create_at) VALUES ('Microsoft Surface Laptop 4', 1299900, NOW());
INSERT INTO productos(nombre, precio, create_at) VALUES ('Amazon Echo Dot (4th Gen)', 49900, NOW());
INSERT INTO productos(nombre, precio, create_at) VALUES ('Fitbit Versa 3', 299900, NOW());
INSERT INTO productos(nombre, precio, create_at) VALUES ('GoPro HERO10 Black', 599900, NOW());
INSERT INTO productos(nombre, precio, create_at) VALUES ('DJI Mavic Air 2S', 1499900, NOW());
INSERT INTO productos(nombre, precio, create_at) VALUES ('Bose QuietComfort 35 II', 279900, NOW());

-- Facturas
-- INSERT INTO facturas(descripcion,observacion,cliente_id,create_at) VALUES ('Factura equipos de oficina',null,1,NOW());
-- INSERT INTO facturas_items (cantidad,factura_id, producto_id) VALUES (1,1,1);
-- INSERT INTO facturas_items (cantidad,factura_id, producto_id) VALUES (2,1,4);
-- INSERT INTO facturas_items (cantidad,factura_id, producto_id) VALUES (1,1,5);
-- INSERT INTO facturas_items (cantidad,factura_id, producto_id) VALUES (1,1,7);
--
-- INSERT INTO facturas(descripcion,observacion,cliente_id,create_at) VALUES ('Factura Bicicleta','alguna nota',1,NOW());
-- INSERT INTO facturas_items(cantidad,factura_id, producto_id) VALUES (3,2,5);
INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura equipos de oficina', null, 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 1);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(2, 1, 4);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 5);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 7);

INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura Bicicleta', 'Alguna nota importante!', 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(3, 2, 6);

-- Creamos usuarios y roles
INSERT INTO  users(username, password, enabled) VALUES ('aethos','$2a$10$vxMiiX5gOGdqSI/CE61s.O4.SXNo07rEv6J1JieS1K8pv/T7yY582',1);
INSERT INTO  users(username, password, enabled) VALUES ('admin','$2a$10$TDZ4YY7WOJbV2SCyxxVM.eHjZJPgOTqMTgg4vmhf2q0038WH6CDiC',1);

INSERT INTO authorities(user_id, authority) VALUES (1,'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES (2,'ROLE_ADMIN');
INSERT INTO authorities(user_id, authority) VALUES (2,'ROLE_USER');