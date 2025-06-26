package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.controller.PIDController;
import com.mineinjava.quail.SwerveModuleBase;
import com.mineinjava.quail.util.geometry.Vec2d;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class SwerveModule extends SwerveModuleBase {
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

    public SwerveModule(Vec2d pos, HardwareMap hardware, CRServo servoMotor, DcMotor motor, AnalogInput servoEncoder, double offset){
        super(pos, 1, 1);

        //pass all hardware and intizalize PID
        this.hardware = hardware;
        //this.driveMotor = driveMotor;
        this.servo = servoMotor;
        this.servoEncoder = servoEncoder;
        driveMotor = motor;

        this.servoOffset = offset;
        targetPoition =0;
        targetSpeed=0;

        pidController = new PIDController(0.015,0,0.00);//actually tune these for once

    }
    @Override
    public void setAngle(double angle){
        angle = Math.toDegrees(angle);
        angle = normalizeAngleDeg(angle);
        targetPoition=angle;
    }
    @Override
    public void setRawSpeed(double speed){
        if(Math.abs(currentError)<10)//make constant
            targetSpeed=speed;
        else
            targetSpeed=speed/4;
    }
    public void update(){
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

    public static double normalizeAngleDeg(double angle) {
        return angleDifferenceDeg(0, angle, 360);
    }
    public static double angleDifferenceDeg(double currentAngle, double targetAngle, int wrapAngle) {
        double result1 = Math.floorMod(Math.round((targetAngle - currentAngle) * 100), wrapAngle * 100L) * 0.01;
        double result2 = Math.floorMod(Math.round((targetAngle - currentAngle) * 100), -wrapAngle * 100L) * 0.01;
        if (Math.abs(result1) <= Math.abs(result2)) return result1;
        else return result2;
    }
}
