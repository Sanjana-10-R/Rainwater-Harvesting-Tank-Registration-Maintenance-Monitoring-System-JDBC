Community Rainwater Harvesting Tank Registration & Maintenance Monitoring System (JDBC)


The Community Rainwater Harvesting Tank Registration & Maintenance Monitoring System is a console-based Java application developed using JDBC and Oracle Database. This system is designed to help municipalities, gated communities, and residential welfare associations efficiently manage rainwater harvesting (RWH) tanks. It enables users to register tanks, schedule maintenance visits, record completion reports, and monitor maintenance history. The application ensures structured data management and promotes proper upkeep of rainwater harvesting infrastructure.

Database Structure

The system is built on a simple yet realistic database schema consisting of three core tables: TANK_TBL, MAINT_VISIT_TBL, and MAINT_REPORT_TBL. The TANK_TBL stores master data for each registered tank. The MAINT_VISIT_TBL maintains records of scheduled maintenance visits, while the MAINT_REPORT_TBL captures detailed completion reports and technician observations for each visit. This structured design ensures data integrity and clear relationship management between tanks, visits, and reports.

Core Functionalities

The application provides six major operations. Users can register new tanks with validation for capacity, installation date, and maintenance frequency, with the tank status initially set to ACTIVE. The system allows viewing individual tank details or listing all tanks, optionally filtered by status. Maintenance visits can be scheduled only for ACTIVE tanks while preventing duplicate scheduling for the same date and time slot. Completion reports can be recorded transactionally, ensuring both report insertion and visit status update occur within a single commit.

Architecture

The application follows a layered architecture consisting of Bean classes for data modeling, DAO classes for database operations, a Service layer for business logic and validation, a Utility layer for database connection handling, and a Main class for console interaction. This separation of concerns improves maintainability, readability, and scalability of the application.

Technologies Used

The project is developed using Core Java, JDBC, and Oracle 21c XE Database. Eclipse IDE is used for development, and GitHub is used for version control and project hosting. Transaction management, exception handling, and SQL operations such as INSERT, UPDATE, and SELECT are implemented to ensure reliable system behavior.

<img width="521" height="316" alt="image" src="https://github.com/user-attachments/assets/c1a755e1-033f-444d-addf-faddf64a78c1" />
