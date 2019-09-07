/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class BallGobberJoystick extends Command {
  private boolean up = false;
  private boolean down = false;
  
  public BallGobberJoystick() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.m_ballGobbler);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    up = Robot.m_oi_aliginer.getButtonB();
    down = Robot.m_oi_aliginer.getButtonA();
    
    if (up) {
      Robot.m_ballGobbler.armUp();
    } else if (down) {
      Robot.m_ballGobbler.armDown();
    }
    else{
    Robot.m_ballGobbler.armStop();
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
    Robot.m_ballGobbler.armStop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}