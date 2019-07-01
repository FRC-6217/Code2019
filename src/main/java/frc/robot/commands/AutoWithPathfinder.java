package frc.robot.commands;

import frc.robot.libraries.Pathfinder_Follow;
import frc.robot.libraries.SwerveDriveClass;
import frc.robot.libraries.SwerveDriveClass.POS;
import frc.robot.subsystems.DriveTrain;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.SwerveModifier;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class AutoWithPathfinder extends Command {

  private String PathName;
  private Pathfinder_Follow follow;
  private Trajectory trajectory;
  private double length = SwerveDriveClass.L * 0.0254;
  private double width = SwerveDriveClass.W * 0.0254;
  private Trajectory[] Wheels = new Trajectory[4];
  private EncoderFollower[] WheelFollower = new EncoderFollower[4];
  private double[] output = new double[4];
  private double[] desiredHeading = new double[4];
  private Encoder[] enc = { new Encoder(12, 13), new Encoder(20, 21), new Encoder(19, 18), new Encoder(11, 10) };

  private double wheel_diameter = 4 * 0.0254;
  private int pulsePerRev = 120;// ratio * pulse per revoletion
  private double max_velocity = .1;
  private Gyro gyro;
  private int[] encoder;

  public AutoWithPathfinder(String PathName) {
    this.PathName = PathName;
    // Use requires() here to declare subsystem dependencies
    requires(Robot.m_driveTrain);
  }

  // Called just before this Command runs the first time
  protected void initialize() {
    try {
    trajectory = PathfinderFRC.getTrajectory(PathName);
    } catch (Exception e) {
      SmartDashboard.putString("Error In pathfinder", e.getMessage() + " (Mostly likely wrong file name)");

    }
    // Wheelbase Width = 0.5m, Wheelbase Depth = 0.6m, Swerve Mode = Default
    SwerveModifier modifier = new SwerveModifier(trajectory).modify(width, length, SwerveModifier.Mode.SWERVE_DEFAULT);

    // Do something with the new Trajectories...
    Wheels[0] = modifier.getFrontLeftTrajectory();//
    Wheels[1] = modifier.getFrontRightTrajectory();
    Wheels[2] = modifier.getBackLeftTrajectory();
    Wheels[3] = modifier.getBackRightTrajectory();//
    // follow = new Pathfinder_Follow(PathName, Robot.m_driveTrain.returnGyro());

    // Called repeatedly when this Command is scheduled to run
  }

  protected void execute() {
    for (int i = 0; i < 4; i++) {
      WheelFollower[i] = new EncoderFollower(Wheels[i]);
      WheelFollower[i].configureEncoder((int) (enc[i].get()), pulsePerRev, wheel_diameter);
      WheelFollower[i].configurePIDVA(1.0, 0.0, 0.0, 1 / max_velocity, 0);
      if((i==2) || (i == 3)){
        output[i] = WheelFollower[i].calculate((int) (-1 * (enc[i].get())));
        SmartDashboard.putNumber(Integer.toString(i), -enc[i].get());
      }
      else{
        output[i] = WheelFollower[i].calculate((int) ((enc[i].get())));
        SmartDashboard.putNumber(Integer.toString(i), enc[i].get());
      }
      desiredHeading[i] = Pathfinder.boundHalfDegrees(Pathfinder.r2d(WheelFollower[i].getHeading()) - Robot.m_driveTrain.GetAngle());
    }
    SmartDashboard.putNumber(key, value)
    Robot.m_driveTrain.UseFL(output[0], desiredHeading[0]);
    Robot.m_driveTrain.UseFR(output[1], desiredHeading[1]);
    Robot.m_driveTrain.UseBL(output[2], desiredHeading[2]);
    Robot.m_driveTrain.UseBR(output[3], desiredHeading[3]);
  }

  // Make this return true when this Command no longer needs to run execute()
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  protected void end() {
    
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  protected void interrupted() {
  }
}