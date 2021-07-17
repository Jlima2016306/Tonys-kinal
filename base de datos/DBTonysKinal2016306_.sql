#Julio Samuel Isaac Lima Donis
drop database if exists DBTonysKinal2016306;

create database DBTonysKinal2016306;

use   DBTonysKinal2016306;


###########################################################
###########################################################
################  EMPRESAS  ############################
###########################################################
###########################################################
###########################################################

	###tabla###
CREATE TABLE Empresas (
    codigoEmpresa INT NOT NULL auto_increment,
    nombreEmpresa VARCHAR(150) NOT NULL,
    direccion VARCHAR(150) NOT NULL,
    telefono VARCHAR(10) NOT NULL,
    PRIMARY KEY Pk_codigoEmpresa (codigoEmpresa)
);
	###Procedimientos###
		#Listar
###########################################################

Delimiter $
create Procedure  sp_ListarEmpresas(
)
	begin
		select
			Empresas.codigoEmpresa,
            Empresas.nombreEmpresa,
            Empresas.direccion,
            Empresas.telefono
		From
			Empresas;
				
	end $
delimiter ;


		#Agregar
###########################################################

Delimiter $
create Procedure  sp_AgregarEmpresas(

    in nombreEmpresa varchar(150),
    in direccion varchar(150),
    in telefono varchar(10) 
)
	begin
		insert into Empresas(nombreEmpresa,Direccion,telefono)
        value(nombreEmpresa,Direccion,telefono );
	end $
delimiter ;

		#Actualizar
###########################################################


Delimiter $
create Procedure  sp_ActualizarEmpresas(
	in Codigo int,
    in CnombreEmpresa varchar(150),
    in Cdireccion varchar(150),
    in Ctelefono varchar(10) 
)
	begin
		update empresas set empresas.nombreEmpresa=CNombreEmpresa,
							empresas.Direccion=Cdireccion,
                            empresas.Telefono= Ctelefono
							 where codigoEmpresa=Codigo;
	end $
delimiter ;


		#Eliminar
###########################################################


		Delimiter $
create Procedure  sp_EliminarEmpresas(
	in CCodigoEmpresa int
)
	begin
		delete from empresas where CodigoEmpresa=CCodigoEmpresa;
	end $
delimiter ;


		#Buscar
###########################################################


Delimiter $
create Procedure  sp_BuscarEmpresas(
	in CCodigoEmpresa int
    
)
	begin
		select
			Empresas.CodigoEmpresa,
            Empresas.nombreEmpresa,
            Empresas.Direccion,
            Empresas.Telefono
		From
			Empresas
		where
			Empresas.CodigoEmpresa=CCodigoEmpresa;
				
	end $
delimiter ;

call sp_BuscarEmpresas(1);

call sp_AgregarEmpresas("Carnitas Chepe","7ma Avenida 11va calle zona 2","11111112");
call sp_AgregarEmpresas("Arnolds Pizza","8va calle zona 5","00004123");
call sp_AgregarEmpresas("Delish Lima","7ma calle zona 21","50560000");
call sp_AgregarEmpresas("La casa del tocino","12va avenida zona 23","14562135");
call sp_AgregarEmpresas("Los 12 platillos","9na Avenida 12va calle zona 3","00004212");



call sp_ListarEmpresas();
###########################################################
###########################################################
################  PRESUPUESTO  ############################
###########################################################
###########################################################
###########################################################


		#Tablas
###########################################################

create table Presupuesto(
	codigoPresupuesto int not null auto_increment,
    fechaSolicitud date not null,
    cantidadPresupuesto decimal(10,2) not null,
    codigoEmpresa INT not null,
    primary key PK_codigoPresupuesto (codigoPresupuesto),
	Constraint FK_Presupuesto_Empresas foreign key (codigoEmpresa) references Empresas(CodigoEmpresa)

);
	###Procedimientos###
    
		#Listar
###########################################################

Delimiter $
create procedure sp_ListarPresupuesto(
)
	begin
    
		select
			Presupuesto.codigoPresupuesto,
			Presupuesto.fechaSolicitud,
			Presupuesto.cantidadPresupuesto,
			Presupuesto.codigoEmpresa
		from
			Presupuesto;
        
    end$
Delimiter ;

		#Agregar
###########################################################

Delimiter $
create procedure sp_AgregarPresupuesto(
    in fechaSolicitud date ,
    in cantidadPresupuesto decimal(10,2),
    in codigoEmpresa INT
)
	begin
		Insert Into Presupuesto( fechaSolicitud, cantidadPresupuesto, codigoEmpresa)
        values( fechaSolicitud, cantidadPresupuesto, codigoEmpresa);
    end$
