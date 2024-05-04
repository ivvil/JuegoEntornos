
# A Simple Swing Game

---

## Roadmap 
- [ ] Create a database with scores - mario
- [ ] Display the top scores in the game - mario

- [ ] Single player mode:
  - [x] Base game logic - victor
  - [ ] Improve gameplay - iván
      - [ ] Improve movement
  - [ ] Add levels (arenas) - ?
  - [ ] Refactor the way enemies work - ?

- [ ] Multiplayer mode:
  - [ ] Client:
    - [x] Define a packet structure - victor
    - [ ] Refactor the way enemies work - ?
    - [ ] Listen for server sent packets - ?
    - [ ] Interprete server sent packets - ?
    - [ ] Send inputs to server - ?
    - [ ] Menu for connecting to server - ?

  - [ ] Server:
    - [ ] Define a packet structure - victor
    - [ ] Listen for packets and send them to all clients - ?
    - [ ] Workout how to shut down the server - ?
    - [ ] Checks fow when the match is over - ?

## Movement ideas 

  * Maybe movement should be a little slippery (for added difficulty)
  * Movement should be based on acceleration and deceleration, instead of just applying a constant velocity
  * Snappier feeling
  * Friction 
  * Dash movement?
