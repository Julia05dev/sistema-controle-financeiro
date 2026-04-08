# Personal Finance System (Java)

This is a personal project I'm building to practice and consolidate my knowledge of Java and object-oriented programming.

The idea is to create a simple system to manage income and expenses, with room to evolve into a more complete financial control tool over time.

## Current features

- Register transactions (income and expenses)
- List all transactions
- Remove a transaction by ID
- Calculate the current balance
- Filter transactions by exact date
- Filter transactions by month and year
- Handle invalid numeric input more safely

## Technologies used

- Java
- Object-Oriented Programming (OOP)
- Java Collections (ArrayList)
- Java Date API (LocalDate, DateTimeFormatter)

## Project structure

The project is currently divided into these main parts:

- `Lancamento`: represents a financial transaction
- `Carteira`: stores and manages transactions
- `Main`: handles user interaction through a command-line interface

## Why I built this

I'm currently studying Computer Science and learning Java, so I wanted to build something practical instead of doing only isolated exercises.

This project is still evolving, and I'm using it to practice object-oriented design, input validation, collections, and date handling in Java.

## Next steps

- Improve business rules and input validation
- Refactor the main flow into smaller and more organized methods
- Enhance filtering and reporting features
- Improve formatting and user experience in the terminal
- Prepare the system for future scalability
