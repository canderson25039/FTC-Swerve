package org.firstinspires.ftc.teamcode.OpModes;

// Command Base
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import org.firstinspires.ftc.teamcode.Subsystems.SwerveDrive;
import org.firstinspires.ftc.teamcode.Subsystems.SwerveModule;
import org.firstinspires.ftc.teamcode.Subsystems.SwerveModule.SwerveConfig;

/**
 * Tester TeleOp for swerve orientation and alignment.<br>
 */
@TeleOp
public class TeleOPTest extends CommandOpMode {
    // Objects
    private SwerveDrive swerve;
    private GamepadEx gamepad;

    @Override
    public void initialize() {
        // Gamepad button controls
        gamepad = new GamepadEx(gamepad1);
        gamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new InstantCommand(() -> swerve.drive(true)), true);
        gamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new InstantCommand(() -> swerve.drive(false)), true);

        // Initialize swerve drivetrain subsystem
        SwerveConfig config = new SwerveConfig();
        swerve = new SwerveDrive(
                new SwerveModule(hardwareMap,"driveMotor0","swerveServo0","servoEncoder0"),
                new SwerveModule(hardwareMap,"driveMotor1","swerveServo1","servoEncoder1"),
                new SwerveModule(hardwareMap,"driveMotor2","swerveServo2","servoEncoder2"),
                gamepad::getLeftX,
                gamepad::getLeftY,
                gamepad::getRightX);

        /* OLD CLAW STUFF
        Claw r_claw = new Claw(hardwareMap, servoList,.8,.1,.9,-.5, 0.1);
        ClawCommand Claw = new ClawCommand(r_claw);

        // schedule claw commands
        gamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(Claw.oClaw(Claw), true);
        gamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(Claw.pClawUp(Claw), true);
        gamepad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(Claw.pClawDown(Claw), true);
        */
    }
}
