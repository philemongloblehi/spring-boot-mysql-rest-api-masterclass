ARG VERSION

FROM openjdk:${VERSION}-jdk-alpine3.13 as build
LABEL vendor="10000codeurs/api-rest-spring-boot" maintainer="philemon.globlehi@gmail.com"

WORKDIR /app

COPY . .

RUN set -ex && \
	./mvnw clean compile && \
	cp target/*.jar app.jar

FROM build as dev

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar ${0} ${@}"]

RUN ./mvnw test

FROM openjdk:${VERSION}-jdk-alpine3.13

COPY --from=build /app/app.jar app.jar

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar ${0} ${@}"]
