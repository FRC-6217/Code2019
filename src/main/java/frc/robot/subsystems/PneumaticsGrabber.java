package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.Grabbers;

public class PneumaticsGrabber extends Subsystem{
    private DoubleSolenoid sol1 = new DoubleSolenoid(1, 2);
    
    
    
    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new Grabbers());
    }

    public void runSolenoid(DoubleSolenoid.Value direction){
        sol1.set(direction);
    }

}