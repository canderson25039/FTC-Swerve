package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Commands.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.Subsystems.SwerveModule;


@TeleOp
public class TeleOPTest extends CommandOpMode {
    MotorEx swerveDriveM;
    ServoEx swerveServo;

    @Override
    public void initialize() {

        String[] servoList = {"open, rotate"};


        // controls
        GamepadEx dOP = new GamepadEx(gamepad1);

        //subsystems
        SwerveModule swerve1 = new SwerveModule(hardwareMap, 0.5);




        //Claw r_claw = new Claw(hardwareMap, servoList,.8,.1,.9,-.5, 0.1);
        //ClawCommand Claw = new ClawCommand(r_claw);

        // schedule all commands
        /*dOP.getGamepadButton(GamepadKeys.Button.A).whenPressed(Claw.oClaw(Claw), true);
        dOP.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(Claw.pClawUp(Claw), true);
        dOP.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(Claw.pClawDown(Claw), true);
        */
        dOP.getGamepadButton(GamepadKeys.Button.A).whenPressed(new InstantCommand(() -> {
            swerve1.Swivle(0.5);
        }));
        dOP.getGamepadButton(GamepadKeys.Button.B).whenPressed(new InstantCommand(() -> {
            swerve1.Swivle(-0.5);
        }));
        schedule();

        // register  subsystems
        register(swerve1);


        //default commands

        //swerve1.setDefaultCommand(new InstantCommand(() -> {swerve1.Swivle(0.5);}));


    }

}
