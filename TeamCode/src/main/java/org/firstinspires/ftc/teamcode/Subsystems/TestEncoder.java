package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class TestEncoder extends SubsystemBase {

    AnalogInput encoder0;
    AnalogInput encoder1;
    AnalogInput encoder2;
    AnalogInput encoder3;
    HardwareMap hardwareMap;
    Telemetry telemetry;
    double Position = 0;
    double indicator=0;
    public TestEncoder(HardwareMap hardware, AnalogInput servoEncoder,AnalogInput encoder1, AnalogInput encoder2, AnalogInput encoder3, Telemetry telemetry){
        this.hardwareMap = hardware;
        this.telemetry = telemetry;
        this.encoder0=servoEncoder;
        this.encoder1=encoder1;
        this.encoder2=encoder2;
        this.encoder3=encoder3;
    }

    public void changeEncoder(AnalogInput servoEncoder, double indicator){
        this.encoder0 = servoEncoder;
        this.indicator = indicator;
    }

    @Override
    public void periodic(){
        Position= encoder0.getVoltage();
        telemetry.addLine(indicator+" Position: "+Position);
        telemetry.addLine( "Position1: "+encoder1.getVoltage());
        telemetry.addLine( "Position2: "+encoder2.getVoltage());
        telemetry.addLine( "Position3: "+encoder3.getVoltage());
        telemetry.update();


    }

}