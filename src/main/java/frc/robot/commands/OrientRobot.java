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

public class OrientRobot extends Command {
  private double angle;
  private PID pid;
  private double p = .3, i = 0, d = 0;
  private double deadband = 7;

  public OrientRobot(double angle) {
    this.angle = angle;
    requires(Robot.m_driveTrain);
    pid = new PID(p, i, d);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    pid.setSetpoint(angle);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // pid.p = SmartDashboard.getNumber("pkey Drive", 0);
    // pid.i = SmartDashboard.getNumber("ikey Drive", 0);
    // pid.d = SmartDashboard.getNumber("dkey Drive", 0);
    SmartDashboard.putNumber("Gyro1", Robot.m_driveTrain.GetAngle());
    Robot.m_driveTrain.Drive(0, 0, -1, pid.update(Robot.m_driveTrain.GetAngle()));
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return (Robot.m_driveTrain.GetAngle() < (angle + deadband)) && (Robot.m_driveTrain.GetAngle() > (angle - deadband));
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.m_driveTrain.Drive(0, 0, 0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
