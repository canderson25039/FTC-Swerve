package org.firstinspires.ftc.teamcode.Subsystems;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
public class SwerveDrive extends SubsystemBase {
    private final SwerveModule pod1;
    private final SwerveModule pod2;
    private final SwerveModule pod3;
    private final HardwareMap map;
    private double direction;
    public SwerveDrive(HardwareMap map, SwerveModule pod1, SwerveModule pod2, SwerveModule pod3){
        this. map = map;
        this.pod1 = pod1;
        this.pod2 = pod2;
        this.pod3 = pod3;
    }
    public void drive(){
        pod1.Swivle(direction+10);
    }
    @Override
    public void periodic(){
    }

}
