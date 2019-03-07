/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.PidAlignJoystick;
import frc.robot.libraries.Pixy.Pixy2;
import frc.robot.libraries.Pixy.Pixy2.LinkType;

/**
 * Add your docs here.
 */
public class PixyCam extends Subsystem {
  Pixy2 pixy = Pixy2.createInstance(LinkType.SPI);
  private int average;

  public PixyCam(){
    pixy.init();
  }
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new PidAlignJoystick());
  }

  public int returnAverage(){
    pixy.getCCC().getBlocks(true, 1, 2);
    average = 0;

    for(int i = 0; i < pixy.getCCC().getBlocks().size(); i++){
      average += pixy.getCCC().getBlocks().get(i).getX();

      SmartDashboard.putNumber("X"+i, pixy.getCCC().getBlocks().get(i).getX());
      SmartDashboard.putNumber("Y"+i, pixy.getCCC().getBlocks().get(i).getY());
      SmartDashboard.putNumber("Height"+i, pixy.getCCC().getBlocks().get(i).getHeight());
      SmartDashboard.putNumber("Width"+i, pixy.getCCC().getBlocks().get(i).getWidth());
      SmartDashboard.putNumber("Angle"+i, pixy.getCCC().getBlocks().get(i).getAngle());
      SmartDashboard.putNumber("Age"+i, pixy.getCCC().getBlocks().get(i).getAge());
    }

    if(pixy.getCCC().getBlocks().size() != 0){
      return (average /= pixy.getCCC().getBlocks().size());
    }
    else{
      return 0;
    }
  }
}
