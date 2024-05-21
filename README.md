# BOOK MANAGEMENT API

Welcome to my project **ssp-id-backend**! 🎉

## About This Project

**ssp-id-backend** is a backend API that allows you to manage book data efficiently. Built with Java Spring Boot and using SQL as the database, this API offers various features to add, delete, update and manage book data. The project also includes a secure authentication system with Bearer Authentication using access tokens and refresh tokens.

## Main Fiture

- 📖 **Book Data Registration**: Add, delete and update book data easily.
- 👥 **User Management**: Create and manage users who can access the API.
- 🗂️ **Book Categories**: Organize books by category for more structured management.
- 🚛 **Book Supplier**: Manage book supplier data.
- 🔐 **Login and Logout**: Secure authentication system using Bearer Authentication with access token and refresh token

## Model Entitys Structur

- **User**: Manages user data that has access to the API.
- **Book Products**: Manage book data including title, author, price, etc.
- **Book Categories**: Manage book categories for grouping.
- **Book Supplier**: Manage book supplier data.

## Teknology Used

- **Java Spring Boot**: The main framework for application development.
- **SQL Database**: Reliable and structured data storage.
- **Bearer Authentication**: For a secure authentication system.

## Installation

Follow the steps below to install and run this project in your local environment.

1. **Clone this repo**:
    ```sh
    git clone https://github.com/username/book-management-api.git
    ```
2. **Go to project Directory**:
    ```sh
    cd ssp-id-backend
    ```
3. **Install Dependency**:
    ```sh
    ./mvnw clean install -DskipTest
    ```
4. **Database Configuration**:
   Make sure you have setup your SQL database and updated `application.properties` with the correct database credentials.
6. **Run App**:
    ```sh
    ./mvnw spring-boot:run -DskipTest
    ```
    
## Contact

If you have any questions or feedback, please feel free to contact us via [hajrulahmad689@gmail.com].

---
Thank you for visiting our project! We hope you find the **Book Management API** useful and easy to use. Don't forget to give a ⭐ star if you like this project!


## Still in development 📖📖📖📖
