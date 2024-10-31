# MarioBrosGame

## Acknowledgments
Mario Bros. is owned by [Nintendo Inc.](https://www.nintendo.com/)

## Contributor
[Victor Micha](https://github.com/vmic2002)

## About
MarioBrosGame is an attempted recreation (with some new touches) of the Super Mario Bros. 3 game on the NES.

This game is coded in Java using the Eclipse IDE and [Stanford's acm library](https://cs.stanford.edu/people/eroberts/jtf/), more specifically the [acm.graphics package](https://cs.stanford.edu/people/eroberts/jtf/rationale/GraphicsPackage.html).

This is an ongoing project from Feb 5th, 2023 - Now

The desktop game is written entirely in Java and uses an open source graphics library from ACM. In order to play the game online, some HTML, CSS, and JS was required. Essentially, the Java game is played in headless mode on an Apache Tomcat Java container and communicates with the client(s) using websockets for real time data transfer. This means that the game is being played twice simultaneously, once at the client side and once at the server side. The client side handles the graphics and calls the server to deal with the game logic.


## How to play the game on your computer
*might not work on windows*
1. Open up your Terminal App.
2. Clone this repo by running:
```bash
git clone https://github.com/vmic2002/MarioBrosGame.git
```
3. Run
```bash
cd MarioBrosGame
./playMarioGame.sh
```
The *playMarioGame.sh* file is a bash script that will run MarioBrosGame from the command line using the *.class* files in the *bin* directory and *acm.jar*.

To control Luigi: Use &#8592;, &#8593;, and &#8594; to move, &#8595; to crouch and `K` to shoot fire balls or swing Luigi's tail.

To control Mario: Use `F`, `T`, and `H` to move, `G` to crouch and `Q` to shoot fire balls or swing Mario's tail.

You can also press `1`, `2`, `3`, `4`, and `5` to change both Mario and Luigi to small, big, fire, cat, or tanooki, respectively.

## Quick preview 1
This video was recorded on Feb 27th, 2023 after the 18th commit:

https://user-images.githubusercontent.com/89990471/221724952-f823573c-0560-49b2-9ba5-48deaf7d2c8b.mov



## Quick preview 2
This video was recorded on April 12th, 2023 after the 65th commit:

https://user-images.githubusercontent.com/89990471/231574900-150501af-47b7-4d54-91ab-eea96760c24b.mp4

## Next steps: Making game playable online from browser!
Ongoing...

