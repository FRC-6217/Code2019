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
import frc.robot.commandGroups.Hatch.*;
import frc.robot.commandGroups.Ball.*;
import frc.robot.commands.LiftAuto;
// import frc.robot.commands.PixyAndGyroAuto;
import frc.robot.commands.VacuumAuto;
/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  public Joystick joystick = new Joystick(0);
  private Button button1 = new JoystickButton(joystick, 1);
  private Button button2 = new JoystickButton(joystick, 2);
  private Button button7 = new JoystickButton(joystick, 7);
  private Button button8 = new JoystickButton(joystick, 8);
  private Button button9 = new JoystickButton(joystick, 9);
  private Button button10 = new JoystickButton(joystick, 10);
  private Button button11 = new JoystickButton(joystick, 11);
  private Button button12 = new JoystickButton(joystick, 12);

  public OI(/*int port*/) {
    // button1.whenPressed(new HatchLow());

    // button2.whileHeld(new PixyAndGyroAuto(270, true, false, true));
    // // button7.whenPressed(new LiftAuto(25));
    // button1.whenPressed(new HatchLow());
    // // button9.whenPressed(new BallMiddle());
    // button10.whenPressed(new HatchMiddle());
    // // button11.whenPressed(new BallHigh());
    // button12.whenPressed(new HatchHigh());

    // button7.whenPressed(new CargoShipCargoDropOff());

    // joystick = new Joystick(port);
    // button10 = new JoystickButton(joystick, 10);
    //button1.whileHeld(new PidAlignJoystick());
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
