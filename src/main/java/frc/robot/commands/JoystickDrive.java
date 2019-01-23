package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 *
 */
public class JoystickDrive extends Command {
    
    private double x;
    private double y;
    private double z;
    private boolean gyroButton;
    private double governer;

    private double x1;
    private double y1;

    public JoystickDrive() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.m_driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.m_driveTrain.ResetGryo();   
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        x = Robot.m_oi.joystick.getRawAxis(0);
        y = Robot.m_oi.joystick.getRawAxis(1);
        z = Robot.m_oi.joystick.getRawAxis(2);
        gyroButton = Robot.m_oi.joystick.getRawButton(7);
        governer = Robot.m_oi.joystick.getRawAxis(3);
    
        if(gyroButton){
            Robot.m_driveTrain.ResetGryo();
        }
        
        x = (Math.abs(x) > .2 ? x : 0.0);
        y = (Math.abs(y) > .2 ? y : 0.0);
        z = (Math.abs(z) > .2 ? z : 0.0);

        x1 = Robot.m_driveTrain.TransformX(x, y);
        y1 = Robot.m_driveTrain.TransformY(x, y);

        


        Robot.m_driveTrain.Drive (x1, -y1, z, Math.abs(governer-1));
        gyroButton = false;
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