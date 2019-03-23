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
  private boolean closeAngle;
  private final double PIXY_SETPOINT = 65.0;
  // private final double GYRO_SETPOINT = 0.0;
  private double smallest;
  private boolean isDone = true;

  public PixyAndGyroAuto(double goToAngle, boolean gyro, boolean pixy, boolean closeAngle) {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.m_driveTrain);

    this.goToAngle = goToAngle;
    this.gyro = gyro;
    this.pixy = pixy;
    this.closeAngle = closeAngle;


    if(closeAngle){
      smallest = Math.abs(Robot.m_driveTrain.GetAngleX() - 61.25);
      this.goToAngle = 61.25;
      if (smallest > Math.abs(Robot.m_driveTrain.GetAngleX() - 151.25)) {
        smallest = Math.abs(Robot.m_driveTrain.GetAngleX() - 151.25);
        this.goToAngle = 151.25;
      }
      if (smallest > Math.abs(Robot.m_driveTrain.GetAngleX() - 241.25)) {
        smallest = Math.abs(Robot.m_driveTrain.GetAngleX() - 241.25);
        this.goToAngle = 241.25;
      }
      if (smallest > Math.abs(Robot.m_driveTrain.GetAngleX() - 331.25)) {
        smallest = Math.abs(Robot.m_driveTrain.GetAngleX() - 331.25);
        this.goToAngle = 331.25;
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
    if (closeAngle) {
      smallest = Math.abs(Robot.m_driveTrain.GetAngleX() - 61.25);
      this.goToAngle = 61.25;
      if (smallest > Math.abs(Robot.m_driveTrain.GetAngleX() - 151.25)) {
        smallest = Math.abs(Robot.m_driveTrain.GetAngleX() - 151.25);
        this.goToAngle = 151.25;
      }
      if (smallest > Math.abs(Robot.m_driveTrain.GetAngleX() - 241.25)) {
        smallest = Math.abs(Robot.m_driveTrain.GetAngleX() - 241.25);
        this.goToAngle = 241.25;
      }
      if (smallest > Math.abs(Robot.m_driveTrain.GetAngleX() - 331.25)) {
        smallest = Math.abs(Robot.m_driveTrain.GetAngleX() - 331.25);
        this.goToAngle = 331.25;
      }
    }
    isDone = true;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (isDone) {
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
  }
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
      return (Math.abs(Robot.m_driveTrain.GetAngleX()-goToAngle) < 4);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    isDone = false;
    Robot.m_driveTrain.Drive(0, 0, 0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
