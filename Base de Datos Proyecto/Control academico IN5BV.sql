/*
* Estudiantes:
* Lionar Gerardy Villalta Barrientos     2021329
* Marcos Isaac Alcantara Hernandez       2021332

* Código técnico: IN5BV
* Grupo: 1 dia Jueves.
* Fecha de creación: 26/04/2022
* Fecha de modificación:
*/

/*Ultima Modificacion martes 14/06/2022*/
DROP DATABASE IF EXISTS db_control_academico_in5bv;
CREATE DATABASE db_control_academico_in5bv;
USE db_control_academico_in5bv;

CREATE TABLE IF NOT EXISTS alumnos (
	carne VARCHAR(16) NOT NULL,
    nombre1 VARCHAR(15) NOT NULL,
    nombre2 VARCHAR(15) NULL,
    nombre3 VARCHAR(15) NULL,
    apellido1 VARCHAR(15) NOT NULL,
    apellido2 VARCHAR(15) NULL,
    
    PRIMARY KEY (carne)
);

CREATE TABLE IF NOT EXISTS horarios(
	id INT NOT NULL AUTO_INCREMENT,
    horario_inicio TIME NOT NULL,
    horario_final TIME NOT NULL,
    lunes TINYINT(1),
	martes TINYINT(1),
    miercoles TINYINT(1),
    jueves TINYINT(1),
    viernes TINYINT(1),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS salones(
	codigo_salon VARCHAR(5) NOT NULL,
    descripcion VARCHAR(45),
    capacidad_maxima INT,
    edificio VARCHAR(15),
    nivel INT,
    PRIMARY KEY (codigo_salon)
);

CREATE TABLE IF NOT EXISTS carreras_tecnicas(
	codigo_tecnico VARCHAR(6) NOT NULL,
    carrera VARCHAR(45),
    grado VARCHAR(10),
    seccion CHAR(1),
    jornada VARCHAR(10),
	PRIMARY KEY (codigo_tecnico)
);

CREATE TABLE IF NOT EXISTS instructores(
	id INT NOT NULL AUTO_INCREMENT,
    nombre1 VARCHAR(15),
    nombre2 VARCHAR(15),
    nombre3 VARCHAR(15),
    apellido1 VARCHAR(15),
    apellido2 VARCHAR(15),
    direccion VARCHAR(45),
    email VARCHAR(45),
    telefono VARCHAR(8),
    fecha_nacimiento DATE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS cursos (
	id INT NOT NULL AUTO_INCREMENT,
    nombre_curso VARCHAR(255),
    ciclo YEAR,
    cupo_maximo INT,
    cupo_minimo INT,
    carrera_tecnica_id VARCHAR(128),
    horario_id INT,
    instructor_id INT,
    salon_id VARCHAR(5),
    PRIMARY KEY (id),
    CONSTRAINT fk_cursos_carreras_tecnicas
		FOREIGN KEY (carrera_tecnica_id)
        REFERENCES carreras_tecnicas(codigo_tecnico)
        ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_cursos_horarios
		FOREIGN KEY (horario_id)
        REFERENCES horarios(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_cursos_instructores
		FOREIGN KEY (instructor_id)
        REFERENCES instructores(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_cursos_salones
		FOREIGN KEY (salon_id)
        REFERENCES salones(codigo_salon)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS asignaciones_alumnos(
id INT NOT NULL AUTO_INCREMENT,
alumno_id VARCHAR(16) NOT NULL,
curso_id INT NOT NULL,
fecha_asignacion DATETIME NULL,
CONSTRAINT pk_asignaciones_alumnos PRIMARY KEY (id),
CONSTRAINT fk_asignaciones_alumnos_alumno
FOREIGN KEY (alumno_id) REFERENCES alumnos(carne)
ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT fk_asignaciones_alumnos_cursos
FOREIGN KEY (curso_id) REFERENCES cursos(id)
ON DELETE CASCADE ON UPDATE CASCADE
);


     
     /*****************************************INSERT**************************************************************************/
/*************************************************INSERT*************************************************************************/
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_create $$
CREATE PROCEDURE sp_alumnos_create(
	IN _carne VARCHAR(16),
	IN _nombre1 VARCHAR(15),
	IN _nombre2 VARCHAR(15),
	IN _nombre3 VARCHAR(15),
	IN _apellido1 VARCHAR(15),
	IN _apellido2 VARCHAR(15)

)
BEGIN
INSERT alumnos (
	carne,
	nombre1,
	nombre2,
	nombre3,
	apellido1,
	apellido2
)
VALUES
(
	_carne,
	_nombre1,
	_nombre2,
	_nombre3,
	_apellido1,
	_apellido2
);
END$$
DELIMITER ;

select * from alumnos;

call sp_alumnos_create ("2021329", "Daniel", "Jósue", "Alecxander", "Hernandes", "Peréz");
call sp_alumnos_create ("2021340", "Luis", "Jósue", "Toño", "Villalta", "chajon");
call sp_alumnos_create ("2020259", "Paola", "Yamiled", "Viviana", "Martines", "Contreras");
call sp_alumnos_create ("2021356", "Samanta", "Elizabeht", "Flores", "Sánchez", "Gracia");
call sp_alumnos_create ("2021026", "Deyvis", "Isaias", "Eduardo", "Lopéz", "Peréz");
call sp_alumnos_create ("2021328", "Fatima", "Yessenia", "Yenifer", "Alvarez", "Arevalos");
call sp_alumnos_create ("2020485", "Yeshua", "Jósue", "Alecxander", "Vasquez", "Hernandez");
call sp_alumnos_create ("2021222", "Isaac", "Wetterer", "Alecxander", "Temal", "Lopéz");
call sp_alumnos_create ("2021330", "Luis", "Jósue", "Eduardo", "Morales", "chajon");
call sp_alumnos_create ("2021485", "Julia", "Sofia", "Celine", "Rodas", "Chajon");

/*******************************************horarios****************************************************/

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_create $$
CREATE PROCEDURE sp_horarios_create(
	IN _horario_inicio TIME,
	IN _horario_final TIME,
	IN _lunes TINYINT(1),
	IN _martes TINYINT(1),
	IN _miercoles TINYINT(1),
	IN _jueves TINYINT(1),
	IN _viernes TINYINT(1)
)
BEGIN
INSERT horarios (
	horario_inicio,
	horario_final, 
	lunes, 
	martes, 
	miercoles, 
	jueves, 
	viernes 
    
)
VALUES
(
	_horario_inicio,
	_horario_final, 
	_lunes, 
	_martes, 
	_miercoles, 
	_jueves, 
	_viernes
);
END$$
DELIMITER ;

call sp_horarios_create ( "01:01:00", "17:00:00", 1, 0, 0, 0, 0);
call sp_horarios_create ( "12:30:00", "15:30:00", 0, 1, 0, 0, 0);
call sp_horarios_create ( "10:45:00", "14:00:00", 0, 0, 1, 0, 0);
call sp_horarios_create ( "07:10:35", "12:00:00", 0, 0, 0, 1, 0);
call sp_horarios_create ( "08:00:00", "11:30:20", 0, 0, 0, 0, 1);
call sp_horarios_create ( "12:45:00", "05:30:20", 1, 0, 0, 0, 0);
call sp_horarios_create ( "12:40:00", "05:00:20", 0, 1, 0, 0, 0);
call sp_horarios_create ( "07:00:00", "12:00:00", 0, 0, 1, 0, 0);
call sp_horarios_create ( "06:50:00", "11:30:20", 0, 0, 0, 1, 0);
call sp_horarios_create ( "12:30:00", "5:40:00", 0, 0, 0, 0, 1);
select * from horarios;

/*****************************salones***************************************/
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_create $$
CREATE PROCEDURE sp_salones_create(
	IN _codigo_salon VARCHAR(5),
	IN _capacidad_maxima INT,
	IN _edificio VARCHAR(15),
	IN _nivel INT,
    IN _descripcion VARCHAR(45)
)
BEGIN
INSERT salones (
	codigo_salon,
    capacidad_maxima,
	edificio,
	nivel,
    descripcion
    
)
VALUES
(
	_codigo_salon,
    _capacidad_maxima,
    _edificio,
    _nivel,
	_descripcion
);
END$$
DELIMITER ;

call sp_salones_create ("12345", 25, "Teatro Lux", 3, "Matematicas");
call sp_salones_create ("45678", 30, "Teatro Nacional", 1, "Sociales"); 
call sp_salones_create ("45612", 18, "Mozárabe", "2", "Literatura"); 
call sp_salones_create ("74185", 35, "Refugio", 4, "Quimica"); 
call sp_salones_create ("96385", 15, "Esperanza", 1, "Estadistica"); 
call sp_salones_create ("36914", 35, "Futuro", 3, "Etica"); 
call sp_salones_create ("12385", 12, "Plenitud", 4, "Tecnologia"); 
call sp_salones_create ("45896", 40, "Paraiso", 1, "Fisica");  
call sp_salones_create ("78916", 10, "Elite", 2, "Sociales"); 
call sp_salones_create ("54216", 17, "Fenix", 3, "Historia"); 
select * from salones;

/**********************************************************************************/
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_create $$
CREATE PROCEDURE sp_carreras_tecnicas_create(
	IN _codigo_tecnico VARCHAR(6),
	IN _carrera VARCHAR(45),
	IN _grado VARCHAR(10),
	IN _seccion CHAR(1),
	IN _jornada VARCHAR(10)
)
BEGIN
INSERT carreras_tecnicas (
	codigo_tecnico,
	carrera,
	grado,
	seccion,
	jornada
    
)
VALUES
(
	_codigo_tecnico,
	_carrera,
	_grado,
	_seccion,
	_jornada
);
END$$
DELIMITER ;


call sp_carreras_tecnicas_create ("123458", "informatica", "5to Perito", "B", "Vespertina");

call sp_carreras_tecnicas_create ("456258", "Mecanica", "6to Perito", "A", "Vespertina");
call sp_carreras_tecnicas_create ("985456", "Electricista", "4to Perito", "C", "Vespertina");
call sp_carreras_tecnicas_create ("159357", "Dibujo_Técnico", "5to Perito", "A", "Matutina");
call sp_carreras_tecnicas_create ("482321", "Electronica", "6to Perito", "B", "Matutina");
call sp_carreras_tecnicas_create ("852471", "Informatica", "6to Perito", "A", "Vespertina");
call sp_carreras_tecnicas_create ("963753", "Mecanica", "4to Perito", "B", "Vespertina");
call sp_carreras_tecnicas_create ("642159", "Electricista", "6to Perito", "B", "Matutina");
call sp_carreras_tecnicas_create ("789451", "Dibujo_Técnico", "4to Perito", "C", "Matutina");
call sp_carreras_tecnicas_create ("147492", "Electronica", "5to Perito", "A", "Vespertina");
select * from carreras_tecnicas;

/*********************************************instructores***************************************/
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_create $$
CREATE PROCEDURE sp_instructores_create(
	IN _nombre1 VARCHAR(15),
	IN _nombre2 VARCHAR(15),
	IN _nombre3 VARCHAR(15),
	IN _apellido1 VARCHAR(15),
	IN _apellido2 VARCHAR(15),
	IN _direccion VARCHAR(45),
	IN _email VARCHAR(45),
	IN _telefono VARCHAR(8),
	IN _fecha_nacimiento DATE
)
BEGIN
INSERT instructores (
	nombre1,
	nombre2,
	nombre3,
	apellido1,
	apellido2,
	direccion,
	email,
	telefono,
	fecha_nacimiento
    
)
VALUES
(
	_nombre1,
	_nombre2,
	_nombre3,
	_apellido1,
	_apellido2,
	_direccion,
	_email,
	_telefono,
	_fecha_nacimiento
);
END$$
DELIMITER ;

call sp_instructores_create ( "Julio", "Alverto", "Rafael", "Martinez", "López", "Zona_9", "juliomartinez456@gmail.com", "45213265", '2001-04-15' );
call sp_instructores_create ( "Julian", "Adolfo", "Rafael", "González", "Pérez", "Zona_10", "julianperez852@gmail.com", "12345678", '2000-10-05' );
call sp_instructores_create ( "Maricela", "Yohana", "Rosario", "Carrillo", "Mendez", "Zona_7", "maricelamendez896@gmail.com", "89632147", '2000-12-25' );
call sp_instructores_create ( "Kenny", "Samanta", "Rosmeri", "Hernandez", "Cabrera", "Zona_4", "kennycabrera456@gmail.com", "45213268", '2003-01-28' );
call sp_instructores_create ( "Edilser", "Camilo", "Antonio", "Revolorio", "García", "Zona_15", "antoniorevolorio412@gmail.com", "65213265", '2001-09-18' );
call sp_instructores_create ( "Vicki", "Liseth", "Roselia", "Martinez", "Calel", "Zona_4", "vickimartinez879@gmail.com", "37213265", '2004-06-20' );
call sp_instructores_create ( "Leonardo", "Yordin", "Wilson", "Gónzales", "López", "Zona_9", "leonardolopez2584@gmail.com", "45298265", '2002-02-09' );
call sp_instructores_create ( "Fabiola", "Alejandra", "Marleny", "Rodas", "Pérez", "Zona_2", "alejandrarodas589@gmail.com", "45213415", '2001-07-04' );
call sp_instructores_create ( "Tereso", "Alverto", "Rafael", "Contreras", "Calel", "Zona_18", "teresocalel987@gmail.com", "89213265", '2004-04-15' );
call sp_instructores_create ( "Ervin", "Alverto", "Eduardo", "Pérez", "Pérez", "Zona_9", "ervinperez586@gmail.com", "45258265", '2000-01-25' );
select * from instructores; 

/************************************************cursos*************************************************/
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_create $$
CREATE PROCEDURE sp_cursos_create(
	IN _nombre_curso VARCHAR(255),
	IN _ciclo YEAR,
	IN _cupo_maximo INT,
	IN _cupo_minimo INT,
	IN _carrera_tecnica_id VARCHAR(128),
	IN _horario_id INT,
	IN _instructor_id INT,
	IN _salon_id VARCHAR(5)
)
BEGIN
INSERT cursos (
	nombre_curso,
	ciclo,
	cupo_maximo,
	cupo_minimo,
	carrera_tecnica_id,
	horario_id,
	instructor_id,
	salon_id
    
)
VALUES
(
	_nombre_curso,
	_ciclo,
	_cupo_maximo,
	_cupo_minimo,
	_carrera_tecnica_id,
	_horario_id,
	_instructor_id,
	_salon_id
);
END$$
DELIMITER ;

call sp_cursos_create ("Sistema e informatica", 2022, 60, 30, "123458", 1, 1, "12345");
call sp_cursos_create ("Producción y operación", 2022, 50, 25, "147492", 2, 2, "12385");
call sp_cursos_create ("Telecomunicaciones", 2022, 30, 17, "159357", 3, 3, "36914");
call sp_cursos_create ("Negosación", 2022, 40, 20, "456258", 4, 4, "45612");
call sp_cursos_create ("Ingenieria", 2022, 45, 27, "482321", 5, 5, "45678");
call sp_cursos_create ("Educación y pedagogía", 2022, 70, 45, "642159", 6, 6, "45896");
call sp_cursos_create ("Gastronomia", 2022, 60, 40, "789451", 7, 7, "54216");
call sp_cursos_create ("Economia solidaria", 2022, 20, 17, "852471", 8, 8, "74185");
call sp_cursos_create ("Comerciales y ventas", 2022, 37, 25, "963753", 9, 9, "78916");
call sp_cursos_create ("Ciencias biologícas", 2022, 40, 29, "985456", 10, 10, "96385");
select * from instructores; 

/*****************************************************asignaciones_alumnos*******************************************/
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_create $$
CREATE PROCEDURE sp_asignaciones_alumnos_create(
IN _alumno_id VARCHAR(16),
IN _curso_id INT,
IN _fecha_asignacion DATETIME
)
BEGIN
INSERT INTO asignaciones_alumnos(
alumno_id,
curso_id,
fecha_asignacion
) VALUES (
_alumno_id,
_curso_id,
_fecha_asignacion
);
END $$
DELIMITER ;

call sp_asignaciones_alumnos_create ( "2020259", 1, "2022-01-21 16:45:2");
call sp_asignaciones_alumnos_create ("2020485", 2, "2022-01-20 13:10:2");
call sp_asignaciones_alumnos_create ( "2021026", 3, "2022-01-17 12:45:3");
call sp_asignaciones_alumnos_create ( "2021222", 4, "2022-01-18 12:50:10");
call sp_asignaciones_alumnos_create ( "2021328", 5, "2022-01-19 14:25:1");
call sp_asignaciones_alumnos_create ( "2021329", 6, "2022-01-25 15:45:2");
call sp_asignaciones_alumnos_create ( "2021330", 7, "2022-01-18 11:10:2");
call sp_asignaciones_alumnos_create ( "2021340", 8, "2022-01-11 12:45:3");
call sp_asignaciones_alumnos_create ( "2021356", 9, "2022-01-05 13:50:11");
call sp_asignaciones_alumnos_create ( "2021485", 10, "2022-01-09 14:25:1");

select * from asignaciones_alumnos;
     

/***************************************************READ*************************************************/
/******************************************READ**********************************************************/
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_read $$
CREATE PROCEDURE sp_alumnos_read()
BEGIN
SELECT
	a.carne,
	a.nombre1,
	a.nombre2,
	a.nombre3,
	a.apellido1,
	a.apellido2
FROM
alumnos AS a;
END $$
DELIMITER ;

call sp_alumnos_read();

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_report $$
CREATE PROCEDURE sp_alumnos_report()
BEGIN
	SELECT
		a.carne,
		concat(
		a.nombre1, " ",
		IF(a.nombre2 IS NULL, "", a.nombre2), " ",
		IF(a.nombre3 IS NULL, "", a.nombre3), " ",
		a.apellido1, " ",
		IF(a.apellido2 IS NULL, "", a.apellido2), " "
		) AS nombre_completo
	FROM
	alumnos AS a;
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_read $$
CREATE PROCEDURE sp_horarios_read()
BEGIN
SELECT
	h.id,
	h.horario_inicio,
	h.horario_final,
	h.lunes,
	h.martes,
	h.miercoles,
	h.jueves,
	h.viernes
FROM
horarios AS h;
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_report $$
CREATE PROCEDURE sp_horarios_report()
	BEGIN
	SELECT
		h.id,
		h.horario_inicio,
		h.horario_final,
		if(h.lunes IS TRUE, "Si", "No") AS lunes,
		if(h.martes IS TRUE, "Si", "No") AS martes,
		if(h.miercoles IS TRUE, "Si", "No") AS miercoles,
		if(h.jueves IS TRUE, "Si", "No") AS jueves,
		if(h.viernes IS TRUE, "Si", "No") AS viernes
	FROM
	horarios AS h;
END $$
DELIMITER ;


DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_read $$
CREATE PROCEDURE sp_salones_read()
BEGIN
	SELECT
		s.codigo_salon,
		s.descripcion,
		s.capacidad_maxima,
		s.edificio,
		s.nivel
	FROM
	salones AS s;
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_report $$
CREATE PROCEDURE sp_salones_report()
BEGIN
	SELECT
		s.codigo_salon,
		s.descripcion,
		s.capacidad_maxima,
		s.edificio,
		s.nivel
	FROM
	salones AS s;
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_read $$
CREATE PROCEDURE sp_carreras_tecnicas_read()
BEGIN
	SELECT
		c.codigo_tecnico,
		c.carrera,
		c.grado,
		c.seccion,
		c.jornada
	FROM
	carreras_tecnicas AS c;
END $$
DELIMITER ;



DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_report $$
CREATE PROCEDURE sp_carreras_tecnicas_report()
BEGIN
	SELECT
		c.codigo_tecnico,
		c.carrera,
		c.grado,
		c.seccion,
		c.jornada
	FROM
	carreras_tecnicas AS c;
END $$
DELIMITER ;



DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_read $$
CREATE PROCEDURE sp_instructores_read()
BEGIN
SELECT
	i.id,
	i.nombre1,
	i.nombre2,
	i.nombre3,
	i.apellido1,
	i.apellido2,
	i.direccion,
	i.email,
	i.telefono,
	i.fecha_nacimiento
FROM
instructores AS i;
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_report $$
CREATE PROCEDURE sp_instructores_report()
BEGIN
  SELECT
	    i.id,
		concat(
		i.nombre1, " ",
		IF(i.nombre2 IS NULL, "", i.nombre2), " ",
		IF(i.nombre3 IS NULL, "", i.nombre3), " ",
		i.apellido1, " ",
		IF(i.apellido2 IS NULL, "", i.apellido2), " "
		) AS nombre_completo,
		i.direccion,
		i.email,
		i.telefono,
		i.fecha_nacimiento
	FROM
	instructores AS i;
END $$
DELIMITER ;



DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_read $$
CREATE PROCEDURE sp_cursos_read()
BEGIN
SELECT
	cu.id,
	cu.nombre_curso,
	cu.ciclo,
	cu.cupo_maximo,
	cu.cupo_minimo,
	cu.carrera_tecnica_id,
	cu.horario_id,
	cu.instructor_id,
	cu.salon_id
FROM
cursos AS cu;
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_report $$
CREATE PROCEDURE sp_cursos_report()
BEGIN
	SELECT
		cu.id,
		cu.nombre_curso,
		cu.ciclo,
		cu.cupo_maximo,
		cu.cupo_minimo,
		cu.carrera_tecnica_id,
		c.carrera,
		cu.horario_id,
		cu.instructor_id,
		concat(
			i.nombre1, " ",
			IF(i.nombre2 IS NULL, "", i.nombre2), " ",
			IF(i.nombre3 IS NULL, "", i.nombre3), " ",
			i.apellido1, " ",
			IF(i.apellido2 IS NULL, "", i.apellido2), " "
			) AS nombre_completo,
		cu.salon_id
	FROM
	cursos AS cu
	INNER JOIN carreras_tecnicas as c
	INNER JOIN instructores AS i
    INNER JOIN salones as s
    ON
     cu.carrera_tecnica_id = c.codigo_tecnico AND cu.instructor_id = i.id;
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_report_by_id $$
CREATE PROCEDURE sp_cursos_report_by_id(IN _id INT)
BEGIN
	SELECT
		cu.id,
		cu.nombre_curso,
		cu.ciclo,
		cu.cupo_maximo,
		cu.cupo_minimo,
		cu.carrera_tecnica_id,
		c.carrera,
		cu.horario_id,
		cu.instructor_id,
		concat(
			i.nombre1, " ",
			IF(i.nombre2 IS NULL, "", i.nombre2), " ",
			IF(i.nombre3 IS NULL, "", i.nombre3), " ",
			i.apellido1, " ",
			IF(i.apellido2 IS NULL, "", i.apellido2), " "
			) AS nombre_completo,
		cu.salon_id
	FROM
	cursos AS cu
	INNER JOIN carreras_tecnicas as c
	INNER JOIN instructores AS i
    
    ON
     cu.carrera_tecnica_id = c.codigo_tecnico AND cu.instructor_id = i.id
     WHERE 
     cu.id = _id;
END $$
DELIMITER ;



DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_read $$
CREATE PROCEDURE sp_asignaciones_alumnos_read()
BEGIN
SELECT
	a.id,
	a.alumno_id,
	a.curso_id,
	a.fecha_asignacion
FROM
asignaciones_alumnos AS a;
END $$
DELIMITER ;


DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_report $$
CREATE PROCEDURE sp_asignaciones_alumnos_report()
BEGIN
	SELECT
		aa.id,
		aa.alumno_id,
        
		concat(
		a.nombre1, " ",
		IF(a.nombre2 IS NULL, "", a.nombre2), " ",
		IF(a.nombre3 IS NULL, "", a.nombre3), " ",
		a.apellido1, " ",
		IF(a.apellido2 IS NULL, "", a.apellido2), " "
		) AS nombre_completo,
        aa.curso_id,
        c.nombre_curso,
	    aa.fecha_asignacion	
	FROM
	asignaciones_alumnos AS aa
    INNER JOIN alumnos as a
    INNER JOIN cursos AS c
    ON
    aa.alumno_id = a.carne AND aa.curso_id = c.id;
END $$
DELIMITER ;



DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_report_by_id $$
CREATE PROCEDURE sp_asignaciones_alumnos_report_by_id(IN _id INT)
BEGIN
	SELECT
		aa.id,
		aa.alumno_id,
        
		concat(
		a.nombre1, " ",
		IF(a.nombre2 IS NULL, "", a.nombre2), " ",
		IF(a.nombre3 IS NULL, "", a.nombre3), " ",
		a.apellido1, " ",
		IF(a.apellido2 IS NULL, "", a.apellido2), " "
		) AS nombre_completo,
        aa.curso_id,
        c.nombre_curso,
	    aa.fecha_asignacion	
	FROM
	asignaciones_alumnos AS aa
    INNER JOIN alumnos as a
    INNER JOIN cursos AS c
    ON
    aa.alumno_id = a.carne AND aa.curso_id = c.id
    WHERE 
    aa.id = _id;
END $$
DELIMITER ;


/*******************************************Update******************************************************************************/
/**********************************************UPDATE**********************************************************************************/

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_update $$
CREATE PROCEDURE sp_alumnos_update(
	IN _carne VARCHAR(16),
	IN _nombre1 VARCHAR(15),
	IN _nombre2 VARCHAR(15),
    IN _nombre3 varchar(15),
	IN _apellido1 VARCHAR(15),
	IN _apellido2 VARCHAR(15)

)
BEGIN
UPDATE alumnos set nombre1 = _nombre1, nombre2 = _nombre2, nombre3 = _nombre3, apellido1 = _apellido1, apellido2 = _apellido2 WHERE carne = _carne;
END$$
DELIMITER ;

select * from alumnos;

call sp_alumnos_update ("2022329", "Axel", "Jósue", "",  "Hernandes", "Peréz");
call sp_alumnos_update ("2022330", "Emanuel", "Jósue","Luis",  "Gomes", "Chajon");
call sp_alumnos_update ("2022331",  "Yamiled", "Viviana","Karla", "Martines", "Aguilar");


/*******************************************horarios****************************************************/
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_update $$
CREATE PROCEDURE sp_horarios_update(
	IN _id INT,
	IN _horario_inicio TIME,
	IN _horario_final TIME,
	IN _lunes TINYINT(1),
	IN _martes TINYINT(1),
	IN _miercoles TINYINT(1),
	IN _jueves TINYINT(1),
    IN _viernes TINYINT(1)
)
BEGIN
 UPDATE horarios SET horario_inicio = _horario_inicio, horario_final = _horario_final, lunes = _lunes, martes = _martes, miercoles = _miercoles,
 jueves = _jueves, viernes = _viernes WHERE id = _id;
END$$
DELIMITER ;

call sp_horarios_update ( 1, "12:40:00", "17:05:00", 1, 0, 0, 0, 0);
call sp_horarios_update ( 2, "12:40:00", "17:10:00", 0, 1, 0, 0, 1 );
call sp_horarios_update (3, "12:40:00", "17:01:00", 0, 0, 1, 0, 0);
call sp_horarios_update ( 4, "12:40:00", "17:01:22", 0, 0, 0, 1, 1);

select * from horarios;
/*****************************salones***************************************/
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_update $$
CREATE PROCEDURE sp_salones_update(
	IN _codigo_salon VARCHAR(5),
	IN _capacidad_maxima INT,
	IN _edificio VARCHAR(15),
	IN _nivel INT,
    IN _descripcion VARCHAR(45)
)
BEGIN
UPDATE salones SET capacidad_maxima = _capacidad_maxima, edificio = _edificio, nivel = _nivel, descripcion = _descripcion WHERE codigo_salon = _codigo_salon;   
END$$
DELIMITER ;

call sp_salones_update ("12344", 30, "Mozarabe", 1, "Contabilidad"); 
call sp_salones_update ("12346", 30, "Mozarabe", 2 ,"Estadistica"); 
call sp_salones_update ("12347", 30, "Mozarabe" , 3, "Fisica"); 

select * from salones;

/************************************carreras_tecnicas**********************************************/
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_update $$
CREATE PROCEDURE sp_carreras_tecnicas_update(
	IN _codigo_tecnico VARCHAR(6),
	IN _carrera VARCHAR(45),
	IN _grado VARCHAR(10),
	IN _seccion CHAR(1),
	IN _jornada VARCHAR(10)
)
BEGIN
UPDATE Carreras_tecnicas set carrera = _carrera, grado = _grado, seccion = _seccion, jornada = _jornada WHERE codigo_tecnico = _codigo_tecnico;
END$$
DELIMITER ;


call sp_carreras_tecnicas_update ("987450", "informatica", "6to Perito", "A", "Vespertina");
call sp_carreras_tecnicas_update ("987457", "Mecanica", "6to Perito", "A", "Vespertina");
call sp_carreras_tecnicas_update ("987459", "Electricista", "6to Perito", "A", "Vespertina");

select * from carreras_tecnicas;

/*********************************************instructores***************************************/
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_update $$
CREATE PROCEDURE sp_instructores_update(
	IN _id INT,
	IN _nombre1 VARCHAR(15),
	IN _nombre2 VARCHAR(15),
	IN _nombre3 VARCHAR(15),
	IN _apellido1 VARCHAR(15),
	IN _apellido2 VARCHAR(15),
	IN _direccion VARCHAR(45),
	IN _email VARCHAR(45),
	IN _telefono VARCHAR(8),
	IN _fecha_nacimiento DATE
)
BEGIN
UPDATE instructores SET nombre1 = _nombre1, nombre2 = _nombre2, nombre3 = _nombre3, apellido1 = _apellido1, apellido2 = apellido2,
direccion = _direccion, email = _email, telefono = _telefono, fecha_nacimiento = _fecha_nacimiento WHERE id = _id;
END$$
DELIMITER ;

call sp_instructores_update (1, "Alexander", "", "Rafael", "Hernandez", "López", "Zona_9", "rafaelhernandez456@gmail.com", "45213265", '2004-01-06' );
call sp_instructores_update (2,  "Adolfo","", "Daniel", "Hernandez", "Pérez", "Zona_10", "adolfohernandez852@gmail.com", "12345678", '2004-01-05' );
call sp_instructores_update (3,  "Alvarado", "","Emanúel", "Hernandez", "Mendez", "Zona_7", "alvaradohernandez896@gmail.com", "89632147", '2004-01-07' );

select * from instructores; 

/************************************************cursos*************************************************/
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_update $$
CREATE PROCEDURE sp_cursos_update(
	IN _id INT,
	IN _nombre_curso VARCHAR(255),
	IN _ciclo YEAR,
	IN _cupo_maximo INT,
    IN _cupo_minimo int,
	IN _carrera_tecnica_id VARCHAR(128),
	IN _horario_id INT,
	IN _instructor_id INT,
	IN _salon_id VARCHAR(5)
)
BEGIN
UPDATE cursos set nombre_curso = _nombre_curso, ciclo = _ciclo, cupo_maximo = _cupo_maximo, cupo_minimo = _cupo_minimo, 
carrera_tecnica_id = _carrera_tecnica_id, horario_id = _horario_id, instructor_id = _instructor_id, salon_id = _salon_id
WHERE id = _id;
END$$
DELIMITER ;

call sp_cursos_update (1, "Sistema en aptomotris", 2022, 35, 25,  "123458", 1, 1, "12345");
call sp_cursos_update (2, "Sistema en informatica", 2022, 35, 25, "147492", 2, 2, "12385");
call sp_cursos_update (3, "Cisco", 2022, 35, 25, "159357", 3, 3, "36914");

select * from cursos;

/*****************************************************asignaciones_alumnos*******************************************/
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_update $$
CREATE PROCEDURE sp_asignaciones_alumnos_update(
IN _id INT,
IN _alumno_id VARCHAR(16),
IN _curso_id INT,
IN _fecha_asignacion DATETIME
)
BEGIN
UPDATE
asignaciones_alumnos
SET
alumno_id = _alumno_id,
curso_id = _curso_id,
fecha_asignacion = _fecha_asignacion
WHERE
id = _id;
END $$
DELIMITER ;

call sp_asignaciones_alumnos_update ("10", "2021485", 10, "2022-05-09 14:25:1");

select * from asignaciones_alumnos; 

/**********************************************************DELETE**********************************************************************/
/*******************************************DELETE******************************************************************************/

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_delete $$
CREATE PROCEDURE sp_alumnos_delete(
IN _carne VARCHAR(16)
)
BEGIN
DELETE FROM alumnos WHERE carne = _carne;
END$$
DELIMITER ;

call sp_alumnos_delete ("2022329");
call sp_alumnos_delete("2022330");
call sp_alumnos_delete ("2022331");
select * from alumnos;


/*******************************************horarios****************************************************/
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_delete $$
CREATE PROCEDURE sp_horarios_delete(
IN _id INT
)
BEGIN
DELETE FROM horarios WHERE id = _id;
END$$
DELIMITER ;

call sp_horarios_delete ( 11);
call sp_horarios_delete ( 12);
call sp_horarios_delete ( 13);
call sp_horarios_delete ( 14);

select * from horarios;

/*****************************salones***************************************/
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_delete $$
CREATE PROCEDURE sp_salones_delete(
IN _codigo_salon VARCHAR(5)
)
BEGIN
DELETE FROM salones WHERE codigo_salon = _codigo_salon;
END$$
DELIMITER ;

call sp_salones_delete ("12344" );
call sp_salones_delete ("12346");
call sp_salones_delete ("12347");
select * from salones;

/************************************carreras_tecnicas**********************************************/
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_delete $$
CREATE PROCEDURE sp_carreras_tecnicas_delete(
IN _codigo_tecnico VARCHAR(6)
)
BEGIN
DELETE FROM carreras_tecnicas WHERE codigo_tecnico = _codigo_tecnico;
END$$
DELIMITER ;

call sp_carreras_tecnicas_delete ("987450");
call sp_carreras_tecnicas_delete ("987457");
call sp_carreras_tecnicas_delete ("987459");

select * from carreras_tecnicas;

/*********************************************instructores***************************************/
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_delete $$
CREATE PROCEDURE sp_instructores_delete(
IN _id INT
)
BEGIN
DELETE FROM instructores WHERE id = _id;
END$$
DELIMITER ;

call sp_instructores_delete (11);
call sp_instructores_delete (12 );
call sp_instructores_delete (13);

select * from instructores; 

/************************************************cursos*************************************************/
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_delete $$
CREATE PROCEDURE sp_cursos_delete(
IN _id INT
)
BEGIN
DELETE FROM cursos WHERE id = _id;
END$$
DELIMITER ;

call sp_cursos_delete (11);
call sp_cursos_delete (12);
call sp_cursos_delete (13);

select * from cursos; 

/*****************************************************asignaciones_alumnos*******************************************/
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_delete $$
CREATE PROCEDURE sp_asignaciones_alumnos_delete(IN _id INT)
BEGIN
DELETE FROM asignaciones_alumnos WHERE id = _id;
END $$
DELIMITER ;


call sp_asignaciones_alumnos_delete ("11");

select * from asignaciones_alumnos; 
/***************************************************read_by_id*************************************************/
/******************************************read_by_id**********************************************************/
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_read_by_id $$
CREATE PROCEDURE sp_alumnos_read_by_id(
     IN _carne VARCHAR(16))
BEGIN
SELECT
	a.carne,
	a.nombre1,
	a.nombre2,
	a.nombre3,
	a.apellido1,
	a.apellido2
  FROM
    alumnos AS a
    WHERE carne = _carne;
END $$
DELIMITER ;



DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_read_by_id $$
CREATE PROCEDURE sp_horarios_read_by_id(
   IN _id INT
)
BEGIN
	SELECT
		h.id,
		h.horario_inicio,
		h.horario_final,
		h.lunes,
		h.martes,
		h.miercoles,
		h.jueves,
		h.viernes
	FROM
		horarios AS h
	WHERE 
		h.id=_id;
END $$
DELIMITER ;


DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_read_by_id $$
CREATE PROCEDURE sp_salones_read_by_id(
  IN _codigo_salon VARCHAR(5)
)
BEGIN
	SELECT
		s.codigo_salon,
		s.descripcion,
		s.capacidad_maxima,
		s.edificio,
		s.nivel
	FROM
		salones AS s
	WHERE 
		s.codigo_salon=_codigo_salon;
END $$
DELIMITER ;


DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_read_by_id $$
CREATE PROCEDURE sp_carreras_tecnicas_read_by_id(
IN _codigo_tecnico VARCHAR(6)
)
BEGIN
   SELECT
		c.codigo_tecnico,
		c.carrera,
		c.grado,
		c.seccion,
		c.jornada
	FROM
		carreras_tecnicas AS c
	WHERE c.codigo_tecnico= _codigo_tecnico;
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_read_by_id $$
CREATE PROCEDURE sp_instructores_read_by_id(
	IN _id INT
)
BEGIN
	SELECT
		i.id,
		i.nombre1,
		i.nombre2,
		i.nombre3,
		i.apellido1,
		i.apellido2,
		i.direccion,
		i.email,
		i.telefono,
		i.fecha_nacimiento
	FROM
		instructores AS i
	WHERE
		i.id=_id;
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_read_by_id $$
CREATE PROCEDURE sp_cursos_read_by_id(
	IN _id INT
)
BEGIN
	SELECT
		c.id,
		c.nombre_curso,
		c.ciclo,
		c.cupo_maximo,
		c.cupo_minimo,
		c.carrera_tecnica_id,
		c.horario_id,
		c.instructor_id,
		c.salon_id
	FROM
		cursos AS c
	WHERE
		c.id=_id;
END $$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_read_by_id $$
CREATE PROCEDURE sp_asignaciones_alumnos_read_by_id(IN _id INT)
BEGIN
SELECT
asignaciones_alumnos.id,
asignaciones_alumnos.alumno_id,
asignaciones_alumnos.curso_id,
asignaciones_alumnos.fecha_asignacion
FROM
asignaciones_alumnos
WHERE
id = _id;
END $$
DELIMITER ;




CREATE TABLE IF NOT EXISTS usuarios(
user VARCHAR(25) NOT NULL,
pass VARCHAR(225) NOT NULL,
nombre VARCHAR(50) NOT NULL,
rol_id INT NOT NULL,
PRIMARY KEY (user),
CONSTRAINT fk_usuarios_roles FOREIGN KEY (rol_id) REFERENCES roles(id)	
);

CREATE TABLE IF NOT EXISTS roles (
id INT NOT NULL,
descripcion VARCHAR(50) NOT NULL,
PRIMARY KEY (id)
);

INSERT INTO roles(id, descripcion) VALUES (1, "Administrador");
INSERT INTO roles(id, descripcion) VALUES (2, "Estandar");
INSERT INTO usuarios(user, pass, nombre, rol_id) VALUES("root", "admin", "Jorge Pérez", 1);
INSERT INTO usuarios(user, pass, nombre, rol_id) VALUES ("kinal", "12345", "Luis Canto", 2);

select * from roles;
select * from usuarios;


DELIMITER $$
DROP PROCEDURE IF EXISTS sp_usuarios_read_by_id $$
CREATE PROCEDURE sp_usuarios_read_by_id(IN _user VARCHAR(25))
BEGIN
SELECT
U.user,
u.pass,
u.rol_id
FROM
usuarios AS U
where
user = _user;

END$$
DELIMITER ;

call sp_usuarios_read_by_id("root");







