package org.firstinspires.ftc.teamcode.helpers;

import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.util.concurrent.TimeUnit;

public class DebouncedButton {
    /**
     * Encapsulates a gamepad button press to debounce the press of the button.
     * Each time the gamepadButton is true, return true and return false if button is pressed before
     * timeout.
     * @param gamepadButton GamePad button to monitor
     * @param duration Number of TimeUnits to debounce the input
     * @param timeUnit Unit of time of duration
     */
    private final boolean button;
    private Deadline deadline;

    public DebouncedButton(boolean gamepadButton, long duration, TimeUnit timeUnit) {
        this.button = gamepadButton;
        this.deadline = new Deadline(duration, timeUnit);
    }

    public boolean processInput() {
        if (this.button) {
            if (this.deadline.hasExpired()) {
                this.deadline.reset();
                return true;
            }
            else { return false; }
        }
        else { return false; }
    }

    public boolean deadlineExpired() {
        return this.deadline.hasExpired();
    }

    public long remainingTime(TimeUnit timeUnit) {
        return this.deadline.timeRemaining(timeUnit);
    }

    public void alterDeadline(long duration, TimeUnit timeUnit) {

    }
}
