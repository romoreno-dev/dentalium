# Etapa 1: Construcción de Angular
# Se toma imagen de node y se nombra la etapa como build
FROM node:18 AS build
# Se indica el directorio de trabajo dentro del contenedor
WORKDIR /app
# Se copian las dependencias que necesita la aplicacion
COPY package.json package-lock.json ./
# Se instalan las dependencias de Node.js
RUN npm install -g @angular/cli
RUN npm install
# Se copia el codigo fuente del directorio raiz al directorio de trabajo
COPY . .
# Se ejecuta el comando build del package.json
RUN npm run build

# Etapa 2: Servir la aplicacion en Nginx
FROM nginx:alpine
# Se copian los archivos de la etapa build a la nueva imagen
COPY --from=build /app/dist/dentalium/browser /usr/share/nginx/html
# Exponer el puerto 4200
EXPOSE 4200
