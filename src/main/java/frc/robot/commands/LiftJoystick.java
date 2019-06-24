/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class LiftJoystick extends Command {
  private boolean liftUpButton = false;
  private boolean liftDownButton = false;

  public LiftJoystick() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.m_lift);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    liftUpButton = Robot.m_oi_copilot.getButtonLB();
    liftDownButton = Robot.m_oi_copilot.getButtonRB();


    if (liftUpButton && !liftDownButton) {
      Robot.m_lift.up();
    } else if (!liftUpButton && liftDownButton) {
      Robot.m_lift.down();
    } else {
      Robot.m_lift.stop();
    }
    Robot.m_lift.returnLiftHeight();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.m_lift.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
