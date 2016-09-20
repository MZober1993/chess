## Chess

This repository contains a little command-line chess tool.
The main usage of this repository is to exercise with KI, algorithm 
and datastructures. 

### Motivation
The main motivation is to realize a funny chess game, 
but the interesting part is the implementation of the game itself.

### Features
- Move your chess-figures

### ToDos:
- pre-calculation if the move is possible or not
*this means:* add a data-structure that can calculate for each figure the possible fields
- calculate the chess-state
- turn-counter
- show the possible fields
- add a simple ki, that try to move figures
- add a decision-tree to realize a better ki
- optimize the decision-tree (and add a harder ki)

### Try it!

- install the game with `mvn clean install`
- run the `chess/src/main/resources/start.sh`