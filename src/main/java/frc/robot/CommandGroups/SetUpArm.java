/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SetUpArm extends CommandGroup {
  /**
   * Add your docs here.
   */
  private static final ElevAndAngle[] EAndA = {new ElevAndAngle(0, 0),
                          new ElevAndAngle(0, 0),
                          new ElevAndAngle(0, 0),
                          new ElevAndAngle(0, 0),
                          new ElevAndAngle(0, 0),
                          new ElevAndAngle(0, 0),
                          new ElevAndAngle(0, 0),
                          new ElevAndAngle(0, 0),
                          new ElevAndAngle(0, 0),
                        };
  public static enum whichSet{
    INIT, BALL_PICKUP, LOW_HATCH, MIDDLE_HATCH, HIGH_HATCH, LOW_BALL_DROPOFF, MIDDLE_BALL_DROPOFF, HIGH_BALL_DROPOFF, DEFAULT;
  }
  public SetUpArm(whichSet s) {
    // Add Commands here:
    // e.g. addSequential(new Command1());
    // addSequential(new Command2());
    // these will run in order.

    // To run multiple commands at the same time,
    // use addParallel()
    // e.g. addParallel(new Command1());
    // addSequential(new Command2());
    // Command1 and Command2 will run in parallel.

    // A command group will require all of the subsystems that each member
    // would require.
    // e.g. if Command1 requires chassis, and Command2 requires arm,
    // a CommandGroup containing them would require both the chassis and the
    // arm.
  }
}

class ElevAndAngle{
  public double Elev;
  public double Angle;
  public ElevAndAngle(double Elev, double Angle){
    this.Elev = Elev;
    this.Angle = Angle;
  }
}
