# Web Service API Documentation

## Overview

This Spring Boot application provides a RESTful API for managing products, orders, and user authentication using MongoDB as the database.

## Base URL

```
http://localhost:81/api
```

## Authentication

The API uses JWT (JSON Web Token) for authentication. Include the token in the Authorization header for protected endpoints:

```
Authorization: Bearer {your_jwt_token}
```

## Endpoints

### Authentication

#### Register a new user

```
POST /account/register
```

Request body:

```json
{
  "email": "user@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe"
}
```

Response:

```json
{
  "token": "jwt_token_here",
  "type": "Bearer",
  "id": "60d5f7b9e4b0f3b8a4f9e7d1",
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe"
}
```

#### Login

```
POST /account/login
```

Request body:

```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

Response:

```json
{
  "token": "jwt_token_here",
  "type": "Bearer",
  "id": "60d5f7b9e4b0f3b8a4f9e7d1",
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe"
}
```

#### Direct Login (without password verification)

```
POST /account/direct-login
```

Request body:

```json
{
  "email": "user@example.com",
  "password": "any_value"
}
```

Response:

```json
{
  "token": "jwt_token_here",
  "type": "Bearer",
  "id": "60d5f7b9e4b0f3b8a4f9e7d1",
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe"
}
```

### Products

#### Get all products

```
GET /products
```

Response:

```json
[
  {
    "id": "60d5f7b9e4b0f3b8a4f9e7d1",
    "code": "PROD001",
    "name": "Product Name",
    "price": 19.99,
    "illustration": "image_url",
    "description": "Product description"
  }
]
```

#### Get product by ID

```
GET /products/{id}
```

Response:

```json
{
  "id": "60d5f7b9e4b0f3b8a4f9e7d1",
  "code": "PROD001",
  "name": "Product Name",
  "price": 19.99,
  "illustration": "image_url",
  "description": "Product description"
}
```

#### Create a new product (requires authentication)

```
POST /products
```

Request body:

```json
{
  "code": "PROD001",
  "name": "Product Name",
  "price": 19.99,
  "illustration": "image_url",
  "description": "Product description"
}
```

Response:

```json
{
  "id": "60d5f7b9e4b0f3b8a4f9e7d1",
  "code": "PROD001",
  "name": "Product Name",
  "price": 19.99,
  "illustration": "image_url",
  "description": "Product description"
}
```

#### Update a product (requires authentication)

```
PUT /products/{id}
```

Request body:

```json
{
  "code": "PROD001",
  "name": "Updated Product Name",
  "price": 29.99,
  "illustration": "new_image_url",
  "description": "Updated product description"
}
```

Response:

```json
{
  "id": "60d5f7b9e4b0f3b8a4f9e7d1",
  "code": "PROD001",
  "name": "Updated Product Name",
  "price": 29.99,
  "illustration": "new_image_url",
  "description": "Updated product description"
}
```

#### Partially update a product (requires authentication)

```
PATCH /products/{id}
```

Request body (only include fields to update):

```json
{
  "price": 29.99,
  "description": "Updated product description"
}
```

Response:

```json
{
  "id": "60d5f7b9e4b0f3b8a4f9e7d1",
  "code": "PROD001",
  "name": "Product Name",
  "price": 29.99,
  "illustration": "image_url",
  "description": "Updated product description"
}
```

#### Delete a product (requires authentication)

```
DELETE /products/{id}
```

Response: HTTP 204 No Content

### Orders

#### Get all orders (requires authentication)

```
GET /orders
```

Response:

```json
[
  {
    "id": "60d5f7b9e4b0f3b8a4f9e7d2",
    "orderNumber": "ORD-12345678",
    "totalPrice": 59.97,
    "createdAt": "2025-05-20T14:30:00",
    "user": {
      "id": "60d5f7b9e4b0f3b8a4f9e7d1",
      "email": "user@example.com",
      "firstName": "John",
      "lastName": "Doe"
    },
    "items": [
      {
        "id": "60d5f7b9e4b0f3b8a4f9e7d3",
        "product": {
          "id": "60d5f7b9e4b0f3b8a4f9e7d1",
          "code": "PROD001",
          "name": "Product Name",
          "price": 19.99,
          "illustration": "image_url",
          "description": "Product description"
        },
        "quantity": 3,
        "price": 19.99
      }
    ]
  }
]
```

#### Get order by ID (requires authentication)

```
GET /orders/{id}
```

Response:

```json
{
  "id": "60d5f7b9e4b0f3b8a4f9e7d2",
  "orderNumber": "ORD-12345678",
  "totalPrice": 59.97,
  "createdAt": "2025-05-20T14:30:00",
  "user": {
    "id": "60d5f7b9e4b0f3b8a4f9e7d1",
    "email": "user@example.com",
    "firstName": "John",
    "lastName": "Doe"
  },
  "items": [
    {
      "id": "60d5f7b9e4b0f3b8a4f9e7d3",
      "product": {
        "id": "60d5f7b9e4b0f3b8a4f9e7d1",
        "code": "PROD001",
        "name": "Product Name",
        "price": 19.99,
        "illustration": "image_url",
        "description": "Product description"
      },
      "quantity": 3,
      "price": 19.99
    }
  ]
}
```

#### Create a new order (requires authentication)

```
POST /orders
```

Request body:

```json
{
  "items": [
    {
      "productId": "60d5f7b9e4b0f3b8a4f9e7d1",
      "quantity": 3
    },
    {
      "productId": "60d5f7b9e4b0f3b8a4f9e7d4",
      "quantity": 1
    }
  ]
}
```

Response:

```json
{
  "id": "60d5f7b9e4b0f3b8a4f9e7d2",
  "orderNumber": "ORD-12345678",
  "totalPrice": 79.96,
  "createdAt": "2025-05-20T14:30:00",
  "user": {
    "id": "60d5f7b9e4b0f3b8a4f9e7d1",
    "email": "user@example.com",
    "firstName": "John",
    "lastName": "Doe"
  },
  "items": [
    {
      "id": "60d5f7b9e4b0f3b8a4f9e7d3",
      "product": {
        "id": "60d5f7b9e4b0f3b8a4f9e7d1",
        "code": "PROD001",
        "name": "Product Name",
        "price": 19.99,
        "illustration": "image_url",
        "description": "Product description"
      },
      "quantity": 3,
      "price": 19.99
    },
    {
      "id": "60d5f7b9e4b0f3b8a4f9e7d5",
      "product": {
        "id": "60d5f7b9e4b0f3b8a4f9e7d4",
        "code": "PROD002",
        "name": "Another Product",
        "price": 19.99,
        "illustration": "image_url",
        "description": "Another product description"
      },
      "quantity": 1,
      "price": 19.99
    }
  ]
}
```

#### Update an order (requires authentication)

```
PUT /orders/{id}
```

Request body:

```json
{
  "items": [
    {
      "productId": "60d5f7b9e4b0f3b8a4f9e7d1",
      "quantity": 2
    },
    {
      "productId": "60d5f7b9e4b0f3b8a4f9e7d6",
      "quantity": 4
    }
  ]
}
```

Response: Updated order object

#### Delete an order (requires authentication)

```
DELETE /orders/{id}
```

Response: HTTP 204 No Content

## Error Responses

### Resource Not Found (404)

```json
{
  "status": 404,
  "message": "Resource not found with id: 60d5f7b9e4b0f3b8a4f9e7d1",
  "timestamp": 1621505395123
}
```

### Bad Request (400)

```json
{
  "status": 400,
  "message": "Email is already taken!",
  "timestamp": 1621505395123
}
```

### Validation Error (400)

```json
{
  "email": "must be a well-formed email address",
  "password": "size must be between 6 and 40"
}
```

### Unauthorized (401)

```json
{
  "status": 401,
  "message": "Invalid email or password",
  "timestamp": 1621505395123
}
```

## Database Configuration

The application uses MongoDB as the database. The connection settings can be found in the `application.properties` file:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/webservice_db
```

## Security Configuration

- JWT token expiration: 24 hours (86400000 milliseconds)
- JWT secret key is configured in `application.properties`

## Server Configuration

- Port: 81
- Context Path: /api
