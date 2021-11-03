package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class HardwareMapping {
    public DriveTrainHardware driveTrain = null;

    /**
     * Empty constructor for the HardwareMap. This is called whenever the HardwareMap class is
     * invoked.
     */
    public HardwareMapping() {

    }

    /**
     * When the init method is called, start up most of the initialization of the robot. This is
     * where you will set starting parameters. Make sure that movement complies with the stated
     * rules.
     * @param hwMap OpMode's HardwareMap to use when initializing devices.
     */
    public void init(HardwareMap hwMap) {
        driveTrain = new DriveTrainHardware();
        driveTrain.init(hwMap);
    }
}