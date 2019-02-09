/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.BallPickupJoystick;

/**
 * Add your docs here.
 */
public class BallPickup extends Subsystem {
  //private Spark wheel = new Spark(0);
  private Spark lift = new Spark(0);
  private VictorSP wheel = new VictorSP(1);
  private VictorSP pickup1 = new VictorSP(2);
  private VictorSP pickup2 = new VictorSP(3);
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public void liftSpeed(double speed) {
    lift.setSpeed(speed);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new BallPickupJoystick());
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void wheelDrive(double speed){
    wheel.set(speed);
  }

  public void pickupDrive(double speed){
    pickup1.set(speed);
    pickup2.set(speed);
  }
}