Delimiter ;


call sp_AgregarPresupuesto("2020-10-12", 3200.00, 1);
call sp_AgregarPresupuesto("2020-10-10", 400.00, 2);
call sp_AgregarPresupuesto("2020-9-12", 1300.00, 3);
call sp_AgregarPresupuesto("2020-5-30", 200.00, 4);
call sp_AgregarPresupuesto("2020-4-28", 200.00, 5);


call sp_ListarPresupuesto();


		#Actualizar
###########################################################

Delimiter $
create procedure sp_ActualizarPresupuesto(
	in Codigo int ,
    in CfechaSolicitud date ,
    in CcantidadPresupuesto decimal(10,2),
    in CcodigoEmpresa INT
  
)
	begin
		update Presupuesto set 
							   Presupuesto.fechaSolicitud=CfechaSolicitud,
                               Presupuesto.cantidadPresupuesto=CcantidadPresupuesto,
                               Presupuesto.codigoEmpresa=CcodigoEmpresa
						   where 
								codigoPresupuesto=Codigo;
    end$
Delimiter ;

call sp_ActualizarPresupuesto(4,"10-10-2",100,1);

		#Eliminar
###########################################################

Delimiter $
create procedure sp_EliminarPresupuesto(
	in CcodigoPresupuesto int 
)
	begin
    delete from
				Presupuesto
		   where
				codigoPresupuesto=CcodigoPresupuesto;
        
    end$
Delimiter ;

		#Buscar
###########################################################

Delimiter $
create procedure sp_BuscarPresupuesto(
	in CcodigoPresupuesto int 
)
	begin
    
		select
			Presupuesto.CodigoPresupuesto,
			Presupuesto.FechaSolicitud,
			Presupuesto.CantidadPresupuesto,
			Presupuesto.CodigoEmpresa
		from
			Presupuesto
		where
			codigoPresupuesto=CcodigoPresupuesto ;
        
    end$
Delimiter ;


###########################################################
###########################################################
####################SERVICIOS   ###########################
###########################################################
###########################################################
###########################################################


CREATE TABLE Servicios (
    codigoServicio INT NOT NULL auto_increment,
    fechaServivio DATE NOT NULL,
    tipoServicio VARCHAR(100) NOT NULL,
    horaServicio TIME NOT NULL,
    lugarServicio VARCHAR(100) NOT NULL,
    telefonoContacto VARCHAR(10) NOT NULL,
    codigoEmpresa INT not null,

    PRIMARY KEY PK_codigoServicio (codigoServicio),
    CONSTRAINT FK_Servicios_Empresas FOREIGN KEY (codigoEmpresa)
        REFERENCES Empresas (codigoEmpresa)
);

	###Procedimientos###
		#Listar
Delimiter $
create procedure sp_ListarServicios(
)
	begin
		select 
			Servicios.codigoServicio,
			Servicios.fechaServivio,
			Servicios.tipoServicio, 
			Servicios.horaServicio,  
			Servicios.lugarServicio, 
			Servicios.telefonoContacto,  
			Servicios.codigoEmpresa
		from
			Servicios;
		
    end $
Delimiter ;


		#Agregar
Delimiter $
create Procedure sp_AgregarServicios(
	
	in fechaServivio date, 
	in tipoServicio varchar(200),
	in horaServicio time,
	in lugarServicio varchar(200),
	in telefonoContacto varchar(200), 
	in codigoEmpresa int
)
	begin
		insert into Servicios(fechaServivio, tipoServicio, horaServicio, lugarServicio, telefonoContacto, codigoEmpresa)
        values(fechaServivio, tipoServicio, horaServicio, lugarServicio, telefonoContacto, codigoEmpresa);
        
    end $
Delimiter ;

call sp_AgregarServicios("20/10/12", "Desayuno","6:00:00","Mixco", "1265 4564",1);
call sp_AgregarServicios("20/12/12", "Armuerzo","12:00:00","Ciudad de Guatemala", "3225 4564",2);
call sp_AgregarServicios("20/9/12", "Cena","18:00:00","Mixco", "1441 4874",3);
call sp_AgregarServicios("20/8/12", "Refaccion","16:00:00","Ciudad de Guatemala", "3265 4764",4);
call sp_AgregarServicios("20/7/12", "Postre","8:00:00","Ciudad de guatemala", "2245 2264",5);


		#Actualizar
