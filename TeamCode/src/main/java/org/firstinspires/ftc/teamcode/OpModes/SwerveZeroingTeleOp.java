package org.firstinspires.ftc.teamcode.OpModes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Commands.TestPodCommand;
import org.firstinspires.ftc.teamcode.Commands.TestPodZeroCommand;
import org.firstinspires.ftc.teamcode.Subsystems.SwervePod;
import org.firstinspires.ftc.teamcode.Subsystems.SwervePodZeroing;
import org.firstinspires.ftc.teamcode.Util.Constants;


@TeleOp
public class SwerveZeroingTeleOp extends CommandOpMode {
    SwervePodZeroing swervepod0, swervepod1, swervepod2;
    TestPodZeroCommand testSwerve;
    private GamepadEx driverGamepad;

    @Override
    public void initialize() {
        Constants constants = new Constants();
        AnalogInput encoder0 = hardwareMap.get(AnalogInput.class, "encoder0");
        AnalogInput encoder1 = hardwareMap.get(AnalogInput.class, "encoder1");
        AnalogInput encoder2 = hardwareMap.get(AnalogInput.class, "encoder2");

        CRServo servo0 = hardwareMap.get(CRServo.class, "servo0");
        CRServo servo1 = hardwareMap.get(CRServo.class, "servo1");
        CRServo servo2 = hardwareMap.get(CRServo.class, "servo2");

        DcMotor motor0 = hardwareMap.get(DcMotor.class, "motor0");
        DcMotor motor1 = hardwareMap.get(DcMotor.class, "motor1");
        DcMotor motor2 = hardwareMap.get(DcMotor.class, "motor2");
        //subsystems
        swervepod0 = new SwervePodZeroing(hardwareMap, servo0, encoder0, constants.servo0Offset);
        swervepod1 = new SwervePodZeroing(hardwareMap, servo1, encoder1, constants.servo1Offset);
        swervepod2 = new SwervePodZeroing(hardwareMap, servo2, encoder2, constants.servo2Offset);
        //commands
        testSwerve = new TestPodZeroCommand(swervepod0, swervepod1, swervepod2, telemetry);
        //controllers
        driverGamepad = new GamepadEx(gamepad2);

        // Create a trigger for a button press
        Trigger aButton = new Trigger(() -> driverGamepad.getButton(GamepadKeys.Button.A));
        Trigger bButton = new Trigger(() -> driverGamepad.getButton(GamepadKeys.Button.B));
        Trigger xButton = new Trigger(() -> driverGamepad.getButton(GamepadKeys.Button.X));
        Trigger yButton = new Trigger(() -> driverGamepad.getButton(GamepadKeys.Button.Y));

        // Bind the button to a command

        aButton.whileActiveOnce(new InstantCommand(() -> testSwerve.setPos(270)));
        bButton.whileActiveOnce(new InstantCommand(() -> testSwerve.setPos(0)));
        xButton.whileActiveOnce(new InstantCommand(() -> testSwerve.setPos(179)));
        yButton.whileActiveOnce(new InstantCommand(() -> testSwerve.setPos(90)));

        //schedule commands
        schedule(testSwerve);

    }
}