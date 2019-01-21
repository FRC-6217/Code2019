package frc.robot.subsystems;

import frc.robot.commands.JoystickDrive;
import frc.robot.libraries.WheelDrive;
import frc.robot.libraries.SwerveDriveClass;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
	private WheelDrive backRight = new WheelDrive(45, 46, 2);
	private WheelDrive backLeft = new WheelDrive(47, 44, 0);
	private WheelDrive frontRight = new WheelDrive(40, 43, 3);
	private WheelDrive frontLeft = new WheelDrive(42, 41, 1);
	private ADXRS450_Gyro gyro = new ADXRS450_Gyro();
	private double x1;
	private double y1;

	private SwerveDriveClass swerveDrive = new SwerveDriveClass(backRight, backLeft, frontRight, frontLeft);

	public void initDefaultCommand() {
		setDefaultCommand(new JoystickDrive());

	}

	public void ResetGryo(){
		gyro.reset();
	}

	public double TransformX(double x, double y){
		x1 = (x * Math.cos(Math.toRadians(gyro.getAngle()))) - (y * Math.sin(Math.toRadians(gyro.getAngle())));
		return x1;
	}

	public double TransformY(double x, double y){
		y1 = (x * Math.sin(Math.toRadians(gyro.getAngle()))) + (y * Math.cos(Math.toRadians(gyro.getAngle())));
		return y1;
	}

	public void Drive(double x, double y, double z, double governer) {
		swerveDrive.drive(x*governer, y*governer, z*governer);
	}
}