Delimiter $
create Procedure sp_ActualizarServicios(
	in Codigo int,
	in CfechaServivio date, 
	in CtipoServicio varchar(200),
	in ChoraServicio time,
	in ClugarServicio varchar(200),
	in CtelefonoContacto varchar(200), 
	in CcodigoEmpresa int
)
	begin
		update Servicios set 
							 Servicios.fechaServivio=CfechaServivio,
                             Servicios.tipoServicio=CtipoServicio,
                             Servicios.horaServicio=ChoraServicio,
                             Servicios.lugarServicio=ClugarServicio,
                             Servicios.telefonoContacto=CtelefonoContacto,
                             Servicios.codigoEmpresa=CcodigoEmpresa
						where
							Servicios.codigoServicio=Codigo;
							
                             
							
    end $
Delimiter ;

		#Eliminar
Delimiter $
create procedure sp_EliminarServicios(
	in CcodigoServicio int
) 
	begin
		delete 	
			from 
				Servicios 
			where 
				Servicios.CodigoServicio=CcodigoServicio;

    end $
Delimiter ;

		#Buscar
Delimiter $
create procedure sp_BuscarServicios(
	in CcodigoServicio int
) 
	begin
		select 
			Servicios.codigoServicio,
			Servicios.fechaServivio,
			Servicios.tipoServicio, 
			Servicios.horaServicio,  
			Servicios.lugarServicio, 
			Servicios.telefonoContacto,  
			Servicios.codigoEmpresa
		from
			Servicios
		where
			Servicios.CodigoServicio=CcodigoServicio;
    end $
Delimiter ;




        
###########################################################        
###########################################################
################   TIPOEMPLEADO        ####################
###########################################################
###########################################################
###########################################################

CREATE TABLE TipoEmpleado (
    codigoTipoEmpleado INT NOT NULL auto_increment,
    descripcion VARCHAR(100) NOT NULL,
    PRIMARY KEY PK_codigoTipoEmpleado (codigoTipoEmpleado)
);

	###Procedimientos###
    
		#Listar
###########################################################
Delimiter $
create Procedure sp_ListarTipoEmpleado(

)
	begin
		select
			TipoEmpleado.codigoTipoEmpleado,
            TipoEmpleado.descripcion
		from
			TipoEmpleado;

    
    end $
Delimiter ;


		#Agregar
###########################################################
Delimiter $
create Procedure sp_AgregarTipoEmpleado(

    in Descripcion varchar(100)
)
	begin
		insert into TipoEmpleado(Descripcion)
			values(Descripcion);
	
    
    end $
Delimiter ;
call sp_AgregarTipoEmpleado("Chef");
call sp_AgregarTipoEmpleado(" Chef Ejecutivo");
call sp_AgregarTipoEmpleado("co Chef");
call sp_AgregarTipoEmpleado("Mesero");
call sp_AgregarTipoEmpleado("Lavaplatos");

        
		#Actualizar
###########################################################
Delimiter $
create Procedure sp_ActualizarTipoEmpleado(
	in Codigo int,
    in CDescripcion varchar(100)
)
	begin
		update TipoEmpleado set TipoEmpleado.Descripcion=CDescripcion where TipoEmpleado.CodigoTipoEmpleado=Codigo;
    
    end $
Delimiter ;


		#Eliminar
###########################################################
Delimiter $
create Procedure sp_EliminarTipoEmpleado(
	in CCodigoTipoEmpleado int
    
)
	begin
		delete
			from
				TipoEmpleado
			where
				TipoEmpleado.CodigoTipoEmpleado=CCodigoTipoEmpleado;
    
    end $
Delimiter ;


		#Buscar
###########################################################
Delimiter $
create Procedure sp_BuscarTipoEmpleado(
	in CCodigoTipoEmpleado int
)
	begin
		select
			TipoEmpleado.codigoTipoEmpleado,
            TipoEmpleado.descripcion
		from
			TipoEmpleado
		where
			TipoEmpleado.CodigoTipoEmpleado=CCodigoTipoEmpleado;
    
    end $
Delimiter ;



        
###########################################################
###########################################################
################    EMPLEADOS   ###########################
###########################################################
###########################################################
###########################################################



