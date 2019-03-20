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
import frc.robot.commands.NewPistonGrabberJoystick;

/**
 * Add your docs here.
 */
public class NewPistonGrabber extends Subsystem {
  private DoubleSolenoid ds1;
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public NewPistonGrabber(int port1, int port2){
    ds1 = new DoubleSolenoid(port1, port2);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new NewPistonGrabberJoystick());
    
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void push() {
    ds1.set(Value.kReverse);
  }

  public void off() {
    ds1.set(Value.kOff);
  }

  public void pull() {
    ds1.set(Value.kForward);
  }
}
