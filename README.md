# Demo Project

## Access
- **Swagger UI:** [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)

## Features
- **CRUD Operations**  

- **Customer Name Generator**  
  Generates random large sets of customer names and automatically decides the optimal execution strategy based on dataset size.

- **Unit Tests**  
  Implemented for `NameRegistryService` 

- **Integration Tests**  
  Implemented for `CustomerServiceIntegration` 

- **Unique Request Reference Generator**  
  Each request is assigned a unique random reference ID.  
  - Used for tracing request actions in logs.  
  - Helps in debugging and monitoring.

- **AOP-Based Logging**  
   used to log incoming requests and controller actions automatically.
