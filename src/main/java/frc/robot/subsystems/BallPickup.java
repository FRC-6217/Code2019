/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.BallPickupJoystick;

/**
 * Add your docs here.
 */
public class BallPickup extends Subsystem {
  //private Spark wheel = new Spark(0);
  private VictorSP wheel;
  private VictorSP rightArm;
  private VictorSP leftArm;
  private DigitalInput limitSwitchUp;
  private DigitalInput limitSwitchDown;
  private double armSpeed = 1;
  private double wheelSpeed = 1;
  private boolean upperLimit;
  private boolean lowerLimit;
  
  enum Direction {
    UP, DOWN;
  }
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public BallPickup(int rightArmChannel, int leftArmChannel, int wheelChannel, int limitSwitchDownChannel, int limitSwitchUpChannel) {
    wheel = new VictorSP(wheelChannel);
    rightArm = new VictorSP(rightArmChannel);
    leftArm = new VictorSP(leftArmChannel);
    //PlimitSwitchDown = new DigitalInput(limitSwitchDownChannel);
    //limitSwitchUp = new DigitalInput(limitSwitchUpChannel);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new BallPickupJoystick());
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  private boolean checkLimits() {
    upperLimit = false; //limitSwitchUp.get();
    lowerLimit = false;// limitSwitchDown.get();
    return upperLimit || lowerLimit;
  }

  public void armUp() {
    checkLimits();
    if(!upperLimit) {
      rightArm.set(armSpeed);
      leftArm.set(armSpeed);
    }
    else {
      armStop();
    }
  }

  public void armStop() {
    rightArm.set(0);
    leftArm.set(0);
  }

  public void armDown() {
    checkLimits();
    if(!lowerLimit) {
      rightArm.set(-armSpeed);
      leftArm.set(-armSpeed);
    }
    else {
      armStop();
    }
  }

  public void setArmSpeed(double speed) {
    if(speed > 1) {
      speed = 1;
    }
    else if(speed < 0) {
      speed = 0;
    } 
    armSpeed = speed;
  }

  public double getArmSpeed() {
    return armSpeed;
  }

  public void setWheelSpeed(double speed) {
    if (speed > 1) {
      speed = 1;
    } 
    else if (speed < 0) {
      speed = 0;
    }
    wheelSpeed = speed;
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
