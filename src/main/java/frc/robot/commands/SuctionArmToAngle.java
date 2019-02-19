/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.libraries.PID;
import frc.robot.subsystems.GrabberArm;

public class SuctionArmToAngle extends Command {
  private double angle;
  private GrabberArm suctionArm;
  private PID pid;
  private double p = .1, i = 0, d = 0;
  private double deadband = 1;
  
  public SuctionArmToAngle(double angle) {
    requires(Robot.m_grabberArm);
    this.angle = angle;
    suctionArm = Robot.m_grabberArm;
    pid = new PID(p, i, d);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }
  
  public void setAngle(double angle){
    pid.setSetpoint(angle);
  }
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    setAngle(angle);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Robot.m_oi_pilot.joystick.getRawButton(6)){
      suctionArm.resetEncoder();
    }
    //Robot.m_grabberArm.GetAngle();

    // pid.p = SmartDashboard.getNumber("pkey SuctionArm", 0);
    // pid.i = SmartDashboard.getNumber("ikey SuctionArm", 0);
    // pid.d = SmartDashboard.getNumber("dkey SuctionArm", 0);

    suctionArm.Move(pid.update(suctionArm.GetAngle()));
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;//((suctionArm.GetAngle() < (angle + deadband)) && (suctionArm.GetAngle() > (angle - deadband)));
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    suctionArm.Stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    setAngle(suctionArm.GetWantedAngle());
  }
}
