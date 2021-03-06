package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

/**
 *
 */
public class JoystickDrive extends Command {
    
    private double x;
    private double y;
    private double z;
    private boolean gyroButtonForward;
    private boolean gyroButtonBackward;
    private double governer;
    
    private boolean isReversed;
    private double x1;
    private double y1;
    
    public JoystickDrive() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.m_driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.m_driveTrain.ResetGyro();   
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        x = Robot.m_oi_pilot.joystick.getRawAxis(0);
        y = Robot.m_oi_pilot.joystick.getRawAxis(1);
        z = Robot.m_oi_pilot.joystick.getRawAxis(2);
        gyroButtonForward = Robot.m_oi_pilot.joystick.getRawButton(5);
        gyroButtonBackward = Robot.m_oi_pilot.joystick.getRawButton(6);        
        governer = Robot.m_oi_pilot.joystick.getRawAxis(3);
    
        if(gyroButtonForward){
            Robot.m_driveTrain.ResetGyro();
            isReversed = false;
        }
        else if(gyroButtonBackward){
            Robot.m_driveTrain.ResetGyro();
            isReversed = true;
        }
        
        x = (Math.abs(x) > .2 ? x : 0.0);
        y = (Math.abs(y) > .2 ? y : 0.0);
        z = (Math.abs(z) > .2 ? z : 0.0);

        x1 = Robot.m_driveTrain.TransformX(x, y, isReversed);
        y1 = Robot.m_driveTrain.TransformY(x, y, isReversed);

        Robot.m_driveTrain.Drive (x1, -y1, z, Math.abs(governer-1));
        gyroButtonForward = false;
        gyroButtonBackward = false;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	isFinished();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}