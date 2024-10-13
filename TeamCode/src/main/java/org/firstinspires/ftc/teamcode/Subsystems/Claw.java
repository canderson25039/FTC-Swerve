package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Claw extends SubsystemBase {

    private final ServoEx close;
    private final ServoEx pivot;
    private boolean clawOpen = true;
    public double openPos;
    public double closePos;
    private double maxPivot;
    private double minPivot;
    private double clawPivot = 0.0;
    private double clawPivotAmount;



    public Claw(final HardwareMap map, final String[] servoList,
                double openPostion, double closePosition, double maxPivotRange, double minPivotRange, double pivotIncreament) {
        close= map.get(ServoEx.class, servoList[1]);
        pivot= map.get(ServoEx.class, servoList[2]);

        openPos = openPostion;
        closePos = closePosition;

        maxPivot = maxPivotRange;
        minPivot = minPivotRange;

        clawPivotAmount = pivotIncreament;
    }
    public void setClaw(boolean a) {
        clawOpen=a;
        moveClaw();
    }
    public void setClaw(double y) {
        if(clawPivot+y<=maxPivot && clawPivot+y>=minPivot){
            clawPivot=clawPivot+(y+clawPivotAmount);
            moveClaw();
        }
    }
    public void moveClaw(){
        if (clawOpen) close.setPosition(openPos);
        else if (!clawOpen) close.setPosition(closePos);
        pivot.setPosition(clawPivot);
    }
}
