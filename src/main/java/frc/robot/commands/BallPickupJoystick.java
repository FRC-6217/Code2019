/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class BallPickupJoystick extends Command {
  private boolean upButton;
  private boolean downButton;
  private boolean spinButton;

  public BallPickupJoystick() {
    requires(Robot.m_ballPickup);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    upButton = Robot.m_oi.joystick.getRawButton(12);
    downButton = Robot.m_oi.joystick.getRawButton(11);
    spinButton = Robot.m_oi.joystick.getRawButton(5);

    if(!upButton && !downButton){
      Robot.m_ballPickup.pickupDrive(0);
    }
    if(upButton){
      Robot.m_ballPickup.pickupDrive(1);
      //isFinished();
    }
    if(downButton){
      Robot.m_ballPickup.pickupDrive(-1);
      //isFinished();
    }

    if(spinButton){
      Robot.m_ballPickup.wheelDrive(.5);
    }else{
      Robot.m_ballPickup.wheelDrive(0);
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
    isFinished();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
