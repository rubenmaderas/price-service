# 📜 Price Service

![Coverage](https://img.shields.io/badge/Coverage-97.5%25-brightgreen)

Servicio REST para consultar el precio aplicable de un producto en una fecha determinada, según marca, producto y prioridad tarifaria. Desarrollado como parte de una prueba técnica utilizando **Spring Boot**, siguiendo principios de **Domain-Driven Design (DDD)** y **arquitectura hexagonal**.

---

## 📌 Descripción del problema

La base de datos contiene una tabla `PRICES` que registra el precio final y la tarifa que aplica a un producto de una marca durante un intervalo de fechas. Si existen múltiples precios válidos en un rango, se aplicará el de **mayor prioridad**.

### Ejemplo de tabla `PRICES`:

| ID | BRAND\_ID | PRODUCT\_ID | PRICE\_LIST | PRIORITY | START\_DATE         | END\_DATE           | PRICE | CURRENCY |
|----|-----------|-------------|-------------|----------|---------------------|---------------------|-------|----------|
| 1  | 1         | 35455       | 1           | 0        | 2020-06-14 00:00:00 | 2020-12-31 23:59:59 | 35.50 | EUR      |
| 2  | 1         | 35455       | 2           | 1        | 2020-06-14 15:00:00 | 2020-06-14 18:30:00 | 25.45 | EUR      |
| 3  | 1         | 35455       | 3           | 1        | 2020-06-15 00:00:00 | 2020-06-15 11:00:00 | 30.50 | EUR      |
| 4  | 1         | 35455       | 4           | 1        | 2020-06-15 16:00:00 | 2020-12-31 23:59:59 | 38.95 | EUR      |

---

## 🛡️ Arquitectura

El proyecto está diseñado siguiendo la **arquitectura hexagonal**, con las siguientes capas:

- **Dominio** (`domain`): Modelos, interfaces de repositorio y lógica de negocio.
- **Aplicación** (`application`): Casos de uso (servicios).
- **Infraestructura** (`infrastructure`): Adaptadores REST y de persistencia (H2), DTO y mapeadores.

---

## 🛠️ Tecnologías

- Java 17
- Spring Boot 3.5.3
- H2 Database (modo en memoria)
- Spring Data JPA
- MapStruct
- Lombok
- Jakarta Validation
- JUnit 5 + Spring Test (MockMvc)

---

## 📁 Estructura de paquetes

```
com.ecommerce.priceservice
├── application
│   ├── services
│   └── usecases
├── domain
│   ├── models
│   ├── exceptions
│   └── repository
├── infrastructure
│   ├── adapters
│   │   ├── in
│   │   │   ├── controllers
│   │   │   ├── dto
│   │   │   └── mappers
│   │   └── out
│   │       └── persistence
│   │           └── h2
│   │               ├── entity
│   │               ├── mappers
│   │               └── repository
│   └── exceptions
└── PriceServiceApplication.java
```

---

## 🌐 Endpoint principal

### `GET /brands/{brandId}/products/{productId}/prices`

Consulta el precio vigente para un producto de una marca en una fecha concreta.

#### Parámetros:

| Parámetro         | Tipo     | Descripción                                                  |
|-------------------|----------|--------------------------------------------------------------|
| `brandId`         | `Long`   | Identificador de la marca (path variable)                    |
| `productId`       | `Long`   | Identificador del producto (path variable)                   |
| `applicationDate` | `String` | Fecha de consulta (`yyyy-MM-dd'T'HH:mm:ss`) como query param |

#### Ejemplo:

```
GET /brands/1/products/35455/prices?applicationDate=2020-06-16T21:00:00
```

#### Respuesta esperada:

```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 4,
  "startDate": "2020-06-15T16:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 38.95
}
```

---

## ✅ Pruebas funcionales

El sistema ha sido validado con los siguientes escenarios:

| Caso | Fecha consulta      | Resultado esperado                       |
|------|---------------------|------------------------------------------|
| 1    | 2020-06-14 10:00:00 | Tarifa 1, precio 35.50                   |
| 2    | 2020-06-14 16:00:00 | Tarifa 2, precio 25.45 (prioridad mayor) |
| 3    | 2020-06-14 21:00:00 | Tarifa 1, precio 35.50                   |
| 4    | 2020-06-15 10:00:00 | Tarifa 3, precio 30.50                   |
| 5    | 2020-06-16 21:00:00 | Tarifa 4, precio 38.95                   |

---

## 🗄️ Base de datos

- Motor: **H2 en memoria**
- Script de carga inicial: `resources/data.sql`
- Accesible en `http://localhost:8080/h2-console` (consola habilitada en desarrollo)

---

## ▶️ Cómo ejecutar

```bash
./mvnw clean spring-boot:run
```

La aplicación quedará disponible en:\
👉 `http://localhost:8080`

---

## 🧪 Ejecutar tests

```bash
./mvnw test
```

---

## 🔗 Repositorio Git

- Proyecto: [price-service](https://github.com/rubenmaderas/price-service)
- Clonado:

```bash
git clone https://github.com/rubenmaderas/price-service.git
```

---

## 📄 Licencia

Este proyecto es parte de una prueba técnica.

---

© 2025 - Ruben Maderas