create table Empleados(
	codigoEmpleado int not null auto_increment,
    numeroEmpleado int not null,
    apellidosEmpleado varchar(150) not null,
    nombresEmpleado varchar(150) not null,
    direccionEmpleado Varchar(150) not null,
    telefonoContacto varchar(10) not null,
    gradoCocinero varchar(50) ,
    codigoTipoEmpleado int not null,
    primary key PK_codigoEmpleado (codigoEmpleado),
    Constraint FK_Empleados_TipoEmpleado  
		foreign key (CodigoTipoEmpleado) references TipoEmpleado(CodigoTipoEmpleado)
);

	###Procedimientos###
    
		#Listar
###########################################################
Delimiter $
Create Procedure sp_ListarEmpleados(
)
	begin
		select
			Empleados.codigoEmpleado, 
			Empleados.numeroEmpleado,
			Empleados.apellidosEmpleado,  
			Empleados.nombresEmpleado,  
			Empleados.direccionEmpleado, 
			Empleados.telefonoContacto, 
			Empleados.gradoCocinero, 
			Empleados.codigoTipoEmpleado
		From
			Empleados;
    end $
Delimiter ;

		#Agregar
###########################################################
Delimiter $
Create Procedure sp_AgregarEmpleados(
	in numeroEmpleado int, 
	in apellidosEmpleado varchar(150), 
	in nombresEmpleado varchar(150),
	in direccionEmpleado varchar(150), 
	in telefonoContacto varchar(10), 
	in gradoCocinero varchar(50), 
	in codigoTipoEmpleado int
)
	begin
		Insert into Empleados(numeroEmpleado, apellidosEmpleado, nombresEmpleado, direccionEmpleado, telefonoContacto, gradoCocinero, codigoTipoEmpleado)
			Values(numeroEmpleado, apellidosEmpleado, nombresEmpleado, direccionEmpleado, telefonoContacto, gradoCocinero, codigoTipoEmpleado);
    end $
Delimiter ;

		#Actualizar
###########################################################
Delimiter $
create procedure sp_ActualizarEmpleados(
	in Codigo int,
	in CnumeroEmpleado int, 
    in CapellidosEmpleado varchar(150),
	in CnombresEmpleado varchar(150),
	in CdireccionEmpleado varchar(150), 
	in CtelefonoContacto varchar(10), 
    in CgradoCocinero varchar(50),
	in CcodigoTipoEmpleado int
)
	Begin
		Update Empleados set Empleados.numeroEmpleado = CnumeroEmpleado,
							 Empleados.apellidosEmpleado = CapellidosEmpleado,
                             Empleados.nombresEmpleado = CnombresEmpleado,
                             Empleados.direccionEmpleado = CdireccionEmpleado,
                             Empleados.telefonoContacto = CtelefonoContacto,
                             Empleados.gradoCocinero = CgradoCocinero,
                             Empleados.codigoTipoEmpleado = CcodigoTipoEmpleado
                             where
							 Empleados.codigoEmpleado = Codigo ;
							
    end $
Delimiter ;

		#Eliminar
###########################################################

Delimiter $
create Procedure sp_EliminarEmpleados(
	in CcodigoEmpleado int
)
	begin
		Delete
		from
			Empleados
		where
			Empleados.codigoEmpleado= CcodigoEmpleado;
            
    end $
Delimiter ;

		#Buscar
###########################################################

Delimiter $
Create Procedure sp_BuscarEmpleados(
	in CcodigoEmpleado int
)
	begin
		select
			Empleados.codigoEmpleado, 
			Empleados.numeroEmpleado,
			Empleados.apellidosEmpleado,  
			Empleados.nombresEmpleado,  
			Empleados.direccionEmpleado, 
			Empleados.telefonoContacto, 
			Empleados.gradoCocinero, 
			Empleados.codigoTipoEmpleado
		From
			Empleados
		where
			Empleados.codigoEmpleado= CcodigoEmpleado;
    end $
Delimiter ;

call sp_AgregarEmpleados(1,"Lima Donis","Julio Samuel","4to avenida ","4561 4682", "Alta cocina",1);
call sp_AgregarEmpleados(22,"Fernandez Lopéz","Armando Joel","5to avenida ","2231 2385", "Alta cocina",2);
call sp_AgregarEmpleados(33,"Ordoñes Jors"," Jasuel","9to avenida ","6761 4682", "Media cocina",3);
call sp_AgregarEmpleados(42,"Villa toro","James","7ma avenida ","4856 9582", "Ninguno",4);
call sp_AgregarEmpleados(52,"Pan y Agua ","Javier","9nao avenida ","4879 4672", "Ninguno",5);

###########################################################
###########################################################
###########    SERVICIOS_HAS_EMPLEADOS    #################
###########################################################
###########################################################
###########################################################


