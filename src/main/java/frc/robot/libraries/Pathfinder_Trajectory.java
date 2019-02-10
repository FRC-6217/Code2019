package frc.robot.libraries;
import jaci.pathfinder.Pathfinder;
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
    
    public Pathfinder_Trajectory (Waypoint[] args){
        StartPath(args);
    }

    public void StartPath(Waypoint[] args) {
        config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
                Trajectory.Config.SAMPLES_HIGH, 0.05, 1.7, 2.0, 60.0);
        Waypoint[] points = args;

        trajectory = Pathfinder.generate(points, config);

        // Wheelbase Width = 0.5m, Wheelbase Depth = 0.6m, Swerve Mode = Default
        SwerveModifier modifier = new SwerveModifier(trajectory).modify(width, length, SwerveModifier.Mode.SWERVE_DEFAULT);

        // Do something with the new Trajectories...
        Wheels[0] = modifier.getFrontLeftTrajectory();
        Wheels[1] = modifier.getFrontRightTrajectory();
        Wheels[2] = modifier.getBackLeftTrajectory();
        Wheels[3] = modifier.getBackRightTrajectory();
    }

    public Trajectory[] GetTraj(){
        return Wheels;
    }

}