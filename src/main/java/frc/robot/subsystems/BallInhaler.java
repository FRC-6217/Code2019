/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.BallInhalerJoystick;
import edu.wpi.first.wpilibj.VictorSP;
/**
 * Add your docs here.
 */
public class BallInhaler extends Subsystem {
  //private Spark wheel = new Spark(0);
  private VictorSP wheel;
  private double wheelSpeed = 1;

  public BallInhaler(int wheelChannel) {
    wheel = new VictorSP(wheelChannel);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new BallInhalerJoystick());
  }

  public void grabberOn() {
    wheel.set(-wheelSpeed);
  }

  public void grabberOff() {
    wheel.set(0);
  }

  public void reverseGrabber() {
    wheel.set(wheelSpeed);
  }
}
