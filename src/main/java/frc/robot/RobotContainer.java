// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.SubsystemTesting;
import frc.robot.subsystems.controller.Controller;
import frc.robot.subsystems.controller.ControllerConstants.CONTROLLABLE_SYSTEMS;
import frc.robot.subsystems.drivetrain.CommandSwerveDrivetrain;
import frc.robot.subsystems.vision.Vision;
import lombok.Getter;

public class RobotContainer {
  // Subsystems
  @Getter
  private static CommandSwerveDrivetrain drivetrain = null;

  @Getter
  private static Controller driver1 = null;

  @Getter
  private static Controller driver2 = null;

  @Getter
  private static Controller testingController = null;// used for running the
  // subsystem tests
  @Getter
  private static Vision vision = null;

  private static Command currentAuto;
  // audio
  private SendableChooser<Command> autoChooser; // TODO implement pathplanner

  public RobotContainer() {
    initializeSubsystems();
    RobotStates.setupStates();

    setupSubsystems();

    // autoChooser = AutoBuilder.buildAutoChooser();
    // currentAuto = autoChooser.getSelected();
    // autoChooser.onChange((command) -> currentAuto = command);
    // SmartDashboard.putData("Autonomous", autoChooser);
  }

  public Command getAutonomousCommand() {
    return currentAuto;
  }

  private void initializeSubsystems() {
    driver1 = new Controller(0).withControl(CONTROLLABLE_SYSTEMS.CHASSIS);
    driver2 = new Controller(1);
    testingController = new Controller(2).withControl(CONTROLLABLE_SYSTEMS.TESTS);

    drivetrain = TunerConstants.createDrivetrain();

    vision = new Vision();
  }

  private void setupSubsystems() {
    SubsystemTesting.setupTests();
    drivetrain.setup();
    vision.setup();
  }
}
