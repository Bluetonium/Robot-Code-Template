package frc.robot.subsystems.vision;

import frc.robot.RobotContainer;

public class VisionStates {
    public static void setStates() {
        Vision vision = RobotContainer.getM_vision();

        vision.setDefaultCommand(vision.SetAllLocalization());
    }
}
