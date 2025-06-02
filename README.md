## Dentalium, gestor de pacientes en una clínica dental

### 1. Descripción

**Dentalium** es una aplicación web que tiene como principal objetivo ayudar en la gestión de los pacientes de una clínica dental.  
- El profesional **odontólogo** puede acceder al historial de un paciente, citarle conforme a su agenda e incluir presupuestos y estudios médicos (tanto imágenes como pruebas diagnósticas en formato DICOM). También puede emitir justificantes de asistencia a consulta firmados electrónicamente.  
- Por su parte los **pacientes** pueden consultar y confirmar sus citas, así como descargar o exportar todo el material proporcionado por su odontólogo.   
- Un usuario **administrador** será encargado de controlar los roles de profesionales y pacientes, así como los tratamientos.

### 2. Características técnicas

- El proyecto presenta una división entre frontend y backend, habiéndose realizado el frontend en Angular y el backend en Java empleando Spring Boot con arquitectura hexagonal. Ha sido necesaria la implementación de una base de datos en MySQL.  

- El despliegue se encuentra Dockerizado y desplegado en un VPS. Es necesario situar junto al `docker-compose.yml` el fichero de variables de entorno `docker.env` con el siguiente contenido:

```
 DB_URL=url_base_de_datos
 DB_USER=nombre_usuario_root
 DB_PASSWORD=password_usuario_root
 MYSQL_ROOT_PASSWORD=password_usuario_root
 MYSQL_DATABASE:nombre_base_de_datos
 MYSQL_USER:nombre_usuario_mysql
 MYSQL_PASSWORD:password_usuario_mysql
 JWT_SECRET=secreto_jwt
 MAIL_USERNAME=direccion_mail
 MAIL_PASSWORD=password_mail
 CERTIFICATE_PASSWORD=password_certificado_digital
 FRONTEND_URL=http://url_despliegue_front
 FRONTEND_URL_2=http://url_despliegue_front
```

- También es necesario modificar el fichero `front/src/environment/environment.ts` para indicarle al front a qué backend debe apuntar (indicándole el backend local):

```typescript
export const environment = {
    apiUrl: 'http://localhost:8080/dentalium/api'
};
```

- Además, de forma opcional, puede ubicarse un certificado digital (p.12) en la ruta `documents/certificate.p12` para realizar la firma digital de los documentos de la clínica

### 3. Colaboraciones

Aunque trata de un proyecto con aplicaciones puramente didácticas que no va a ser utilizado en un entorno productivo, puedes abrir un PR realizado mediante fork del repositorio a la rama `develop`. ¡Gracias!