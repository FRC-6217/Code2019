/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class VacuumJoystick extends Command {
  private boolean forward60 = false;
  private boolean forward20 = false;
  private boolean button = false;
  private boolean Comp = true;

  public VacuumJoystick() {
    requires(Robot.m_Vacuum);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.m_Vacuum.deactivateVacuum();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    forward60 = (Robot.m_oi_copilot.getRightYAxis()) < (-0.15);
    forward20 = (Robot.m_oi_copilot.getLeftYAxis()) < (-0.15);
    button = (Robot.m_oi_copilot.getButtonR3() || Robot.m_oi_copilot.getButtonL3());
    Comp = (Robot.m_oi_pilot.joystick.getRawButton(4));
    
    Robot.m_Vacuum.deOrActivateCompress(Comp);

    if(forward60){
      Robot.m_Vacuum.activateVacuum60PSI();
    }
    else if(forward20){
      Robot.m_Vacuum.activateVacuum20PSI();
    }
    else if(button) {
      Robot.m_Vacuum.deactivateVacuum();
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
    end();
  }
}
