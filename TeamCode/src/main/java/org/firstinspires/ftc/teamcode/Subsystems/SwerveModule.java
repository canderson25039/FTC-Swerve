package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class SwerveModule extends SubsystemBase {
    private final Servo servo;
    private final DcMotorEx driveMotor;
    private final HardwareMap hardware;
    private final double start;


    public SwerveModule(HardwareMap hardware, Double startAngle){
        this.hardware = hardware;
        this.start = startAngle;
        //hard coded rn will fix later
        driveMotor = hardware.get(DcMotorEx.class, "test");
        servo = hardware.get(Servo.class, "testservo");
    }
    public void Swivle(double targetPoition){
       servo.setPosition(targetPoition);
    }
    public void sendPos(){
        telemetry.addLine("POS is");
        //+ servo.getAngle(AngleUnit.DEGREES)
    }
}
