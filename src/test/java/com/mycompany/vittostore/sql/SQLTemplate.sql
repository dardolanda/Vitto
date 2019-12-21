/*
    Tener en cuenta que se modificó el tipo de dato de:
    horario_apertura date --> timestamp
    horario-cierre   date --> timestamp 
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
    horario_apertura date,
    horario_cierre date
);


/*
    cambio de tipo de dato:
*/
alter table operating_table 
alter column horario_apertura 
SET DATA TYPE TIMESTAMP;



create table payments (
    id int not null auto_increment,
    mesa int,
    total double,
    descuento_aplicado double,
    nombre_mozo varchar(100),
    tipo_pago varchar(50) -- débito, crédito, efectivo, mercado_pago, cuenta corriente
    horario_pago date
);


insert into operating_table (mesa, nombre_mozo, producto_id, producto_nombre, producto_cantidad, producto_precio_unitario, actividad, horario_apertura, horario_cierre) values (?,?,?,?,?,?,?,?,?)



delete from operating_table
where id between 27 and 38;