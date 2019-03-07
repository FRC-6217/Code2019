/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;

/**
 * Add your docs here.
 */
public class BallGobbler extends Subsystem {
  private VictorSP rightArm;
  private VictorSP leftArm;
  private DigitalInput limitSwitchUp;
  private DigitalInput limitSwitchDown;
  private double armSpeed = 1;
 
  public BallGobbler(int rightArmChannel, int leftArmChannel, int limitSwitchDownChannel, int limitSwitchUpChannel) {

    rightArm = new VictorSP(rightArmChannel);
    leftArm = new VictorSP(leftArmChannel);
    limitSwitchDown = new DigitalInput(limitSwitchDownChannel);
    limitSwitchUp = new DigitalInput(limitSwitchUpChannel);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
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
}