create table Servicios_has_Empleados(
	Servicios_codigoServicios int auto_increment,
	codigoServicio INT,
    codigoEmpleado int,
	fechaEvento date not null,
    horaEvento time not null,
    lugarEvento Varchar(150) not null,
		primary key  PK_Servicios_codigoServicios(Servicios_codigoServicios),
		constraint FK_Servicios_has_empleados_Servicios foreign key (CodigoServicio) references Servicios(codigoServicio) ON DELETE cascade,
		constraint FK_Servicios_has_empleados_Empleados foreign key (CodigoEmpleado) references Empleados(codigoEmpleado) on delete cascade
    
);
	###Procedimientos###
    
		#Listar
###########################################################
Delimiter $
create Procedure sp_ListarServicios_has_Empleados(
)
	begin
		select 
			Servicios_has_Empleados.Servicios_codigoServicios,
			Servicios_has_Empleados.codigoServicio, 
			Servicios_has_Empleados.codigoEmpleado, 
			Servicios_has_Empleados.fechaEvento,
			Servicios_has_Empleados.horaEvento, 
			Servicios_has_Empleados.lugarEvento
		from
			Servicios_has_Empleados;
			
    end$
Delimiter ;
        call 
		#Agregar
###########################################################
Delimiter $
create Procedure sp_AgregarServicios_has_Empleados(
	in Servicios_codigoServicio int,
	in Empleados_codigoEmpleados int,
	in fechaEvento date ,
	in horaEvento time,
	in lugarEvento varchar(150)
)
	begin
		insert into Servicios_has_Empleados(codigoServicio, codigoEmpleado, fechaEvento, horaEvento, lugarEvento)
			values(Servicios_codigoServicio, Empleados_codigoEmpleados, fechaEvento, horaEvento, lugarEvento);
    end$
Delimiter ;

CALL  sp_AgregarServicios_has_Empleados(1,1,"20/1/12",now(), "Mixco");
CALL  sp_AgregarServicios_has_Empleados(2,2,"20/4/12",now(), "Guatemala");
CALL  sp_AgregarServicios_has_Empleados(3,3,"20/6/12",now(), "Mixco");
CALL  sp_AgregarServicios_has_Empleados(4,4,"20/8/12",now(), "Guatemala");
CALL  sp_AgregarServicios_has_Empleados(5,5,"20/12/12",now(), "villa Nueva");


		#Actualizar
###########################################################        
Delimiter $
Create Procedure sp_ActualizarServicios_has_empleados(
	in Codigo int,
    in CCodigoServicio int,
	in CEmpleados_codigoEmpleados int,
	in CfechaEvento date ,
	in ChoraEvento time,
	in ClugarEvento varchar(150)
)
	begin
		Update Servicios_has_empleados  set
											servicios_has_empleados.codigoServicio= CCodigoServicio,
                                            Servicios_has_empleados.CodigoEmpleado=CEmpleados_codigoEmpleados,
                                            Servicios_has_empleados.fechaEvento=CfechaEvento,
                                            Servicios_has_empleados.horaEvento=ChoraEvento,
                                            Servicios_has_empleados.lugarEvento=ClugarEvento
										where
											Servicios_has_empleados.Servicios_codigoServicios=Codigo;
											
										
    end $
Delimiter ;


		#Eliminar
###########################################################     
  Delimiter $
create Procedure sp_EliminarServicios_has_Empleados(
	in CServicios_codigoServicios int
)
	begin
		delete
		from
			Servicios_has_Empleados
		where
			Servicios_has_empleados.Servicios_codigoServicios=CServicios_codigoServicios;
			
    end$
Delimiter ;
 
		#Buscar
###########################################################        
Delimiter $
create Procedure sp_BuscarServicios_has_Empleados(
	in CServicios_codigoServicio int
)
	begin
		select 
			Servicios_has_Empleados.codigoServicio, 
			Servicios_has_Empleados.codigoEmpleados, 
			Servicios_has_Empleados.fechaEvento,
			Servicios_has_Empleados.horaEvento, 
			Servicios_has_Empleados.lugarEvento
		from
			Servicios_has_Empleados
		where
			Servicios_has_empleados.Servicios_codigoServicios=CServicios_codigoServicio;
			
    end$
Delimiter ;



###########################################################
###########################################################
###############         PRODUCTOS         #################
###########################################################
###########################################################
###########################################################

create table Productos(
	codigoProducto int not null auto_increment,
    nombreProducto varchar(150) not null,
    cantidad int,
    primary key PK_codigoProducto (codigoProducto)
);

	###Procedimientos###
    
		#Listar
