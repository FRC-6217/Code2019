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
    private double minIn = 0;
    private double maxIn = 0;
    private double minOut = 0;
    private double maxOut = 0;
    private double deltaTime = 0.02;
    private double forward = 0;
    private double reverse = 0;
    private boolean continuous = false;

    public PID(double p, double i, double d){
        this.P = p;
        this.I = i;
        this.D = d;

        //set previous error for first iteration
        previous_error = 0;
    }

    public void setInputRange(double min, double max){
        this.minIn = min;
        this.maxIn = max;
    }

    public void setOutputRange(double min, double max){
        this.minOut = min;
        this.maxOut = max;
    }

    public void setSetpoint(double setpoint){
        this.setpoint = setpoint;
    }

    public void setCurrentState(double current){
        this.current = current;
    }

    public void setContinuous(boolean setContinuous){
        continuous = setContinuous;
    }

    public void setContinuous(){
        setContinuous(true);
    }

    public void run(){
        if(!continuous){
            error = setpoint - current; // Error = Target - Actual
        }
        else{
            forward = setpoint - current;
            reverse = current - setpoint;

            if(forward < minIn){
                forward += maxIn;
            }
            if(forward > maxIn){
                forward -= maxIn;
            }

            if(reverse < minIn){
                reverse += maxIn;
            }
            if(reverse > maxIn){
                reverse -= maxIn;
            }

            if(forward < reverse){
                error = setpoint - current;
            }
            else{
                error = current - setpoint;
            }
        }

        integral += (error*deltaTime); // Integral is increased by the error*time (which is .02 seconds using normal IterativeRobot)
        derivative = (error - previous_error) / deltaTime;

        //set integral within bounds
        if(integral > maxOut){
            integral = maxOut;
        }
        if(integral < minOut){
            integral = minOut;
        }

        output = P*error + I*integral + D*derivative;

        //set output within bounds
        if(output > maxOut){
            output = maxOut;
        }
        if(output < minOut){
            output = minOut;
        }

        //set previous error for next iteration
        previous_error = error;
    }

    public double getOutput(){
        run();
        return output;
    }
}
