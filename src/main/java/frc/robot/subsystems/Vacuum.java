/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.VacuumJoystick;

/**
 * Add your docs here.
 */
public class Vacuum extends Subsystem {
  private DoubleSolenoid vac;
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public Vacuum(int vacuumChannelA, int vacuumChannelB){
    vac = new DoubleSolenoid(vacuumChannelA, vacuumChannelB);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new VacuumJoystick());
  }

  public void activateVacuum(){
    vac.set(Value.kForward);
  }

  public void deactivateVacuum(){
    vac.set(Value.kReverse);
  }
}
