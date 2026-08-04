package frc.Bluetonium;

import java.util.HashSet;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import lombok.Getter;

public abstract class BluetoniumSubsystemBase extends SubsystemBase implements IBluetoniumSubsystem {
    @Getter
    protected static final HashSet<IBluetoniumSubsystem> subsystems = new HashSet<>();

    public static void registerBluetoniumSubsystem(final IBluetoniumSubsystem subsystem) {
        subsystems.add(subsystem);
    }

    public BluetoniumSubsystemBase() {
        super();
        subsystems.add(this);
    }

    public BluetoniumSubsystemBase(final String name) {
        super(name);
        subsystems.add(this);
    }
}
