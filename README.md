
# A Simple Swing Game

---

## Roadmap 
- [X] Create a database with scores - mario
- [X] Display the top scores in the game - mario

- [ ] Single player mode:
  - [x] Base game logic - victor
  - [ ] Improve gameplay - ivan
      - [x] Improve movement - victor
  - [ ] Add levels (arenas) - ?
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

## Movement ideas 

  * Maybe movement should be a little slippery (for added difficulty)
  * Movement should be based on acceleration and deceleration, instead of just applying a constant velocity
  * Snappier feeling
  * Friction 
  * Dash movement?

## Comunicacion client-server (Multiplayer)

- [ ] The client will send a playerpacket on join or an admin packet is is gameAdmin
  - [x] Server implementation
  - [ ] Cleint implementation
- [ ] The server will wait for the admin to send a start game packet
  - [ ] Server implementation
  - [ ] Admin implementation
- [ ] The server will send a start game packet to all clients or the game packet to all clients
  - [ ] Server implementation
  - [ ] Client implementation
- [ ] The server will start listening for player packets packets for every client
  - [ ] Server implementation
  - [ ] Client implementation
