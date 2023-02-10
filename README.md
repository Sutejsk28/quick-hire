Quick Hire is a hiring portal where users can hire workers for certain tasks. It's built on the Spring boot framework and exposes REST API. It has task, worker, and user entities.

Quick Hire uses Spring data JPA for database connection. It also uses JWT-based authentication and Spring security for authentication.



# Quick-Hire
Quick-Hire is a backend application built on Spring Boot. It uses a MySQL database, with Spring Data JPA being used to connect to the database, and exposes a REST API for various services such as creating workers, users, and tasks. Additionally, it has a JWT-based authentication scheme and utilizes Spring Security for its implementation.

## Prerequisites
* Java 8+
* Maven
* MySQL server

## Installation
1. Clone the repository:
    ```sh
    git clone https://github.com/username/Quick-Hire.git
    ```
2. Create a database in MySQL and update the application.properties file with the correct database connection details.
3. Compile and build the project using Maven:
    ```sh
    mvn clean install
    ```
4. Run the application by running the main class or using the following command:
    ```sh
    java -jar Quick-Hire.jar
    ```

## Usage
Once the application is running, you can access the API using the following base URL: `http://localhost:8080/api/`. You can use this API to create workers, users, and tasks, as well as to authenticate users.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License
[MIT](https://choosealicense.com/licenses/mit/)
