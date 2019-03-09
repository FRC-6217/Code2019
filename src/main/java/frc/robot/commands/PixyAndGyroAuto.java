/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class PixyAndGyroAuto extends Command {
  private double goToAngle;
  private boolean gyro;
  private boolean pixy;
  private final double PIXY_SETPOINT = 65.0;
  // private final double GYRO_SETPOINT = 0.0;
  private double smallest;

  public PixyAndGyroAuto(double goToAngle, boolean gyro, boolean pixy, boolean closeAngle) {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.m_driveTrain);
    if(closeAngle){
      smallest = Math.abs(Robot.m_driveTrain.GetAngle() - 0);
      goToAngle = 0;
      if (smallest > Math.abs(Robot.m_driveTrain.GetAngle() - 61.25)) {
        smallest = Math.abs(Robot.m_driveTrain.GetAngle() - 61.25);
        goToAngle = 61.25;
      }
      if (smallest > Math.abs(Robot.m_driveTrain.GetAngle() - 90)) {
        smallest = Math.abs(Robot.m_driveTrain.GetAngle() - 90);
        goToAngle = 90;
      }
      if (smallest > Math.abs(Robot.m_driveTrain.GetAngle() - 151.25)) {
        smallest = Math.abs(Robot.m_driveTrain.GetAngle() - 151.25);
        goToAngle = 151.25;
      }
      if (smallest > Math.abs(Robot.m_driveTrain.GetAngle() - 180)) {
        smallest = Math.abs(Robot.m_driveTrain.GetAngle() - 180);
        goToAngle = 180;
      }
      if (smallest > Math.abs(Robot.m_driveTrain.GetAngle() - 241.25)) {
        smallest = Math.abs(Robot.m_driveTrain.GetAngle() - 241.25);
        goToAngle = 241.25;
      }
      if (smallest > Math.abs(Robot.m_driveTrain.GetAngle() - 270)) {
        smallest = Math.abs(Robot.m_driveTrain.GetAngle() - 270);
        goToAngle = 270;
      }
      if (smallest > Math.abs(Robot.m_driveTrain.GetAngle() - 331.25)) {
        smallest = Math.abs(Robot.m_driveTrain.GetAngle() - 331.25);
        goToAngle = 331.25;
      }
      if (smallest > Math.abs(Robot.m_driveTrain.GetAngle() - 360)) {
        smallest = Math.abs(Robot.m_driveTrain.GetAngle() - 360);
        goToAngle = 360;
      }
    }
    else{
      this.goToAngle = goToAngle;
    }
    this.gyro = gyro;
    this.pixy = pixy;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (pixy && gyro) {
      Robot.m_driveTrain.AlignPixyAndGyro(PIXY_SETPOINT, goToAngle);
    }
    else if (gyro && !pixy) {
      Robot.m_driveTrain.AlignGyroOnly(goToAngle);
    }
    else if (pixy && !gyro){
      Robot.m_driveTrain.AlignPixyOnly(PIXY_SETPOINT);
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
