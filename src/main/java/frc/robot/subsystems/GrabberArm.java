/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.GrabberArmJoystick;
import frc.robot.commands.SuctionArmToAngle;

/**
 * Add your docs here.
 */
public class GrabberArm extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private VictorSPX arm;
  private Encoder enc;
  private double angle = 0;
  public double Scalar = 0.0188481675;

  public GrabberArm(int motorPort, int encPortA, int encPortB){
    arm = new VictorSPX(motorPort);
   enc = new Encoder(encPortA, encPortB);
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    //setDefaultCommand(new SuctionArmToAngle(0));
    setDefaultCommand(new GrabberArmJoystick());
  }

  public void SetWantedAngle(double angle){
    this.angle = angle;
  }

  public double GetWantedAngle(){
    return angle;
  }
  public void Up(double speed){
    arm.set(ControlMode.PercentOutput, Math.abs(speed));
  }

  public void Down(double speed){
    arm.set(ControlMode.PercentOutput, -Math.abs(speed));
  }

  public void Move(double velocity){
    arm.set(ControlMode.PercentOutput, -velocity);
  }

  public void Stop(){
    arm.set(ControlMode.PercentOutput, 0);
  }
  public double GetEncoderValue(){
    return enc.get();
  }
  public double GetAngle(){
    //return 0;
    update();
    return (enc.get() * Scalar);
  }
  public void update(){
    SmartDashboard.putNumber("Suction enc raw", enc.get());
    SmartDashboard.putNumber("Suction enc deg", (enc.get()*Scalar));
  }
  public void resetEncoder(){
    enc.reset();
  }

}
