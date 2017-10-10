# RobberRun
Java Cops n' Robbers game with Steering Behaviours AI.

GAME DESCRIPTION

Robber run allows the player to control a bank robber. The objective is to go around collecting money drops and timers without colliding with police officers. Every time a drop/timer is collected, money is added and a new police officer spawns. In a normal drop, all police officers return to (SEEK/ARRIVE) their previously investigated drop locations and a new officer goes to the new drop location. In a timer, all police officers leave (FLEE) their drops and leave the neighbourhood. In a super drop, police officers group and sweep the neighbourhood (FORMATION). Use WASD or arrow keys to control the robber.

RUNNING THE GAME

This has been tested ONLY on OSX Sierra. To use it:
	1) make sure the path to the main file is /robberrun/RobberRunMain.java
	2) cd to just outside the "robberrun" folder
	3) compile the package with "javac robberrun/RobberRunMain.java"
	4) run the package with "java robberrun.RobberRunMain"

usage video: https://youtu.be/tpX6BXan_iA
