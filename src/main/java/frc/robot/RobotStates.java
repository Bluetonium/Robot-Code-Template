package frc.robot;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import lombok.Setter;

public class RobotStates {
  
    public RobotStates() {
        setupStates();
    }

    // states
    public static Trigger teleop;
    public static Trigger autoMode;
    public static Trigger testMode;
    public static Trigger disabled;
    public static Trigger dsAttached;
    public static Trigger endGame;
    public static Trigger Estopped;
   
    // chassis
    public static Trigger brake;
    public static Trigger resetHeading;
    public static Trigger slowMode;





    public static void setupStates() {
        teleop = new Trigger(DriverStation::isTeleopEnabled);
        autoMode = new Trigger(RobotState::isAutonomous);
        testMode = new Trigger(RobotState::isTest);
        disabled = new Trigger(RobotState::isDisabled);
        dsAttached = new Trigger(DriverStation::isDSAttached);
        Estopped = new Trigger(DriverStation::isEStopped);

        endGame = teleop.and(() -> DriverStation.getMatchTime() < 20);


        // PID Stuff
        sysDyn = new Trigger(RobotContainer.shootController.x());
        sysSta = new Trigger(RobotContainer.shootController.y());
        sysDynRev = new Trigger(RobotContainer.shootController.povRight());
        sysStaRev = new Trigger(RobotContainer.shootController.b());

        // chassis
        brake = RobotContainer.chassisController.leftBumper();
        resetHeading = RobotContainer.chassisController.a();
        slowMode = RobotContainer.chassisController.leftTrigger();
        autoAim = RobotContainer.chassisController.rightTrigger();

    }
}
