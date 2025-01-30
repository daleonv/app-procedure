# Instrucciones

1) Se debe descargar el proyecto app-gradle. En este proyecto se centralizan todas las versiones y las dependencias del proyecto.
   Este proyecto debe estar en la misma ruta de los otros proyectos.

`git clone https://github.com/daleonv/app-gradle.git`

Este proyecto no necesita ser compilado

2) Se debe descargar el proyecto app-entities. En este proyecto está el mapeo de las entidades

`git clone https://github.com/daleonv/app-entities.git`

Este proyecto debe ser compilado.

`gradle clean build publish`

3) Se debe descargar el proyecto app-microservices. En este proyecto se hallan las capas principales del proyecto.

`git clone https://github.com/daleonv/app-microservices.git`

Para levantar el proyecto se debe tener levantada la base de datos en xampp y posteriormente se debe usar el siguiente comando:

`gradle bootRun`

El archivo json para la prueba de los web services está en la raíz del proyecto.
El script para la creación de la base de datos está también en la raíz. Es para MySQL
