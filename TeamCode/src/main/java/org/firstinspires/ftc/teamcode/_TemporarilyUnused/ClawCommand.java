/*
package org.firstinspires.ftc.teamcode._TempUnnecessary;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;

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
*/