###########################################################
Delimiter $
create Procedure sp_ListarProductos(
)
	Begin
		select
			Productos.codigoProducto,
			Productos.nombreProducto,
			Productos.cantidad 
		from
			Productos;
			
    end $
Delimiter ;

		#Agregar
###########################################################
Delimiter $
create Procedure sp_AgregarProductos(
	
	in nombreProducto varchar(150) ,
	in cantidad int
)
	Begin
		insert into Productos(nombreProducto, cantidad)
			value(nombreProducto, cantidad);
			
    end $
Delimiter ;

call sp_AgregarProductos("Camarones", 14);
call sp_AgregarProductos("Bistec", 14);
call sp_AgregarProductos("Pollo", 12);
call sp_AgregarProductos("Café", 4);
call sp_AgregarProductos("Pescado", 4);


		#Actualizar
###########################################################
Delimiter $
create Procedure sp_ActualizarProductos(
	in Codigo int,
	in CnombreProducto varchar(150) ,
	in Ccantidad int
)
	Begin
		update Productos set Productos.nombreProducto=CnombreProducto,
							 Productos.cantidad=Ccantidad
						 where
							Productos.CodigoProducto=Codigo; 
			
    end $
Delimiter ;

        
		#Eliminar
###########################################################
Delimiter $
create Procedure sp_EliminarProductos(
	in CcodigoProducto int

)
	Begin
		delete
		from
			Productos
		where
			Productos.CodigoProducto=CCodigoProducto;
			
    end $
Delimiter ;


        
		#Buscar
###########################################################
Delimiter $
create Procedure sp_BuscarProductos(
	in CcodigoProducto int
	
)
	Begin
		select
			Productos.codigoProducto,
            Productos.nombreProducto,
            Productos.cantidad
		from
			Productos
		where
			Productos.CodigoProducto=CCodigoProducto;
			
    end $
Delimiter ;




###########################################################
###########################################################
################   TIPOPLATO    ###########################
###########################################################
###########################################################
###########################################################

create table TipoPlato(
	codigoTipoPlato INT not null auto_increment,
    descripcionTipo varchar(100) not null,
    primary key PK_codigoTipoPlato (codigoTipoPlato)
);

	###Procedimientos###
    
		#Listar
###########################################################
Delimiter $
create Procedure sp_ListarTipoPlato(

)
	begin
		select
			TipoPlato.codigoTipoPlato,
            TipoPlato.descripcionTipo
		from
			TipoPlato;

    
    end $
Delimiter ;


		#Agregar
###########################################################
Delimiter $
create Procedure sp_AgregarTipoPlato(
	
    in DescripcionTipo varchar(100)
)
	begin
		insert into TipoPlato(DescripcionTipo)
			values(DescripcionTipo);
	
    
    end $
Delimiter ;
        
        
		#Actualizar
###########################################################
Delimiter $
create Procedure sp_ActualizarTipoPlato(
	in Codigo int,
    in CDescripcionTipo varchar(100)
)
	begin
		update TipoPlato set TipoPlato.DescripcionTipo=CDescripcionTipo where TipoPlato.CodigoTipoPlato=Codigo;
    
    end $
Delimiter ;


		#Eliminar
###########################################################
Delimiter $
create Procedure sp_EliminarTipoPlato(
	in CCodigoTipoPlato int
    
)
	begin
		delete
			from
				TipoPlato
			where
				TipoPlato.CodigoTipoPlato=CCodigoTipoPlato;
    
    end $
Delimiter ;


		#Buscar
###########################################################
Delimiter $
create Procedure sp_BuscarTipoPlato(
	in CCodigoTipoPlato int
)
	begin
		select
			TipoPlato.codigoTipoPlato,
            tipoplato.descripcionTipo
		from
			TipoPlato
		where
			TipoPlato.CodigoTipoPlato=CCodigoTipoPlato;
    
    end $
Delimiter ;

call sp_AgregarTipoPlato("Mariscos");
call sp_AgregarTipoPlato("Azados");
call sp_AgregarTipoPlato("Fritos");
call sp_AgregarTipoPlato("Café");
call sp_AgregarTipoPlato("Caldos");

 
###########################################################
###########################################################
################     PLATOS     ###########################
###########################################################
###########################################################
###########################################################


