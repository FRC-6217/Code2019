/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.libraries.WheelDrive;
import frc.robot.libraries.Pixy.Pixy2;
import frc.robot.libraries.Pixy.Pixy2.LinkType;
import frc.robot.libraries.PID;
import frc.robot.libraries.SwerveDriveClass;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class DriveTrain extends Subsystem {
  private WheelDrive backRight;
	private WheelDrive backLeft;
	private WheelDrive frontRight;
  private WheelDrive frontLeft;
  private SwerveDriveClass swerveDrive;

  private ADXRS450_Gyro gyro;
  private Pixy2 pixy = Pixy2.createInstance(LinkType.SPI);
	private double x1;
  private double y1;

  ////PID stuff

  //pid calculating objects
  private PID pixyPID;
  private PID gyroPID;

  private static final double MIN_ANGLE = 0;
  private static final double MAX_ANGLE = 360;

  private static final double MIN_OFFSET = -160;
  private static final double MAX_OFFSET = 160;

  public DriveTrain(){
    //Initilize Wheels
    backRight = new WheelDrive(45, 43, 3);
		backLeft = new WheelDrive(42, 47, 2);
		frontRight = new WheelDrive(46, 40, 1);
		frontLeft = new WheelDrive(41, 44, 0);

    //Initilize Drivetrain
		swerveDrive = new SwerveDriveClass(backRight, backLeft, frontRight, frontLeft);

    //Initilize pixycam
    pixy.init();

    //Initilize gyro
    gyro = new ADXRS450_Gyro();

    //pid objects
    pixyPID = new PID(0.25, 0.15, 0.05);
    gyroPID = new PID(0.25, 0.15, 0.05);

    pixyPID.setOutputRange(-1, 1);
    gyroPID.setOutputRange(-1, 1);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  ////////////Sensor Interfaces

    //pixycam get current average reading
  public double returnPixyAverage(boolean debug){
    pixy.getCCC().getBlocks(true, 1, 2);
    
    double average = 0;

    for(int i = 0; i < pixy.getCCC().getBlocks().size(); i++){
      average += pixy.getCCC().getBlocks().get(i).getX();

      if(debug){
        SmartDashboard.putNumber("X"+i, pixy.getCCC().getBlocks().get(i).getX());
        SmartDashboard.putNumber("Y"+i, pixy.getCCC().getBlocks().get(i).getY());
        SmartDashboard.putNumber("Height"+i, pixy.getCCC().getBlocks().get(i).getHeight());
        SmartDashboard.putNumber("Width"+i, pixy.getCCC().getBlocks().get(i).getWidth());
        SmartDashboard.putNumber("Angle"+i, pixy.getCCC().getBlocks().get(i).getAngle());
        SmartDashboard.putNumber("Age"+i, pixy.getCCC().getBlocks().get(i).getAge());
      }
    }

    if(pixy.getCCC().getBlocks().size() != 0){
      return (average /= pixy.getCCC().getBlocks().size());
    }
    else {
       return 0;
    }
  }

  public double returnPixyAverage(){
    return returnPixyAverage(false);
  }

  //return FRC gyro angle
  public double GetAngle(){
		SmartDashboard.putNumber("Gyro", -gyro.getAngle());
		return -gyro.getAngle();
  }
  
  //reset FRC gyro
  public void ResetGyro(){ 
		gyro.reset();
	}

  ////////////Non-Pid Control

  public double TransformX(double x, double y, boolean isReversed){
		if(isReversed){
			x1 = (x * Math.cos((GetAngle() + 180) * (Math.PI / 180))) - (y * Math.sin((GetAngle() + 180) * (Math.PI / 180)));	
		}
		else{
			x1 = (x * Math.cos(GetAngle() * (Math.PI / 180))) - (y * Math.sin(GetAngle() * (Math.PI / 180)));
		}
		return x1;
	}

	public double TransformY(double x, double y, boolean isReversed){
		if(isReversed){
			y1 = (x * Math.sin((GetAngle() + 180) * (Math.PI / 180))) + (y * Math.cos((GetAngle() + 180) * (Math.PI / 180)));
		}
		else{
			y1 = (x * Math.sin(GetAngle() * (Math.PI / 180))) + (y * Math.cos(GetAngle() * (Math.PI / 180)));
		}
		return y1;
	}
  
  public void Drive(double x, double y, double z, double governer) {
		swerveDrive.drive(x*governer, y*governer, z*governer);
  }
  
  //////////Pid-Control

  public void AlignPixyOnly(double setpoint){
    //Set out of range setpoints to be in range
    if(setpoint > MAX_OFFSET){
      setpoint = MAX_OFFSET;
    }
    else if(setpoint < MIN_OFFSET){
      setpoint = MIN_OFFSET;
    }
    
    pixyPID.setSetpoint(setpoint);
    pixyPID.setCurrentState(returnPixyAverage());

    Drive(pixyPID.getOutput(), 0, 0, 1);
  }

  public void AlignGyroOnly(double setpoint){
    //Set out of range setpoints to be in range
    if(setpoint > MAX_ANGLE){
      setpoint = MAX_ANGLE;
    }
    else if(setpoint < MIN_ANGLE){
      setpoint = MIN_ANGLE;
    }

    gyroPID.setSetpoint(setpoint);

    //wraps gyro value back to 0-360 if out of range
    double angle = GetAngle();
    while(angle > 360){
      angle = (angle - 360);
    }
    while(angle < 0){
      angle = (angle + 360);
    }

    gyroPID.setCurrentState(angle);

    Drive(0, 0, gyroPID.getOutput(), 1);
  }

  public void AlignPixyAndGyro(double setpointPixy, double setpointGyro){
    //Set out of range setpoints to be in range
    if(setpointPixy > MAX_OFFSET){
      setpointPixy = MAX_OFFSET;
    }
    else if(setpointPixy < MIN_OFFSET){
      setpointPixy = MIN_OFFSET;
    }

    //Set out of range setpoints to be in range
    if(setpointGyro > MAX_ANGLE){
      setpointGyro = MAX_ANGLE;
    }
    else if(setpointGyro < MIN_ANGLE){
      setpointGyro = MIN_ANGLE;
    }

    pixyPID.setSetpoint(setpointPixy);
    gyroPID.setSetpoint(setpointGyro);

    //wraps gyro value back to 0-360 if out of range
    double angle = GetAngle();
    while(angle > 360){
      angle = (angle - 360);
    }
    while(angle < 0){
      angle = (angle + 360);
    }

    pixyPID.setCurrentState(returnPixyAverage());
    gyroPID.setCurrentState(angle);

    Drive(pixyPID.getOutput(), 0, gyroPID.getOutput(), 1);
  }
}
