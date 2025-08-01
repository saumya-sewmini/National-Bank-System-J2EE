# National Banking System

## 📌 Introduction

This is a full-fledged **banking system** developed as part of my university assignment for the **Business Component Development 2** subject. The application is built using **Java EE**, deployed on **GlassFish**, and follows a clean multi-module architecture.

---

## ⚙️ Technologies Used

* Java EE (JDK 21)
* GlassFish Server
* MySQL
* JPA (Java Persistence API)
* EJB (Enterprise JavaBeans)
* Servlets & JSP
* Jakarta Annotations

---

## 🧱 Project Structure (Multi-Module)

* `core`: Business logic, entity classes, service interfaces, `persistence.xml`
* `auth`: Handles login and user authentication
* `bank`: Contains session beans for core banking features (accounts, transactions)
* `web`: Frontend using JSPs, Servlets, and `web.xml` for security constraints
* `ear`: EAR module for final deployment packaging

---

## 🔐 Security Architecture

* **Role-Based Access Control** using `web.xml` and `@RolesAllowed`
* Roles: `ADMIN`, `OFFICER`, `CUSTOMER`
* URL restriction based on role using security constraints
* Backend protection using EJB security annotations

---

## 🔄 Time-Sensitive Operations

Automated using **EJB Timer Services**:

* **Scheduled Fund Transfers**: Executed at future date/time
* **Daily Interest Calculation**: Runs every day at midnight

Implemented using `@Schedule` annotations:

```java
@Schedule(hour = "0", minute = "0", second = "0", persistent = false)
```

---

## 📑 Transactional Management

* Container-managed transactions using `@TransactionAttribute(REQUIRED)`
* Ensures atomicity for debit and credit operations
* Automatic rollback on failure (e.g., insufficient funds)

### 💡 Logging Interceptor

* Logs transfer details (account, amount, timestamp, exceptions)
* Uses `@AroundInvoke` with `@TransactionAttribute(REQUIRES_NEW)` to log in separate transactions

---

## 🛡 Exception Handling

* Controlled using `@ApplicationException(rollback = true)`
* Graceful error messages for user experience
* Rollbacks ensure data integrity

---

## 🔍 Testing Strategy

* Persistent vs non-persistent EJB Timer behavior post-restart
* Load test with 100+ transfers using **JMeter**
* Manual method calls to test interest accrual every minute
* Security test using multiple roles

---

## 📁 Split Directory Structure

```bash
/national-banking-system
|-- core
|-- auth
|-- bank
|-- web
|-- ear
```

* Promotes modularity and scalability
* Easier to test and debug
* Supports parallel development

---

## 📈 Non-Functional Requirements (NFRs)

* **Reliable**: Timers trigger consistently
* **Available**: Hosted on GlassFish, accessible anytime
* **Secure**: Role-based access + data validation

---

## ✅ Key Components

* `AccountSessionBean` & `TransactionSessionBean`: Core business logic
* `EJB Timer Services`: Background schedulers
* `web.xml`: URL-level role enforcement
* `Interceptor`: Secure and audit transactions

---

## 🧪 Example Code Snippets

### Schedule-based Interest Processor

```java
@Schedule(hour = "0", minute = "0", second = "0", persistent = false)
public void processDailyInterest() {
    // logic here
}
```

### Transactional Fund Transfer

```java
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public void transferFunds(...) {
    // Debit + Credit + Record
}
```

---

## 🙌 Acknowledgments

This project was completed as part of the **Business Component Development 2** curriculum. Grateful for the guidance and academic support throughout this assignment.

---

