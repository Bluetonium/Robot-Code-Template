package frc.robot;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

public class RobotSim {
    public static final double m_height = 120;
    public static final double m_width = 60;
    public static final Mechanism2d m_rightView = new Mechanism2d(Units.inchesToMeters(m_width) * 2,
    Units.inchesToMeters(m_height));

    public RobotSim() {

        SmartDashboard.putData("RightView", m_rightView);
        m_rightView.setBackgroundColor(new Color8Bit(Color.kLightGray));
    }
}
