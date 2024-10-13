package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DriveTrain extends SubsystemBase {

    private final DcMotorEx frontRightMotor;
    private final DcMotorEx frontLeftMotor;
    private final DcMotorEx backRightMotor;
    private final DcMotorEx backLeftMotor;



    public DriveTrain(final HardwareMap map, final String[] driveTrain) {
        frontRightMotor= map.get(DcMotorEx.class, driveTrain[0]);
        frontLeftMotor= map.get(DcMotorEx.class, driveTrain[1]);
        backRightMotor= map.get(DcMotorEx.class, driveTrain[2]);
        backLeftMotor= map.get(DcMotorEx.class, driveTrain[3]);

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void drive(double y, double x, double r) {

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(r), 1);
        double frontLeftPower = (y + x + r) / denominator;
        double backLeftPower = (y - x + r) / denominator;
        double frontRightPower = (y - x - r) / denominator;
        double backRightPower = (y + x - r) / denominator;

        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);

    }
}
