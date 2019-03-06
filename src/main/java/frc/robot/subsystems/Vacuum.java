/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.VacuumJoystick;

/**
 * Add your docs here.
 */
public class Vacuum extends Subsystem {
  private Solenoid vac60;
  private Solenoid vac20;
  private Compressor comp;
  private boolean run;
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public Vacuum(int vacuumChannel60PSI, int vacuumChannel20PSI){
    vac60 = new Solenoid(vacuumChannel60PSI);
    vac20 = new Solenoid(vacuumChannel20PSI);
    comp = new Compressor();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new VacuumJoystick());
  }

  public void activateVacuum60PSI(){
    deactivateVacuum();
    vac60.set(true);
  }

  public void deactivateVacuum(){
    vac20.set(false);
    vac60.set(false);
  }

  public void activateVacuum20PSI(){
    deactivateVacuum();
    vac20.set(true);
  }

  public void deOrActivateCompress(boolean on) {
    comp.setClosedLoopControl(on);
  }
}
