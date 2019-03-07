/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class BallInhalerJoystick extends Command {
  private boolean spinButton;
  private boolean reverseSpinButton;

  public BallInhalerJoystick() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.m_ballInhaler);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    spinButton = Robot.m_oi_copilot.getButtonX();
    reverseSpinButton = Robot.m_oi_copilot.getButtonY();

    if (spinButton && !reverseSpinButton) {
      Robot.m_ballInhaler.grabberOn();
    } else if (!spinButton && reverseSpinButton) {
      Robot.m_ballInhaler.reverseGrabber();
    } else {
      Robot.m_ballInhaler.grabberOff();
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
    Robot.m_ballInhaler.grabberOff();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
