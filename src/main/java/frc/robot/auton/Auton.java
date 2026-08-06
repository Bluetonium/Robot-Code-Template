package frc.robot.auton;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.events.EventTrigger;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.utils.Elastic;
import frc.utils.Elastic.NotificationLevel;
import lombok.Getter;

public class Auton {
    private static SendableChooser<Command> autonChooser; // TODO implement pathplanner
    @Getter
    private static Command selectedAuton = null;

    // Event triggers

    public static final EventTrigger autonExample = new EventTrigger("example");

    public static void setupAutonChooser() {
        if (AutoBuilder.isConfigured()) {
            autonChooser = AutoBuilder.buildAutoChooser();
            autonChooser.setDefaultOption("Do nothing", doNothing());

            selectedAuton = autonChooser.getSelected();
            autonChooser.onChange((command) -> selectedAuton = command);
            SmartDashboard.putData("Autonomous", autonChooser);
        } else {
            Elastic.sendNotification(new Elastic.Notification(NotificationLevel.ERROR, "Auto chooser not configured",
                    "Pathplanner auto builder is not configured. Make sure robot is configured in the pathplanner gui"));
        }
    }

    public static Command doNothing() {
        return Commands.print("Do Nothing Auto ran").withName("Do Nothing");
    }
}
