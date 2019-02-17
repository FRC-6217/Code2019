/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
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
    public double SCALAR = 0.0013790733942556592330823762591;
    private static final double bOffset = 16.75;
    private double upSpeed = .8;
    private double downSpeed = .4;
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

    public void setUpSpeed(double upSpeed) {
        if(upSpeed < 0) {
            upSpeed = upSpeed * -1;
        }
        else if(upSpeed > 1) {
            upSpeed = 1;
        }

        this.upSpeed = upSpeed;    

    }

    public void setDownSpeed(double downSpeed) {
        if(downSpeed < 0) {
            downSpeed = downSpeed * -1;
        }
        else if(downSpeed > 1) {
            downSpeed = 1;
        }

        this.downSpeed = downSpeed;    

    }

    public void goUp() {
        motor.setSpeed(upSpeed);
        direction = Direction.UP;
    }

    public void goDown() {
        motor.setSpeed(-downSpeed);
        direction = Direction.DOWN;
    }

    public void stop() {
        motor.setSpeed(0);
        direction = Direction.NONE;
    }

    public double increaseUpSpeedBy(double amount) {
        upSpeed += amount;
        if(debugEnable) {
        //    System.out.println("Speed is set to " + speed);
        }
        return upSpeed;
    }

    public double increaseDownSpeedBy(double amount) {
        downSpeed += amount;
        if(debugEnable) {
        //    System.out.println("Speed is set to " + speed);
        }
        return downSpeed;
    }

    public double decreaseUpSpeedBy(double amount) {
        upSpeed -= amount;
        if(debugEnable) {
         //   System.out.println("Speed is set to: " + speed);
        }
        return upSpeed;
    }

    public double decreaseDownSpeedBy(double amount) {
        downSpeed -= amount;
        if(debugEnable) {
         //   System.out.println("Speed is set to: " + speed);
        }
        return downSpeed;
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
    public double updatePosition() {
        double pos = encoder.get();
        position = pos * SCALAR + bOffset;

        SmartDashboard.putNumber("Raw Encoder", pos);
        SmartDashboard.putNumber("Encoder Position in", position);

        return position;
    }
    public void resetEnc() {
        encoder.reset();
    }
}