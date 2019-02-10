package frc.robot.libraries;

import edu.wpi.first.wpilibj.AnalogInput;
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
    private AnalogInput[] enc = {new AnalogInput(), new AnalogInput(), new AnalogInput(), new AnalogInput()};
   
    private double wheel_diameter = 4 * 0.0254;
    private int pulsePerRev = 120;//ratio * pulse per revoletion
    private double max_velocity;
    private Pathfinder_Trajectory traj;

    public Pathfinder_Follow(Waypoint[] args){
        setupFollow(args);
    }

    public void setupFollow(Waypoint[] args){
        traj = new Pathfinder_Trajectory(args);
        WheelTraj = traj.GetTraj();
        for(int i = 0; i < 3; i++){
            WheelFollower[i] = new EncoderFollower(WheelTraj[i]);
            WheelFollower[i].configureEncoder((int)(enc[i].getVoltage()), pulsePerRev, wheel_diameter);
            WheelFollower[i].configurePIDVA(1.0, 0.0, 0.0, 1 / max_velocity, 0);
            
            output[i] = WheelFollower[i].calculate((int)(enc[i].getVoltage()));
            desiredHeading[i] = Pathfinder.boundHalfDegrees(Pathfinder.r2d(WheelFollower[i].getHeading())); // Bound to -180..180 degrees
lCV 
        }

    }

    public double[] returnOutput() {
        return output;
    }

    public double[] returnHeading() {
        return desiredHeading;
    }

}