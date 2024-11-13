package org.firstinspires.ftc.teamcode.Subsystems;

import static com.arcrobotics.ftclib.util.MathUtils.clamp;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.hardware.motors.Motor.GoBILDA;
import org.firstinspires.ftc.teamcode.Util.AxonCRServo;

/**
 * Coaxial swerve module subsystem.<br>
 * <b>Specifications:</b>
 * <ul>
 *   <li>GoBilda 435 rpm 5203 series hex-shaft DC motor.</li>
 *   <li>Axon MAX+ brushless programmable servo (Continuous Rotation mode).</li>
 * </ul>
 */
public class SwerveModule extends SubsystemBase {

    // Swerve config encapsulation
    @Config
    public static class SwerveConfig {
        // Constants
        public static double kP, kI, kD, kF, tolerance, falloff;    // TEMPORARILY static for ftc-dashboard tuning
        public final GoBILDA MOTOR_TYPE;

        // Specific constructor
        public SwerveConfig(double kP, double kI, double kD, double kF, double tolerance, double falloff,
                            GoBILDA motorType) {
            SwerveConfig.kP = kP;
            SwerveConfig.kI = kI;
            SwerveConfig.kD = kD;
            SwerveConfig.kF = kF;
            SwerveConfig.tolerance = tolerance;
            SwerveConfig.falloff = falloff;
            MOTOR_TYPE = motorType;
        }

        // Default constructor
        public SwerveConfig() {
            this(0.1, 0.0, 0.0, 0.001, 3.0, 5.0, GoBILDA.RPM_435);
        }
    }

    // Objects
    private final SwerveConfig config;
    private final MotorEx driveMotor;   // https://docs.ftclib.org/ftclib/features/hardware/motors
    private final AxonCRServo servo;
    private final PIDFController servoPIDFController;

    // Config constructor
    public SwerveModule(HardwareMap hardware, String driveMotorID, String servoID, String servoEncoderID,
                        SwerveConfig swerveConfig) {
        config = swerveConfig;
        this.driveMotor = new MotorEx(hardware, driveMotorID, config.MOTOR_TYPE);
        this.servo = new AxonCRServo(hardware, servoID, servoEncoderID);
        servoPIDFController = new PIDFController(config.kP, config.kI, config.kD, config.kF);
        servoPIDFController.setSetPoint(0.0);

        // Init target variables
        targetAngle = 100.0;
        targetSpeed = 0.0;
    }

    // Default constructor
    public SwerveModule(HardwareMap hardware, String driveMotorID, String servoID, String servoEncoderID) {
        this(hardware, driveMotorID, servoID, servoEncoderID, new SwerveConfig());
    }

    // Target variables and setters
    private double targetAngle;
    private double targetSpeed;
    public void setTargetAngle(double angle) {
        targetAngle = angle;
    }
    public void setTargetSpeed(double speed) {
        targetSpeed = speed;
    }

    // Maintain angle
    public void maintainAngle() {
        // Reset PIDF constants for training when they are changed with FTC dashboard
        servoPIDFController.setPIDF(config.kP, config.kI, config.kD, config.kF);
        servoPIDFController.setTolerance(config.tolerance);

        // Calculate turn power using the PID controller
        double angleDiff = angleError(servo.getDegrees(), targetAngle);
        double turnPower = servoPIDFController.atSetPoint() ? 0.0 : servoPIDFController.calculate(angleDiff);

        // Limit the power
        turnPower = clamp(turnPower / config.falloff, -1.0, 1.0);
        servo.setPower(turnPower);
    }

    // Maintain speed
    public void maintainSpeed() {
    }

    // Math utility to calculate shortest path between two degree values
    public static double angleError(double sourceDegrees, double destDegrees){
        // 540 degrees is necessary because modulo keeps sign in Java, and we need a positive value
        return (destDegrees - sourceDegrees + 540) % 360 - 180;
    }

    // Always maintain angle and speed
    @Override
    public void periodic(){
        maintainAngle();
        maintainSpeed();
    }

    // FTC-dashboard debug functions
    public double debugServoDegrees() {
        return servo.getDegrees();
    }
    public double debugAngleError() {
        return angleError(debugServoDegrees(), targetAngle);
    }
    public double debugPIDFOutput() {
        return servoPIDFController.calculate(debugAngleError());
    }
}
