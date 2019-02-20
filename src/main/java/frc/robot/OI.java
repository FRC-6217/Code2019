/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.BallGobber;
import frc.robot.commands.BallGobberWheels;
import frc.robot.commands.ElevatorToHeight;
import frc.robot.commands.GoToHeightAuto;
import frc.robot.commands.SuctionArmToAngle;
import frc.robot.commands.VacuumJoystick;
import frc.robot.commands.VacuumToPSI;
import frc.robot.libraries.XboxController;
import frc.robot.subsystems.BallPickup;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Vacuum;
import frc.robot.commands.OrientRobot;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  public Joystick joystick;
  public XboxController xboxCon;
  
  public OI(int portJoy, int portXbox) {
    xboxCon = new XboxController(portXbox);
    joystick = new Joystick(portJoy);
    Button GoDownElev = new JoystickButton(joystick, 11);
    Button GoUpElev = new JoystickButton(joystick, 12);
    Button GoHalfElev = new JoystickButton(joystick, 10);

    Button ElevUp = xboxCon.getButton(XboxController.buttonID.LB);
    Button ElevDown = xboxCon.getButton(XboxController.buttonID.LeftTrigger, true);

    Button SuctionArmUp = xboxCon.getButton(XboxController.buttonID.RB);
    Button SuctionArmDown = xboxCon.getButton(XboxController.buttonID.RightTrigger, true);

//    Button Orient = xboxCon.getButton(XboxController.buttonID.Y);
    
    Button SuctionCup20PsiOn = xboxCon.getButton(XboxController.buttonID.LeftYAxis, true);
    Button SuctionCup20PsiOff = xboxCon.getButton(XboxController.buttonID.LeftYAxis, false);
    Button SuctionCup60PsiOn = xboxCon.getButton(XboxController.buttonID.RightYAxis, true);
    Button SuctionCup60PsiOff = xboxCon.getButton(XboxController.buttonID.RightYAxis, false);
    
    Button BallGobberUp = xboxCon.getButton(XboxController.buttonID.A);
    Button BallGobberDown = xboxCon.getButton(XboxController.buttonID.B);
    Button BallGobberIn = xboxCon.getButton(XboxController.buttonID.X);
    Button BallGobberOut = xboxCon.getButton(XboxController.buttonID.Y);

    GoDownElev.whenActive(new ElevatorToHeight(Elevator.MIN_HEIGHT));
    GoUpElev.whenActive(new ElevatorToHeight(Elevator.MAX_HEIGHT));
    GoHalfElev.whenActive(new ElevatorToHeight((Elevator.MAX_HEIGHT+Elevator.MIN_HEIGHT)/2));
    
    ElevUp.whileActive(new ElevatorToHeight(Elevator.MIN_HEIGHT));
    ElevDown.whileActive(new ElevatorToHeight(Elevator.MAX_HEIGHT));

//  Orient.whenActive(new OrientRobot(45));

    SuctionArmUp.whileActive(new SuctionArmToAngle(0));
    SuctionArmDown.whileActive(new SuctionArmToAngle(90));

    SuctionCup20PsiOn.whenActive(new VacuumToPSI(Vacuum.PsiNeed.PSI_20));
    SuctionCup20PsiOff.whenActive(new VacuumToPSI(Vacuum.PsiNeed.OFF));
    SuctionCup60PsiOn.whenActive(new VacuumToPSI(Vacuum.PsiNeed.PSI_60));
    SuctionCup60PsiOff.whenActive(new VacuumToPSI(Vacuum.PsiNeed.OFF));


    BallGobberUp.whileHeld(new BallGobber(BallPickup.Direction.UP));
    BallGobberUp.whenReleased(new BallGobber(BallPickup.Direction.OFF));
    BallGobberDown.whileHeld(new BallGobber(BallPickup.Direction.DOWN));
    BallGobberDown.whileHeld(new BallGobber(BallPickup.Direction.OFF));

    BallGobberIn.whileHeld(new BallGobberWheels(BallPickup.WheelDirection.FORWARD));
    BallGobberIn.whenReleased(new BallGobberWheels(BallPickup.WheelDirection.OFF));
    BallGobberOut.whileHeld(new BallGobberWheels(BallPickup.WheelDirection.REVERSE));
    BallGobberOut.whileHeld(new BallGobberWheels(BallPickup.WheelDirection.OFF));
  }

  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);

  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());
}
