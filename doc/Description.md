# Description

This is a Whack-Mole Game.

## Environment
[TinyOS](http://tinyos.stanford.edu/tinyos-wiki/index.php/Main_Page)
[Virtual Box](https://www.virtualbox.org/wiki/Downloads)

## Requirements
There are 9 "Moles" (each of which is a tmote), one "GameCenter" (a tmote, BaseStation, which connects to the Server running on PC) and a "Gun" (a laser).
### Mole
Mole receives signal from GameCenter and then turn on one of the LEDs to show that the Mole comes out of the hole. It will wait for the Gun to shoot. 
### GameCenter
GameCenter controls the game. It includes a tmote which is used to send command to Mole and receive data from mole, and a Server which is running on PC and get the data from 
### GunController
GunController

