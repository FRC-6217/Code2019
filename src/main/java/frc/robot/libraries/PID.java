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
    //default pid values
    private double P, I, D = 1;

    //variables for calculating
    private double integral, derivative, previous_error, setpoint, current, error, output = 0;

    public PID(double p, double i, double d){
        this.P = p;
        this.I = i;
        this.D = d;

        //set previous error for first iteration
        previous_error = 0;
    }

    public void setSetpoint(double setpoint){
        this.setpoint = setpoint;
        integral = 0;
    }

    public void setCurrentState(double current){
        this.current = current;
    }

    public void run(){
        error = setpoint - current; // Error = Target - Actual

        integral += (error*.02); // Integral is increased by the error*time (which is .02 seconds using normal IterativeRobot)
        derivative = (error - previous_error) / .02;

        output = P*error + I*integral + D*derivative;

        //set previous error for next iteration
        previous_error = error;
    }

    public double getOutput(){
        run();
        return output;
    }
}
