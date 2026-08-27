package frc.Bluetonium;

import java.util.HashSet;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.utils.Elastic;
import frc.utils.Elastic.Notification;
import frc.utils.Elastic.NotificationLevel;
import lombok.Getter;

public abstract class BluetoniumSubsystemBase extends SubsystemBase implements IBluetoniumSubsystem {
    @Getter
    protected static final HashSet<IBluetoniumSubsystem> m_subsystems = new HashSet<>();

    public static void registerBluetoniumSubsystem(final IBluetoniumSubsystem subsystem) {
        m_subsystems.add(subsystem);
    }

    public BluetoniumSubsystemBase() {
        super();
        m_subsystems.add(this);
    }

    public BluetoniumSubsystemBase(final String name) {
        super(name);
        m_subsystems.add(this);
    }

    public void applyConfig(TalonFX motor, TalonFXConfiguration config) {
        StatusCode status = motor.getConfigurator().apply(config);
        if (!status.isOK()) {

            String message = String.format("Failed to apply configs : %s", status.getDescription());
            DriverStation.reportWarning(message, false);
            Elastic.sendNotification(new Notification(NotificationLevel.WARNING, "Failed to configure motor", message));
        }
    }
}
