# PerifTech

## Descripción de la aplicación
Vamos a crear una web de venta de productos informáticos. Tendrá 2 partes:
- Una **parte pública**, que permite navegar por las distintas categorías y ver los productos disponibles
- Una **parte privada**, que permitirá la gestión de la cuenta de usuario y realizar los pagos de los pedidos

## Entidades
- **Cliente:** usuario que accede a nuestra página web, y puede estar registrado o no. Para poder realizar un pedido, deberá estar autentificado.
- **Producto:**  los distintos productos informáticos que ofreceremos en nuestra web.
- **Categoría:**  se recogerán bajo una misma categoría todos los productos que sean del mismo tipo (ratones, teclados...)
- **Carro de compra:**  contendrá el número de productos y el coste de los mismos.
- **Pedido:**  tendrá el coste de los productos seleccionados por el cliente, además del coste de envío.

## Funcionamiento del servicio interno
- Envío de correo al cliente con la confirmación del pedido y la información del mismo


## Integrantes
- Iván Carlos Nieto Montilla - ic.nieto.2018@alumnos.urjc.es - https://github.com/ivannietom
- Manuel Roman Dydak Kuchta - mr.dydak.2018@alumnos.urjc.es - https://github.com/mxnvel
- Carlos Herce Nombela - c.herce.2018@alumnos.urjc.es - https://github.com/Carloshercen24

# FASE 2
## Capturas de las pantallas:
Al entrar a la web, esta sería la primera pantalla en aparecer:
![Index](https://user-images.githubusercontent.com/78872015/110705535-d4f9a300-81f6-11eb-8f66-839831112e4f.PNG)
Desde el index tenemos 3 opciones:
- Iniciar sesión (1):
![Iniciar-sesión](https://user-images.githubusercontent.com/78872015/110705706-0e321300-81f7-11eb-8ee6-44448262dcc8.PNG)
  - Si iniciamos sesión como un cliente:
    - Consultar nuestro perfil:
    ![Mi-perfil](https://user-images.githubusercontent.com/78872015/110706047-8bf61e80-81f7-11eb-832d-cbf1b538f82a.PNG)
    - Ver las categorías (2):
   ![categorias](https://user-images.githubusercontent.com/78872015/110706131-a92aed00-81f7-11eb-93e4-5c4481eab60c.PNG)
    - Ver nuestro carrito (3):
    ![Carrito](https://user-images.githubusercontent.com/78872015/110706181-c1027100-81f7-11eb-8448-fe08cf0c422a.PNG)
  - Si inicamos sesión como administrador:
    - Consultar nuestro perfil (Idem cliente normal)
    - Agregar categorías:
    ![Nueva-categoria](https://user-images.githubusercontent.com/78872015/110706356-032bb280-81f8-11eb-82da-f3faa65b45a3.PNG)
    - Agregar productos:
    ![Nuevo-producto](https://user-images.githubusercontent.com/78872015/110706381-0de64780-81f8-11eb-8324-ff91b08cd5ce.PNG)

(1) Desde la pantalla de login podemos registrarnos si no tenemos cuenta:
![Registrarse](https://user-images.githubusercontent.com/78872015/110707144-2acf4a80-81f9-11eb-8eb4-f5fadc1b90a8.PNG)

(2) Desde categorías, podemos ver los productos de una categoría:
![Ver-categoria](https://user-images.githubusercontent.com/78872015/110706649-7d5c3700-81f8-11eb-9995-d3003204d47e.PNG)
Y haciendo click en el producto podemos ver la información de este mismo:
![Ver-producto](https://user-images.githubusercontent.com/78872015/110706709-97961500-81f8-11eb-97d7-6cb10b1f65c0.PNG)

(3) Desde nuestro carrito, podemos ver un resumen del pedido:
![Realizar-pedido](https://user-images.githubusercontent.com/78872015/110706823-b98f9780-81f8-11eb-851c-550463a5319a.PNG)

## Diagrama de navegación:
![Diagrama_de_navegacion](https://user-images.githubusercontent.com/70818106/110705028-1473bf80-81f6-11eb-9bcb-6129c66a74d8.png)


## Diagrama de clases UML:
![UML](https://user-images.githubusercontent.com/70818106/110702629-138d5e80-81f3-11eb-8e01-aa0bf715b41d.png)

## Diagrama Entidad/Relación:
![Diagrama_Entidad-Relacion](https://user-images.githubusercontent.com/70818106/110702646-18eaa900-81f3-11eb-9963-20dd260f4bf3.png)

# FASE 3

## Capturas de las pantallas principales y diagrama de navegación:
Ni las pantallas principales ni el diagrama de navegación han cambiado respecto a la fase anterior, por lo que son las mismas capturas de la fase anterior

## Seguridad
- Las únicas rutas disponibles sin haber hecho login son "/" y "/login"
- Una vez se hace el login, estas son las posibilidades
	- Login como usuario normal: acceso a "/mi-perfil", "/categorias", "/cart"
	- Login como administrador: acceso a "/mi-perfil", "/nueva-categoria", "/nuevo-producto"
	
## Instrucciones para desplegar la aplicación:

1.- Arrancar el contenedor de docker con mysql 
 
	docker run --rm -e MYSQL_ROOT_PASSWORD=PASSWORD \
	-e MYSQL_DATABASE=NOMBREBD -p 3306:3306 -d mysql:8.0.22
 
2.- Clonar el repositorio actual
 
3.- Acceder al directorio ejecutables/ y ejecutar los siguientes comandos:
 
	java -jar periftech-0.0.1-SNAPSHOT.jar 
 
	java -jar ServicioInterno-0.0.1-SNAPSHOT.jar

5.- Acceder a localhost:9090/ y utilizar la aplicación

# FASE 4

Asumiendo que tenemos las imágenes de periftech y servicio-interno (haciendo "docker build -t nombreImagen . " en la correspondiente carpeta /target), sólo tendríamos que ejecutar

	docker-compose up -d

Y ya tendríamos todo lo necesario para utilizar nuestra aplicación desde localhost:8888 (nuestro balanceador)
