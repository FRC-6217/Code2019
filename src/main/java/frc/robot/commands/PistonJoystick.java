/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.libraries.XboxController.DPAD_STATE;

public class PistonJoystick extends Command {
  boolean down = false;
  boolean up = false;

  public PistonJoystick() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.m_Pistons);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    up = Robot.m_oi_copilot.getDPAD().compareTo(DPAD_STATE.UP) == 1;
    down = Robot.m_oi_copilot.getDPAD().compareTo(DPAD_STATE.DOWN) == 1;

    if (!up && down) {
      Robot.m_Pistons.run(true);
    }
    else if (!down && up) {
      Robot.m_Pistons.run(false);
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
