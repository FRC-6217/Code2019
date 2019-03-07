/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import frc.robot.libraries.WheelDrive;
import frc.robot.libraries.SwerveDriveClass;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class SwerveTrain extends PIDSubsystem {
  private WheelDrive backRight;
	private WheelDrive backLeft;
	private WheelDrive frontRight;
  private WheelDrive frontLeft;
  private SwerveDriveClass swerveDrive;

	private ADXRS450_Gyro gyro;
	private double x1;
  private double y1;

  private static final double MIN_ANGLE = 0;
  private static final double MAX_ANGLE = 360;
  
  public SwerveTrain() {
    // Insert a subsystem name and PID values here
    super("SwerveTrain", 0.5, 0.05, 0);
    
    //Initilize Wheels
    backRight = new WheelDrive(45, 43, 3);
		backLeft = new WheelDrive(42, 47, 2);
		frontRight = new WheelDrive(46, 40, 1);
		frontLeft = new WheelDrive(41, 44, 0);

    //Initilize Drivetrain
		swerveDrive = new SwerveDriveClass(backRight, backLeft, frontRight, frontLeft);

    //Initilize gyro
    gyro = new ADXRS450_Gyro();
    
    // //set input and output ranges for the pid loop
    // setInputRange(MIN_ANGLE, MAX_ANGLE);
    // setOutputRange(-1, 1);

    // //Allow pid loop to cross 0-360 boundary
    // getPIDController().setContinuous();

    // //start pid
    // enable();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

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

	public double GetAngle(){
		SmartDashboard.putNumber("Gyro", -gyro.getAngle());
		return -gyro.getAngle();
	}
  
  public void Drive(double x, double y, double z, double governer) {
		swerveDrive.drive(x*governer, y*governer, z*governer);
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
    SmartDashboard.putNumber("Drive Setpoint", setpoint);
    
    //set
    setSetpoint(setpoint);
  }

  @Override
  protected double returnPIDInput() {
    //angle from gyro
    double angle = GetAngle();
    
    //wraps gyro value back to 0-360 if out of range
    while(angle > 360){
      angle = (angle - 360);
    }
    while(angle < 0){
      angle = (angle + 360);
    }

    //Prints gyro values
    SmartDashboard.putNumber("Drive Gyro PID", angle);

    return angle;
  }

  @Override
  protected void usePIDOutput(double output) {
    //print motor speed and direction
    SmartDashboard.putNumber("Drive PID Velocity", output);

    //set output to rotate robot
    Drive(0, 0, output, 1);
  }

  public void resetGyro(){ 
		gyro.reset();
	}
}
