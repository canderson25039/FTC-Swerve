package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Commands.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;



@TeleOp
public class TeleOPTest extends CommandOpMode {
    @Override
    public void initialize() {
        String[] driveMotors = {"frontRightMotor","frontLeftMotor","backRightMotor","backLeftMotor"};
        String[] servoList = {"open, rotate"};
        // controls
        GamepadEx dOP = new GamepadEx(gamepad1);

        //subsystems
        DriveTrain driveTrain = new DriveTrain(hardwareMap,driveMotors);
        Drive drive = new Drive(driveTrain, () ->  dOP.getLeftY(), () ->  dOP.getLeftX() , () ->  dOP.getRightX());
        telemetry.addLine("Y"+drive.getY());
        telemetry.addLine("X"+drive.getX());
        telemetry.addLine("R"+drive.getR());
        telemetry.update();


        //Claw r_claw = new Claw(hardwareMap, servoList,.8,.1,.9,-.5, 0.1);
        //ClawCommand Claw = new ClawCommand(r_claw);

        // schedule all commands
        /*dOP.getGamepadButton(GamepadKeys.Button.A).whenPressed(Claw.oClaw(Claw), true);
        dOP.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(Claw.pClawUp(Claw), true);
        dOP.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(Claw.pClawDown(Claw), true);
        */
        schedule(drive);

        // register  subsystems
        register(driveTrain);


        //default commands
        driveTrain.setDefaultCommand(drive);


    }
}
