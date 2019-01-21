package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.Lift;

public class PneumaticsLift extends Subsystem{
    private DoubleSolenoid sol1 = new DoubleSolenoid(5, 6);
    
    
    
    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new Lift());
    }

    public void runSolenoid(DoubleSolenoid.Value direction){
        sol1.set(direction);
    }

}