package frc.robot.commands;

import frc.robot.libraries.Pathfinder_Follow;
import frc.robot.subsystems.DriveTrain;
import jaci.pathfinder.Waypoint;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class AutoWithPathfinder extends Command {

  private String PathName;
  private Pathfinder_Follow follow;
  private double[] speed;
  private double[] angle;

  public AutoWithPathfinder(String PathName) {
    this.PathName = PathName;
    // Use requires() here to declare subsystem dependencies
    requires(Robot.m_driveTrain);
  }

  // Called just before this Command runs the first time
  protected void initialize() {
    follow = new Pathfinder_Follow(PathName, Robot.m_driveTrain.returnGyro());

    // Called repeatedly when this Command is scheduled to run
  }

  protected void execute() {
    follow.setupFollow();
    speed = follow.returnOutput();
    angle = follow.returnHeading();

    SmartDashboard.putNumberArray("Pathfinder Angle", angle);
    SmartDashboard.putNumberArray("Pathfinder Speed", speed);
    Robot.m_driveTrain.UseFL(speed[0], angle[0]);
    Robot.m_driveTrain.UseFR(speed[1], angle[1]);
    Robot.m_driveTrain.UseBL(speed[2], angle[2]);
    Robot.m_driveTrain.UseBR(speed[3], angle[3]);
  }

  // Make this return true when this Command no longer needs to run execute()
  protected boolean isFinished() {
    return speed[0] == 0;
  }

  // Called once after isFinished returns true
  protected void end() {
    isFinished();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  protected void interrupted() {
  }
}