-- ============================================
-- BASE DE DATOS: biblioteca_db
-- Universidad Don Bosco - POO404
-- Desafío Práctico #02
-- ============================================

CREATE DATABASE IF NOT EXISTS biblioteca_db;
USE biblioteca_db;

-- Tabla: autor
CREATE TABLE IF NOT EXISTS autor (
    id_autor      INT AUTO_INCREMENT PRIMARY KEY,
    nombre        VARCHAR(100) NOT NULL,
    nacionalidad  VARCHAR(80)  NOT NULL
);

-- Tabla: categoria
CREATE TABLE IF NOT EXISTS categoria (
    id_categoria     INT AUTO_INCREMENT PRIMARY KEY,
    nombre_categoria VARCHAR(80) NOT NULL
);

-- Tabla: libro
CREATE TABLE IF NOT EXISTS libro (
    id_libro         INT AUTO_INCREMENT PRIMARY KEY,
    titulo           VARCHAR(200) NOT NULL,
    anio_publicacion INT          NOT NULL,
    id_autor         INT          NOT NULL,
    id_categoria     INT          NOT NULL,
    CONSTRAINT fk_libro_autor     FOREIGN KEY (id_autor)     REFERENCES autor(id_autor),
    CONSTRAINT fk_libro_categoria FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
);

-- ============================================
-- DATOS DE EJEMPLO
-- ============================================

INSERT INTO autor (nombre, nacionalidad) VALUES
('Gabriel García Márquez', 'Colombiana'),
('J.K. Rowling',           'Británica'),
('Isabel Allende',         'Chilena'),
('Paulo Coelho',           'Brasileña'),
('Stephen King',           'Estadounidense');

INSERT INTO categoria (nombre_categoria) VALUES
('Ficción'),
('Fantasía'),
('Terror'),
('Romance'),
('Ciencia Ficción'),
('Biografía'),
('Historia');

INSERT INTO libro (titulo, anio_publicacion, id_autor, id_categoria) VALUES
('Cien años de soledad',      1967, 1, 1),
('El amor en los tiempos del cólera', 1985, 1, 4),
('Harry Potter y la piedra filosofal', 1997, 2, 2),
('La casa de los espíritus',  1982, 3, 1),
('El alquimista',             1988, 4, 1),
('It',                        1986, 5, 3);
