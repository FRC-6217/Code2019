/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.libraries.PixyException;
import frc.robot.libraries.PixyI2C;
import frc.robot.libraries.PixyPacket;

/**
 * Add your docs here.
 */

public class Pixycam extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private PixyI2C pixy = new PixyI2C();
  private PixyPacket packet = new PixyPacket();
  private int x1;
  private int x2;
  private double average;
  private double center;

  public double getError() throws PixyException {
    packet = pixy.readPacket(0);
    x1 = 0;
    
    for(int i = 0; i <= 1;) {
      int x = 0;
      packet = pixy.readPacket(x);
      x++;
      if(packet.Height * packet.Width > 20) {
        if(x1 == 0) {
          x1 = packet.X;
        }

        else{
          x2 = packet.X;
        }
        i++;
      }
    }

    center = 160;
    average = (x1 + x2)/2;
    return center - average;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
