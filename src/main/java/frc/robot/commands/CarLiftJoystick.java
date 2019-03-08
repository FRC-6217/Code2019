/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class CarLiftJoystick extends Command {
  private boolean up = false;
  private boolean down = true;

  public CarLiftJoystick() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.m_carLift);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    up = Robot.m_oi_copilot.getButtonY();
    down = Robot.m_oi_copilot.getButtonX();

    if (up && !down) {
      Robot.m_carLift.up();
    }
    else if (!up && down) {
      Robot.m_carLift.down();
    }
    else{
      Robot.m_carLift.stop();
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
    Robot.m_carLift.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
