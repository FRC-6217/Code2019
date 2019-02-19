/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.libraries;

/**
 * Add your docs here.
 */
public class PID {
    public double p;
    public double i;
    public double d;
    private double prevError = 0;
    private double prevTime = 0;
    private double error;
    private long time;
    private double setpoint;
    private double dt;
    private double output;

    public PID(double p, double i, double d){
        this.p = p;
        this.i = i;
        this.d = d;
    }

    public double update(double current){
        error = (setpoint - current);
        time = System.currentTimeMillis();
        dt = time - prevTime;

        output = (p*error) + ((error + prevError)*i*dt) + ((prevError - error)*d/dt);

        prevError = error;
        prevTime = time;

        if(output > 1){
            output = 1;
        }
        else if(output < -1){
            output = -1;
        }

        return output;
    }

    public void setSetpoint(double setpoint){
        this.setpoint = setpoint;

    }
}
