package frc.robot.subsystems.vision;

import java.util.HashSet;
import java.util.Iterator;

import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.drivetrain.CommandSwerveDrivetrain;
import frc.robot.subsystems.vision.VisionConstants.LIMELIGHTS;
import frc.robot.subsystems.vision.VisionConstants.LIMELIGHT_PIPELINES;

public class Vision extends SubsystemBase {
    private final HashSet<LIMELIGHTS> localization_limelights = new HashSet<>();
    private CommandSwerveDrivetrain drivetrain;
    private Pigeon2 gyro;

    public Vision() {
        for (LIMELIGHTS limelight : LIMELIGHTS.values()) {
            setLimelightPos(limelight);
        }

        // SendableRegistry.add(this, "Outtake");
        SmartDashboard.putData(this);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Vision");
        builder.addStringArrayProperty("Localization", this::listLocalizationLimelights, null);
    }

    /**
     * A command that will set all localization limelights to the localization
     * pipeline then does nothing.
     * 
     * @return The command
     */
    public Command SetAllLocalization() {
        return runOnce(() -> {
            for (LIMELIGHTS limelight : LIMELIGHTS.values()) {
                setPipeline(limelight, LIMELIGHT_PIPELINES.LOCALIZATION);
            }
        }).andThen(Commands.idle()).withName("Vision.Localization").ignoringDisable(true);
    }

    /**
     * Sets the pipeline on the limelight
     * 
     * @param limelight the limelight to operate on
     * @param pipeline the pipeline to change it to
     */
    public void setPipeline(LIMELIGHTS limelight, LIMELIGHT_PIPELINES pipeline) {
        LimelightHelpers.setPipelineIndex(limelight.name, pipeline.pipeline);

        if (limelight.localization)
            if (pipeline == LIMELIGHT_PIPELINES.LOCALIZATION)
                localization_limelights.add(limelight);
            else
                localization_limelights.remove(limelight);
    }

    @Override
    public void periodic() {
        for (LIMELIGHTS limelight : localization_limelights) {
            localizationMeasurement(limelight);
        }
    }

    public void setup() {
        drivetrain = RobotContainer.getDrivetrain();
        gyro = drivetrain.getPigeon2();

        VisionStates.setStates();
    }

    private String[] listLocalizationLimelights() {
        String[] names = new String[localization_limelights.size()];
        int i = 0;
        Iterator<LIMELIGHTS> iter = localization_limelights.iterator();
        while (iter.hasNext()) {
            names[i++] = iter.next().name;
        }

        return names;

    }

    private void setLimelightPos(LIMELIGHTS limelight) {
        LimelightHelpers.setCameraPose_RobotSpace(limelight.name, limelight.x, limelight.y, limelight.z, limelight.roll,
        limelight.pitch, limelight.yaw);

    }

    private void localizationMeasurement(LIMELIGHTS limelight) {
        LimelightHelpers.SetRobotOrientation(limelight.name, drivetrain.getState().Pose.getRotation().getDegrees(),
        gyro.getAngularVelocityZWorld().getValueAsDouble(), 0, 0, 0, 0);

        LimelightHelpers.PoseEstimate estimatedPosition = LimelightHelpers
        .getBotPoseEstimate_wpiBlue_MegaTag2(limelight.name);

        if (estimatedPosition == null || estimatedPosition.tagCount == 0
        || Math.abs(gyro.getAngularVelocityZWorld().getValueAsDouble()) > 720)
            return;// reject measurements if not seeing a tag or going too fast

        double timeStamp = drivetrain.getState().Timestamp - estimatedPosition.latency / 1000;
        drivetrain.addVisionMeasurement(estimatedPosition.pose, timeStamp);

    }
}
