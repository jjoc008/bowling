Name
====

Bowling Game

Description
===========
Calculates and show the result of a bowling game based on score rules.

Compiling
========

To compile:

maven 3.X required

Execute:
    
    mvn clean install
    cd target
    java -jar bowling-0.0.1-SNAPSHOT.jar < pathInputsFile >

Example:

    java -jar bowling-0.0.1-SNAPSHOT.jar ../src/test/resources/TestFileOnePlayer.txt
        

Output Example:

    Frame       1         2         3         4         5         6         7         8         9         10
    John
    Pinfalls    3    /    6    3         X    8    1         X         X    9    0    7    /    4    4    X    9    0
    Score       16        25        44        53        82        101       110       124       132       151
    Jeff
    Pinfalls         X    7    /    9    0         X    0    8    8    /    F    6         X         X    X    8    1
    Score       20        39        48        66        74        84        90        120       148       167

  