/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.helpers;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.concurrent.TimeUnit;

/**
 * Top of the mornin' to ya!
 *
 * This is an example use of the Debounced button class. This program is used to show the benefits
 * of software debouncing. More information on this class and example can be found in
 * DebouncedButton.md.
 *
 * Behavior:
 * Use the gamepad1 a button to control output states of both bouncing and debounced switches.
 * When started, pressing the a button will change both output states with different intervals
 * because one is bounced and one is debounced. When stopped, show the amount of times each variable
 * was flipped.
 */

@TeleOp(name="Debounced Button", group="Examples")
public class DebouncedBtnExample extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    // Create an instance of a DebouncedButton class we created for the example
    private DebouncedButton btnA;

    // Bools to hold the actual output state of the variable
    private boolean state;
    private boolean stateB;

    // Keeps track of the number of flips for each variable.
    private int flipsA;
    private int flipsB;

    // Telemetry outputs that are updated each time the loop runs
    private Telemetry.Item timeRemaining;
    private Telemetry.Item outputState;
    private Telemetry.Item outputStateB;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initializing...");

        this.state = false;
        this.stateB = false;

        // Set these to 0 to record the number of times we flip outputs
        this.flipsA = 0;
        this.flipsB = 0;

        // Instantiate (create a new copy/object of it) the DebouncedButton with a 5 sec. interval.
        this.btnA = new DebouncedButton(gamepad1.a, 5, TimeUnit.SECONDS);

        // Add all of the telemetry data. This long of a call will only be made in init()
        this.timeRemaining = telemetry.addData("Delay time remaining",
                this.btnA.remainingTime(TimeUnit.SECONDS));
        this.outputState = telemetry.addData("Output State Debounced", this.state);
        this.outputStateB = telemetry.addData("Output State Bouncing", this.state);

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized.");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        // Run the processInput method each time the mainloop cycles. Enter the block only if the
        // processInput method returns true.
        if (this.btnA.processInput()) {
            // Flip the var. This only happens once per debounced button press. Increment flip count
            this.state = !this.state;
            this.flipsA++;
        }

        // Check for the A button press on gamepad 1 and cycle the var if true. No debouncing.
        if (gamepad1.a) {
            this.stateB = !this.stateB;
            this.flipsB++;
        }

        // Update the telem output variables each time the loop runs regardless of if flipped.
        // You may need to sprinkle in a telemetry.update() at the end of this if it don't work.
        this.timeRemaining.setValue(this.btnA.remainingTime(TimeUnit.SECONDS));
        this.outputState.setValue(this.state);
        this.outputStateB.setValue(this.stateB);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        telemetry.addData("Flips A", this.flipsA);
        telemetry.addData("Flips B", this.flipsB);
        // Maybe an update call here too
    }

}
