package frc.robot.subsystems.drivers;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.drivers.DriverConstants.CONTROLLABLE_SYSTEMS;
import java.util.function.DoubleSupplier;

public class Drivers {
  // Control axis
  // chassis
  public static DoubleSupplier chassisControlTranslation;
  public static DoubleSupplier chassisControlStrafe;
  public static DoubleSupplier chassisControlRotation;

  // Triggers
  // Chassis
  public static Trigger wheelsXPosition;
  public static Trigger steerWheels;
  public static Trigger zeroHeading;

  // Instance variables
  private final CommandXboxController controller;

  private double translate() {
    return controller.getRawAxis(DriverConstants.ChassisControls.TRANSLATION);
  }

  public Drivers withControl(CONTROLLABLE_SYSTEMS control) {
    switch (control) {
      case CHASSIS:

        // dpad micro adjustments

        chassisControlTranslation = this::translate;

        chassisControlStrafe = () -> controller.getRawAxis(DriverConstants.ChassisControls.STRAFE);
        chassisControlRotation =
            () -> controller.getRawAxis(DriverConstants.ChassisControls.ROTATION);

        wheelsXPosition = controller.button(DriverConstants.ChassisControls.WHEEL_X_POSITION);
        steerWheels = controller.button(DriverConstants.ChassisControls.POINT_WHEELS);
        zeroHeading = controller.button(DriverConstants.ChassisControls.ZERO_HEADING);
        break;

      default:
        throw new RuntimeException("No setup configured for control " + control.name());
    }

    return this;
  }

  public boolean isDisconnected() {
    return !controller.isConnected();
  }

  public Drivers(int port) {
    controller = new CommandXboxController(port);
  }
}
// hello world
