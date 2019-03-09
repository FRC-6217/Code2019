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
import frc.robot.commands.PistonJoystick;

/**
 * Add your docs here.
 */
public class pistons extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  DoubleSolenoid soli;
  public pistons(int sol1, int sol2){
    soli = new DoubleSolenoid(sol1, sol2);
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new PistonJoystick());
  }

  public void run(boolean down){
    if(down){
      soli.set(Value.kReverse);
    }
    else{
      soli.set(Value.kForward);
    }
  }
}
