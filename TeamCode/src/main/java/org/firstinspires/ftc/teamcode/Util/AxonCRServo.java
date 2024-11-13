package org.firstinspires.ftc.teamcode.Util;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.AnalogInput;

/**
 * Wrapper for the Axon MAX+ brushless programmable servo and
 * its corresponding Analog encoder in Continuous Rotation mode.
 */
@Config
public class AxonCRServo {
    private final CRServo servo;
    private final AnalogInput servoEncoder;

    // Constants
    private static final double SERVO_MAX_RANGE = 3.3;  // Volts
    public static double MAX_ANGLE_ROC = 360.0;  // Degrees per second - Configurable in FTC dashboard

    private double rawEncoderDegrees() {
        return servoEncoder.getVoltage() / SERVO_MAX_RANGE * 360;
    }

    // Previous variables
    private double prevEncoderAngle;
    private double prevNanoTime;
    /**
     * Get the current degrees in range 0 to 360.
     * Accounts for encoder irregularities near 0.
     * @return Current degrees.
     */
    public double getDegrees() {
        double encoderAngle = rawEncoderDegrees();
        double nanoTime = System.nanoTime();
        double angleROC = (encoderAngle - prevEncoderAngle) * 1e9 / (nanoTime - prevNanoTime); // Degrees per second

        // Update previous variables
        prevEncoderAngle = encoderAngle;
        prevNanoTime = nanoTime;

        // If encoder reports unrealistic rate of change, return 0, otherwise, return encoder angle
        return Math.abs(angleROC) > MAX_ANGLE_ROC ? 0 : encoderAngle;
    }

    /**
     * Set the power of the servo.
     * @param v Power value in range -1 to 1.
     */
    public void setPower(double v) {
        servo.setPower(v);
    }

    // Constructor
    public AxonCRServo(HardwareMap hardware, String servoID, String servoEncoderID) {
        servo = hardware.get(CRServo.class, servoID);
        servoEncoder = hardware.get(AnalogInput.class, servoEncoderID);
        prevEncoderAngle = rawEncoderDegrees();
        prevNanoTime = System.nanoTime();
    }
}
