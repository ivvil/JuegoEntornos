
# A Simple Swing Game

---

## Roadmap 
- [ ] Create a database with scores - mario
- [ ] Display the top scores in the game - mario

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

## Movement ideas 

  * Maybe movement should be a little slippery (for added difficulty)
  * Movement should be based on acceleration and deceleration, instead of just applying a constant velocity
  * Snappier feeling
  * Friction 
  * Dash movement?
