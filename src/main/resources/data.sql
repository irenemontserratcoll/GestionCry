-- Administrador
INSERT IGNORE INTO administradores (id, nombre, correo, contrasena) VALUES 
(1, 'admin', 'admin', 'admin');
-- Usuarios
INSERT IGNORE INTO usuarios (id, nombre, correo, contrasena) VALUES  
(1, 'Idoia', 'idoia@gmail.es', 'pass'),
(3, 'Juan Pérez', 'juan@gmail.com', 'pass'),
(2, 'María López', 'maria@gmail.com', 'pass'),
(4, 'Ana Torres', 'ana@gmail.com', 'pass'),
(5, 'Luis Mendoza', 'luis@gmail.com', 'pass'),
(6, 'Claudia Vega', 'claudia@gmail.com', 'pass');

-- Espacio individual
INSERT IGNORE INTO espacios_individuales (id, piso, numero_asiento, tiempo_reservado) VALUES 
(1, 1, 12, '2 horas'),
(2, 2, 8, '1 hora'),
(3, 3, 5, null), -- tiempo_reservado puede ser nulo
(4, 1, 15, '3 horas'),
(5, 2, 10, null),
(6, 3, 7, '30 minutos');

-- Libros
INSERT IGNORE INTO libros (id, titulo, autor, isbn) VALUES  
(7, '1984', 'George Orwell', '978-0451524935'),
(8, 'Cien anos de soledad', 'Gabriel Garcia Marquez', '978-0307474728'),
(9, 'El Principito', 'Antoine de Saint-Exupery', '978-0156012195'),
(10, 'Don Quijote de la Mancha', 'Miguel de Cervantes', '978-8491050297'),
(11, 'Fahrenheit 451', 'Ray Bradbury', '978-1451673319'),
(12, 'Orgullo y prejuicio', 'Jane Austen', '978-0141439518');

-- Recursos Reservables
INSERT IGNORE INTO recursos_reservables (id) VALUES
(1),
(2),
(3),
(4),
(5),
(6),
(7),
(8),
(9),
(10),
(11),
(12);

-- Ordenadores
INSERT IGNORE INTO ordenadores (id, marca, modelo, numero_serie, disponible) VALUES  
(1, 'HP', 'EliteBook 840', 'SN12345678', true),
(2, 'Dell', 'XPS 13', 'SN87654321', false),
(3, 'Lenovo', 'ThinkPad X1', 'SN11223344', true),
(4, 'Apple', 'MacBook Air', 'SN55667788', true),
(5, 'Asus', 'ZenBook 14', 'SN99887766', false),
(6, 'Acer', 'Swift 3', 'SN44556677', true);

-- Salas grupales
INSERT IGNORE INTO salas_grupales (id, piso, numero_sala, numero_personas) VALUES  
(1, 1, 101, 4),
(2, 2, 202, 6),
(3, 3, 303, 8),
(4, 1, 102, 10),
(5, 2, 203, 12);

-- Reservas (ahora que todas las tablas relacionadas ya tienen datos)
INSERT IGNORE INTO reservas (id, nombre_cliente, email_cliente, fecha_reserva, hora_reserva, num_personas, libro_id, ordenador_id, sala_grupal_id, espacio_individual_id) VALUES 
(1, 'Ana Torres', 'ana@gmail.com', '2025-04-10', '10:00', 2, 7, NULL, NULL, NULL), -- Reservó un libro
(2, 'Luis Mendoza', 'luis@gmail.com', '2025-04-11', '12:30', 4, NULL, 2, NULL, NULL), -- Reservó un ordenador
(3, 'Claudia Vega', 'claudia@gmail.com', '2025-04-12', '09:00', 1, NULL, NULL, NULL, 3); -- Reservó un espacio individual