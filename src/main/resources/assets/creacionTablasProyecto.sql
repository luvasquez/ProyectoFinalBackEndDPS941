CREATE TABLE ROLES(
	ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	CODIGO VARCHAR(20),
	DESCRIPCION VARCHAR(150),
	FECHA_REGISTRO TIMESTAMP NOT NULL DEFAULT current_timestamp
);

INSERT INTO ROLES (CODIGO, DESCRIPCION) VALUES
	('ADMIN_USER','Usuarios con los mayores privilegios en el sistema'),
	('DOCTOR_USER','Usuario del tipo Doctor para el manejo de las citas a los pacientes'),
	('PACIENTE_USER','Usuario comun netamente de consulta');

CREATE TABLE USUARIOS(
	ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	ID_ROL INT NOT NULL,
	CORREO VARCHAR(150) UNIQUE NOT NULL,
	PASSWORD VARCHAR(300) NOT NULL,
	ACTIVO BOOLEAN DEFAULT '0' NOT NULL,
	CONFIG_COMPLETADA BOOLEAN DEFAULT '0' NOT NULL,
	FECHA_REGISTRO TIMESTAMP NOT NULL DEFAULT current_timestamp,
	CONSTRAINT FK_USUARIO_ROL
	FOREIGN KEY(ID_ROL) REFERENCES ROLES(ID)
);

INSERT INTO USUARIOS (ID_ROL, CORREO, PASSWORD, ACTIVO, CONFIG_COMPLETADA) VALUES 
	((SELECT ID FROM ROLES WHERE CODIGO = 'ADMIN_USER'), 
		'josue.menendez23@gmail.com', '$2a$10$x.RG4De4ZbQ5FASSC3n.reiRjMLMxJMeRfpJ8C6bpF841M0YW9I0C', '1','1');
--admin12345

CREATE TABLE ESTADOS_CITA(
	ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	CODIGO VARCHAR(20),
	DESCRIPCION VARCHAR(150),
	FECHA_REGISTRO TIMESTAMP NOT NULL DEFAULT current_timestamp
);

CREATE TABLE CONSULTORIOS (
	ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	CODIGO VARCHAR(20),
	DESCRIPCION VARCHAR(250),
	FECHA_REGISTRO TIMESTAMP NOT NULL DEFAULT current_timestamp
);
-- PED-01, MED-INT-01, NEURO-01, RADIO-01, PSIQUI-01, OFTALMO-01

INSERT INTO ESTADOS_CITA (CODIGO, DESCRIPCION) VALUES 
	('PROGRA','Programada'),
	('REPROG','Reprogramada'),
	('COMPL','Completada'),
	('CANCE','Cancelada');

INSERT INTO CONSULTORIOS (CODIGO, DESCRIPCION) VALUES 
('PED-01','Consultorio de Pediatria sala 1'),
('NEURO-01','Consultorio para Neurologia sala 1');


CREATE TABLE ESPECIALIDADES(
	ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	CODIGO VARCHAR(20),
	DESCRIPCION VARCHAR(150),
	FECHA_REGISTRO TIMESTAMP NOT NULL DEFAULT current_timestamp
);

CREATE TABLE DOCTORES(
	ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	ID_USUARIO INT NOT NULL,
	ID_ESPECIALIDAD INT NOT NULL,
	NOMBRES VARCHAR(150),
	APELLIDOS VARCHAR(150),
	TELEFONO VARCHAR(20),
	ACTIVO BOOLEAN DEFAULT '0' NOT NULL,
	FECHA_REGISTRO TIMESTAMP NOT NULL DEFAULT current_timestamp,
	CONSTRAINT FK_DOCTOR_USUARIO
	FOREIGN KEY(ID_USUARIO) REFERENCES USUARIOS(ID),
	CONSTRAINT FK_DOCTOR_ESPECIALIDAD
	FOREIGN KEY(ID_ESPECIALIDAD) REFERENCES ESPECIALIDADES(ID)
);

CREATE TABLE PACIENTES(
	ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	ID_USUARIO INT REFERENCES USUARIOS(ID),
	NOMBRES VARCHAR(150),
	APELLIDOS VARCHAR(150),
	FECHA_NACIMIENTO TIMESTAMP NOT NULL,
	GENERO VARCHAR(15) NOT NULL,
	TIPO_SANGRE VARCHAR(10),
	PESO NUMERIC(6,2),
	ALTURA NUMERIC(4,2),
	TELEFONO VARCHAR(20),
	DIRECCION VARCHAR(300) NOT NULL,
	FECHA_REGISTRO TIMESTAMP NOT NULL DEFAULT current_timestamp
);

CREATE TABLE CITAS(
	ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	ID_PACIENTE INT NOT NULL,
	ID_DOCTOR INT NOT NULL,
	ID_CONSULTORIO INT NOT NULL,
	ID_ESTADO INT NOT NULL,
	FECHA_REGISTRO TIMESTAMP NOT NULL DEFAULT current_timestamp,
	FECHA_CITA TIMESTAMP NOT NULL,
	FECHA_FIN TIMESTAMP,
	FECHA_REPROGRAMACION TIMESTAMP,
	ORIGEN VARCHAR(300),
	ALERGIAS VARCHAR(300),
	TRATAMIENTO VARCHAR(300),
	MEDICINAS VARCHAR(300),
	COMENTARIOS_ADICIONALES VARCHAR(300),
	CONSTRAINT FK_CITA_PACIENTE
	FOREIGN KEY(ID_PACIENTE) REFERENCES PACIENTES(ID),
	CONSTRAINT FK_CITA_DOCTOR
	FOREIGN KEY(ID_DOCTOR) REFERENCES DOCTORES(ID),
	CONSTRAINT FK_CITA_CONSULTORIO
	FOREIGN KEY(ID_CONSULTORIO) REFERENCES CONSULTORIOS(ID),
	CONSTRAINT FK_CITA_ESTADO
	FOREIGN KEY(ID_ESTADO) REFERENCES ESTADOS_CITA(ID)
);



