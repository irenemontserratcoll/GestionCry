-- 1) Administradores
CREATE TABLE IF NOT EXISTS administradores (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  correo VARCHAR(100) UNIQUE NOT NULL,
  contrasena VARCHAR(100) NOT NULL
);

-- 2) Usuarios
CREATE TABLE IF NOT EXISTS usuarios (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  correo VARCHAR(100) UNIQUE NOT NULL,
  contrasena VARCHAR(100) NOT NULL
);

-- 4) Recursos Reservables (tabla base)
CREATE TABLE IF NOT EXISTS recursos_reservables (
  id INT AUTO_INCREMENT PRIMARY KEY
  );

-- 3) Espacios individuales
CREATE TABLE IF NOT EXISTS espacios_individuales (
  id INT AUTO_INCREMENT PRIMARY KEY,
  piso INT NOT NULL,
  numero_asiento INT NOT NULL,
  tiempo_reservado VARCHAR(50)
);

-- 4) Libros
CREATE TABLE IF NOT EXISTS libros (
  id INT AUTO_INCREMENT PRIMARY KEY,
  titulo VARCHAR(255) NOT NULL,
  autor VARCHAR(255) NOT NULL,
  isbn VARCHAR(20) NOT NULL
);

-- 5) Ordenadores
CREATE TABLE IF NOT EXISTS ordenadores (
  id INT PRIMARY KEY,
  marca VARCHAR(100) NOT NULL,
  modelo VARCHAR(100) NOT NULL,
  numero_serie VARCHAR(100) NOT NULL,
  disponible BOOLEAN NOT NULL,
  CONSTRAINT fk_ordenador_recurso
    FOREIGN KEY (id) REFERENCES recursos_reservables(id)
);


-- 6) Salas grupales
CREATE TABLE IF NOT EXISTS salas_grupales (
  id INT AUTO_INCREMENT PRIMARY KEY,
  piso INT NOT NULL,
  numero_sala INT NOT NULL,
  numero_personas INT NOT NULL
);


-- 7) Reservas (¡aquí se crean las FK!)
CREATE TABLE IF NOT EXISTS reservas (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre_cliente VARCHAR(255) NOT NULL,
  email_cliente VARCHAR(255) NOT NULL,
  fecha_reserva DATE NOT NULL,
  hora_reserva TIME NOT NULL,
  num_personas INT NOT NULL,
  
  libro_id INT,
  ordenador_id INT,
  sala_grupal_id INT,
  espacio_individual_id INT,

  CONSTRAINT fk_reserva_libro
    FOREIGN KEY (libro_id) REFERENCES libros(id),
  CONSTRAINT fk_reserva_ordenador
    FOREIGN KEY (ordenador_id) REFERENCES ordenadores(id),
  CONSTRAINT fk_reserva_sala_grupal
    FOREIGN KEY (sala_grupal_id) REFERENCES salas_grupales(id),
  CONSTRAINT fk_reserva_espacio_ind
    FOREIGN KEY (espacio_individual_id) REFERENCES espacios_individuales(id)
);
