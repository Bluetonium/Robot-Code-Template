package frc.robot.subsystems.vision;

public class VisionConstants {
    /**
     * This is where all your limelights should go. The name should match the name
     * configured on the limelight
     * LIMELIGHTS
     */
    public enum LIMELIGHTS {
        EXAMPLE("example", 1, 1, 1, 0, 0, 0); // TODO remove this and fill in with limelight names
        // EXAMPLE : FRONT_LIMELIGHT("front",1,0,0.5,0,0,0,0)

        public final String name;
        public final double x;
        public final double y;
        public final double z;
        public final double pitch;
        public final double yaw;
        public final double roll;

        public final boolean localization;

        private LIMELIGHTS(String name, double x, double y, double z, double pitch, double yaw, double roll,
                boolean localization) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.z = z;

            this.pitch = pitch;
            this.yaw = yaw;
            this.roll = roll;
            this.localization = localization; // has a position, can be used for localization
        }

        private LIMELIGHTS(String name, double x, double y, double z, double pitch, double yaw, double roll) {
            this(name, x, y, z, pitch, yaw, roll, true);
        }
    }

    /***
     * All the pipelines that are on the limelights. it is assumed that all
     * limelights have the same set of pipelines on them.
     * LIMELIGHT_PIPELINES
     */
    public enum LIMELIGHT_PIPELINES {
        LOCALIZATION(0);

        public final int pipeline;

        private LIMELIGHT_PIPELINES(int pipeline) {
            this.pipeline = pipeline;
        }
    }

    private VisionConstants() {
    } // hide constructor
}
