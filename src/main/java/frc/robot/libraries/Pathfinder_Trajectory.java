package frc.robot.libraries;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.SwerveModifier;

public class Pathfinder_Trajectory {

    private Trajectory.Config config;
    private Trajectory trajectory;
    private double length = SwerveDriveClass.L * 0.0254;
    private double width = SwerveDriveClass.W * 0.0254;
    private SwerveModifier modifier;

    //Format of wheels is {front left, front right, back left, back right}
    private Trajectory[] Wheels = new Trajectory[4];
    
    public Pathfinder_Trajectory(String PathName) {
        StartPath(PathName);
    }
    public void StartPath(String PathName) {
        try{
        trajectory = PathfinderFRC.getTrajectory(PathName);

        // Wheelbase Width = 0.5m, Wheelbase Depth = 0.6m, Swerve Mode = Default
        SwerveModifier modifier = new SwerveModifier(trajectory).modify(width, length,
                SwerveModifier.Mode.SWERVE_DEFAULT);

        // Do something with the new Trajectories...
        Wheels[0] = modifier.getFrontLeftTrajectory();
        Wheels[1] = modifier.getFrontRightTrajectory();
        Wheels[2] = modifier.getBackLeftTrajectory();
        Wheels[3] = modifier.getBackRightTrajectory();
        
        }
        catch(Exception e){
            SmartDashboard.putString("Error In pathfinder", e.getMessage() + " (Mostly likely wrong file name)");

        }
    }

    public Trajectory[] GetTraj(){
        return Wheels;
    }

}