/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class VacuumArmJoystick extends Command {
  private boolean Up = false;
  private boolean Down = false;

  public VacuumArmJoystick() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.m_VacuumArm);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Up = Robot.m_oi_copilot.getButtonRB();
    Down = Robot.m_oi_copilot.getRightTrigger() > 0.0;

    if ((Up) && (!Down)) {
      Robot.m_VacuumArm.up();
    } else if ((Down) && (!Up)) {
      Robot.m_VacuumArm.down();
    } else {
      Robot.m_VacuumArm.stop();
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
