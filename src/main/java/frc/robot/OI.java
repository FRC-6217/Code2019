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
import frc.robot.commands.ElevatorToHeight;
import frc.robot.commands.GoToHeightAuto;
import frc.robot.commands.SuctionArmToAngle;
import frc.robot.libraries.XboxController;
import frc.robot.subsystems.Elevator;
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
    // Button GoDownElev = xboxCon.getButton(XboxController.buttonID.A);
    // Button GoUpElev = xboxCon.getButton(XboxController.buttonID.B);
    // Button GoHalfElev = xboxCon.getButton(XboxController.buttonID.X);
    
    Button GoDownGrabber = xboxCon.getButton(XboxController.buttonID.A);
    Button GoUpGrabber = xboxCon.getButton(XboxController.buttonID.B);
    Button GoHalfGrabber = xboxCon.getButton(XboxController.buttonID.X);
    Button Orient = xboxCon.getButton(XboxController.buttonID.Y);
    // joystick = new Joystick(port);
    // button10 = new JoystickButton(joystick, 10);
    // GoDownElev.whenActive(new ElevatorToHeight(Elevator.MIN_HEIGHT));
    // GoUpElev.whenActive(new ElevatorToHeight(Elevator.MAX_HEIGHT));
    // GoHalfElev.whenActive(new ElevatorToHeight((Elevator.MAX_HEIGHT+Elevator.MIN_HEIGHT)/2));

    GoDownGrabber.whenActive(new SuctionArmToAngle(45));
    GoUpGrabber.whenActive(new SuctionArmToAngle(0));
    GoHalfGrabber.whenActive(new SuctionArmToAngle(90));
    Orient.whenActive(new OrientRobot(45));
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
