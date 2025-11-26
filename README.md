# F1 BETTING SERVICE

A Spring Boot 3.5.9 / Java 21 backend service to place bets on Formula 1 events.
The service integrates with open-source F1 APIs, supports user management, betting, outcome processing, and provides a Swagger UI for API documentation.

## Project Overview
This backend service allows users to:

- Register user (here balance will be default to 100 at registration)

- Retrieve user details and their balance & Bet placed by User.

- View F1 events filtered by session type, country, and year.

- View drivers participating in an event.

- Place bets on a driver for a specific event.

- Process event outcomes, updating bets to WON / LOST, and adjust user balance accordingly.