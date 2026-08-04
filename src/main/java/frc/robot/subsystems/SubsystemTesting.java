package frc.robot.subsystems;

import java.util.HashSet;
import java.util.stream.Collectors;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.drivers.Drivers;

public class SubsystemTesting {
    private static SendableChooser<Command> testSelector = new SendableChooser<>();
    private static HashSet<String> registeredTests = new HashSet<>();

    public static void setupTests() {
        SmartDashboard.putData("Test Chooser", testSelector);

        Drivers.runTest.whileTrue(Commands.deferredProxy(() -> {
            return testSelector.getSelected();
        }));
    }

    /**
     * Registers a test with the given name and command
     * 
     * @param testCommand the command to run for the test
     * @param name the name of the command
     */
    public static void registerTest(Command testCommand, String name) {
        if (registeredTests.contains(name)) {
            DriverStation.reportWarning(String.format("TEST %s is already registered!", name), true);
            return;
        }

        registeredTests.add(name);
        testSelector.addOption(name, testCommand);
    }

    /**
     * Registers a command as a test. Automatically constructors the test name
     * from its requirements and commands name; Example: A command named "auto
     * align" using drivetrain and vision would be [drivetrain,vision].auto
     * align
     * 
     * @param testCommand The command to run for the test
     */
    public static void registerTest(Command testCommand) {
        String requirements = testCommand.getRequirements().stream().map((s) -> s.getName()).sorted()
        .collect(Collectors.joining(","));

        String commandName = testCommand.getName();

        registerTest(testCommand, String.format("[%s].%s", requirements, commandName));
    }
}
