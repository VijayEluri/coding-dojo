wumpus-diversity-032013
=======================

Code from the Wumpus coding dojo: exploring diversity, hold in March 2013

We were creating the Wumpus game. Mutliple pairs worked at the same time,
people switched pair every 15 min (1 person on one codebase for 30 min).

See the description of the dojo in the `about-dojo.html` file.

Observations
------------
* Switching every 15 min is hard but possible and easier than expected
* Most designs are quite similar (rectangles via arrays) with one exception (Asgeir's PC)
* People focused on getting something done in the short time => ugly code
  that would profit from refactoring
* Encapsulation can slow development down

Improvement ideas
------------------
* Split into smaller groups and rotate only within them so that pairs come back to their original PC
  in the time frame
* Rotation algorithm improvements:
  * Those who start rotating first rotate clockwise, their pairs counter-clockwise
  * People from the same "set" rotate in the same direction and never work together => switch
   somewhow in the middle (rotate within those two groups?)
* Make the design phase longer than 5 min, e.g. 15
* Help to spin off really different designs by having a longer design, by listing some of the
  possible starting points and by listing what the individual pairs have chosen
* Help the people to really focus on practicing some one thing (instead of everybody just
  trying to implement Wumpus)

Additional challenges/requirements
----------------------------------
* Your design must support placing a wall anywhere (=> not a rectangle with walls around)
