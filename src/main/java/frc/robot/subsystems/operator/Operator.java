package frc.robot.subsystems.operator;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.operator.OperatorConstants.CONTROLLABLE_SYSTEMS;

public class Operator {
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

  public Operator(int port) {
    controller = new CommandXboxController(port);
  }

  public Operator withControl(CONTROLLABLE_SYSTEMS control) {
    switch (control) {
      case CHASSIS:
        chassisControlTranslation = this::translate;

        chassisControlStrafe = () -> controller.getRawAxis(OperatorConstants.ChassisControls.STRAFE);
        chassisControlRotation = () -> controller.getRawAxis(OperatorConstants.ChassisControls.ROTATION);

        wheelsXPosition = controller.button(OperatorConstants.ChassisControls.WHEEL_X_POSITION);
        pointWheels = controller.button(OperatorConstants.ChassisControls.POINT_WHEELS);
        zeroHeading = controller.button(OperatorConstants.ChassisControls.ZERO_HEADING);
        break;

      case TESTS:
        runTest = controller.button(OperatorConstants.TestControls.RUN_TEST);
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
    return controller.getRawAxis(OperatorConstants.ChassisControls.TRANSLATION);
  }
}
// hello world
