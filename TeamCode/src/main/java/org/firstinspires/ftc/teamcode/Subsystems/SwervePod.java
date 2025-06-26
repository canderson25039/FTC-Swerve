package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.apache.commons.math3.exception.NotANumberException;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.io.NotActiveException;


public class SwervePod extends SubsystemBase {
    private final CRServo servo;
    private final AnalogInput servoEncoder;
    private final DcMotor driveMotor;
    private final HardwareMap hardware;
    private double servoOffset;
    private double currentPos;
    private double targetPoition;
    private double currentError;
    private double targetSpeed;
    private PIDController pidController;

    public SwervePod(HardwareMap hardware, CRServo servoMotor, DcMotor motor, AnalogInput servoEncoder, double offset){
        //pass all hardware and intizalize PID
        this.hardware = hardware;
        //this.driveMotor = driveMotor;
        this.servo = servoMotor;
        this.servoEncoder = servoEncoder;
        this.servoOffset = offset;
        driveMotor = motor;
        targetPoition =0;
        targetSpeed=0;
        pidController = new PIDController(0.015,0,0.00);//actually tune these for once

    }

    public void Swivle(double Position){

        Position = Math.toDegrees(Position);
        Position = normalizeAngleDeg(Position);
        targetPoition=Position;

    }
    public void setSpeed(double Speed){
        if(Math.abs(currentError)<10)//make constant
            targetSpeed=Speed;
        else
            targetSpeed=Speed/4;

    }
    public static double normalizeAngleDeg(double angle) {
        return angleDifferenceDeg(0, angle, 360);
    }
    public static double angleDifferenceDeg(double currentAngle, double targetAngle, int wrapAngle) {
        double result1 = Math.floorMod(Math.round((targetAngle - currentAngle) * 100), wrapAngle * 100L) * 0.01;
        double result2 = Math.floorMod(Math.round((targetAngle - currentAngle) * 100), -wrapAngle * 100L) * 0.01;
        if (Math.abs(result1) <= Math.abs(result2)) return result1;
        else return result2;
    }
    public double getPos(){
        return currentPos;
    }

    @Override
    public void periodic(){
        if(targetPoition>180)
            targetPoition=targetPoition-360;
        currentPos = (servoEncoder.getVoltage() / 3.3 * 360)+servoOffset;
        if(currentPos>180)
            currentPos=currentPos-360;
        currentError= targetPoition-currentPos;
        if(currentError>180)
            currentError = -(currentError-180);
        else if(currentError<-180)
            currentError = -(currentError+180);

        if(Math.abs(currentError)<2)//make constant
            servo.setPower(0);
        else if(Double.isNaN(pidController.calculate(currentError)))
            servo.setPower(0);
        else
            servo.setPower(pidController.calculate(currentError));

        if(driveMotor.getPower()!=targetSpeed)
            driveMotor.setPower(targetSpeed);

    }


}