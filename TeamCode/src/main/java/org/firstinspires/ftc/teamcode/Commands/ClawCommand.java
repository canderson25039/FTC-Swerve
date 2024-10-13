package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.Subsystems.Claw;

public class ClawCommand extends CommandBase {
    private final Claw claw;
    private boolean open = true;

    public ClawCommand(Claw claw){
        this.claw = claw;

    }
    public static Command oClaw(ClawCommand claw){
        claw.open = !claw.open;
        return new InstantCommand(()-> claw.claw.setClaw(claw.open));
    }
    public static Command pClawUp(ClawCommand claw){
        claw.open = !claw.open;
        return new InstantCommand(()-> claw.claw.setClaw(1));
    }
    public static Command pClawDown(ClawCommand claw){
        claw.open = !claw.open;
        return new InstantCommand(()-> claw.claw.setClaw(-1));
    }

}
