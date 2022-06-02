# Chat application
---
Chat application with JWT authorization.

### Functional description
---
- First, you should log in by specifying your nickname which must be unique.
- After that you will get access and refresh tokens for using application
- Logged users are storing in Redis.
- After logging out users will be removed from redis storage
- After logging in you could connect to WebSocket via Stomp-protocol. Also you must add Authorization header with 
  access-token to websocket headers
- In WebSocket you could send json-objects in ChatMessageDto format.

### Endpoint description
---
- <b>POST /api/v1/login --- login endpoit 
- <b>POST /api/v1/token/refresh --- refresh token
- <b>ws://host-name/chat --- websocket endpoint