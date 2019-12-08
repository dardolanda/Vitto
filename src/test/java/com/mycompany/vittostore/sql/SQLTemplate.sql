/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
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

alter table operating_table 
alter column horario_apertura 
SET DATA TYPE TIMESTAMP;

/*
    Tener en cuenta que se modificó el tipo de dato de:
    horario_apertura date --> timestamp
    horario-cierre   date --> timestamp 
    
    H2 -> dataBase
*/


insert into operating_table (mesa, nombre_mozo, producto_id, producto_nombre, producto_cantidad, producto_precio_unitario, actividad, horario_apertura, horario_cierre) values (?,?,?,?,?,?,?,?,?)