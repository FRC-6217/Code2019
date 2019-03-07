/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.VacuumArmJoystick;
import frc.robot.libraries.PID;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Encoder;

/**
 * Add your docs here.
 */
public class VacuumArm extends Subsystem {
  //Measures in Inches
  private static final double MIN_ANGLE = 0;
  private static final double MAX_ANGLE = 180;
  private double SCALAR = 0.0188481675;
  private double speed = 0.7;

  private VictorSPX arm;
  private Encoder enc;

  //pid calculating object
  private PID pid;

  public VacuumArm(int motorPort, int encPortA, int encPortB) {
    //initilize motor and encoder
    arm = new VictorSPX(motorPort);
    enc = new Encoder(encPortA, encPortB);

    //pid object
    pid = new PID(0.05, 0.05, 0.01);
    pid.setOutputRange(-1, 1);
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new VacuumArmJoystick());
  }

  ////////////Sensor Interface
  public double returnArmAngle(){
    //scale encoder reading to arm angle
    double pos = enc.get();
    double position = (pos * SCALAR);

    //Prints encoder values
    SmartDashboard.putNumber("Arm Raw Encoder", pos);
    SmartDashboard.putNumber("Arm Encoder Position in.", position);
   
    return position;
  }

  public void resetEnc(){
    enc.reset();
  }

  ////////////Non-Pid Control
  public void up(){
    arm.set(ControlMode.PercentOutput, speed);
  }

  public void down(){
    arm.set(ControlMode.PercentOutput, -speed);
  }

  public void stop(){
    arm.set(ControlMode.PercentOutput, 0);
  }

  //////////Pid-Control
  public void setToAngle(double setpoint){
    //Set out of range setpoints to be in range
    if(setpoint > MAX_ANGLE){
      setpoint = MAX_ANGLE;
    }
    else if(setpoint < MIN_ANGLE){
      setpoint = MIN_ANGLE;
    }

    pid.setSetpoint(setpoint);
    pid.setCurrentState(returnArmAngle());

    arm.set(ControlMode.PercentOutput, pid.getOutput());
  }
}
