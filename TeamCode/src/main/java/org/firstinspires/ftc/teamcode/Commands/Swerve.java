package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.Subsystems.SwerveModule;

public class Swerve extends CommandBase {
    private final SwerveModule pod1;
    private final SwerveModule pod2;
    private final SwerveModule pod3;

    public Swerve(SwerveModule pod1, SwerveModule pod2, SwerveModule pod3){
        this.pod1 = pod1;
        this.pod2 = pod2;
        this.pod3 = pod3;

    }
    public static Command a(ClawCommand claw){

        return new InstantCommand();
    }

}
