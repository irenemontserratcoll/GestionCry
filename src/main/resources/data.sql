-- Administrador
INSERT IGNORE INTO administradores (id, nombre, correo, contrasena) VALUES
(2, 'admin', 'admin@gmail.es', 'admin');

-- Espacio individual
INSERT IGNORE INTO espacios_individuales (id, piso, numero_asiento, tiempo_reservado) VALUES
(1, 1, 12, '2 horas'),
(2, 2, 8, '1 hora'),
(3, 3, 5, null); -- tiempo_reservado puede ser nulo

-- Libros
INSERT IGNORE INTO libros (id, titulo, autor, isbn) VALUES 
(1, '1984', 'George Orwell', '978-0451524935'),
(2, 'Cien anos de soledad', 'Gabriel Garcia Marquez', '978-0307474728'),
(3, 'El Principito', 'Antoine de Saint-Exupery', '978-0156012195');

-- Ordenadores
INSERT IGNORE INTO ordenadores (id, marca, modelo, numero_serie, disponible) VALUES 
(1, 'HP', 'EliteBook 840', 'SN12345678', true),
(2, 'Dell', 'XPS 13', 'SN87654321', false),
(3, 'Lenovo', 'ThinkPad X1', 'SN11223344', true);

-- Reservas
INSERT IGNORE INTO reservas (id, nombre_cliente, email_cliente, fecha_reserva, hora_reserva, num_personas) VALUES
(1, 'Ana Torres', 'ana@gmail.com', '2025-04-10', '10:00', 2),
(2, 'Luis Mendoza', 'luis@gmail.com', '2025-04-11', '12:30', 4),
(3, 'Claudia Vega', 'claudia@gmail.com', '2025-04-12', '09:00', 1);

-- Salas grupales
INSERT IGNORE INTO salas_grupales (id, piso, numero_sala, numero_personas) VALUES 
(1, 1, 101, 4),
(2, 2, 202, 6),
(3, 3, 303, 8),
(1, 1, 102, 4),
(2, 2, 203, 6),
(3, 3, 304, 8);

-- Usuarios
INSERT IGNORE INTO usuarios (id, nombre, correo, contrasena) VALUES 
(3, 'Juan Pérez', 'juan@gmail.com', 'pass'),
(2, 'María López', 'maria@gmail.com', 'pass'),
(4, 'Ana Torres', 'ana@gmail.com', 'pass'),
(5, 'Luis Mendoza', 'luis@gmail.com', 'pass'),
(6, 'Claudia Vega', 'claudia@gmail.com', 'pass');

