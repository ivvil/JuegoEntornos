
# A Simple Swing Game

---

## Roadmap 
- [X] Create a database with scores - mario
- [X] Display the top scores in the game - mario
- [X] Send the score to the server - mario
- [X] Use a cloud server to store the scores - mario

- [ ] Single player mode:
  - [x] Base game logic - victor
  - [x] Refactor the way enemies work - victor
  - [x] Use seed for enemy and coin generation - victor
  - [ ] Add levels (arenas) - TODO: pablo

- [ ] Multiplayer mode:
  - [ ] Client:
    - [x] Define a packet structure - victor
    - [x] Refactor the way enemies work - victor
    - [x] Listen for server sent packets - victor
    - [ ] Handle server sent packets - ?
    - [ ] Interprete server sent packets - ?
    - [ ] Send inputs to server - ?
    - [ ] Menu for connecting to server - ?

  - [ ] Server:
    - [x] Define a packet structure - victor
    - [x] Listen for packets and send them to all clients - victor 
    - [x] Send level data / enemy data to client on join - victor
    - [x] Send initial data to player - victor
    - [x] Workout how to start the game - ivan, victor
    - [x] Implement admin app api - iván, victor
    - [ ] Checks fow when the match is over - ?
	
  - [ ] Admin app:
    - [x] Discover and connect to server - iván, victor
    - [x] Start game - victor
    - [ ] Turn server off - iván 
    - [ ] Restart game
    

---
### Communication client-server (Multiplayer)

- [x] The client will send an integer on join or an admin packet is gameAdmin
  - [x] Server implementation - victor
  - [x] Client implementation - victor
- [x] The server will send back a PlayerPacket with the color received as an integer in previos request
  - [x] Server implementation - victor
  - [x] Client implementation - victor
- [x] The server will wait for the admin to send a start game packet
  - [x] Server implementation - victor
  - [x] Admin implementation - victor
- [x] The server will send a start game packet to all clients
  - [x] Server implementation - victor
  - [x] Client implementation - victor
- [ ] The server will start listening for player packets for every client
  - [x] Server implementation - victor
  - [ ] Client implementation
- [ ] The server will start listening for enemy packets when a player hits and enemy sending the new enemy location
  - [x] Server implementation - victor
  - [ ] Client implementation

---

### Outline of an abstract connection between client and server

The server can only have a limited amount of threads so the max number of players that can be connected has to be
the number of threads used on the server