--NUEVOS CAMBIOS

alter table pacientes add DUI VARCHAR(10) not null unique;


INSERT INTO especialidades (CODIGO, DESCRIPCION) VALUES
('MED-INT','Medicina Interna'),
('CIR-GEN','Cirugía General'),
('PEDI','Pediatría'),
('GINE-OBSTE','Ginecología y Obstetricia'),
('CARDI','Cardiología'),
('NEUR','Neurología'),
('ORTO','Ortopedia'),
('DERMA','Dermatología'),
('PSIQUI','Psiquiatría'),
('OFTAL','Oftalmología'),
('OTORRI','Otorrinolaringología (ENT)'),
('UROLO','Urología'),
('ANESTE','Anestesiología'),
('RADIO','Radiología'),
('ONCOL','Oncología'),
('ENDOCRI','Endocrinología'),
('GASTROENTE','Gastroenterología'),
('INFECT','Infectología'),
('REUMATO','Reumatología'),
('NEOMATO','Neonatología'),
('GERIATR','Geriatría');




INSERT INTO pacientes (nombres, apellidos, fecha_nacimiento, genero, tipo_sangre, direccion, dui) VALUES
('Juan', 'Pérez', '1985-02-15', 'Masculino', 'O+', 'Calle Falsa 123, Ciudad', '123456789'),
('María', 'González', '1990-07-22', 'Femenino', 'A+', 'Av. Siempre Viva 742, Ciudad', '234567891'),
('Carlos', 'Rodríguez', '1978-11-30', 'Masculino', 'B+', 'Calle Luna 9, Ciudad', '345678912'),
('Ana', 'Martínez', '1983-04-18', 'Femenino', 'O-', 'Calle Sol 88, Ciudad', '456789123'),
('Luis', 'Hernández', '2000-05-05', 'Masculino', 'AB+', 'Av. Primavera 456, Ciudad', '567891234'),
('Laura', 'López', '1995-08-14', 'Femenino', 'A-', 'Calle Flor 22, Ciudad', '678912345'),
('Jorge', 'Díaz', '1988-01-10', 'Masculino', 'B-', 'Calle Pino 345, Ciudad', '789123456'),
('Sofía', 'Ramírez', '1975-09-25', 'Femenino', 'O+', 'Av. Roble 67, Ciudad', '891234567'),
('Miguel', 'Torres', '1992-12-12', 'Masculino', 'A+', 'Calle Nogal 78, Ciudad', '912345678'),
('Valeria', 'Sánchez', '1980-03-29', 'Femenino', 'AB-', 'Calle Arce 134, Ciudad', '123456780'),
('Pedro', 'Castillo', '1998-06-11', 'Masculino', 'O-', 'Av. Olivo 56, Ciudad', '234567892'),
('Carmen', 'Reyes', '1993-10-05', 'Femenino', 'B+', 'Calle Cedro 99, Ciudad', '345678913'),
('Fernando', 'Mendoza', '1982-02-02', 'Masculino', 'A-', 'Calle Abeto 111, Ciudad', '456789124'),
('Isabel', 'Flores', '1979-11-20', 'Femenino', 'O+', 'Av. Sauce 48, Ciudad', '567891235'),
('Rafael', 'Ríos', '2001-04-08', 'Masculino', 'B-', 'Calle Ciprés 75, Ciudad', '678912346'),
('Andrea', 'Fernández', '1987-07-19', 'Femenino', 'A+', 'Calle Manzana 123, Ciudad', '789123457'),
('Ricardo', 'García', '1973-03-23', 'Masculino', 'O-', 'Av. Melocotón 45, Ciudad', '891234568'),
('Paula', 'Vargas', '1994-05-30', 'Femenino', 'B+', 'Calle Pera 67, Ciudad', '912345679'),
('Diego', 'Morales', '1985-12-14', 'Masculino', 'A-', 'Av. Durazno 89, Ciudad', '123456781'),
('Lucía', 'Ortiz', '1991-04-04', 'Femenino', 'AB+', 'Calle Cereza 10, Ciudad', '234567893'),
('Adriana', 'Cruz', '1976-08-20', 'Femenino', 'O-', 'Av. Ciruela 112, Ciudad', '345678914'),
('Martín', 'Silva', '1989-11-01', 'Masculino', 'B-', 'Calle Naranja 56, Ciudad', '456789125'),
('Elena', 'Navarro', '1997-09-17', 'Femenino', 'A+', 'Av. Sandía 98, Ciudad', '567891236'),
('Oscar', 'Romero', '1983-06-26', 'Masculino', 'AB-', 'Calle Fresa 23, Ciudad', '678912347'),
('Gabriela', 'Iglesias', '1992-01-15', 'Femenino', 'O+', 'Av. Uva 34, Ciudad', '789123458');
