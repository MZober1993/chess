## Chess

This repository contains a little command-line chess tool.
The main usage of this repository is to exercise with KI, algorithm 
and datastructures. 

## Build-State

[![Branch: master](https://travis-ci.org/MZober1993/chess.svg?branch=master)](https://travis-ci.org//MZober1993/chess)

### Motivation
The main motivation is to realize a funny chess game, 
but the interesting part is the implementation of the game itself.

### Features
- Move your chess-figures
- pre-calculation if the move is possible or not (color, move possible)
- calculate the chess-state (1. after move and 2. move possible or chess?)
- turn-counter and calculation which figure can move on each turn
- check-mate check when chess

### ToDos:
- stalement check
- add a simple ai, that try to move figures
- add a decision-tree to realize a better ai
- optimize the decision-tree (and add a harder ai)

### Try it!

- install the game with `mvn clean install`
- run the `chess/src/main/resources/start.sh`
