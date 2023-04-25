DROP DATABASE IF EXISTS db_designer__in5bv;
CREATE DATABASE db_designer__in5bv;
USE db_designer__in5bv;

CREATE TABLE IF NOT EXISTS personas (
	id_persona INTEGER,
    nombre1 VARCHAR(15),
    nombre2 VARCHAR(15),
    nombre3 VARCHAR(15),
    apellido1 VARCHAR(15),
    apellido2 Varchar(15),
    email VARCHAR(15),
    telefono VARCHAR(8),
    direccion VARCHAR(45),
    PRIMARY KEY (id_persona)
);


CREATE TABLE IF NOT EXISTS tipo_productos (
	id_tipo_producto INTEGER,
    categoria VARCHAR(20),
    descripcion VARCHAR(126),
    PRIMARY KEY (id_tipo_producto)
);

CREATE TABLE IF NOT EXISTS provedoores (
	id_provedoor INTEGER,
    nombre_provedoor VARCHAR(45),
    telefono VARCHAR(8),
    direccion VARCHAR(45),
    PRIMARY KEY (id_provedoor)
);

CREATE TABLE IF NOT EXISTS ofertas (
	id_oferta INTEGER,
    descuento DECIMAL,
    PRIMARY KEY (id_oferta)
);

CREATE TABLE IF NOT EXISTS roles (
	id_rol INTEGER,
    descripcion_rol VARCHAR(20),
    PRIMARY KEY (id_rol)
);

CREATE TABLE IF NOT EXISTS productos (
	id_producto INTEGER,
    nombre_producto VARCHAR(30),
    precio DECIMAL,
    stock INTEGER,
    id_tipo_producto INTEGER,
    id_provedoor INTEGER,
    id_oferta INTEGER,
    PRIMARY KEY (id_producto),
    CONSTRAINT fk_productos_tipo_productos
		FOREIGN KEY (id_tipo_producto)
        REFERENCES tipo_productos(id_tipo_producto)
        ON DELETE CASCADE ON UPDATE CASCADE,
        
	CONSTRAINT fk_productos_provedoores
		FOREIGN KEY (id_provedoor)
        REFERENCES provedoores(id_provedoor)
        ON DELETE CASCADE ON UPDATE CASCADE,
        
	CONSTRAINT fk_productos_ofertas
		FOREIGN KEY (id_oferta)
        REFERENCES ofertas(id_oferta)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS detalle_ventas (
	id_detalle_venta INTEGER,
    cantidad INTEGER,
    subtotal DECIMAL,
    id_producto INTEGER,
    PRIMARY KEY (id_detalle_venta),
    CONSTRAINT fk_detalle_ventas_productos
		FOREIGN KEY (id_producto)
        REFERENCES productos(id_producto)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS clientes (
	id_cliente INTEGER,
    persona_id INTEGER,
    nit VARCHAR(10),
    PRIMARY KEY (id_cliente),
    CONSTRAINT fk_clientes_personas
		FOREIGN KEY (persona_id)
        REFERENCES personas(id_persona)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS facturas (
	no_factura INTEGER,
    serie VARCHAR(25),
    fecha DATE,
    nombre VARCHAR(50),
    direccion VARCHAR(50),
    nit VARCHAR(10),
    id_detalle_venta INTEGER,
    id_cliente INTEGER,
    PRIMARY KEY (no_factura),
    CONSTRAINT fk_facturas_detalle_ventas
		FOREIGN KEY (id_detalle_venta)
        REFERENCES detalle_ventas(id_detalle_venta)
        ON DELETE CASCADE ON UPDATE CASCADE,
        
	CONSTRAINT fk_facturas_clientes
		FOREIGN KEY (id_cliente)
        REFERENCES clientes(id_cliente)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS empleados (
	id_empleado INTEGER,
    persona_id INTEGER,
    PRIMARY KEY (id_empleado),
    
    CONSTRAINT fk_empleados_clientes
		FOREIGN KEY (persona_id)
        REFERENCES clientes(persona_id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS usuarios (
	user VARCHAR(30),
    password VARCHAR(30),
    id_empleado INTEGER,
    id_rol INTEGER,
    PRIMARY KEY (user),
    CONSTRAINT fk_usuarios_empleados
		FOREIGN KEY (id_empleado)
        REFERENCES empleados(id_empleado)
        ON DELETE CASCADE ON UPDATE CASCADE,
        
	CONSTRAINT fk_usuarios_roles
		FOREIGN KEY (id_rol)
        REFERENCES roles(id_rol)
        ON DELETE CASCADE ON UPDATE CASCADE
);

