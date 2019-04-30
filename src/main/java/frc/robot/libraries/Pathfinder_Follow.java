package frc.robot.libraries;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

public class Pathfinder_Follow {
    // All have the format of { front left, front right, back left, back right}
    private EncoderFollower[] WheelFollower = new EncoderFollower[4];
    private double[] output = new double[4];
    private double[] desiredHeading = new double[4];
    private Trajectory[] WheelTraj;
    private Encoder[] enc = { new Encoder(12, 13), new Encoder(10, 11), new Encoder(18, 19), new Encoder(20, 21) };

    private double wheel_diameter = 4 * 0.0254;
    private int pulsePerRev = 120;// ratio * pulse per revoletion
    private double max_velocity;
    private Pathfinder_Trajectory traj;
    private Gyro gyro;

    public Pathfinder_Follow(String PathName, Gyro gyro) {
        traj = new Pathfinder_Trajectory(PathName);
        WheelTraj = traj.GetTraj();
        this.gyro = gyro;
    }

    public void setupFollow() {
        for (int i = 0; i < 4; i++) {
            WheelFollower[i] = new EncoderFollower(WheelTraj[i]);
            WheelFollower[i].configureEncoder((int) (enc[i].get()), pulsePerRev, wheel_diameter);
            WheelFollower[i].configurePIDVA(1.0, 0.0, 0.0, 1 / max_velocity, 0);

            output[i] = WheelFollower[i].calculate((int) (enc[i].get()));
            desiredHeading[i] = Pathfinder.boundHalfDegrees(Pathfinder.r2d(WheelFollower[i].getHeading()) - gyro.getAngle());
        }

    }

    public double[] returnOutput() {
        return output;
    }

    public double[] returnHeading() {
        return desiredHeading;
    }

}