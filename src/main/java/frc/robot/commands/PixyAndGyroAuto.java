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
  private int goToAngle;
  private boolean gyro;
  private boolean pixy;
  private final double PIXY_SETPOINT = 0.0;
  private final double GYRO_SETPOINT = 0.0;
  private double smallest;

  public PixyAndGyroAuto(int goToAngle, boolean gyro, boolean pixy, boolean closeAngle) {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.m_driveTrain);
    if(closeAngle){
      smallest = Math.abs(Robot.m_driveTrain.GetAngle() - 0);
      goToAngle = 0;
      for (int i = 1; i<9; i++){
        if (smallest > Math.abs(Robot.m_driveTrain.GetAngle() - (i*45))) {
          smallest = Math.abs(Robot.m_driveTrain.GetAngle() - (i*45));
          goToAngle = i*45;
        } 
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
    else if (gyro) {
      Robot.m_driveTrain.AlignGyroOnly(goToAngle);
    }
    else if (pixy){
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
