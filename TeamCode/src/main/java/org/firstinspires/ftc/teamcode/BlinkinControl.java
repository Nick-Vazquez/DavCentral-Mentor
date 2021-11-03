package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver.BlinkinPattern;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class BlinkinControl {
    // Period to cycle LED pattern when in AUTO mode
    private final static int PATTERN_PERIOD = 20;

    // Milliseconds to debounce gamepad entry after press.
    private final static int GAMEPAD_DEBOUNCE = 500;

    RevBlinkinLedDriver                 blinkinLedDriver;
    int                                 patternIndex;

    Telemetry telemetry;
    Telemetry.Item patternName;
    Telemetry.Item mode;
    PatternMode patternMode;
    Deadline ledCycleDeadline;
    Deadline gamepadDebounce;

    static final ArrayList<BlinkinPattern> patterns = new ArrayList<>(
            Arrays.asList(
                    BlinkinPattern.CP1_2_SINELON,
                    BlinkinPattern.CP1_BREATH_FAST,
                    BlinkinPattern.CP2_BREATH_FAST)
    );

    protected enum PatternMode {
        MANUAL,
        AUTO
    }

    public BlinkinControl(RevBlinkinLedDriver blinkin, Telemetry telemetry) {
        this.blinkinLedDriver = blinkin;
        this.telemetry = telemetry;
    }

    public void init() {
        this.patternMode = PatternMode.AUTO;
        this.blinkinLedDriver.setPattern(patterns.get(patternIndex));

        mode = telemetry.addData("Display Kind: ", patternMode.toString());
        patternName = telemetry.addData("Pattern: ", patterns.get(patternIndex).toString());

        ledCycleDeadline = new Deadline(PATTERN_PERIOD, TimeUnit.SECONDS);
        gamepadDebounce = new Deadline(GAMEPAD_DEBOUNCE, TimeUnit.MILLISECONDS);
    }

    protected void handleGamePad(Gamepad gamepad) {
        if (!gamepadDebounce.hasExpired()) return;
        if (gamepad.a) {
            if (patternMode == PatternMode.AUTO) setMode(PatternMode.MANUAL);
            if (patternMode == PatternMode.MANUAL) setMode(PatternMode.AUTO);
            gamepadDebounce.reset();
        }
        else if ((patternMode == PatternMode.MANUAL) && (gamepad.dpad_left)) {
            patternIndex++;
            if (patternIndex > (patterns.size() - 1)) patternIndex = 0;
        }
        else if ((patternMode == PatternMode.MANUAL) && (gamepad.dpad_right)) {
            patternIndex--;
            if (patternIndex < 0) patternIndex = patterns.size() - 1;
        }
    }

    protected void setMode(PatternMode patternMode) {
        this.patternMode = patternMode;
        mode.setValue(patternMode.toString());
    }

    protected void doAutoDisplay()
    {
        if (ledCycleDeadline.hasExpired()) {
            patternIndex++;
            // Loop the index back if over size
            if (patternIndex > (patterns.size() - 1)) patternIndex = 0;
            displayPattern();
            ledCycleDeadline.reset();
        }
    }

    protected void displayPattern() {
        blinkinLedDriver.setPattern(patterns.get(patternIndex));
        patternName.setValue(patterns.get(patternIndex).toString());
    }
}
