# ChordKnight API Documentation

## Base URL

```
http://localhost:8080
```

## Endpoints

### Health Check

#### GET /health

Returns the health status of the API.

**Response**

```json
{
  "status": "healthy",
  "version": "1.0.0",
  "timestamp": 1234567890123
}
```

**Status Codes**
- `200 OK` - API is healthy

---

### Chess Game Management

#### POST /chess/game

Create a new chess game.

**Request Body**

```json
{
  "gameId": "unique-game-id"
}
```

**Response**

```json
{
  "gameId": "unique-game-id",
  "currentTurn": "WHITE",
  "fen": "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
}
```

**Status Codes**
- `201 Created` - Game created successfully
- `400 Bad Request` - Invalid request body

**Example**

```bash
curl -X POST http://localhost:8080/chess/game \
  -H "Content-Type: application/json" \
  -d '{"gameId": "game-123"}'
```

---

#### GET /chess/game/{gameId}

Get the current state of a chess game.

**Path Parameters**
- `gameId` (string, required) - The unique identifier of the game

**Response**

```json
{
  "gameId": "game-123",
  "currentTurn": "WHITE",
  "fen": "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
}
```

**Status Codes**
- `200 OK` - Game state retrieved successfully
- `400 Bad Request` - Missing gameId
- `404 Not Found` - Game not found

**Example**

```bash
curl http://localhost:8080/chess/game/game-123
```

---

#### POST /chess/game/{gameId}/move

Make a move in a chess game.

**Path Parameters**
- `gameId` (string, required) - The unique identifier of the game

**Request Body**

```json
{
  "from": "e2",
  "to": "e4"
}
```

**Response**

```json
{
  "gameId": "game-123",
  "currentTurn": "BLACK",
  "fen": "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1"
}
```

**Status Codes**
- `200 OK` - Move made successfully
- `400 Bad Request` - Invalid move or missing parameters
- `404 Not Found` - Game not found

**Example**

```bash
curl -X POST http://localhost:8080/chess/game/game-123/move \
  -H "Content-Type: application/json" \
  -d '{"from": "e2", "to": "e4"}'
```

---

#### DELETE /chess/game/{gameId}

Delete a chess game.

**Path Parameters**
- `gameId` (string, required) - The unique identifier of the game

**Response**

```json
{
  "message": "Game deleted"
}
```

**Status Codes**
- `200 OK` - Game deleted successfully
- `400 Bad Request` - Missing gameId
- `404 Not Found` - Game not found

**Example**

```bash
curl -X DELETE http://localhost:8080/chess/game/game-123
```

---

## Error Responses

All endpoints may return error responses in the following format:

```json
{
  "error": "Error message describing what went wrong"
}
```

Common error status codes:
- `400 Bad Request` - Invalid request parameters or body
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error

## CORS

The API supports Cross-Origin Resource Sharing (CORS) and accepts requests from any origin by default. This can be configured via the `CORS_ALLOWED_HOSTS` environment variable.

## Rate Limiting

Currently, no rate limiting is implemented. This may be added in future versions.

## Authentication

Currently, no authentication is required. This may be added in future versions for production deployments.

## Examples

### Complete Chess Game Flow

```bash
# 1. Create a new game
curl -X POST http://localhost:8080/chess/game \
  -H "Content-Type: application/json" \
  -d '{"gameId": "my-game"}'

# 2. Make white's first move (e2 to e4)
curl -X POST http://localhost:8080/chess/game/my-game/move \
  -H "Content-Type: application/json" \
  -d '{"from": "e2", "to": "e4"}'

# 3. Make black's first move (e7 to e5)
curl -X POST http://localhost:8080/chess/game/my-game/move \
  -H "Content-Type: application/json" \
  -d '{"from": "e7", "to": "e5"}'

# 4. Get current game state
curl http://localhost:8080/chess/game/my-game

# 5. Delete the game when done
curl -X DELETE http://localhost:8080/chess/game/my-game
```

### Using with JavaScript/TypeScript

```typescript
// Create a game
const createGame = async (gameId: string) => {
  const response = await fetch('http://localhost:8080/chess/game', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ gameId }),
  });
  return await response.json();
};

// Make a move
const makeMove = async (gameId: string, from: string, to: string) => {
  const response = await fetch(`http://localhost:8080/chess/game/${gameId}/move`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ from, to }),
  });
  return await response.json();
};

// Get game state
const getGame = async (gameId: string) => {
  const response = await fetch(`http://localhost:8080/chess/game/${gameId}`);
  return await response.json();
};

// Example usage
const game = await createGame('my-game');
await makeMove('my-game', 'e2', 'e4');
const state = await getGame('my-game');
console.log(state);
```

## Future Endpoints

The following endpoints are planned for future releases:

- `GET /music/queue/{guildId}` - Get music queue for a guild
- `POST /music/play/{guildId}` - Add track to queue
- `GET /guilds/{guildId}/state` - Get guild state
- `POST /features/{featureName}/enable` - Enable a feature
- `POST /features/{featureName}/disable` - Disable a feature

## Changelog

### Version 1.0.0 (Current)
- Initial release
- Health check endpoint
- Chess game management endpoints
- Basic CORS support
