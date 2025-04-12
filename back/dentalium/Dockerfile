FROM openjdk:17

WORKDIR /app

#COPY arialbd.ttf /usr/share/fonts/truetype/arialbd.ttf
#RUN apt-get update && apt-get install -y fontconfig && fc-cache -f -v
#RUN apt-get update && apt install --reinstall ttf-mscorefonts-installer && fc-cache -fv
#RUN apk add --no-cache fontconfig freetype

COPY target/dentalium.jar dentalium.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "dentalium.jar"]