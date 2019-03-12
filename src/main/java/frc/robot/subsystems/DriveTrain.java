package frc.robot.subsystems;

import frc.robot.commands.JoystickDrive;
import frc.robot.libraries.WheelDrive;
import frc.robot.libraries.SwerveDriveClass;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem {
	private WheelDrive backRight = new WheelDrive(45, 43, 3);
	private WheelDrive backLeft = new WheelDrive(42, 47, 2);
	private WheelDrive frontRight = new WheelDrive(46, 40, 1);
	private WheelDrive frontLeft = new WheelDrive(41, 44, 0);
	private Gyro gyro = new ADXRS450_Gyro();
	private double x1;
	private double y1;

	private SwerveDriveClass swerveDrive = new SwerveDriveClass(backRight, backLeft, frontRight, frontLeft);

	public void initDefaultCommand() {
		setDefaultCommand(new JoystickDrive());

	}

	public void ResetGryo(){
		gyro.reset();
	}

	public Gyro returnGyro(){
		return gyro;
	}
	public double TransformX(double x, double y){
		x1 = (x * Math.cos((GetAngle() * (Math.PI / 180)))) - (y * Math.sin((GetAngle() * (Math.PI / 180))));
		return x1;
	}

	public double TransformY(double x, double y){
		y1 = (x * Math.sin(GetAngle() * (Math.PI / 180))) + (y * Math.cos(GetAngle() * (Math.PI / 180)));
		return y1;
	}

	public double GetAngle(){
		SmartDashboard.putNumber("Gryo", -gyro.getAngle());
		return -gyro.getAngle();
	}

	public void Drive(double x, double y, double z, double governer) {
		swerveDrive.drive(x*governer, y*governer, z*governer);
	}

	public void UseFL(double speed, double angle) {
		frontLeft.drive(speed, angle);
	}
	
	public void UseFR(double speed, double angle) {
		frontRight.drive(speed, angle);
	}

	public void UseBL(double speed, double angle) {
		backLeft.drive(speed, angle);
	}

	public void UseBR(double speed, double angle) {
		backRight.drive(speed, angle);
	}
}