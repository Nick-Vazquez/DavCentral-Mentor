# Button Debouncing
---
I made the DebouncedButton.java class to enforce software debouncing on boolean controller inputs.
This includes any D-pad or A,B,X,Y buttons, but also stick clicks and bumpers.

## But _what is_ debouncing??
Debouncing is the act of removing the bounce of a switch.

_Well no shit, Sherlock!_ ... A switch _bounces_ when a single intended press of a switch from a
user is registered multiple times by a device program. There are a few different situations that
bouncing happens in:

* Electrical contacts shorting more than once in a depression
* Multiple clock cycles completing within a single button press
* Some others I probably don't know about. Go ask an electrical engineer if you're so inclined.

### Active Debouncing
There also also a few other ways to debounce a switch. Once of these ways is actually already
implemented in the gamepad itself. This is *harware debouncing*. Hardware debouncing is a result
of using both a resistor and capacitor in a button circuit. Once the button is pressed, it takes
a little bit of time for the capacitor to recharge before another high signal is registered. Thus,
this gives the user ample time to promptly remove their digit.

Another way that this is implemented is within software. This is what the DebouncedButton class is
aimed to emulate. Because there may be multiple clock cycles completed during a single button press,
we use a software timer and *flag* (Something to keep track of something for something else) to keep
track of when the button was pressed last and only trigger the intended action after a set period.

### Passive Debouncing
*Be fast!* _like a cat!_ (But really, do the little bit of work to implement debouncing)


## How the DebouncedButton class works
_A simple little class, but a beaut at that.*

DebouncedButton is a class with a the *constructor* (method ran when you do `new SomeClass()`):
```java
public DebouncedButton(boolean gamepadButton, long duration, TimeUnit timeUnit)
```
This creates a `ftc.robotcore.internal.system.Deadline` object with the specified method parameters
and is used to keep track of the time since last button press. It also assigns the specified
gamepad button to an instance variable that can be used to check within the `processInput()` method.

The `processInput()` method is what does the grunt of the work for the class. This is called
whenever an outside process (Some other program that instantiates a DebouncedButton object) would
like to check the debounced state of the button. This checks to see if the button is depressed,
and if so whether the timer since the last button press has been elapsed. If so, return true. In any
other instance, return false.

There are also some other filler methods that I thought may be useful at some point. One of these,
`remainingTime(TimeUnit timeUnit)`, is used within the example program to update the amount of time
left of the timer if it has been triggered recently. Telemetry will also be shown with the output
state of both a bouncing and debounced output controlled with `gamepad1.a`. 

When the program ends, telemetry will show the amount of times an output has changed. The bouncing
output should almost always be switched more than the debounced.