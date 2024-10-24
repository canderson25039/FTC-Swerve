package org.firstinspires.ftc.teamcode.Commands;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;

import java.util.function.DoubleSupplier;

public class Drive extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final DriveTrain m_drive;
    private final DoubleSupplier m_forward;
    private final DoubleSupplier m_side;
    private final DoubleSupplier m_rot;
    public double getX(){
        return m_side.getAsDouble();
    }
    public double getY(){
        return m_forward.getAsDouble();
    }
    public double getR(){
        return m_rot.getAsDouble();
    }
    public Drive(DriveTrain drive, DoubleSupplier forward, DoubleSupplier side, DoubleSupplier rot){
        m_drive = drive;
        m_forward = forward;
        m_side = side;
        m_rot = rot;
        addRequirements(drive);
    }
    public void execute(){
        m_drive.drive(m_forward.getAsDouble(),m_side.getAsDouble(),m_rot.getAsDouble());

    }
}

