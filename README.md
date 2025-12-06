# Proyecto de la materia Desarrollo Web Backend (2025-2

Este repositorio contiene la arquitectura de microservicios desarrollada para la materia de Desarrollo Web Backend. El sistema utiliza **Spring Boot** y **Spring Cloud** para gestionar la facturación, productos, autenticación y clientes de un sistema de comercio electrónico.

## 📋 Requisitos Previos

Asegúrate de tener instalado lo siguiente antes de ejecutar el proyecto:

* **Java 21** (o 17 según compatibilidad)
* **Maven** 3.8+
* **MySQL** (Corriendo en el puerto 3306)
* **Postman** (Para pruebas de endpoints)

## ⚙️ Configuración del Entorno

### 1. Servidor de Configuración
La configuración centralizada no se encuentra en este repositorio. Los microservicios obtienen su configuración del siguiente repositorio externo, el cual debe ser clonado o accesible por el `config-service`:

* **Repositorio Config:** [https://github.com/Daniiel314/config-data](https://github.com/Daniiel314/config-data)

### 2. Variables de Entorno (IMPORTANTE)
Para que los servicios conecten correctamente a la base de datos sin exponer credenciales en el código, es **obligatorio** configurar la siguiente variable de entorno en tu sistema operativo o en tu IDE (Eclipse/IntelliJ) antes de ejecutar los servicios:

| Variable | Descripción | Ejemplo |
| :--- | :--- | :--- |
| `DB_PASSWORD` | Tu contraseña local de MySQL (root) | `12345`, `root`, `admin` |

> **Nota:** Si no configuras esta variable, los servicios de base de datos (`invoice`, `auth`, `customer`, `product`) fallarán al iniciar.

---

## 🚀 Orden de Ejecución

Para levantar la arquitectura correctamente, inicia los servicios en el siguiente orden estricto:

1.  **Infrastructure Services:**
    1.  `config-service` (Puerto 8888) - *Debe estar completamente arriba antes de seguir.*
    2.  `registry-service` (Eureka Server - Puerto 8761)

2.  **Core Microservices:**
    * `auth-service`
    * `customer-service`
    * `invoice-service`
    * `product-api`
    * `admin-service`

3.  **Edge Server:**
    * `gateway-service` (Puerto 8080) - *Punto de entrada único para las peticiones.*

---

## 📡 Endpoints Principales

Todas las peticiones deben pasar por el **Gateway** en el puerto `8080`.

| Servicio | Ruta Base en Gateway | Descripción |
| :--- | :--- | :--- |
| **Auth** | `/auth-service/**` | Login y validación de tokens JWT. |
| **Customer** | `/customer-service/**` | Gestión de clientes y regiones. |
| **Invoice** | `/invoice-service/**` | Carrito de compras y facturación. |
| **Product** | `/product-api/**` | Catálogo de productos y categorías. |

> **Ejemplo de prueba:** `GET http://localhost:8080/product-api/product`

## 🛠️ Tecnologías Utilizadas

* **Framework:** Spring Boot 3.3.6
* **Cloud:** Spring Cloud (Config, Gateway MVC, Netflix Eureka)
* **Seguridad:** Spring Security + JWT (JSON Web Tokens)
* **Base de Datos:** MySQL (JPA/Hibernate)
* **Build Tool:** Maven

## 📄 Notas Adicionales

* Si encuentras errores de conexión (Connection Refused), verifica que el `config-service` y `registry-service` estén activos.
* El servicio de facturación (`invoice-service`) requiere un token Bearer válido obtenido del `auth-service` para realizar operaciones de carrito.

---
**Autor:** Juan Daniel Barrera Holan
**Materia:** Desarrollo Web Backend 2025-2
