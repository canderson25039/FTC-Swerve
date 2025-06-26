package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.SwervePod;

/**
 * An example command that uses an example subsystem.
 */
public class TestPodCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final SwervePod swervePod0;
    private final SwervePod swervePod1;
    private final SwervePod swervePod2;
    Telemetry telemetry;

    public TestPodCommand(SwervePod swervePod0,SwervePod swervePod1,SwervePod swervePod2, Telemetry telemetry) {
        this.telemetry = telemetry;
        this.swervePod0=swervePod0;
        this.swervePod1=swervePod1;
        this.swervePod2=swervePod2;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(swervePod0,swervePod1,swervePod2);
    }
    public void setPos(double pos){
        swervePod0.Swivle(pos);
        swervePod1.Swivle(pos);
        swervePod2.Swivle(pos);

    }
    @Override
    public void execute(){
        telemetry.addLine("Pod0 "+swervePod0.getPos());
        telemetry.addLine("Pod1 "+swervePod1.getPos());
        telemetry.addLine("Pod2 "+swervePod2.getPos());
        telemetry.update();
    }
}