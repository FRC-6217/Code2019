/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.libraries;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * Add your docs here.
 */
public class XboxController{
    private Joystick joystick;
    public static enum DPAD_STATE {
        UP, RIGHTUP, RIGHT, RIGHTDOWN, DOWN, LEFTDOWN, LEFT, LEFTUP, INVALID, NONE;
    }
    public static enum buttonID {
        A, B, X, Y, LB, RB, BACK, START, L3, R3, LeftXAxis, LeftYAxis, RightXAxis, RightYAxis, LeftTrigger, RightTrigger;
    }

    public JoystickButton getButton(buttonID button){
        if(button.ordinal() > buttonID.R3.ordinal()){
            return new JoystickAxis(joystick, button.ordinal() - buttonID.R3.ordinal() - 1);
        }
        else{
            return new JoystickButton(joystick, (button.ordinal() + 1));
        }
    }

    public XboxController(int port) {
        joystick = new Joystick(port);
    }

    public Joystick returnJoystick(){
        return joystick;
    }
    public boolean getButtonA() {
        return joystick.getRawButton(0+1);
    }

    public boolean getButtonB() {
        return joystick.getRawButton(1+1);
    }

    public boolean getButtonX() {
        return joystick.getRawButton(2+1);
    }
    
    public boolean getButtonY() {
        return joystick.getRawButton(3+1);
    }

    public boolean getButtonLB() {
        return joystick.getRawButton(4+1);
    }

    public boolean getButtonRB() {
        return joystick.getRawButton(5+1);
    }

    public boolean getButtonBACK() {
        return joystick.getRawButton(6+1);
    }

    public boolean getButtonSTART() {
        return joystick.getRawButton(7+1);
    }

    public boolean getButtonL3() {
        return joystick.getRawButton(8+1);
    }

    public boolean getButtonR3() {
        return joystick.getRawButton(9+1);
    }

    public double getLeftXAxis() {
        return joystick.getRawAxis(0);
    }

    public double getLeftYAxis() {
        return joystick.getRawAxis(1);
    }

    public double getRightXAxis() {
        return joystick.getRawAxis(4);
    }

    public double getRightYAxis() {
        return joystick.getRawAxis(5);
    }

    public double getLeftTrigger() {
        return joystick.getRawAxis(2);
    }

    public double getRightTrigger() {
        return joystick.getRawAxis(3);
    }

    public DPAD_STATE getDPAD() {
        int pov = joystick.getPOV();
        if(pov == -1) {
            return DPAD_STATE.NONE;
        }
        
        int index = Math.round((7/315) * pov);
        return DPAD_STATE.values()[index];
    }
}
class JoystickAxis extends JoystickButton {
  private final GenericHID m_joystick;
  private final int m_AxisNumber;

  /**
   * Create a joystick Axis for triggering commands.
   *
   * @param joystick     The GenericHID object that has the Axis (e.g. Joystick, KinectStick,
   *                     etc)
   * @param AxisNumber The Axis number (see {@link GenericHID#getRawAxis(int) }
   */
  public JoystickAxis(GenericHID joystick, int AxisNumber) {
    super(joystick, 0);
    m_joystick = joystick;
    m_AxisNumber = AxisNumber;
  }

  /**
   * Gets the value of the joystick Axis.
   *
   * @return The value of the joystick Axis
   */
  @Override
  public boolean get() {
    return (Math.abs(m_joystick.getRawAxis(m_AxisNumber)) > .15);
  }
}