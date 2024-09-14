# RestAssured Practice

## Overview

This project implements automated tests for a RESTful API using RestAssured, Cucumber, and Java. The tests are designed to be flexible and maintainable, allowing for easy adaptation to future changes in endpoints or test data. The source code is organized to follow best coding practices and includes proper documentation.

## Tools and Technologies

- **Java**: Programming language used for test implementation.
- **Maven**: Dependency management tool.
- **RestAssured**: Library for testing REST services.
- **POJO**: Plain Old Java Objects for data representation.
- **Cucumber**: Tool for Behavior-Driven Development (BDD) that allows writing test cases in a natural language format.

## Base URL

The tests will interact with the following base URL on mockApi.io:
```
https://63b6dfe11907f863aa04ff81.mockapi.io/api/v1/
```

## Test Cases

### Test Case 1: Change the phone number of the first Client named Laura

**Pre-Conditions**:
- Have at least 10 registered clients.

**Steps**:
1. Find the first client named Laura.
2. Save her current phone number.
3. Update her phone number.
4. Validate her new phone number is different.

**Post-Conditions**:
- Delete all the registered clients.

**Possible Verifications**:
- Verify that the response is equal to an HTTP status code of 200.
- Verify the structure of the response body schema.

---

### Test Case 2: Get the list of active resources

**Pre-Conditions**:
- Have at least 5 active resources.

**Steps**:
1. Find all active resources.

**Post-Conditions**:
- Update them as inactive.

**Possible Verifications**:
- Verify that the response is equal to an HTTP status code of 200.
- Verify the structure of the response body schema.

---

### Test Case 3: Update and delete a New Client

**Pre-Conditions**:
- N/A

**Steps**:
1. Create a new client.
2. Find the new client.
3. Update any parameter of the new client.
4. Delete the new client.

**Post-Conditions**:
- N/A

**Possible Verifications**:
- Verify that the response is equal to an HTTP status code of 200.
- Verify the structure of the response body schema.
- Verify the response body data after the update.

---

### Test Case 4: Update the last created resource

**Pre-Conditions**:
- Have at least 15 resources.

**Steps**:
1. Find the latest resource.
2. Update all the parameters of this resource.

**Post-Conditions**:
- N/A

**Possible Verifications**:
- Verify that the response is equal to an HTTP status code of 200.
- Verify the structure of the response body schema.
- Verify the response body data after the update.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Maven installed on your machine
- An IDE (e.g., IntelliJ IDEA, Eclipse) for Java development

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/JuanChav/AutomationAPI
   ```

2. Install the project dependencies:
   ```bash
   mvn install
   ```
This project is licensed under the MIT License. See the LICENSE file for details.