create table Platos(
	codigoPlato int not null auto_increment,
    cantidad int not null ,
    nombrePlato varchar(150) not null,
    descripcionPlato varchar(150) not null,
    precioPlato DECIMAL (10.2) not null,
    codigoTipoPlato INT,
    primary key PK_codigoPlato (codigoPlato),
    constraint FK_Platos_TipoPlato foreign key (codigoTipoPlato) references Tipoplato(codigotipoPlato)
    
);

	###Procedimientos###
		#Listar
###########################################################
Delimiter $
create procedure sp_ListarPlatos(
)
	begin
		select
				Platos.codigoPlato,
				Platos.cantidad,
				Platos.nombrePlato,
				Platos.descripcionPlato,
				Platos.precioPlato ,
				Platos.codigoTipoPlato 
        from
			Platos;
    end$
Delimiter ;

		#Agregar
###########################################################
Delimiter $
create procedure sp_AgregarPlatos(
    in cantidad int  ,
    in nombrePlato varchar(150) ,
    in descripcionPlato varchar(150) ,
    in precioPlato DECIMAL (10.2) ,
    in codigoTipoPlato INT
)
	begin
		insert into Platos( cantidad, nombrePlato, descripcionPlato, precioPlato, codigoTipoPlato)
			values( cantidad, nombrePlato, descripcionPlato, precioPlato, codigoTipoPlato);
    end$
Delimiter ;

call sp_AgregarPlatos(12,"Camarones al agio", "Camarones sofreidos en aceite de oliva con la mezcla secreta de agio",30,1);
call sp_AgregarPlatos(12,"Pollo frito", "Pollo freido a alta temperatura",20,2);
call sp_AgregarPlatos(12,"Bistec azado", " Bistec Azado a alta temperatura",20,3);
call sp_AgregarPlatos(12,"Café con cremora", "Café Hervido con cremora",20,4);
call sp_AgregarPlatos(12,"Pescado azado", "Pescado azado a las brazas con salsas de tomate e inglesas",40,5);

        
		#Actualizar
###########################################################
delimiter $
create procedure sp_ActualizarPlatos(
	in Codigo int,
    in Ccantidad int  ,
    in CnombrePlato varchar(150),
    in CdescripcionPlato varchar(150) ,
    in CprecioPlato DECIMAL (10.2) ,
    in CcodigoTipoPlato INT
)
	begin
		update Platos set 
						  Platos.cantidad=Ccantidad,
                          Platos.nombrePlato=CnombrePlato,
                          Platos.descripcionPlato=CdescripcionPlato,
                          Platos.precioPlato=CprecioPlato,
                          Platos.codigoTipoPlato=CcodigoTipoPlato
					where
                          Platos.codigoPlato=Codigo ;
    end$
Delimiter ;
        

        
		#Eliminar
###########################################################
delimiter $
create procedure sp_EliminarPlatos(
	in CcodigoPlato int
   
)
	begin
		delete
        from
			Platos
		where
			Platos.codigoPlato=CcodigoPlato ;
    end$
Delimiter ;        
        
		#Buscar
###########################################################
Delimiter $
create procedure sp_BuscarPlatos(
	in CcodigoPlato int
   
)
	begin
		select
				Platos.codigoPlato,
				Platos.cantidad,
				Platos.nombrePlato,
				Platos.descripcionPlato,
				Platos.precioPlato ,
				Platos.codigoTipoPlato 
        from
			Platos
		where
			Platos.codigoPlato=CcodigoPlato ;
    end$
Delimiter ; 




###########################################################
###########################################################
############      PRODUCTOS_HAS_PLATOS       ##############
###########################################################
###########################################################
###########################################################


create table Productos_has_Platos(
	codigoProductos int,
    CodigoPlato int,
         constraint FK_Productos_has_platos_Productos foreign key (codigoProductos) references Productos(codigoProducto),
		 constraint FK_Productos_has_platos_Platos foreign key (CodigoPlato) references Platos(codigoPlato)   
);


	###Procedimientos###
		#Listar
###########################################################
delimiter $
create procedure sp_ListarProductos_has_Platos(
)
	begin
    
			select productos.codigoProducto, platos.codigoplato from productos, platos 
            where codigoProducto = codigoProducto and CodigoPlato = CodigoPlato;
    end $
delimiter ;

		#Agregar
###########################################################
delimiter $
create procedure sp_AgregarProductos_has_Platos(
	in Productos_codigoProductos int,
    in Platos_CodigoPlato int
)
	begin
		insert into Productos_has_Platos(Productos_codigoProductos,Platos_CodigoPlato)
			value(Productos_codigoProductos,Platos_CodigoPlato);
    end $
delimiter ;

		#Actualizar
