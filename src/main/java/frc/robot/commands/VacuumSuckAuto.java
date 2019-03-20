// /*----------------------------------------------------------------------------*/
// /* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
// /* Open Source Software - may be modified and shared by FRC teams. The code   */
// /* must be accompanied by the FIRST BSD license file in the root directory of */
// /* the project.                                                               */
// /*----------------------------------------------------------------------------*/

// package frc.robot.commands;

// import edu.wpi.first.wpilibj.command.Command;
// import frc.robot.Robot;

// public class VacuumSuckAuto extends Command {
//   private int psi;
//   public VacuumSuckAuto(int psi) {
//     // Use requires() here to declare subsystem dependencies
//     // eg. requires(chassis);
//     requires(Robot.m_Vacuum);
//     this.psi = psi;
//   }

//   // Called just before this Command runs the first time
//   @Override
//   protected void initialize() {
//   }

//   // Called repeatedly when this Command is scheduled to run
//   @Override
//   protected void execute() {
//     switch (psi){
//       case 20:
//         Robot.m_Vacuum.activateVacuum20PSI();
//       case 60:
//         Robot.m_Vacuum.activateVacuum60PSI();
//       case 0:
//         Robot.m_Vacuum.deactivateVacuum();

//     }
//   }

//   // Make this return true when this Command no longer needs to run execute()
//   @Override
//   protected boolean isFinished() {
//     return true;
//   }

//   // Called once after isFinished returns true
//   @Override
//   protected void end() {
//   }

//   // Called when another command which requires one or more of the same
//   // subsystems is scheduled to run
//   @Override
//   protected void interrupted() {
//     end();
//   }
// }
