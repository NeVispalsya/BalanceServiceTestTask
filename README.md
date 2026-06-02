# Balance Service Test Task

## Description

REST API service for managing user balances.

The service provides functionality for:

* Depositing funds to a user's balance
* Viewing current balance
* Reserving funds for an order
* Confirming reserved funds
* Canceling reservations and returning funds
* Viewing transaction history

The project is implemented using Spring Boot, Spring Data JPA, Hibernate, and MySQL.

---

## Technologies

* Java 21
* Spring Boot
* Spring Data JPA
* Hibernate
* MySQL
* Lombok
* Swagger / OpenAPI
* Maven

---

## Project Structure

### Balance

Stores user balances.

Fields:

* userId
* balance
* reservedBalance
* createdAt
* updatedAt

### Reservation

Stores information about reserved funds.

Fields:

* userId
* serviceId
* orderId
* amount
* status
* createdAt

Reservation statuses:

* RESERVED
* CONFIRMED
* CANCELED

### Transaction

Stores balance operation history.

Transaction types:

* DEPOSIT
* RESERVE
* CONFIRM
* UNRESERVE

---

## Business Logic

### Deposit

Adds funds to a user's balance.

If the user balance record does not exist, it is created automatically.

---

### Get Balance

Returns:

* available balance
* reserved balance

---

### Reserve Funds

Moves funds from available balance to reserved balance.

Conditions:

* user must exist
* balance must be sufficient
* orderId must be unique

---

### Confirm Reservation

Confirms previously reserved funds.

Result:

* reserved balance decreases
* reservation status becomes CONFIRMED
* transaction is written to history

---

### Cancel Reservation

Returns reserved funds back to the available balance.

Result:

* balance increases
* reserved balance decreases
* reservation status becomes CANCELED
* transaction is written to history

---

### Transaction History

Returns all operations performed by a user.

---

## API Endpoints

### Deposit Funds

POST /balance/deposit

Request:

```json
{
  "userId": 1,
  "amount": 1000
}
```

Response:

```json
{
  "userId": 1,
  "amount": 1000,
  "mess": "Deposit access"
}
```

---

### Get Balance

GET /balance/{userId}

Response:

```json
{
  "userId": 1,
  "balance": 700,
  "reservedBalance": 300
}
```

---

### Reserve Funds

POST /balance/reserve

Request:

```json
{
  "userId": 1,
  "amount": 300,
  "serviceId": 10,
  "orderId": 100
}
```

Response:

```json
{
  "userId": 1,
  "amount": 300,
  "serviceId": 10,
  "orderId": 100
}
```

---

### Confirm Reservation

POST /balance/confirm/{orderId}

Response:

```json
{
  "orderId": 100,
  "message": "Order 100 confirm!"
}
```

---

### Cancel Reservation

POST /balance/unreserve/{orderId}

Response:

```json
{
  "orderId": 100,
  "message": "Order 100 unreserve!"
}
```

---

### Get Transaction History

GET /transaction/{userId}

Response:

```json
{
  "userId": 1,
  "transactions": [
    {
      "id": 1,
      "userId": 1,
      "type": "DEPOSIT",
      "amount": 1000
    }
  ]
}
```

---

## Database Configuration

application.properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/balance
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## Running the Project

1. Create MySQL database:

```sql
CREATE DATABASE balance;
```

2. Configure database credentials in `application.properties`.

3. Run the application:

```bash
mvn spring-boot:run
```

or run:

```java
MarketShopTestTaskApplication.main()
```

---

## Swagger

After application startup:

```
http://localhost:8080/swagger-ui/index.html
```

---

## Features Implemented

* Deposit funds
* Balance retrieval
* Fund reservation
* Reservation confirmation
* Reservation cancellation
* Transaction history
* DTO-based API responses
* Exception handling
* Swagger documentation

---

## Author

Nikita

Java Developer
