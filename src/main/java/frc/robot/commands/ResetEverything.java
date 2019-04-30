/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ResetEverything extends Command {
  // public ResetEverything() {
  //   requires(Robot.m_VacuumArm);
  //   requires(Robot.m_driveTrain);
  //   requires(Robot.m_lift);
  //   setTimeout(.01);
  //   // Use requires() here to declare subsystem dependencies
  //   // eg. requires(chassis);
  // }

  // // Called just before this Command runs the first time
  // @Override
  // protected void initialize() {
  //   Robot.m_driveTrain.ResetGyro();
  //   Robot.m_driveTrain.ResetGyroX();
  //   Robot.m_lift.resetEnc();
  //   Robot.m_VacuumArm.resetEnc();
  // }

  // // Called repeatedly when this Command is scheduled to run
  // @Override
  // protected void execute() {
  //   end();
  // }

  // // Make this return true when this Command no longer needs to run execute()
  // @Override
  // protected boolean isFinished() {
  //   return isTimedOut();
  // }

  // // Called once after isFinished returns true
  // @Override
  // protected void end() {
  // }

  // // Called when another command which requires one or more of the same
  // // subsystems is scheduled to run
  // @Override
  // protected void interrupted() {
  //   end();
  // }


  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // Robot.m_VacuumArm.setToAngle(goToAngle);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;// (Math.abs(Robot.m_VacuumArm.returnArmAngle() - goToAngle) < 1.5) ||
                // (isTimedOut());
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
