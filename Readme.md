## Project Structure

- **src/main/java/com/example/demo/**
  - `DemoApplication.java`
  - **controller/**
    - `UserController.java`
  - **service/**
    - `UserService.java`
  - **repository/**
    - `UserRepository.java`
  - **entity/**
    - `User.java`
  - **dto/**
    - `UserDTO.java`
  - **mapper/**
    - `UserMapper.java`

---

### ✅ **What This README Includes**
✔ **Project Overview**  
✔ **Technology Stack**  
✔ **Setup Instructions**  
✔ **API Documentation**  
✔ **Testing Instructions**  
✔ **License Information**  

---

# 🏗️ User Management API

![GitHub Workflow Status](https://img.shields.io/github/actions/workflow/status/YOUR_USERNAME/YOUR_REPO/ci.yml?branch=main)

## 🚀 Overview
This is a **Spring Boot REST API** for managing users. It supports **CRUD operations** and follows best practices like **DTO mapping**, **exception handling**, and **validation**.

## 🛠️ Technologies Used
- Java 21
- Spring Boot 3.x
- Spring Data JPA
- Lombok
- MapStruct
- H2 Database (for development)
- Maven

## 🔥 Features
✔ User registration  
✔ Retrieve all users  
✔ Update user details  
✔ Delete a user  

## 🏗️ Installation

### **1. Clone the repository**
```bash
git clone https://github.com/YOUR_USERNAME/YOUR_REPO.git
cd YOUR_REPO
```

### **2. Run the application**
```bash
./mvnw spring-boot:run
```
or (if Maven is installed globally):
```bash
mvn spring-boot:run
```

## 📡 API Endpoints

| Method | Endpoint       | Description           |
|--------|--------------|----------------------|
| POST   | `/users`     | Create a new user    |
| GET    | `/users`     | Get all users        |
| GET    | `/users/{id}` | Get user by ID       |
| PUT    | `/users/{id}` | Update user details  |
| DELETE | `/users/{id}` | Delete user          |

## 📋 Request Example

### **Create User**
```json
{
  "name": "John Doe",
  "email": "john@example.com"
}
```

## 🧪 Running Tests
To run unit tests:
```bash
mvn test
```

## 📜 License
This project is licensed under the MIT License.
---


**Automate testing** every time you push changes! 🎉  

