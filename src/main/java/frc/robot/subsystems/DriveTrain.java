package frc.robot.subsystems;

import frc.robot.commands.JoystickDrive;
import frc.robot.libraries.WheelDrive;
import frc.robot.libraries.SwerveDriveClass;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem { //angle, speed
	private WheelDrive backRight;
	private WheelDrive backLeft;
	private WheelDrive frontRight;
	private WheelDrive frontLeft;
	private Gyro gyro;
	private double x1;
	private double y1;

	private SwerveDriveClass swerveDrive;

	DriveTrain( int brAngle, int brDrive, int brEnc,
				int blAngle, int blDrive, int blEnc,
				int frAngle, int frDrive, int frEnc,
				int flAngle, int flDrive, int flEnc){
		backRight = new WheelDrive(40, 43, 3);
		backLeft = new WheelDrive(45, 46, 2);
		frontRight = new WheelDrive(42, 41, 1);
		frontLeft = new WheelDrive(47, 44, 0);
		
		swerveDrive = new SwerveDriveClass(backRight, backLeft, frontRight, frontLeft);
	}

	public DriveTrain(){
		backRight = new 
		WheelDrive(45, 43, 3);
		backLeft = new WheelDrive(42, 47, 2);
		frontRight = new WheelDrive(46, 40, 1);
		frontLeft = new WheelDrive(41, 44, 0);

		swerveDrive = new SwerveDriveClass(backRight, backLeft, frontRight, frontLeft);

		gyro = new ADXRS450_Gyro();
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new JoystickDrive());

	}

	public void ResetGyro(){ //had to change from ResetGryo to ResetGyro so if that wasn´t something I was supposed to change feel free to change it back
		gyro.reset();
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
}