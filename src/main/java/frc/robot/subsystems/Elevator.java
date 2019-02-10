/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.BallPickupJoystick;

/**
 * Add your docs here.
 */

public class Elevator extends Subsystem {
    //Measures in Inches
    private static final double MIN_HEIGHT = 0;
    private static final double MAX_HEIGHT = 1000;
    public double SCALAR = 1;
    private static final double bOffset = 20.25;
    private double speed = 1;
    private double position = 0;
    private boolean debugEnable = true;

    enum Direction {
       UP, DOWN, NONE; 
    }
    
    private Direction direction = Direction.NONE;
    private Spark motor;
    public Encoder encoder;
    
    public Elevator(int motorChannel, int encoderChannelA, int encoderChannelB) {
        motor = new Spark(motorChannel);
        encoder = new Encoder(encoderChannelA, encoderChannelB);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new BallPickupJoystick());
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    public void setSpeed(double speed) {
        if(speed < 0) {
            speed = speed * -1;
        }
        else if(speed > 1) {
            speed = 1;
        }

        this.speed = speed;    

    }

    public void goUp() {
        motor.setSpeed(speed);
        direction = Direction.UP;
    }

    public void goDown() {
        motor.setSpeed(-speed);
        direction = Direction.DOWN;
    }

    public void stop() {
        motor.setSpeed(0);
        direction = Direction.NONE;
    }

    public double increaseSpeedBy(double amount) {
        speed += amount;
        if(debugEnable) {
        //    System.out.println("Speed is set to " + speed);
        }
        return speed;
    }

    public double decreaseSpeedBy(double amount) {
        speed -= amount;
        if(debugEnable) {
         //   System.out.println("Speed is set to: " + speed);
        }
        return speed;
    }

    public double getHeight() {
        return position;
    }

    public void goTop() {
        goToHeight(MAX_HEIGHT);
    }

    public void goBottom() {
        goToHeight(MIN_HEIGHT);
    }

    public void changeHeightBy(double amount) {
        goToHeight(position + amount);
    }

    public void goToHeight(double height) {
        if(height > MAX_HEIGHT) {
            height = MAX_HEIGHT;
        }
        else if(height < MIN_HEIGHT) {
            height = MIN_HEIGHT;
        }

        if(position == height) {
            direction = Direction.NONE;
            return;
        }
        
        while(updatePosition() != height) {
            if(position < height) { //it needs to go up
                goUp();
            }
            else if(position > height) { //it needs to go down
                goDown();
            }
        }
        stop();
    }
    private double updatePosition() {
        double pos = encoder.getDistance();
        position = pos * SCALAR;

        SmartDashboard.putNumber("Raw Encoder", pos);
        SmartDashboard.putNumber("Encoder Position cm", position);

        return position;
    }
}