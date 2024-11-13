package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.arcrobotics.ftclib.command.SubsystemBase;
import java.util.function.DoubleSupplier;

// Swerve drivetrain class
@Config
public class SwerveDrive extends SubsystemBase {
    private final SwerveModule pod0, pod1, pod2;
    private final DoubleSupplier controllerX, controllerY, controllerR;

    // Constructor
    public SwerveDrive(SwerveModule pod0, SwerveModule pod1, SwerveModule pod2,
                       DoubleSupplier controllerX, DoubleSupplier controllerY, DoubleSupplier controllerR) {
        this.pod0 = pod0;
        this.pod1 = pod1;
        this.pod2 = pod2;
        this.controllerX = controllerX;
        this.controllerY = controllerY;
        this.controllerR = controllerR;
    }

    // Flip testing drive
    public void drive(boolean flip) {
        double target = flip ? 0.0 : 100.0;
        pod0.setTargetAngle(target);
        pod1.setTargetAngle(target);
        pod2.setTargetAngle(target);
    }

    @Override
    public void periodic() {
        // Graph PIDF debugging information
        TelemetryPacket packet = new TelemetryPacket();
        packet.put("Pod0-Degrees", pod0.debugServoDegrees());
        packet.put("Pod0-Error",   pod0.debugAngleError());
        packet.put("Pod0-PIDF",    pod0.debugPIDFOutput());
        packet.put("Pod1-Degrees", pod1.debugServoDegrees());
        packet.put("Pod1-Error",   pod1.debugAngleError());
        packet.put("Pod1-PIDF",    pod1.debugPIDFOutput());
        packet.put("Pod2-Degrees", pod2.debugServoDegrees());
        packet.put("Pod2-Error",   pod2.debugAngleError());
        packet.put("Pod2-PIDF",    pod2.debugPIDFOutput());
        FtcDashboard dashboard = FtcDashboard.getInstance();
        dashboard.sendTelemetryPacket(packet);
    }
}
