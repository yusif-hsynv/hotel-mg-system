# 🏨 Hotel Management System (Spring Boot)

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen?logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-Database-blue?logo=mysql)
![Maven](https://img.shields.io/badge/Maven-Build-blueviolet?logo=apachemaven)
![Lombok](https://img.shields.io/badge/Lombok-Automation-red?logo=lombok)

A simple **Hotel Reservation and Management Application** built with **Spring Boot** and **MySQL**.  
It provides functionality for managing rooms, room types, customers, and reservations,  
with layered architecture and reusable response handling.

---

## 🚀 Features
- CRUD operations for **Customer**, **Room**, **Room Type**, and **Reservation**
- Room availability check before booking  
- Automatic calculation of reservation total price  
- Custom exception handling with `HotelException` and `ExceptionConstants`  
- Enum-based active/deactive control for entities (`EnumAvailableStatus`)  
- Clean layered architecture (Controller → Service → Repository)  
- Unified response model using `Response<T>` and `RespStatus`

---

## 🧠 Reservation Logic Overview
When creating a reservation:
1. The system checks if the **room** and **customer** exist and are active.  
2. It verifies that the **check-in** and **check-out** dates are valid.  
3. It checks room **availability** for overlapping dates using the repository query.  
4. If available, the **total price** is calculated automatically based on the room’s price and stay duration.  
5. The reservation is saved successfully to the database.

---

## ⚙️ Tech Stack
- Java 17  
- Spring Boot (Web, Data JPA)  
- MySQL  
- Maven  
- Lombok  

---
## 🧰 How to Run
```bash
git clone https://github.com/yusif-hsynv/hotel-management-spring.git
cd hotel-management-spring
mvn spring-boot:run
```
---
## 👨‍💻 Author  
**Yusif Hüseynov**  
*Java Developer | Spring Boot | REST APIs*  
[GitHub Profile](https://github.com/yusif-hsynv)
