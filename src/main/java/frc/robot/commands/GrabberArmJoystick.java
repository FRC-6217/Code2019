/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class GrabberArmJoystick extends Command {
  
  public GrabberArmJoystick() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.m_grabberArm);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.m_grabberArm.Stop();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    boolean Up = Robot.m_oi_pilot.joystick.getRawButton(3);
    boolean Down = Robot.m_oi_pilot.joystick.getRawButton(4);

    if((Up) && (! Down)) {
      Robot.m_grabberArm.Up(.5);
    }
    else if((Down) && (! Up)) {
      Robot.m_grabberArm.Down(.5);
    }
    else {
      Robot.m_grabberArm.Stop();
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
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}