Introduction
============

The tests are based on the `Buster.JS`_ JavaScript test framework, and the
domain for this kata is the game of bowling.

`Underscore.JS`_ is included as a utility library, use it wise and often!

.. _Buster.JS: http://busterjs.org/
.. _Underscore.JS: http://underscorejs.org/


Project layout
==============
::
    .
    ├── autotest.py          # Auto test runner
    ├── buster.js            # Buster.JS config file
    ├── lib                  # libs and dependencies
    │   ├── iterate.js       # Setup namespace
    │   └── underscore.js    # Utility lib
    ├── src                  # Source code
    │   └── bowlingGame.js
    └── test                 # Tests
        └── bowling_test.js


Setup
=====

Using `npm`_ (the package manager of Node.JS), run::

    npm install -g buster

.. _npm: http://npmjs.org/


Running the tests
=================

**Buster.JS** is a versatile test framework. To create and serve a static HTML
page that runs the tests like QUnit, issue the following command::

    buster static

This is however quite cumbersome. Buster.JS can also do what JSTestDriver does.
To get output in a terminal (or for you CI), but at the same time actually run
the tests in a browser, you need to first run ::

    buster server

and direct the browser you would like to test in at http://localhost:1111/
followed by a click on the big button. Repeat this process in as many browsers
as you like. When you are ready to run the tests, do a ::

    buster test -e browser

If you like getting feedback from your tests at a fast pace, use the included
Python script that automagically runs the tests whenever a ``.js`` file is
changed::

    python autotest.py

