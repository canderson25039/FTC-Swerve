package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class SwervePodZeroing extends SubsystemBase {
    private final CRServo servo;
    private final AnalogInput servoEncoder;
    //private final DcMotorEx driveMotor;
    private final HardwareMap hardware;
    private double servoOffset;
    private double currentPos;
    private double targetPoition;
    private double currentError;
    private PIDController pidController;

    public SwervePodZeroing(HardwareMap hardware, CRServo servoMotor, AnalogInput servoEncoder, double offset){
        //pass all hardware and intizalize PID
        this.hardware = hardware;
        //this.driveMotor = driveMotor;
        this.servo = servoMotor;
        this.servoEncoder = servoEncoder;
        this.servoOffset = offset;
        targetPoition =0;
        pidController = new PIDController(0.01,0,0.0);//actually tune these for once

    }

    public void Swivle(double Position){

        targetPoition = Position;
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

        if(Math.abs(currentError)<3)//make constant
            servo.setPower(0);
        else
            servo.setPower(0);



    }


}