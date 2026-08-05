package frc.robot.subsystems.operator;

import edu.wpi.first.wpilibj.XboxController;

public class OperatorConstants {
  public static enum CONTROLLABLE_SYSTEMS {
    CHASSIS,
    TESTS
  }

  public static class ChassisControls {
    public static int TRANSLATION = XboxController.Axis.kLeftY.value;

    public static int STRAFE = XboxController.Axis.kLeftX.value;
    public static int ROTATION = XboxController.Axis.kRightX.value;
    public static int WHEEL_X_POSITION = XboxController.Button.kX.value;

    public static int POINT_WHEELS = XboxController.Button.kA.value;
    public static int ZERO_HEADING = XboxController.Button.kB.value;
    public static double TRANSLATION_DEADBAND = 0.1; // 10% deadband

    public static double ROTATION_DEADBAND = 0.1; // 10% deadband

    private ChassisControls() {
    } // Hide constructor
  }

  public static class TestControls {
    public static int RUN_TEST = XboxController.Button.kA.value;

    private TestControls() {
    }// Hide constructor
  }

  private OperatorConstants() {
  } // Hide constructor
}
