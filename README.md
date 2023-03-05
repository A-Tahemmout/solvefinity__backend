# Solvefinity

Solvefinity is a Spring REST API that utilizes a neural network to predict the solvency of potential loaners. The app allows for an app admin to manage the addition of banks and their users (bank admin and bank agent), while bank admins can manage bank agents. Bank agents can then manage loaners and their loans, and generate a solvency prediction.

## Technologies Used
* Java 18
* Spring Boot 3
* Spring Data Security
* Spring Data JPA
* MySQL
* Postman
* Docker

## Getting Started

### Prerequisites
* Java 11
* Maven
* Docker

### Installing
1. Clone the repository:
```
git clone https://github.com/[username]/[repository-name].git
```
2. Build the Spring app:
```
cd [repository-name]/solvefinity
./mvnw clean install
```
3. Build the Docker image for the Spring app:
```
docker build -t solvefinity:latest .
```
4. Start the Docker containers for MySQL, PhpMyAdmin, and the Spring app:
```
docker-compose up
```
5. Access the API at http://localhost:8084/api.

## API Endpoints
### Bank Endpoints
* `GET /api/bank/all`: Get all banks
* `GET /api/bank/all/name`: Get all bank names
* `GET /api/bank/search`: Search banks by name or slug
* `GET /api/bank/{id}`: Get a bank by ID
* `GET /api/bank/slug/{slug}`: Get a bank by slug
* `POST /api/bank/create`: Create a new bank
* `POST /api/bank/update`: Update an existing bank
* `DELETE /api/bank/delete/{id}`: Delete a bank by ID 
* `GET /api/bank/pagination/all`: Get all banks with pagination
* `GET /api/bank/pagination/search`: Search banks by name or slug with pagination
* `GET /api/bank/images/{filename}`: Get a bank's logo

### User Endpoints
* `GET /api/user/all`: Get all users
* `GET /api/user/search`: Search users by firstname or lastname
* `GET /api/user/{id}`: Get a user by ID
* `POST /api/user/create`: Create a new user
* `POST /api/user/update`: Update an existing user
* `DELETE /api/user/delete/{id}`: Delete a user by ID 
* `GET /api/user/pagination/all`: Get all users with pagination
* `GET /api/user/pagination/search`: Search users by firstname or lastname with pagination
* `GET /api/user/images/{filename}`: Get a user's avatar

### Loaner Endpoints
* `GET /api/loaner/all`: Get all loaners
* `GET /api/loaner/{id}`: Get a loaner by ID
* `GET /api/loaner/cin/{cin}`: Get a loaner by CIN
* `POST /api/loaner/create`: Create a new loaner
* `POST /api/loaner/update`: Update an existing loaner
* `DELETE /api/loaner/delete/{id}`: Delete a loaner by ID 
* `POST /api/loaner/upload-avatar`: Upload a loaner's avatar
* `GET /api/loaner/images/{filename}`: Get a loaner's avatar

### Loan Endpoints
* `GET /api/bank/all`: Get all loans
* `GET /api/bank/{id}`: Get a loan by ID
* `POST /api/bank/create`: Create a new loan
* `POST /api/bank/update`: Update an existing loan
* `DELETE /api/bank/delete/{id}`: Delete a loan by ID 
* `GET /api/bank/predict`: Predict a loan solvency

## Authors
Tahemmout Aymane

## License
This project is licensed under the MIT License - see the LICENSE file for details.
