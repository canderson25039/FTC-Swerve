package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.mineinjava.quail.RobotMovement;
import com.mineinjava.quail.SwerveDrive;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.Subsystems.SwerveModule;

import java.util.function.DoubleSupplier;

public class SwerveDriveCommand extends CommandBase {
    private IMU imu;
    private SwerveDrive swerveDrive;
    private SwerveModule mod0;
    private SwerveModule mod1;
    private SwerveModule mod2;

    private PIDController headingPID;

    private DoubleSupplier rightY;
    private DoubleSupplier rightX;
    private DoubleSupplier leftX;

    private double currentHeading;
    private double targetHeading;
    private double xField;
    private double yField;
    private double rotField;
    private double cosA;
    private double sinA;
    private Telemetry telemetry;

    public SwerveDriveCommand(IMU imu, SwerveDrive swerveDrive, DoubleSupplier leftX, DoubleSupplier rightX, DoubleSupplier rightY, Telemetry telemetry){
        this.imu = imu;
        this.telemetry = telemetry;

        this.leftX = leftX;
        this.rightX = rightX;
        this.rightY = rightY;

        this.swerveDrive = swerveDrive;
        mod0 = (SwerveModule) swerveDrive.swerveModules.get(0);
        mod1 = (SwerveModule) swerveDrive.swerveModules.get(1);
        mod2 = (SwerveModule) swerveDrive.swerveModules.get(2);

        currentHeading = -90;
        targetHeading = -90;

        headingPID = new PIDController(0.01,0,0);//MAKE INTO CONSTANTS
    }
    @Override
    public  void execute(){
        mod0.update();
        mod1.update();
        mod2.update();
        //get input from imu
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();

        //set current heading to the direction the robot is facing -90 degrees for starting ofset
        currentHeading = orientation.getYaw(AngleUnit.DEGREES)-90;
        if (Math.abs(leftX.getAsDouble()) > 0.05) {
            // Driver is turning / manual control = no heading lock
            targetHeading = currentHeading;
            rotField = leftX.getAsDouble();
        } else {
            // Lock heading using PID when not manual controling
            double error = angleWrap(targetHeading - currentHeading);
            rotField = -headingPID.calculate(error);
            if(Math.abs(rotField)<0.01)
                rotField=0;
        }

        //math for absolute driving
        cosA = Math.cos(-Math.toRadians(orientation.getYaw(AngleUnit.DEGREES))-Math.PI);
        sinA = Math.sin(-Math.toRadians(orientation.getYaw(AngleUnit.DEGREES))-Math.PI);

        //input values after absolute drive
        xField = -rightX.getAsDouble() * cosA - rightY.getAsDouble() * sinA;
        yField = -rightX.getAsDouble() * sinA + rightY.getAsDouble() * cosA;

        //inputs -> swerve drive
        RobotMovement currentInput = new RobotMovement(rotField/4,xField,yField);

        if(!(Math.abs(rotField)<0.01&rightX.getAsDouble()==0&rightY.getAsDouble()==0))
            swerveDrive.move(currentInput, -Math.PI/2);
        else{
            mod0.setSpeed(0);
            mod1.setSpeed(0);
            mod2.setSpeed(0);
        }

        //updates to driver hub
        telemetry.addLine("Current input: "+ leftX.getAsDouble()+" "+ rightX.getAsDouble()+" "+ -rightY.getAsDouble());
        /*telemetry.addLine("mod0 "+mod0.steeringVector.getAngle());
        telemetry.addLine("mod1 "+mod1.steeringVector.getAngle());
        telemetry.addLine("mod2 "+mod2.steeringVector.getAngle());
        telemetry.addLine("mod2 PID "+mod2.swervePod.getPidOutput());*/
        telemetry.addLine("rotField: "+rotField);
        telemetry.addLine("target heading: "+(targetHeading+90));
        telemetry.addLine("heading: "+(currentHeading+90));
        telemetry.update();
    }
    public void setHeading(double heading){
        targetHeading = heading-90;
    }
    public double angleWrap(double angle) {
        while (angle > 180) angle -= 360;
        while (angle < -180) angle += 360;
        return angle;
    }
}

