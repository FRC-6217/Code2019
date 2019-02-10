package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Grabbers extends Command{
    private boolean buttonOpenValue;
    private boolean buttonCloseValue;
    private DoubleSolenoid.Value direction;

    public Grabbers() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.m_pneumatics);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        buttonOpenValue = Robot.m_oi_pilot.joystick.getRawButton(1);
        buttonCloseValue = Robot.m_oi_pilot.joystick.getRawButton(2);

        if(buttonOpenValue){
            direction = DoubleSolenoid.Value.kForward;
        }
        else if(buttonCloseValue){
            direction = DoubleSolenoid.Value.kReverse;
        }
        else{
            direction = DoubleSolenoid.Value.kOff;
        }
        Robot.m_pneumatics.runSolenoid(direction);
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