/*
    Creación de tablas:
*/
create table operating_table (
    id int not null auto_increment,
    mesa int,
    nombre_mozo varchar(100),
    producto_id int,
    producto_nombre varchar(100),
    producto_cantidad int,
    producto_precio_unitario double,
    actividad boolean,
    horario_apertura timestamp,
    horario_cierre timestamp
);


create table payments (
    id int not null auto_increment,
    mesa int,
    total double,
    descuento_aplicado double,
    nombre_mozo varchar(100),
    tipo_pago varchar(50) -- débito, crédito, efectivo, mercado_pago, cuenta corriente
    horario_pago date
);



/*
    cambio de tipo de dato:
*/
alter table operating_table 
alter column horario_apertura 
SET DATA TYPE TIMESTAMP;

/*
    Agrega primary key
*/
alter table payments
add constraint payments_pk
primary key (id);


/*
    Agrega foreign key
*/
alter table payments_customer_data
add constraint payments__fk
foreign key(payment_id) references payments (id)


create table payments_customer_data (
    id int not null auto_increment,
    payment_id int not null,
    nombre varchar(100),
    apellido varchar(100),
    dni varchar(50),
    tel varchar(70)
);


/*
    Inserta registros
*/

insert into operating_table 
-- columnas: Tener en cuenta que como el id -> autoincremental -> no hace falta su mención en las columnas.
(mesa, nombre_mozo, producto_id, producto_nombre, producto_cantidad, producto_precio_unitario, actividad, horario_apertura, horario_cierre) 
-- valores:
values (?,?,?,?,?,?,?,?,?)


/*
    Delete registros
*/

delete from operating_table
where id between 27 and 38;