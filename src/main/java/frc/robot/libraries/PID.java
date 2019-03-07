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
    private double integral = 0;
    private double derivative = 0;
    private double previous_error = 0;
    private double setpoint = 0;
    private double current = 0;
    private double error = 0;
    private double output = 0;
    private double min = 0;
    private double max = 0;
    private double deltaTime = 0.02;

    public PID(double p, double i, double d){
        this.P = p;
        this.I = i;
        this.D = d;

        //set previous error for first iteration
        previous_error = 0;
    }

    public void setOutputRange(double min, double max){
        this.min = min;
        this.max = max;
    }

    public void setSetpoint(double setpoint){
        this.setpoint = setpoint;
    }

    public void setCurrentState(double current){
        this.current = current;
    }

    public void run(){
        error = setpoint - current; // Error = Target - Actual

        integral += (error*deltaTime); // Integral is increased by the error*time (which is .02 seconds using normal IterativeRobot)
        derivative = (error - previous_error) / deltaTime;

        //set integral within bounds
        if(integral > max){
            integral = max;
        }
        if(integral < min){
            integral = min;
        }

        output = P*error + I*integral + D*derivative;

        //set output within bounds
        if(output > max){
            output = max;
        }
        if(output < min){
            output = min;
        }

        //set previous error for next iteration
        previous_error = error;
    }

    public double getOutput(){
        run();
        return output;
    }
}
