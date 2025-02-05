
## 📌 Monorepo: Spring Ecosystem

Este repositorio contiene múltiples proyectos relacionados con el ecosistema de Spring Boot, organizados en diferentes ramas. Cada una aborda un tema específico, desde autenticación hasta microservicios y pruebas.

### 📂 Estructura del Monorepo
```
Spring-Ecosystem
|-- auth-service/          # OAuth2, JWT, Spring Security
|-- api-gateway/           # Spring Cloud Gateway, Eureka
|-- microservice-1/        # Ejemplo con REST y JPA
|-- microservice-2/        # Otro servicio con WebFlux
|-- batch-processing/      # Spring Batch
|-- event-driven/          # Kafka, RabbitMQ
|-- testing/               # TestContainers, JUnit5, Mockito
|-- docs/                  # Documentación y guías
```

---

## 📂 Ramas y Descripción

### 🔐 `auth-service`
```md
# Spring Authentication & Security 🔐

Este proyecto demuestra cómo implementar autenticación y autorización en aplicaciones Spring Boot. Incluye JWT, OAuth2 y Spring Security.

## 📌 Características
- 🔑 Autenticación con JWT
- 🔒 Seguridad con Spring Security
- 🌍 OAuth2 con Authorization Server
- 🛠 Configuración con Spring Boot 3.x

## 🚀 Instalación
```sh
git clone -b auth-service https://github.com/tu-usuario/spring-ecosystem.git
docker-compose up -d  # Si usa contenedores
mvn clean install
```
```

---

### 📂 `spring-rest-api`
```md
# Spring Boot REST API 🛠️

Este repositorio contiene un CRUD completo con Spring Boot, JPA y PostgreSQL.

## 📌 Tecnologías
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL
- Swagger OpenAPI

## 🚀 Instalación
```sh
git clone -b spring-rest-api https://github.com/tu-usuario/spring-ecosystem.git
mvn clean install
```
```

---

### ⚡ `spring-webflux`
```md
# Spring WebFlux ⚡

Ejemplo de arquitectura reactiva usando Spring WebFlux.

## 🚀 Características
- Programación reactiva con Project Reactor
- API asíncrona y no bloqueante
- Conexión con MongoDB reactivo

## 🛠 Instalación
```sh
git clone -b spring-webflux https://github.com/tu-usuario/spring-ecosystem.git
mvn clean install
```
```

---

### 🏗️ `spring-microservices`
```md
# Spring Microservices 🏗️

Ejemplo de arquitectura de microservicios con Spring Boot.

## 🏛 Tecnologías
- Spring Cloud Gateway
- Eureka Server & Client
- Resilience4J (Circuit Breaker)
- OpenFeign (Comunicación entre microservicios)

## 🚀 Instalación
```sh
git clone -b spring-microservices https://github.com/tu-usuario/spring-ecosystem.git
mvn clean install
```
```

---

### 📩 `spring-kafka-rabbitmq`
```md
# Spring Messaging: Kafka & RabbitMQ 📩

Este proyecto muestra cómo integrar Spring Boot con Kafka y RabbitMQ para sistemas event-driven.

## 🏛 Tecnologías
- Apache Kafka
- RabbitMQ
- Spring Boot Messaging

## 🚀 Instalación
```sh
git clone -b spring-kafka-rabbitmq https://github.com/tu-usuario/spring-ecosystem.git
mvn clean install
```
```

---

### 🧪 `spring-testing`
```md
# Spring Testing 🧪

Este repositorio contiene ejemplos de pruebas unitarias e integraciones con Spring Boot.

## 🔬 Tecnologías
- JUnit 5
- Mockito
- TestContainers

## 🚀 Instalación
```sh
git clone -b spring-testing https://github.com/tu-usuario/spring-ecosystem.git
mvn clean test
```
```

---

## 📜 Licencia
MIT

---

Estos `README.md` deben colocarse en cada una de sus ramas respectivas para mantener una documentación clara. 🚀
