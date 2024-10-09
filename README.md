


### Prerequisites
* Java 17 or higher
* Maven
* PostgreSQL (if using PostgreSQL instead of H2)
* Kafka (for email verification messaging)
* An IDE like IntelliJ IDEA or Eclipse

### Setup
1. **Clone the repository:**
    ```bash
    git clone https://github.com/your-repo/email-auth-service.git
    cd email-auth-service
    ```

2. **Configure Database:**
    Update `src/main/resources/application.properties` with your database credentials.
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.jpa.hibernate.ddl-auto=update
    ```
    If using H2, no additional configuration is needed for the database.

3. **Build the Project:**
    Use Maven to build the project:
    ```bash
    mvn clean install
    ```

4. **Run the Application:**
    You can run the application using your IDE or by executing the following command in the terminal:
    ```bash
    mvn spring-boot:run
    ```

### Accessing the Application
* **Login Page:** Navigate to `http://localhost:8080/login`
* **User Page:** After logging in, you will be redirected to `http://localhost:8080/user`

## API Endpoints

### Authentication Endpoints
* **POST /api/auth/register**
    Registers a new user and sends a verification email.
    
    **Request Body:**
    ```json
    {
        "email": "user@example.com",
        "password": "your_password"
    }
    ```

* **POST /api/auth/login**
    Authenticates a user and generates a JWT token.
    
    **Request Body:**
    ```json
    {
        "email": "user@example.com",
        "password": "your_password"
    }
    ```

* **GET /api/auth/verify**
    Verifies the user's email using the provided token.
    
    **Query Parameter:**
    * `token`: Verification token

### Frontend Pages
* **Login Page:** Rendered from `login.html`
* **User Page:** Rendered from `user.html`, displays user email after authentication.

## Testing
You can use Postman or any other API testing tool to test the endpoints.

1. **Register a User:** Send a POST request to `http://localhost:8080/api/auth/register` with the required JSON body.
2. **Verify Email:** Use the token received in the email to verify the user's email by sending a GET request to `http://localhost:8080/api/auth/verify?token=your_token`.
3. **Login:** Send a POST request to `http://localhost:8080/api/auth/login` with the user's credentials to receive a JWT token.

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Contributing
If you would like to contribute to this project, please fork the repository and create a pull request. All contributions are welcome!
