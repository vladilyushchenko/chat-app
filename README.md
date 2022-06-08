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

### Run instruction
---
1. Run redis on your machine (or any other machine with web-access)
2. Run spring-boot application, add redis properties to environment (look for application.yaml)
3. Open index.html file in fron-app