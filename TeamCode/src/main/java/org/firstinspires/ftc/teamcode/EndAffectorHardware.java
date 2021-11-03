package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.util.concurrent.TimeUnit;

public class EndAffectorHardware {
    Servo servoArmLeft;
    Servo servoArmRight;
    Deadline gamepadDebounce;

    static final double ARM_MAX = 1.0;
    static final double ARM_MIN = 0.0;

    static final int GAMEPAD_TIMEOUT = 500;

    double position = (ARM_MAX - ARM_MIN) / 2;

    public EndAffectorHardware() {

    }

    public void init(HardwareMap hwMap) {
        servoArmLeft    = hwMap.get(Servo.class, "arm_left");
        servoArmRight   = hwMap.get(Servo.class, "arm_right");

        gamepadDebounce = new Deadline(GAMEPAD_TIMEOUT, TimeUnit.MILLISECONDS);

        servoArmLeft.setPosition(-position);
        servoArmRight.setPosition(position);
    }

    public void handleGamepad(Gamepad gamepad) {
        if (!gamepadDebounce.hasExpired()) return;
        if (gamepad.left_bumper) {
            toggleArm();
            gamepadDebounce.reset();
        }
    }

    public void toggleArm() {
        if (servoArmRight.getPosition() == ARM_MAX) {
            closeArm();
        }
        else openArm();
    }

    public void closeArm() {
        this.position = ARM_MIN;
        servoArmLeft.setPosition(position);
        servoArmRight.setPosition(position);
    }

    public void openArm() {
        this.position = ARM_MAX;
    }
}