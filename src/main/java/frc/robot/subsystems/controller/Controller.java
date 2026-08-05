package frc.robot.subsystems.controller;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.controller.ControllerConstants.CONTROLLABLE_SYSTEMS;

public class Controller {
  // Control axis
  // chassis
  public static DoubleSupplier chassisControlTranslation = null;
  public static DoubleSupplier chassisControlStrafe = null;
  public static DoubleSupplier chassisControlRotation = null;

  // Triggers
  // Chassis
  public static Trigger wheelsXPosition = null;
  public static Trigger pointWheels = null;
  public static Trigger zeroHeading = null;

  // Tests
  public static Trigger runTest = null;

  // Instance variables
  private final CommandXboxController controller;

  public Controller(int port) {
    controller = new CommandXboxController(port);
  }

  public Controller withControl(CONTROLLABLE_SYSTEMS control) {
    switch (control) {
      case CHASSIS:
        chassisControlTranslation = this::translate;

        chassisControlStrafe = () -> controller.getRawAxis(ControllerConstants.ChassisControls.STRAFE);
        chassisControlRotation = () -> controller.getRawAxis(ControllerConstants.ChassisControls.ROTATION);

        wheelsXPosition = controller.button(ControllerConstants.ChassisControls.WHEEL_X_POSITION);
        pointWheels = controller.button(ControllerConstants.ChassisControls.POINT_WHEELS);
        zeroHeading = controller.button(ControllerConstants.ChassisControls.ZERO_HEADING);
        break;

      case TESTS:
        runTest = controller.button(ControllerConstants.TestControls.RUN_TEST);
        break;

      default:
        throw new RuntimeException("No setup configured for control " + control.name());
    }

    return this;
  }

  public boolean isDisconnected() {
    return !controller.isConnected();
  }

  private double translate() {
    return controller.getRawAxis(ControllerConstants.ChassisControls.TRANSLATION);
  }
}
// hello world
