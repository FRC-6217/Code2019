package frc.robot.libraries;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;

public class Pathfinder_Follow{
    // All have the format of { front left, front right, back left, back right}
    private EncoderFollower[] WheelFollower = new EncoderFollower[4];
    private double[] output = new double[4];
    private double[] desiredHeading = new double[4];
    private Trajectory[] WheelTraj;
    private Encoder[] enc = {new Encoder(9,8), new Encoder(7,6), new Encoder(5,4), new Encoder(3,2)};
   
    private double wheel_diameter = 4 * 0.0254;
    private int pulsePerRev = 120;//ratio * pulse per revoletion
    private double max_velocity;
    private Pathfinder_Trajectory traj;

    public Pathfinder_Follow(Waypoint[] args){
        traj = new Pathfinder_Trajectory(args);
        WheelTraj = traj.GetTraj();
    }

    public void setupFollow(Waypoint[] args){
        for(int i = 0; i < 3; i++){
            WheelFollower[i] = new EncoderFollower(WheelTraj[i]);
            WheelFollower[i].configureEncoder((int)(enc[i].get()), pulsePerRev, wheel_diameter);
            WheelFollower[i].configurePIDVA(1.0, 0.0, 0.0, 1 / max_velocity, 0);
            
            output[i] = WheelFollower[i].calculate((int)(enc[i].get()));
            desiredHeading[i] = Pathfinder.boundHalfDegrees(Pathfinder.r2d(WheelFollower[i].getHeading())); // Bound to -180..180 degrees
        }

    }

    public double[] returnOutput() {
        return output;
    }

    public double[] returnHeading() {
        return desiredHeading;
    }

}