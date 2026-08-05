package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.RobotStates;
import frc.robot.subsystems.controller.Controller;
import frc.robot.subsystems.controller.ControllerConstants;

/** DrivetrainStates defines all commands and states */
public class DrivetrainStates {
        private static CommandSwerveDrivetrain swerve;

        // Swerve requests
        private static final SwerveRequest brake = new SwerveRequest.SwerveDriveBrake();
        private static final SwerveRequest idle = new SwerveRequest.Idle();
        private static final SwerveRequest.FieldCentric fieldCentricDrive = new SwerveRequest.FieldCentric()
                        .withDeadband(DriveTrainConstants.MAX_SPEED
                                        * ControllerConstants.ChassisControls.TRANSLATION_DEADBAND)
                        .withRotationalDeadband(
                                        DriveTrainConstants.MAX_ANGULAR_SPEED
                                                        * ControllerConstants.ChassisControls.ROTATION_DEADBAND)
                        .withDriveRequestType(DriveRequestType.OpenLoopVoltage);
        private static final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

        public static void setStates() {
                swerve = RobotContainer.getDrivetrain();

                RobotStates.wheelXPosition
                                .whileTrue(swerve.applyRequest(() -> brake).withName("Chassis.WheelXPosition"));
                RobotStates.disabled.whileTrue(swerve.applyRequest(() -> idle).withName("Chassis.Idle"));
                RobotStates.zeroHeading
                                .onTrue(swerve.runOnce(() -> swerve.seedFieldCentric())
                                                .withName("Chassis.ZeroHeading"));

                RobotStates.pointWheel.whileTrue(pointWheels());

                swerve.setDefaultCommand(teleopDrive());
        }

        private static Command teleopDrive() {
                return swerve
                                .applyRequest(() -> fieldCentricDrive
                                                .withVelocityX(-Controller.chassisControlTranslation.getAsDouble()
                                                                * DriveTrainConstants.MAX_SPEED)
                                                .withVelocityY(-Controller.chassisControlStrafe.getAsDouble()
                                                                * DriveTrainConstants.MAX_SPEED)
                                                .withRotationalRate(
                                                                -Controller.chassisControlRotation.getAsDouble()
                                                                                * DriveTrainConstants.MAX_ANGULAR_SPEED))
                                .withName("Chassis.TeleopDrive");
        }

        private static Command pointWheels() {
                return swerve.applyRequest(
                                () -> point.withModuleDirection(
                                                new Rotation2d(-Controller.chassisControlTranslation.getAsDouble(),
                                                                -Controller.chassisControlStrafe.getAsDouble())))
                                .withName("Chassis.PointWheels");
        }
}
