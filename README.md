# Capstone Project

A Spring Boot application for managing products, including CRUD operations and integration with an external API (FakeStore).

## Features

- Add, update, delete, and retrieve products
- Category management for products
- Integration with FakeStore API
- Exception handling for common errors
- RESTful endpoints

## Technologies Used

- Java
- Spring Boot
- Maven

## Getting Started

1. **Clone the repository:**
   ```
   git clone https://github.com/MaheshbabuCh/Capstone-project.git
   ```
2. **Navigate to the project directory:**
   ```
   cd Capstone-project
   ```
3. **Build the project:**
   ```
   mvn clean install
   ```
4. **Run the application:**
   ```
   mvn spring-boot:run
   ```

## API Endpoints

- `GET /products` - List all products
- `GET /products/{id}` - Get product by ID
- `POST /products` - Add a new product
- `PUT /products/{id}` - Update a product
- `DELETE /products/{id}` - Delete a product

## Project Structure
- `client/` - Client api handling
- `controllers/` - REST controllers
- `services/` - Business logic
- `models/` - Entity classes
- `dtos/` - Data transfer objects
- `exceptions/` - Custom exception classes

```
