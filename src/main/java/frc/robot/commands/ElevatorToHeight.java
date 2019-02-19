/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.libraries.PID;
import frc.robot.subsystems.Elevator;

public class ElevatorToHeight extends Command {
  private double height;
  private Elevator elevator;
  private PID pid;
  private double p = 1, i = 0.1, d = 0;
  private double deadband = 1;

  public ElevatorToHeight(double height) {
    requires(Robot.m_Elevator);
    this.height = height;
    elevator = Robot.m_Elevator;
    pid = new PID(p, i, d);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    pid.setSetpoint(height);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // pid.p = SmartDashboard.getNumber("pkey elevator", 0);
    // pid.i = SmartDashboard.getNumber("ikey elevator", 0);
    // pid.d = SmartDashboard.getNumber("dkey elevator", 0);

    elevator.pidControlMovement(pid.update(elevator.getHeight()));
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return ((elevator.getHeight() < (height + deadband)) && (elevator.getHeight() > (height - deadband)));
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    elevator.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
