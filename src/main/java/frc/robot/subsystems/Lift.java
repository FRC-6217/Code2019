/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class Lift extends PIDSubsystem {
  //Measures in Inches
  private static final double MIN_HEIGHT = 16.75;
  private static final double MAX_HEIGHT = 41.5;
  private double SCALAR = 0.000671;
  private static final double bOffset = 16.75;
  private double upSpeed = 1;
  private double downSpeed = .8;

  private Spark motor;
  private Encoder encoder;

  public Lift(int motorChannel, int encoderChannelA, int encoderChannelB) {
    // Insert a subsystem name and PID values here
    super("Lift", .5, .05, 0);
    
    //initilize motor and encoder objects
    motor = new Spark(motorChannel);
    encoder = new Encoder(encoderChannelA, encoderChannelB);

    //set input and output ranges for the pid loop
    setInputRange(MIN_HEIGHT, MAX_HEIGHT);
    setOutputRange(-1, 1);

    //start pid loop
    enable();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void setSetpoint(double setpoint){
    //Set out of range setpoints to be in range
    if(setpoint > MAX_HEIGHT){
      setpoint = MAX_HEIGHT;
    }
    else if(setpoint < MIN_HEIGHT){
      setpoint = MIN_HEIGHT;
    }

    //print setpoint
    SmartDashboard.putNumber("Lift Setpoint", setpoint);
    
    //set
    setSetpoint(setpoint);
  }

  @Override
  protected double returnPIDInput() {
    //calculate position in inches from encoder
    double pos = encoder.get();
    double position = ((pos * SCALAR) + bOffset);

    //Prints encoder values
    SmartDashboard.putNumber("Lift Raw Encoder", pos);
    SmartDashboard.putNumber("Lift Encoder Position in.", position);
    
    return position;
  }

  @Override
  protected void usePIDOutput(double output) {
    //print motor speed and direction
    SmartDashboard.putNumber("Lift Velocity", output);
    
    //set
    motor.set(output);
  }

  public void up(){
    motor.set(upSpeed);
  }

  public void down(){
    motor.set(-downSpeed);
  }

  public void stop(){
    motor.set(0);
  }

  public void resetEnc(){
    encoder.reset();
  }
}
