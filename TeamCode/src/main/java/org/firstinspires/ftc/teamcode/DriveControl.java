package org.firstinspires.ftc.teamcode;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.qualcomm.robotcore.hardware.DcMotor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DriveControl {
    // Value to smooth between 0 and 1.
    private final static double SMOOTHING_VALUE = 0.05;

    public DcMotor frontLeft, frontRight, backRight, backLeft;

    /**
     * Moves the robot in the specified directions supplementing the drive type for a mecanum style
     * drivetrain.
     * @param drive Vertical movement of the robot (inches). Positive value moves forward.
     * @param strafe Horizontal movement of the robot (inches). Positive value moves right.
     * @param rotate Rotation of the robot relative to its center. Positive value rotates clockwise.
     * @return Array of length 4 denoting the power to set to each motor.
     */
    public static List<Double> MecanumMovement(double drive, double strafe, double rotate) {
        List<Double> powers = new ArrayList<>();
        // Find the number to scale vectors by. If the input to drive strafe and rotate > 1, then
        // the program will clip any power value to 1. This will remove ratios of drive.
        double scaleFactor = Math.max((Math.abs(drive) + Math.abs(strafe) + Math.abs(rotate)), 1);
        // Divide all power values by this number. (Functions derived from mecanum wheel tutorial)
        powers.set(0, (drive + strafe + rotate) / scaleFactor);
        powers.set(1, (drive - strafe + rotate) / scaleFactor);
        powers.set(2, (drive - strafe - rotate) / scaleFactor);
        powers.set(3, (drive + strafe - rotate) / scaleFactor);

        return powers;
    }

    public DriveControl(DcMotor fl, DcMotor fr, DcMotor br, DcMotor bl) {
        this.frontLeft = fl;
        this.frontRight = fr;
        this.backRight = br;
        this.backLeft = bl;
    }

    public void setMotorPowers(double fl, double fr, double br, double bl) {
        this.frontLeft.setPower(fl);
        this.frontRight.setPower(fr);
        this.backRight.setPower(br);
        this.backLeft.setPower(bl);
    }

    public void setMotorPowers(List<Double> powers) {
        this.frontLeft.setPower(powers.get(0));
        this.frontRight.setPower(powers.get(1));
        this.backRight.setPower(powers.get(2));
        this.backLeft.setPower(powers.get(3));
    }

    public void setMotorPowerSmooth(double fl, double fr, double br, double bl) {
        double prevFL = this.frontLeft.getPower();
        double prevFR = this.frontRight.getPower();
        double prevBR = this.backRight.getPower();
        double prevBL = this.backLeft.getPower();

        this.frontLeft.setPower((fl * SMOOTHING_VALUE) + (prevFL * (1 - SMOOTHING_VALUE)));
        this.frontRight.setPower((fr * SMOOTHING_VALUE) + (prevFR * (1 - SMOOTHING_VALUE)));
        this.backRight.setPower((br * SMOOTHING_VALUE) + (prevBL * (1 - SMOOTHING_VALUE)));
        this.backLeft.setPower((bl * SMOOTHING_VALUE) + (prevBR * (1 - SMOOTHING_VALUE)));
    }

    public void setMotorPowerSmooth(List<Double> powers) {
        double prevFL = this.frontLeft.getPower();
        double prevFR = this.frontRight.getPower();
        double prevBR = this.backRight.getPower();
        double prevBL = this.backLeft.getPower();

        this.frontLeft. setPower((powers.get(0) * SMOOTHING_VALUE) + (prevFL * (1 - SMOOTHING_VALUE)));
        this.frontRight.setPower((powers.get(1) * SMOOTHING_VALUE) + (prevFR * (1 - SMOOTHING_VALUE)));
        this.backRight. setPower((powers.get(2) * SMOOTHING_VALUE) + (prevBL * (1 - SMOOTHING_VALUE)));
        this.backLeft.  setPower((powers.get(3) * SMOOTHING_VALUE) + (prevBR * (1 - SMOOTHING_VALUE)));
    }
}
