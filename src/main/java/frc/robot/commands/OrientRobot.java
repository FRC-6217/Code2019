/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class OrientRobot extends Command {
  private double angle;
  public OrientRobot(double angle) {
    this.angle = angle;
    requires(Robot.m_driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(angle > Robot.m_driveTrain.GetAngle()){
      while(Robot.m_driveTrain.GetAngle() <= angle){
        Robot.m_driveTrain.Drive(0, 0, 1, 0.5);
        end();
      }
    }
    else if(angle < Robot.m_driveTrain.GetAngle()){
      while (Robot.m_driveTrain.GetAngle() >= angle) {
        Robot.m_driveTrain.Drive(0, 0, 1, -0.5);
        end();
      }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.m_driveTrain.Drive(0, 0, 0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
