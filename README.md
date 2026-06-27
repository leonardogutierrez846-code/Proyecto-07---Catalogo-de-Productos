# Catálogo de Productos con Gestión de Inventario

Proyecto desarrollado para el Track 07: Spring Boot 3 Microservices. Implementa una arquitectura de microservicios con Product Service, Inventory Service y API Gateway.

## Tecnologías utilizadas

- Java 21
- Spring Boot 3.3.5
- Spring Cloud Gateway MVC
- Spring Data JPA
- H2 Database
- RestClient
- Springdoc OpenAPI 3
- Maven multi-módulo
- Docker y Docker Compose

## Estructura del proyecto

```text
catalogo-productos-inventario/
├── api-gateway/
├── product-service/
├── inventory-service/
├── docker-compose.yml
├── pom.xml
└── README.md
```

## Microservicios

### Product Service

Puerto: `8081`

Endpoints:

| Método | Ruta | Descripción |
|---|---|---|
| POST | `/api/products` | Registra un nuevo producto |
| GET | `/api/products` | Lista todos los productos |
| GET | `/api/products/{id}` | Obtiene un producto por ID |
| PUT | `/api/products/{id}` | Actualiza un producto |
| DELETE | `/api/products/{id}` | Elimina un producto |

Modelo principal:

```json
{
  "id": 1,
  "nombre": "Laptop Lenovo",
  "descripcion": "Laptop para oficina",
  "precio": 12500.00,
  "categoria": "Tecnologia"
}
```

### Inventory Service

Puerto: `8082`

Endpoints:

| Método | Ruta | Descripción |
|---|---|---|
| POST | `/api/inventory` | Registra stock de un producto |
| GET | `/api/inventory` | Lista registros de inventario |
| GET | `/api/inventory/{productId}` | Consulta stock de un producto y lo enriquece con datos del Product Service |
| PUT | `/api/inventory/{productId}` | Actualiza la cantidad disponible |

Modelo principal:

```json
{
  "id": 1,
  "productId": 1,
  "nombreProducto": "Laptop Lenovo",
  "precioProducto": 12500.00,
  "cantidadDisponible": 10,
  "ubicacion": "Almacen A"
}
```

### API Gateway

Puerto: `8080`

Rutas:

| Ruta Gateway | Servicio destino |
|---|---|
| `/api/products/**` | Product Service |
| `/api/inventory/**` | Inventory Service |

## Ejecución local con Maven

Abrir una terminal en la raíz del proyecto y compilar:

```bash
mvn clean package -DskipTests
```

Ejecutar Product Service:

```bash
mvn -pl product-service spring-boot:run
```

Ejecutar Inventory Service en otra terminal:

```bash
mvn -pl inventory-service spring-boot:run
```

Ejecutar API Gateway en otra terminal:

```bash
mvn -pl api-gateway spring-boot:run
```

## Ejecución con Docker Compose

Desde la raíz del proyecto:

```bash
docker compose up --build
```

Para detener los contenedores:

```bash
docker compose down
```

## Swagger UI

- Product Service: `http://localhost:8081/swagger-ui.html`
- Inventory Service: `http://localhost:8082/swagger-ui.html`

También se expone la documentación OpenAPI en:

- Product Service: `http://localhost:8081/v3/api-docs`
- Inventory Service: `http://localhost:8082/v3/api-docs`

## Pruebas rápidas

Crear producto usando el Gateway:

```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Laptop Lenovo","descripcion":"Laptop para oficina","precio":12500.00,"categoria":"Tecnologia"}'
```

Listar productos:

```bash
curl http://localhost:8080/api/products
```

Crear inventario:

```bash
curl -X POST http://localhost:8080/api/inventory \
  -H "Content-Type: application/json" \
  -d '{"productId":1,"cantidadDisponible":10,"ubicacion":"Almacen A"}'
```

Consultar inventario enriquecido:

```bash
curl http://localhost:8080/api/inventory/1
```

Actualizar inventario:

```bash
curl -X PUT http://localhost:8080/api/inventory/1 \
  -H "Content-Type: application/json" \
  -d '{"cantidadDisponible":20,"ubicacion":"Almacen B"}'
```

## Plataforma de IA elegida y justificación

La plataforma de IA elegida fue ChatGPT en capa gratuita/educativa, ya que permite resolver dudas técnicas, generar ejemplos de código y revisar errores durante el desarrollo. Se eligió porque facilita entender la estructura de un proyecto con microservicios en Spring Boot y ayuda a documentar mejor el proceso.

Ejemplos de uso durante el desarrollo:

1. Apoyo para definir la estructura multi-módulo con Maven, separando `product-service`, `inventory-service` y `api-gateway`.
2. Apoyo para implementar la comunicación síncrona entre Inventory Service y Product Service usando `RestClient`.
3. Apoyo para configurar Docker Compose y levantar los tres servicios de forma local.
4. Apoyo para redactar el README con instrucciones claras de instalación, ejecución y pruebas.

## Retos o dificultades enfrentadas

Uno de los principales retos fue configurar correctamente la comunicación entre microservicios, ya que Inventory Service depende de Product Service para enriquecer la respuesta del inventario. También fue importante configurar correctamente las rutas del API Gateway y las variables de entorno para que el proyecto funcionara tanto de forma local como con Docker Compose.

## Ramas sugeridas para GitHub

```text
main        -> README inicial creado por GitHub
wip         -> rama de trabajo con al menos 5 commits
development -> rama final creada desde wip cuando el proyecto esté terminado
```

