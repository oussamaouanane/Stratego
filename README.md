# Stratego

This Java project is based on the [Stratego](https://en.wikipedia.org/wiki/Stratego) turn-based game, it is a strategy board game for two players on a board of 10×10 squares. Each player controls 40 pieces representing individual officer ranks in an army. The pieces have Napoleonic insignia. The objective of the game is to find and capture the opponent's Flag, or to capture so many enemy pieces that the opponent cannot make any further moves.

The game was made for the [Computer Science](http://applications.umons.ac.be/web/en/pde/2018-2019/aa/S-INFO-605.htm) course at University of Mons. The project is not totally finished so you may have errors. The save/load system doesn't work well.
## Screenshot

![menu](../master/screenshot/menu.png)
![main](../master/screenshot/main.png)

## Installation

```bash
git clone https://github.com/oussamaouanane/Stratego.git
```

## Requirements

| Name | Link | 
| :------------- | :----------: |
| Java | https://www.java.com/fr/download/   | 
| OpenJFX | https://gluonhq.com/products/javafx/ |
| Ant | https://ant.apache.org/bindownload.cgi |

## Usage

The project was finalized in Arch Linux so if you run it with Windows, you have to change the height of the GameView
Window to 600px.

```bash
# Building
ant build

# Running
ant run
```

## License
Feel free to use the source code if you need anything.

[MIT](https://choosealicense.com/licenses/mit/)
