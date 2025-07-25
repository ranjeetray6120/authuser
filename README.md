# AuthUser - Spring Boot Authentication System

A complete user authentication system built with **Spring Boot**, **JWT**, **Email OTP verification**, and a clean **Thymeleaf + Tailwind CSS** UI.

---

## 🚀 Features

- ✅ User Registration (Signup)
- ✅ Email OTP verification before account activation
- ✅ Secure JWT-based Login
- ✅ TailwindCSS-powered responsive UI
- ✅ Thymeleaf template integration
- ✅ Spring Security Authentication
- ✅ In-memory OTP store and validation
- ✅ Clean separation of layers (Controller, Service, Repository)

---

## 🔐 Technologies Used

- Spring Boot 3
- Spring Security
- Spring Data JPA
- JWT (JSON Web Token)
- Java Mail Sender
- H2 / MySQL (configurable)
- Lombok
- Thymeleaf
- Tailwind CSS
- Git + GitHub

---

## 📸 Screenshots


| Signup Page | OTP Verification | Login Page | Dashboard |
|-------------|------------------|-------------|------------|
| ![Signup](screenshots/signup.png) | ![OTP](screenshots/verify-otp.png) | ![Login](screenshots/login.png) | ![Success](screenshots/dashboard.png) |

> 🔎 Screenshots should be saved in a `screenshots/` folder.

---

## ⚙️ Project Structure

authdemo/
├── src/
│ ├── main/
│ │ ├── java/com/example/authdemo/
│ │ │ ├── controller/
│ │ │ ├── dto/
│ │ │ ├── model/
│ │ │ ├── repository/
│ │ │ ├── security/
│ │ │ ├── service/
│ ├── resources/
│ │ ├── templates/
│ │ │ ├── signup.html
│ │ │ ├── verify-otp.html
│ │ │ ├── login.html
│ │ │ └── success.html
│ │ └── application.properties
├── pom.xml
└── README.md


---

## 🛠️ Getting Started

### ✅ Prerequisites
- Java 17+
- Maven
- Git

### 🔧 Steps to Run Locally

git clone https://github.com/ranjeetray6120/authuser.git
cd authuser
mvn clean install
mvn spring-boot:run


Open in browser:
👉 http://localhost:8080/auth/signup
