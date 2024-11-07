package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Subsystems.SwerveDrive;
import org.firstinspires.ftc.teamcode.Subsystems.SwerveModule;


@TeleOp
public class TeleOPTest extends CommandOpMode {
    private DcMotorEx driveMotor1;
    private CRServo swerveServo1;
    private AnalogInput servoEncoder1;
    private DcMotorEx driveMotor2;
    private CRServo swerveServo2;
    private AnalogInput servoEncoder2;
    private  DcMotorEx driveMotor3;
    private CRServo swerveServo3;
    private AnalogInput servoEncoder3;
    private SwerveDrive swerveDrive;


    @Override
    public void initialize() {

        //intialize swerve hardware
        //should move this to constants later on
        //pod 1
        driveMotor1 = hardwareMap.get(DcMotorEx.class, "driveMotor1");
        swerveServo1 = hardwareMap.get(CRServo.class, "servo1");
        servoEncoder1 = hardwareMap.get(AnalogInput.class, "servoEncoder1");
        //pod 2
        driveMotor2 = hardwareMap.get(DcMotorEx.class, "driveMotor2");
        swerveServo2 = hardwareMap.get(CRServo.class, "servo2");
        servoEncoder2 = hardwareMap.get(AnalogInput.class, "servoEncoder2");
        //pod 3
        driveMotor3 = hardwareMap.get(DcMotorEx.class, "driveMotor3");
        swerveServo3 = hardwareMap.get(CRServo.class, "servo3");
        servoEncoder3 = hardwareMap.get(AnalogInput.class, "servoEncoder3");

        //subsystems
        SwerveModule swervePod1 = new SwerveModule(hardwareMap,driveMotor1, swerveServo1, servoEncoder1);
        SwerveModule swervePod2 = new SwerveModule(hardwareMap,driveMotor1, swerveServo1, servoEncoder1);
        SwerveModule swervePod3 = new SwerveModule(hardwareMap,driveMotor1, swerveServo1, servoEncoder1);

        swerveDrive = new SwerveDrive(hardwareMap, swervePod1, swervePod2, swervePod3);

        // controls
        GamepadEx dOP = new GamepadEx(gamepad1);
        dOP.getGamepadButton(GamepadKeys.Button.A).whenPressed(new InstantCommand(() -> {
            swerveDrive.drive();
        }), true);

        schedule();

        // register  subsystems
        register(swervePod1, swervePod2, swervePod3, swerveDrive);


        //default commands


        //OLD CLAW STUFF
        //Claw r_claw = new Claw(hardwareMap, servoList,.8,.1,.9,-.5, 0.1);
        //ClawCommand Claw = new ClawCommand(r_claw);

        // schedule all commands
        /*dOP.getGamepadButton(GamepadKeys.Button.A).whenPressed(Claw.oClaw(Claw), true);
        dOP.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(Claw.pClawUp(Claw), true);
        dOP.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(Claw.pClawDown(Claw), true);
        */


    }

}
