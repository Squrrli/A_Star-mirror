# A*
An implementation of A* for the 8-square game for Intelligent Systems.

## Authors
* Austin Bohannon [18286119]
* Niall Dillane [13132911]
* Adam O'Mahony []

## Build
All code should be in the single file `A_Star.java`. To compile, run:
```bash
javac A_Star.java
```

## Run
To run, run:
```bash
java A_Star
```

## Status
- [ ] Complete first sprint *(Due: 17:00 29 March 2019)*
  - [ ] Parse input
    - [ ] Get first state from a JOptionPane
    - [ ] Validate all numbers in [0, 8] are represented without duplicates and are space separated
    - [ ] Get the final state from a JOptionPane and validate as well
  - [x] Implement game state data structure
  - [x] Calculate possible moves
    * Always either 2, 3, or 4 possible moves
  - [ ] Calculate the *h* function for each move
  - [x] Print board state and *h* values to screen
    * Board state should be 3 x 3, not like text input
  - [ ] Ensure our names/IDs are in the files header
  - [ ] Actually submit
    - [ ] Ensure code compiles
    - [ ] Rename file
    - [ ] Email code
- [ ] Complete second sprint *(Due: 17:00 19 April 2019)*
  - [ ] Implement A*
    - [x] Use move calculation to generate new states
    - [ ] Maintain open and closed sets
    - [ ] Sort open set by *f = g + h*
    - [ ] Each state keeps track of previous state so full path can be generated
    - [ ] Display shortest path
  - [ ] *Optional:* Implement 15-square game
    - [ ] Modify data structures to handle 16 slots, from 9
    - [ ] Ensure computational and memory efficiency, as the problem size has increased 57657600 fold
  - [ ] Actually submit
    - [ ] Ensure code compiles
    - [ ] Rename file
    - [ ] Email code

## License
No license has been chosen at this time.
