# Jokes Application

This is a full-stack Jokes Application that allows users to input a number of jokes and fetch them from a backend API. The backend is built with Quarkus, and the frontend is developed using React.

## Table of Contents
- [Project Structure](#project-structure)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
  - [Backend Setup](#backend-setup)
  - [Frontend Setup](#frontend-setup)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
  - [Backend Testing](#backend-testing)
  - [Frontend Testing](#frontend-testing)
  - [E2E Testing](#e2e-testing)
- [OpenAPI Specification](#openapi-specification)
- [License](#license)

---

## Project Structure

jokes-app/ │ ├── backend/ # Backend Quarkus application │ ├── src/ # Backend source code │ ├── pom.xml # Maven configuration │
├── frontend/ # Frontend React application │ ├── src/ # Frontend source code │ ├── package.json # Node.js dependencies │ ├── cucumber/ # Feature files and step definitions for E2E testing │ 
├── README.md # Project README file 


---

## Features

- Fetch jokes from the backend by specifying the number of jokes.
- Display the jokes on the frontend.
- Input validation and error handling for joke requests.
- Backend error handling for invalid joke counts and external server errors.
- Fully tested with unit, integration, and end-to-end tests using Testcontainers and Cucumber.
- OpenAPI specification for backend services.

---

## Prerequisites

- [Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) (for backend)
- [Node.js](https://nodejs.org/en/) (for frontend)
- [Maven](https://maven.apache.org/) (for backend)
- [Docker](https://www.docker.com/get-started) (optional for containerized deployment)

---

## Installation

### Backend Setup
1. Navigate to the `backend` directory: cd backend
2.Install the required dependencies: mvn clean install
  
### Frontend Setup
1.Navigate to the frontend directory: cd frontend
2.Install the required dependencies: npm install

### Running the Application
Running the Backend
To start the Quarkus backend, run the following command: mvn quarkus:dev
By default, the backend runs on port 8080. The backend provides API endpoints to fetch jokes.

Running the Frontend
To start the React frontend, run the following command: npm start
By default, the frontend runs on port 3000. It sends requests to the backend running on http://localhost:8080.

API Endpoints
HTTP Method	   Endpoint	          Description
GET	          /jokes?count=10	    Fetches a list of jokes.

Example response:
[
  {
    "id": "1",
    "question": "What did the ocean say to the shore?",
    "answer": "Nothing, it just waved."
  }
]

### Testing
Backend Testing
Backend tests are written using JUnit and Testcontainers for integration testing with databases.
To run the backend tests: mvn test

Frontend Testing
Frontend tests are written using Jest and React Testing Library.
To run the frontend tests: npm test

E2E Testing
End-to-end (E2E) testing is performed using Cucumber for behavior-driven development (BDD). The test scenarios are written in Gherkin, with step definitions implemented using JavaScript.
To run the E2E tests, ensure both frontend and backend are running, and execute the Cucumber tests.

### OpenAPI Specification
The backend API follows the OpenAPI 3.0 specification. Below is an example of the API specification for the jokes endpoint:

openapi: 3.0.0
info:
  title: Jokes API
  description: Provide jokes on demand
  version: 0.0.1

paths:
  /jokes:
    get:
      summary: Returns a list of jokes
      parameters:
        - name: count
          in: query
          required: true
          schema:
            type: integer
            minimum: 1
            maximum: 100
      responses:
        '200':
          description: A JSON array of jokes
          content:
            application/json:
              schema:
                type: array
                items:
                  type: object
                  properties:
                    id:
                      type: string
                    question:
                      type: string
                    answer:
                      type: string
The OpenAPI specification is available at http://localhost:8080/q/openapi.

### License
This project is licensed under the MIT License. See the LICENSE file for more details.
This README covers both backend and frontend setup, running instructions, and testing, as well as the OpenAPI specification and API endpoints. Feel free to adjust any sections based on your project requirements.


