/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.LiftAuto;
import frc.robot.commands.LiftJoystick;
import frc.robot.libraries.PID;

/**
 * Add your docs here.
 */
public class Lift extends Subsystem {
  //Measures in Inches
  private static final double MIN_HEIGHT = 16.75;
  private static final double MAX_HEIGHT = 42;
  private double SCALAR = 0.000674745249;
  private static final double bOffset = 16.75;
  private double upSpeed = 1;
  private double downSpeed = .6;

  private Spark motor;
  private Encoder encoder;

  //pid calculating object
  private PID pid;

  public Lift(int motorChannel, int encoderChannelA, int encoderChannelB){
    //initilize motor and encoder objects
    motor = new Spark(motorChannel);
    encoder = new Encoder(encoderChannelA, encoderChannelB);

    //pid object
    pid = new PID(0.5, 0, 0);
    pid.setOutputRange(-.8, 1);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new LiftJoystick());
  }
  
  ////////////Sensor Interface
  public double returnLiftHeight(){
    //calculate position in inches from encoder
    double pos = encoder.get();
    double position = ((pos * SCALAR) + bOffset);

    //Prints encoder values
    SmartDashboard.putNumber("Lift Raw Encoder", pos);
    SmartDashboard.putNumber("Lift Encoder Position in.", position);
    
    return position;
  }

  public void resetEnc(){
    encoder.reset();
  }

  ////////////Non-Pid Control
  public void up(){
    motor.set(-upSpeed);
  }
  
  public void down(){
    motor.set(downSpeed);
  }
  
  public void stop(){
    motor.set(0);
  }

  //////////Pid-Control
  public void setToHeight(double setpoint){
    //Set out of range setpoints to be in range
    if(setpoint > MAX_HEIGHT){
      setpoint = MAX_HEIGHT;
    }
    else if(setpoint < MIN_HEIGHT){
      setpoint = MIN_HEIGHT;
    }

    pid.setSetpoint(setpoint);
    pid.setCurrentState(returnLiftHeight());

    motor.set(-pid.getOutput());
  }
}
