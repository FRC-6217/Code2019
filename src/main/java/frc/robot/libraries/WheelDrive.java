package frc.robot.libraries;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;

public class WheelDrive {
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

	public void drive(double speed, double angle) {
		angle *= 180;
		angle += 180;
		f1 = angle - (enc.getVoltage()*(7200/99));
		f2 = (enc.getVoltage()*(7200/99)) - angle;
		
		if (f1 < 0){
			f1 += 360;
		}
		if (f2 < 0){
			f2 += 360;
		}

		rAngle = angle + 180;
		rAngle = (rAngle > 360 ) ? rAngle - 360 : rAngle;

		r1 = rAngle - (enc.getVoltage()*(7200/99));
		r2 = (enc.getVoltage()*(7200/99)) - rAngle;
		
		if (r1 < 0){
			r1 += 360;
		}
		
		if(r2 < 0){
			r2 += 360;
		} 

		shortest = f1;
		isF = true;

		if(shortest > f2){
			shortest = f2;
		}

		if(shortest > r1){
			shortest = r1;
			isF = false;
		}

		if (shortest > r2){
			shortest = r2;
			isF = false;
		}
		
		if(!isF){
			angle = rAngle;
			speed *= -1;
		}

		angle -= 180;
		angle /= 180;
		
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

