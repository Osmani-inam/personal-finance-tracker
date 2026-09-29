Personal Finance Tracker

A full-stack personal finance tracker:
- **Backend:** Java + Spring Boot (REST API) + MySQL
- **Frontend:** HTML/CSS/JavaScript with Chart.js for expense visualization

---

## 1. Install prerequisites (Windows)

### a) Java 17 (JDK)
1. Download the JDK 17 installer from https://adoptium.net (choose "Temurin 17", Windows x64 .msi).
2. Run the installer, keep defaults, make sure "Set JAVA_HOME" is checked.
3. Verify in Command Prompt:
   ```
   java -version
   ```
   You should see `17.x.x`.

### b) Maven
1. Download the binary zip from https://maven.apache.org/download.cgi.
2. Extract it to e.g. `C:\Program Files\Apache\maven`.
3. Add `C:\Program Files\Apache\maven\bin` to your system `PATH` (Search "Edit environment variables" → Environment Variables → Path → New).
4. Verify:
   ```
   mvn -version
   ```

### c) MySQL
1. Download "MySQL Installer for Windows" from https://dev.mysql.com/downloads/installer/.
2. Run it, choose "Developer Default", install MySQL Server + MySQL Workbench.
3. During setup, set a root password — **remember this**, you'll need it below.
4. After install, open **MySQL Workbench**, connect using root + your password to confirm it works.

---

## 2. Configure the database connection

Open `backend/src/main/resources/application.properties` and replace `YOUR_PASSWORD` with your actual MySQL root password:

```
spring.datasource.password=YOUR_PASSWORD
```

You don't need to manually create the database — `createDatabaseIfNotExist=true` in the connection URL creates `finance_tracker` automatically, and `spring.jpa.hibernate.ddl-auto=update` creates the `transactions` table automatically on first run.

---

## 3. Run the backend

Open Command Prompt in the `backend` folder:

```
cd backend
mvn spring-boot:run
```

First run will take a few minutes (Maven downloads dependencies — internet required). Once you see:

```
Tomcat started on port(s): 8080
```

the API is live at `http://localhost:8080/api/transactions`.

**Quick test:** open `http://localhost:8080/api/transactions` in your browser — you should see `[]` (empty list, no errors).

---

## 4. Run the frontend

Just open `frontend/index.html` directly in your browser (double-click it). It talks to the backend at `http://localhost:8080`, so **keep the backend running** in the background.

Add a transaction using the form — it should appear in the table, and the summary cards + pie chart should update.

---

## 5. API reference (for your own understanding / interview prep)

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/transactions` | List all transactions |
| GET | `/api/transactions/{id}` | Get one transaction |
| POST | `/api/transactions` | Create a transaction |
| PUT | `/api/transactions/{id}` | Update a transaction |
| DELETE | `/api/transactions/{id}` | Delete a transaction |
| GET | `/api/transactions/summary` | Total income, expense, balance, and expense-by-category breakdown |

Example POST body:
```json
{
  "title": "Groceries",
  "amount": 1200.50,
  "type": "EXPENSE",
  "category": "Food",
  "date": "2026-09-01",
  "notes": "Weekly shopping"
}
```

---

## 6. Project structure

```
finance-tracker/
├── backend/
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/inamul/financetracker/
│       │   ├── FinanceTrackerApplication.java
│       │   ├── model/Transaction.java
│       │   ├── model/TransactionType.java
│       │   ├── repository/TransactionRepository.java
│       │   ├── service/TransactionService.java
│       │   ├── controller/TransactionController.java
│       │   └── dto/SummaryResponse.java
│       └── resources/application.properties
└── frontend/
    └── index.html
```

## 7. Troubleshooting

- **"Access denied for user 'root'"** → wrong password in `application.properties`.
- **"Communications link failure"** → MySQL service isn't running. Open Windows Services (`services.msc`) and check "MySQL80" (or similar) is started.
- **CORS error in browser console** → make sure you're loading `frontend/index.html` from disk and the backend is running on port 8080; `@CrossOrigin(origins = "*")` on the controller already allows this.
