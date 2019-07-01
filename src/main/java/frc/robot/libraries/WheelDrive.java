package frc.robot.libraries;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WheelDrive {
	public double angle = 0;
	private VictorSPX angleMotor;
	private VictorSPX speedMotor;
	private VictorSPX_PIDOutput motorPID;
	private PIDController pidController;
	private final double MAX_VOLTS = 4.987;
	private AnalogInput enc;
	private double f1;
	private double f2;
	private double r1;
	private double r2;
	private double rAngle;
	private double shortest;
	private boolean isF;
	
	public WheelDrive(int angleMotor, int speedMotor, int encoder) {
		this.angleMotor = new VictorSPX(angleMotor);
		this.speedMotor = new VictorSPX(speedMotor);
		this.motorPID = new VictorSPX_PIDOutput(this.angleMotor);
		this.enc = new AnalogInput(encoder);
		// VictorSPX is not a subclass of PIDOutput;
		pidController = new PIDController(1, 0, 0.5, enc, this.motorPID);

		pidController.setInputRange(0.015, MAX_VOLTS);
		pidController.setOutputRange(-1, 1);
		pidController.setContinuous();
		pidController.enable();

	}

	public double returnAngle(String s){
		SmartDashboard.putNumber(s, (enc.getVoltage() * (7200 / 99)));
		return (enc.getVoltage()*(7200/99));
	}
	public void drive(double speed, double angle1) {
		angle = angle1;
		
		speedMotor.set(ControlMode.PercentOutput, speed);
			double setpoint = (angle * (MAX_VOLTS * 0.5)) + (MAX_VOLTS * 0.5); // Optimization offset can be calculated	here.
			if (setpoint < 0.015) {
				setpoint = MAX_VOLTS + setpoint;
			}
			if (setpoint > MAX_VOLTS) {
				setpoint = setpoint - MAX_VOLTS;
			}
			pidController.setSetpoint(setpoint);
	}
}

