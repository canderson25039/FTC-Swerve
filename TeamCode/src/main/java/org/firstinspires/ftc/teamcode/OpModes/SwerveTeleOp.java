package org.firstinspires.ftc.teamcode.OpModes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.mineinjava.quail.RobotMovement;
import com.mineinjava.quail.SwerveDrive;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.Commands.SwerveDriveCommand;
import org.firstinspires.ftc.teamcode.Commands.TestPodCommand;
import org.firstinspires.ftc.teamcode.Subsystems.SwerveModule;
import org.firstinspires.ftc.teamcode.Subsystems.SwervePod;
import org.firstinspires.ftc.teamcode.Util.Constants;

import java.util.ArrayList;
import java.util.List;


@TeleOp
public class SwerveTeleOp extends CommandOpMode {
    IMU imu;
    SwervePod swervepod0, swervepod1, swervepod2;
    SwerveModule swervemod0, swervemod1, swervemod2;
    List<SwerveModule> swerveMods = new ArrayList<>();

    SwerveDrive swerveDrive;
    SwerveDriveCommand swerveDriveCommand;
    TestPodCommand testSwerve;
    private GamepadEx driverGamepad;

    @Override
    public void initialize() {
        Constants constants = new Constants();
        initIMU();
        imu.resetYaw();
        //encorder init
        AnalogInput encoder0 = hardwareMap.get(AnalogInput.class, "encoder0");
        AnalogInput encoder1 = hardwareMap.get(AnalogInput.class, "encoder1");
        AnalogInput encoder2 = hardwareMap.get(AnalogInput.class, "encoder2");
        //servo init
        CRServo servo0 = hardwareMap.get(CRServo.class, "servo0");
        CRServo servo1 = hardwareMap.get(CRServo.class, "servo1");
        CRServo servo2 = hardwareMap.get(CRServo.class, "servo2");

        DcMotor motor0 = hardwareMap.get(DcMotor.class, "motor0");
        DcMotor motor1 = hardwareMap.get(DcMotor.class, "motor1");
        DcMotor motor2 = hardwareMap.get(DcMotor.class, "motor2");
        motor0.setDirection(DcMotorSimple.Direction.REVERSE);
        motor1.setDirection(DcMotorSimple.Direction.REVERSE);
        motor2.setDirection(DcMotorSimple.Direction.FORWARD);

        //Quail swerve mods
        swervemod0 = new SwerveModule(constants.modulePos0,hardwareMap, servo0, motor0, encoder0, constants.servo0Offset);
        swervemod1 = new SwerveModule(constants.modulePos1,hardwareMap, servo1, motor1, encoder1, constants.servo1Offset);
        swervemod2 = new SwerveModule(constants.modulePos2,hardwareMap, servo2, motor2, encoder2, constants.servo2Offset);

        //add to list
        swerveMods.add(swervemod0);
        swerveMods.add(swervemod1);
        swerveMods.add(swervemod2);
        //Quail swerve drive
        swerveDrive = new SwerveDrive(swerveMods);
        driverGamepad = new GamepadEx(gamepad2);
        swerveDriveCommand = new SwerveDriveCommand(imu, swerveDrive, ()-> driverGamepad.getLeftX(),()->  driverGamepad.getRightX(),()-> driverGamepad.getRightY(), telemetry);
        //commands

        // Create a trigger for a button press
        Trigger aButton = new Trigger(() -> driverGamepad.getButton(GamepadKeys.Button.A));
        Trigger bButton = new Trigger(() -> driverGamepad.getButton(GamepadKeys.Button.B));

        //a button resets heading
        aButton.whileActiveOnce(new InstantCommand(() -> imu.resetYaw()));
        bButton.whileActiveOnce(new InstantCommand(() -> swerveDriveCommand.setHeading(0)));

        schedule(swerveDriveCommand);
        register(swervepod1,swervepod0,swervepod2);

    }

    //int imu
    public void initIMU(){
        imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
                )
        );

        imu.initialize(parameters);
    }
}