/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

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
  private Direction d;
  private WheelDirection wd;
  
  public static enum Direction {
    UP, DOWN, OFF;
  }

  public static enum WheelDirection {
    FORWARD, REVERSE, OFF;
  }
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public BallPickup(int rightArmChannel, int leftArmChannel, int wheelChannel, int limitSwitchDownChannel, int limitSwitchUpChannel) {
    wheel = new VictorSP(wheelChannel);
    rightArm = new VictorSP(rightArmChannel);
    leftArm = new VictorSP(leftArmChannel);
    limitSwitchDown = new DigitalInput(limitSwitchDownChannel);
    limitSwitchUp = new DigitalInput(limitSwitchUpChannel);
  }

  @Override
  public void initDefaultCommand() {
    //setDefaultCommand(new BallPickupJoystick());
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  private boolean checkLimits() {
    //upperLimit = limitSwitchUp.get();
    // limitSwitchDown.get();
    upperLimit = false;
    lowerLimit = false;
    return upperLimit || lowerLimit;
  }

  public void armUp() {
    //checkLimits();
    if(limitSwitchUp.get()) {
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
    //checkLimits();
    if(limitSwitchDown.get()) {
      rightArm.set(-armSpeed);
      leftArm.set(-armSpeed);
    }
    else {
      armStop();
    }
  }
  public void operateArm(Direction d){
    if(d.equals(Direction.UP)){
      armUp();
    }
    else if(d.equals(Direction.DOWN)){
      armDown();
    }
    else{
      armStop();
    }
  }

  public void changeArmState(Direction d){
    if(((this.d.equals(Direction.UP)) && (d.equals(Direction.DOWN)))
        || ((this.d.equals(Direction.DOWN)) && (d.equals(Direction.UP)))){
      
    }
    else{
      this.d = d;
      operateArm(d);
      operateWheel(wd);
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

  public void operateWheel(WheelDirection d){
    if(d.equals(WheelDirection.FORWARD)){
      grabberOn();
    }
    else if(d.equals(WheelDirection.REVERSE)){
      reverseGrabber();
    }
    else{
      grabberOff();
    }
  }
  
  public void changeWheelState(WheelDirection wd){
    if(((this.wd.equals(WheelDirection.FORWARD)) && (wd.equals(WheelDirection.REVERSE))) || ((this.wd.equals(WheelDirection.REVERSE)) && (wd.equals(WheelDirection.FORWARD)))){
      
    }
    else{
      this.wd = wd;
      operateArm(d);
      operateWheel(wd);
    }
  }
}
