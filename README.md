# A*
An implementation of A* for the 8-square game for Intelligent Systems.

## Authors
* Austin Bohannon [18286119]
* Nial Dillane []
* Adam O'Mahony []

## Build
To compile, run:
```bash
javac src/*.java
```

## Run
To run, run:
```bash
java -cp src/ A_Star
```

## Status
- [ ] Complete first sprint
  - [ ] Parse input
  - [ ] Implement game state data structure
  - [ ] Calculate possible moves
  - [ ] Print board state to screen
- [ ] Complete second sprint
  - [ ] Implement A*
    - [ ] Use move calculation to generate new states
    - [ ] Sort open set
    - [ ] Keep track of paths
    - [ ] Display shortest path
  - [ ] Implement optional 15-square game
    - [ ] Modify data structures to handle 16 slots, from 9
    - [ ] Ensure computational and memory efficiency, as the problem size has increased 57657600 fold

## License
No license has been chosen at this time.
