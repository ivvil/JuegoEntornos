
# A Simple Swing Game

---

## Roadmap 
- [X] Create a database with scores - mario
- [X] Display the top scores in the game - mario
- [X] Send the score to the server - mario
- [X] Use a cloud server to store the scores - mario

- [ ] Single player mode:
  - [x] Base game logic - victor
  - [ ] Improve gameplay - ivan
      - [x] Improve movement - victor
  - [ ] Add levels (arenas) - TODO: pablo
  - [ ] Refactor the way enemies work - ?

- [ ] Multiplayer mode:
  - [ ] Client:
    - [x] Define a packet structure - victor
    - [ ] Refactor the way enemies work - ?
    - [x] Listen for server sent packets - victor
    - [ ] Handle server sent packets - ?
    - [ ] Interprete server sent packets - ?
    - [ ] Send inputs to server - ?
    - [ ] Menu for connecting to server - ?

  - [ ] Server:
    - [x] Define a packet structure - victor
    - [x] Listen for packets and send them to all clients - victor
    - [ ] Workout how to shut down the server - ?
    - [ ] Checks fow when the match is over - ?
    - [x] Send level data / enemy data to client on join - victor
    - [x] Send initial data to player - victor
    - [ ] Workout how to start the game - ?
    - [ ] Implement admin app api - iván
	
  - [ ] Admin app:
    - [ ] Discover and connect to server - iván
    - [ ] Turn server off - iván
    - [ ] Start game
    - [ ] Restart game
    

---
### Comunicacion client-server (Multiplayer)

- [x] The client will send an integer on join or an admin packet is is gameAdmin
  - [x] Server implementation - victor
  - [x] Cleint implementation - victor
- [x] The server will send back a PlayerPacket with the color recived as an integer in previos request
  - [x] Server implementation - victor
  - [x] Client implementation - victor
- [ ] The server will wait for the admin to send a start game packet
  - [ ] Server implementation
  - [ ] Admin implementation
- [x] The server will send a start game packet to all clients
  - [x] Server implementation - victor
  - [x] Client implementation - victor
- [ ] The server will start listening for player packets packets for every client
  - [ ] Server implementation - ?
  - [ ] Client implementation
- [ ] The server will start listening for enemy packets when a player hits and enemy sening the new enemy location
  - [ ] Server implementation - ?
  - [ ] Client implementation

---

### Outline of an abstract connection between client an the server

The server can only have a limited amount of threads so the max number of players that can be connected has to be
the number of threads used on the server