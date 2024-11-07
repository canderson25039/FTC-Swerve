package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class SwerveModule extends SubsystemBase {
    private final CRServo servo;
    private final AnalogInput servoEncoder;
    private final DcMotorEx driveMotor;
    private final HardwareMap hardware;
    private double currentPos;
    private double targetPoition;
    private double direction =1;



    public SwerveModule(HardwareMap hardware, DcMotorEx driveMotor, CRServo servoMotor, AnalogInput servoEncoder){
        this.hardware = hardware;
        this.driveMotor = driveMotor;
        this.servo = servoMotor;
        this.servoEncoder = servoEncoder;
        targetPoition =0;

    }

    public void Swivle(double Position){
        targetPoition = Position;
    }
    public double sendPos(){
        currentPos = servoEncoder.getVoltage() / 3.3 * 360;
        return currentPos;
    }
    @Override
    public void periodic(){
        if(targetPoition>currentPos) direction =1;
        else direction =-1;

        currentPos = servoEncoder.getVoltage()/3.3 *360;
        if((currentPos>targetPoition-5 & currentPos<targetPoition+5)){
            servo.setPower(0);
        }
        else if((currentPos>targetPoition-30 & currentPos<targetPoition+30)){
            servo.setPower(.1*direction);
        }
        else servo.setPower(1*direction);
    }

}
