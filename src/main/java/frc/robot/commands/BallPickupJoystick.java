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
  private boolean liftUpButton;
  private boolean liftDownButton;
  private boolean liftAmountButtonUp;
  private boolean liftAmountButtonDown;
  private double liftAmount = 0.1;
  private static final double liftSpeedDelta = 0.1;

  public BallPickupJoystick() {
    requires(Robot.m_ballPickup);
    requires(Robot.m_Elevator);
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
    spinButton = Robot.m_oi.joystick.getRawButton(7);
    liftUpButton = Robot.m_oi.joystick.getRawButton(6);
    liftDownButton = Robot.m_oi.joystick.getRawButton(4);
    liftAmountButtonUp = Robot.m_oi.joystick.getRawButton(5);
    liftAmountButtonDown = Robot.m_oi.joystick.getRawButton(3);

    if ((!liftAmountButtonUp && !liftAmountButtonDown) || (liftAmountButtonUp && liftAmountButtonDown)) {
    
    }
    else if(liftAmountButtonUp && liftAmount < 1) {
      Robot.m_Elevator.increaseSpeedBy(liftSpeedDelta);
      liftAmount += liftSpeedDelta;
      System.out.println("Speed is set up to: " + liftAmount);
    }
    else if(liftAmountButtonDown && liftAmount > -1) {
      Robot.m_Elevator.decreaseSpeedBy(liftSpeedDelta);
      liftAmount -= liftSpeedDelta;
      System.out.println("Speed is set to: " + liftAmount);
    }


    if((!liftUpButton && !liftDownButton) || (liftUpButton && liftDownButton)) {
      Robot.m_Elevator.stop();
    }
    if(liftUpButton && !liftDownButton) {
      Robot.m_Elevator.goUp();
    }
    if(!liftUpButton && liftDownButton) {
      Robot.m_Elevator.goDown();
    }

    if((!upButton && !downButton) || (upButton && downButton)){
      Robot.m_ballPickup.stopArm();
    }
    if(upButton){
      Robot.m_ballPickup.armUp();
      System.out.println("going up");
      //isFinished();
    }
    if(downButton){
      Robot.m_ballPickup.armDown();
      System.out.println("going down");
      //isFinished();
    }

    if(spinButton){
      Robot.m_ballPickup.grabberOn();
    }
    else{
      Robot.m_ballPickup.grabberOff();
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
