package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.RobotStates;
import frc.robot.subsystems.drivers.Drivers;

/** DrivetrainStates defines all commands and states */
public class DrivetrainStates {
  private static CommandSwerveDrivetrain swerve = RobotContainer.getDrivetrain();

  // Swerve requests
  private static final SwerveRequest brake = new SwerveRequest.SwerveDriveBrake();
  private static final SwerveRequest idle = new SwerveRequest.Idle();
  private static final SwerveRequest.FieldCentric fieldCentricDrive =
      new SwerveRequest.FieldCentric()
          .withDeadband(DriveTrainConstants.MAX_SPEED * DriveTrainConstants.MAX_SPEED)
          .withRotationalDeadband(
              DriveTrainConstants.MAX_ANGULAR_SPEED * DriveTrainConstants.MAX_ANGULAR_SPEED)
          .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

  public static void setStates() {

    RobotStates.wheelXPosition.whileTrue(swerve.applyRequest(() -> brake));
    RobotStates.disabled.whileTrue(swerve.applyRequest(() -> idle));

    System.out.println("SETTING UP");

    swerve.setDefaultCommand(teleopDrive());
  }

  private static Command teleopDrive() {
    return swerve
        .applyRequest(
            () ->
                fieldCentricDrive
                    .withVelocityX(
                        -Drivers.chassisControlStrafe.getAsDouble() * DriveTrainConstants.MAX_SPEED)
                    .withVelocityY(
                        -Drivers.chassisControlStrafe.getAsDouble() * DriveTrainConstants.MAX_SPEED)
                    .withRotationalRate(
                        -Drivers.chassisControlRotation.getAsDouble()
                            * DriveTrainConstants.MAX_ANGULAR_SPEED))
        .withName("Teleop Drive");
  }
}
