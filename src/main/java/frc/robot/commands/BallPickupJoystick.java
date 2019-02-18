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
    Robot.m_Elevator.updatePosition();
    updateElevator();
    updateGrabber();
    updateArm();
    if (Robot.m_oi_pilot.joystick.getRawButton(9)){
      Robot.m_Elevator.resetEnc();
    }
  }

  public boolean updateElevator() {
    boolean liftUpButton = Robot.m_oi_copilot.getButtonLB();
    boolean liftDownButton = Robot.m_oi_copilot.getLeftTrigger() > 0.0;

    if(liftUpButton && !liftDownButton) {
      Robot.m_Elevator.goUp();
    }
    else if (!liftUpButton && liftDownButton)  {
      Robot.m_Elevator.goDown();
    }
    else { 
      Robot.m_Elevator.stop();
      return false;
    }  
    return true;
  }

  public boolean updateGrabber() {
    // boolean spinButton = (Robot.m_oi_copilot.getRightTrigger() < 0 ? true : false);
    // boolean reverseSpinButton = (Robot.m_oi_copilot.getLeftTrigger() < 0 ? true : false);
    boolean spinButton = Robot.m_oi_copilot.getButtonX();
    boolean reverseSpinButton = Robot.m_oi_copilot.getButtonY();

    if(spinButton && !reverseSpinButton){
      Robot.m_ballPickup.grabberOn();
    }
    else if(!spinButton && reverseSpinButton) {
      Robot.m_ballPickup.reverseGrabber();
    }
    else {
      Robot.m_ballPickup.grabberOff();
      return false;
    }
    return true;

  }

  public boolean updateArm() {
    boolean up = Robot.m_oi_copilot.getButtonB();
    boolean down = Robot.m_oi_copilot.getButtonA();   
    boolean slowMotor = Robot.m_oi_copilot.getButtonL3();

    if(slowMotor) {
      Robot.m_ballPickup.setArmSpeed(.5);
    }
    else {
      Robot.m_ballPickup.setArmSpeed(1);
    }


    if(up) {
      Robot.m_ballPickup.armUp();
      return true;
    }
    else if(down) {
      Robot.m_ballPickup.armDown();
      return true;
    }
    Robot.m_ballPickup.armStop();
    return false;
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
    end();
  }
}
