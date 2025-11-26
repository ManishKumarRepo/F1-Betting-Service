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


## Usage Flow
### 1. Register & Retrieve User Details
##### Description:
> - Users can register and automatically receive an initial balance of 100 EUR.
> - User details can be retrieved any time using the user ID.

#### Endpoints:

**Request:** `POST /api/users/register`
  
   > Registers a new user with default starting balance and no bets. 
Request
```json 
  {
     "name": "Alex"
  }
```

**Response:** `HTTP 201 Created`

```json
{
  "id": 1,
  "name": "Alex",
  "balance": 100,
  "bets": []
}
```


**Request:** `GET /api/users/{userId}` 
   > Fetch user details including all bets they placed. Initially with default starting balance and no bets.

`GET /api/users/1`

`Response (200 OK)`
```json
{
  "id": 1,
  "name": "Alex",
  "balance": 100,
  "bets": []
}

```

### 2. Fetch Events
##### Description:
> Returns a list of F1 events. Filters are optional and can be combined.

#### Endpoint:
**REQUEST:** `GET /api/events`

##### Available Filters:

- session_type

- country

- year

**Query** - `/api/events?sessionType=RACE&year=2024&country=Italy`

**RESPONSE:** `(200 OK)`
```json
[
  {
    "session_key": 101,
    "session_name": "Italian Grand Prix - Race",
    "country_name": "Italy",
    "year": 2024
  }
]

```
### 3. Fetch Drivers for an Event
##### Description:
> Returns all drivers participating in a selected event along with randomly generated odds (2, 3, or 4) used for betting.

#### Endpoint:
**REQUEST:** `GET /api/events/{eventId}/drivers`

**Query** - `/api/events/101/drivers`

**RESPONSE:** `(200 OK)`
```json 
[
  {
    "event": {
      "session_key": 101,
      "session_name": "Italian Grand Prix - Race",
      "country_name": "Italy",
      "year": 2024
    },
    "drivers": [
      {
        "driverId": 44,
        "fullName": "Lewis Hamilton",
        "odds": 2
      },
      {
        "driverId": 1,
        "fullName": "Max Verstappen",
        "odds": 4
      }
    ]
  }
]
```

### 4. Place a Bet
##### Description:
> Creates a bet for the specified user as a query param.
The bet is saved with a PENDING status until the event outcome is processed.

#### Endpoint:

**REQUEST:** `POST /api/bets/{userId}`

##### Request Includes:

- Event ID
- Driver ID
- Odds
- Bet amount

**REQUEST BODY** 
```json 
{
  "eventId": 101,
  "driverId": 44,
  "odds": 2,
  "amount": 25
}

```

**RESPONSE::**
```json
{
  "userId": 1,
  "eventId": 101,
  "driverId": 44,
  "odds": 2,
  "amount": 25,
  "status": "PENDING",
  "payout": 0
}
```

### 5. Process Event Outcome
##### Description:
> Determines the event winner (driver on position 1) and returns:
> - The event result
> - A list of users whose bet won

#### Endpoint:
**REQUEST:** `POST /api/outcomes`

**REQUEST BODY**
```json 
{
  "eventId": 101
}
```

**RESPONSE::**
```json 
{
  "eventResult": {
    "position": 1,
    "driver_number": 44,
    "session_key": 101,
    "createdAt": "2025-01-15T10:15:30Z"
  },
  "betsWon": [
    {
      "userId": 1,
      "eventId": 101,
      "driverId": 44,
      "odds": 2,
      "amount": 25,
      "status": "WON",
      "payout": 50
    }
  ]
}
```

### 6. Update Bets & User Balances

Triggered during Step 5

#### Rules:

- WON: Payout is calculated using odds and added to the user’s balance.

- LOST: No payout.

- Bet records are updated with final status and winnings.

### 7. Check User Bets

#### Endpoint:
**REQUEST:** `GET /api/users/{userId}`

##### Description:
Users can view:

- All bets placed

- Current status (PENDING / WON / LOST)

- Payouts and winnings

**Request:** `GET /api/users/{userId}`
> Fetch user details including all bets they placed. Initially with default starting balance and no bets.

**Query**- `GET /api/users/1`

`Response (200 OK)`
```json
{
  "id": 1,
  "name": "Alex",
  "balance": 125,
  "bets": [
    {
      "userId": 1,
      "eventId": 101,
      "driverId": 44,
      "odds": 2,
      "amount": 50,
      "status": "WON",
      "payout": 100
    }
  ]
}
```



## Tech Stack

| Component             | Technology                              |
|-----------------------| --------------------------------------- |
| **Language**          | Java 21                                 |
| **Framework**         | Spring Boot 3.x                         |
| **Build Tool**        | Maven                                   |
| **Database**          | H2 (in-memory)                          |
| **API Documentation** | Swagger / OpenAPI 3                     |
| **Testing**           | JUnit 5 + Mockito                       |
| **Containerization**  | Docker                                  |

▶️ Run with Maven
> mvn spring-boot:run

App will start at:
> http://localhost:8080


## 🐳 Run the Application Using Docker (No Build Required)
You can run the application locally without installing Java or Maven.
Just pull the pre-built Docker image from Docker Hub and run it.

1️⃣ Pull the Docker Image
> docker pull manishyogesh11/f1-betting-app-image:latest

2️⃣ Run the Application - Expose the app on port 8080
> docker run -p 8080:8080 manishyogesh11/f1-betting-app-image:latest

3️⃣ Verify Swagger
> Visit 👉 http://localhost:8080/swagger-ui/index.html