###########################################################
delimiter $
create procedure sp_ActualizarProductos_has_Platos(
	in Codigo int,
    in CPlatos_CodigoPlato int
)
	begin
		update Productos_has_Platos set 
										Productos_has_Platos.Platos_CodigoPlato=CPlatos_CodigoPlato
									where
										Productos_has_Platos.Productos_codigoProductos= Codigo;
    end $
delimiter ;

        
		#Eliminar
###########################################################
 delimiter $
create procedure sp_EliminarProductos_has_Platos(
	in CProductos_codigoProductos int
)
	begin
		Delete
        from
			Productos_has_Platos
		where
			Productos_has_Platos.Productos_codigoProductos=CProductos_codigoProductos;
    end $
delimiter ;
       
		#Buscar
###########################################################
delimiter $
create procedure sp_BuscarProductos_has_Platos(
	in CProductos_codigoProductos int
)
	begin
		select
			Productos_has_Platos.Productos_codigoProductos,
			Productos_has_Platos.Platos_CodigoPlato     
        from
			Productos_has_Platos
		where
			Productos_has_Platos.Productos_codigoProductos=CProductos_codigoProductos;
    end $
delimiter ;





###########################################################
###########################################################
#############      SERVICIOs_HAS_PLATOS        #############
###########################################################
###########################################################
###########################################################

create	table  Servicios_has_Platos(
	codigoServicio INT,
    CodigoPlato int,
        constraint FK_Servicios_has_platos_Servicios foreign key (codigoServicio) references Servicios(codigoServicio),
		constraint FK_Servicios_has_platos_Platos foreign key (CodigoPlato) references Platos(codigoPlato)

);

	###Procedimientos###
		#Listar
###########################################################
 delimiter $
create procedure sp_ListarServicios_has_Platos(
)
	begin
		select
			c1.codigoServicio, c2.codigoPlato   from
            (select s.codigoServicio as codigoServicio from servicios s
				left join servicios_has_platos shp on shp.codigoServicio= s.codigoServicio) c1,
		    (select pl.codigoPlato as codigoPlato from platos pl 
				left join servicios_has_Platos shp on shp.codigoPlato = pl.codigoPlato) c2 ;
              

    end $
delimiter ;
		#Agregar
###########################################################     
delimiter $
create procedure sp_AgregarServicios_has_Platos(
	in codigoServicios int,
    in CodigoPlato int
)
	begin
		insert into Servicios_has_Platos(codigoServicios,CodigoPlato)
			value(codigoServicios,CodigoPlato);
    end $
delimiter ;
 
		#Actualizar
###########################################################    
 delimiter $
create procedure sp_ActualizarServicios_has_Platos(
	in Codigo int,
    in CPlatos_CodigoPlato int
)

delimiter ;
 
 
		#Eliminar
###########################################################    
delimiter $
create procedure sp_EliminarServicios_has_Platos(
	in CServicios_codigoServicios int
)

  
delimiter ;

 
		#Buscar
###########################################################
delimiter $
create procedure sp_BuscarServicios_has_Platos(
	in CServicios_codigoServicios int
)
	begin
		select
			Servicios_has_Platos.Servicios_codigoServicios,
			Servicios_has_Platos.Platos_CodigoPlato     
        from
			Servicios_has_Platos
		where
			Servicios_has_Platos.Servicios_codigoServicios=CServicios_codigoServicios;
    end $
delimiter ;


		#reportes
###########################################################
Delimiter $

Create procedure sp_ListarReporte (in codEmpresa int)
begin

	select *
    from empresas E inner join presupuesto P on
    E.codigoEmpresa= P.codigoEmpresa
    inner join Servicios S on 
    E.codigoEmpresa= S.codigoEmpresa
    where E.codigoEmpresa = codEmpresa order by P.cantidadPresupuesto; 
    
    
end$  

delimiter ;



delimiter $
create procedure sp_ListarReporteServi (in codServicios int)
begin

select S.tipoServicio,Pl.cantidad, Tp.descripcionTipo, Pr.nombreProducto from TipoPlato as Tp
        inner join Platos as Pl on
			Tp.codigoTipoPlato = Pl.codigoTipoPlato
		inner join Servicios as S on
			Pl.codigoTipoPlato = S.codigoServicio
		inner join Productos as Pr
				where  S.codigoServicio = codServicios ;
    
end$  

delimiter ;

call sp_ListarReporteServi(3);
ALTER USER 'root'@'localhost' identified WITH mysql_native_password BY 'admin';