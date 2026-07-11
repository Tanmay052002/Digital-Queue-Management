# Digital Queue Management System

A college mini-project: Login -> pick Hospital or RTO -> pick a service ->
generate a queue token OR check slot availability.

Two parts:

1. **spring-boot-app/** - the main website (Java + Spring Boot + Thymeleaf + H2 database)
2. **dotnet-notification-service/** - a small separate ASP.NET Core microservice
   that "sends" the notification when a token is generated (Spring Boot calls
   this over HTTP).

The notification microservice is optional to run - if it's not running,
token generation still works, it will just show "notification service offline"
on the result page instead of "sent".

## How the flow works

```
Login page
   -> Dashboard (2 boxes: Hospital, RTO)
       -> Services page (list of services for that office)
           -> Generate Token page  ---> Token result page
           -> Slot Availability page
```

## 1. Running the Spring Boot app

Requirements: Java 17+ and Maven (or just use the included `mvnw` wrapper if you add one,
here we assume you have Maven installed).

```bash
cd spring-boot-app
mvn spring-boot:run
```

Then open: http://localhost:8080/login

Demo login:
- username: `student`
- password: `student123`

The database is H2 in-memory, so all data (offices, services, counters, tokens)
resets every time you restart the app. Seed data comes from
`src/main/resources/data.sql`.

You can browse the database directly at http://localhost:8080/h2-console
(JDBC URL: `jdbc:h2:mem:queuedb`, username `sa`, no password).

## 2. Running the .NET notification microservice (optional)

Requirements: .NET 8 SDK.

```bash
cd dotnet-notification-service
dotnet run
```

This starts on http://localhost:5000. You can see everything it has "sent" so
far by opening http://localhost:5000/api/notifications in a browser.

The Spring Boot app is already configured (see `application.properties`,
property `notification.service.url`) to call this service at that address.

## Project structure

```
queue-management-system/
├── spring-boot-app/
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/queuemgmt/
│       │   ├── QueueManagementApplication.java
│       │   ├── model/          (Office, Counter, ServiceEntity, User, Token, QueueEvent, Notification)
│       │   ├── repository/     (Spring Data JPA repositories)
│       │   ├── service/        (TokenService, NotificationClientService)
│       │   ├── controller/     (Login, Dashboard, Service, Token, Slot)
│       │   └── config/         (RestTemplateConfig)
│       └── resources/
│           ├── application.properties
│           ├── data.sql
│           ├── templates/      (login, dashboard, services, generate-token, token-result, slot-availability)
│           └── static/css/style.css
├── dotnet-notification-service/
│   ├── NotificationService.csproj
│   ├── Program.cs
│   └── appsettings.json
└── README.md
```

## Notes / known limitations (typical for a student project)

- Login is a plain username/password check against the database - no Spring
  Security, no password hashing. Do not use this pattern for anything real.
- Token numbers are generated from an in-memory counter, so they reset when
  the app restarts (they are not persisted separately from the DB restart).
- Slot availability is a simplified calculation: `max_slots - tokens currently WAITING`
  on that counter. There's no real time-slot/appointment scheduling.
- If the .NET microservice is down, notification sending just fails silently
  and is logged to the console - it does not block token generation.
- Based on the provided Architecture Diagram and ERD (Office / Counter / Service /
  User / Token / QueueEvent / Notification).
