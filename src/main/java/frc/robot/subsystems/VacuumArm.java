/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Encoder;

/**
 * Add your docs here.
 */
public class VacuumArm extends PIDSubsystem {
  //Measures in Inches
  private static final double MIN_ANGLE = 0;
  private static final double MAX_ANGLE = 180;
  private double SCALAR = 0.0188481675;
  private double speed = 0.7;

  private VictorSPX arm;
  private Encoder enc;

  public VacuumArm(int motorPort, int encPortA, int encPortB) {
    // Insert a subsystem name and PID values here
    super("VacuumArm", 0.5, 0.05, 0);
    
    //initilize motor and encoder
    arm = new VictorSPX(motorPort);
    enc = new Encoder(encPortA, encPortB);

    //set input and output ranges for the pid loop
    setInputRange(MIN_ANGLE, MAX_ANGLE);
    setOutputRange(-1, 1);
    //start pidloop
    enable();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void setSetpoint(double setpoint){
    //Set out of range setpoints to be in range
    if(setpoint > MAX_ANGLE){
      setpoint = MAX_ANGLE;
    }
    else if(setpoint < MIN_ANGLE){
      setpoint = MIN_ANGLE;
    }

    //print setpoint
    SmartDashboard.putNumber("Arm Setpoint", setpoint);
    
    //set
    setSetpoint(setpoint);
  }

  @Override
  protected double returnPIDInput() {
   double pos = enc.get();
   double position = (pos * SCALAR);

   //Prints encoder values
   SmartDashboard.putNumber("Arm Raw Encoder", pos);
   SmartDashboard.putNumber("Arm Encoder Position in.", position);
   
   return position;
  }

  @Override
  protected void usePIDOutput(double output) {
    //print motor speed and direction
    SmartDashboard.putNumber("Arm Velocity", output);
    
    //set
    arm.set(ControlMode.PercentOutput, speed);
  }

  public void up(){
    arm.set(ControlMode.PercentOutput, speed);
  }

  public void down(){
    arm.set(ControlMode.PercentOutput, -speed);
  }

  public void stop(){
    arm.set(ControlMode.PercentOutput, 0);
  }

  public void resetEnc(){
    enc.reset();
  }
}
