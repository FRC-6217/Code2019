/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.GrabberArm;
import frc.robot.subsystems.Vacuum;
import frc.robot.commands.GoToHeightAuto;
import frc.robot.libraries.XboxController;
import frc.robot.subsystems.BallPickup;
import frc.robot.subsystems.Elevator;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  
  public static DriveTrain m_driveTrain;
  public static GrabberArm m_grabberArm;
  public static BallPickup m_ballPickup;
  public static Elevator m_Elevator;
  public static Vacuum m_Vacuum;
  public static OI m_oi_pilot;
  public static XboxController m_oi_copilot;
  //to-do
  public static final int PORT_GRABBER_MOTOR = 48;
  public static final int PORT_GRABBER_ENC_A = 5;
  public static final int PORT_GRABBER_ENC_B = 6;
  public static final int USB_PILOT_PORT = 0;
  public static final int USB_COPILOT_PORT = 1;
  public static final int RIGHT_ARM_MOTOR_CHANNEL = 7;
  public static final int LEFT_ARM_MOTOR_CHANNEL = 8;
  public static final int BALL_GRABBER_WHEEL_MOTOR = 6;
  public static final int LIMIT_SWITCH_BALL_PICKUP_UP = 11;
  public static final int LIMIT_SWITCH_BALL_PICKUP_DOWN = 10;
  public static final int ELEVATOR_MOTOR_CHANNEL = 9;
  public static final int ELEVATOR_ENCODER_CHANNEL_A = 2;
  public static final int ELEVATOR_ENCODER_CHANNEL_B = 1;
  public static final int VACUUM_CHANNEL_60_PSI = 0;
  public static final int VACUUM_CHANNEL_20_PSI = 1;

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  //camera objects
  UsbCamera cam1;
  //UsbCamera cam2;
  // VideoSink server;
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {

    m_driveTrain = new DriveTrain();
    //m_driveTrain.GetAngle();
    //m_pneumatics = new Pneumatics();
    m_ballPickup = new BallPickup(RIGHT_ARM_MOTOR_CHANNEL, LEFT_ARM_MOTOR_CHANNEL, BALL_GRABBER_WHEEL_MOTOR, 
        LIMIT_SWITCH_BALL_PICKUP_UP, LIMIT_SWITCH_BALL_PICKUP_DOWN);
    m_Elevator = new Elevator(ELEVATOR_MOTOR_CHANNEL, ELEVATOR_ENCODER_CHANNEL_A, ELEVATOR_ENCODER_CHANNEL_B);
    m_Vacuum = new Vacuum(VACUUM_CHANNEL_60_PSI, VACUUM_CHANNEL_20_PSI);
    m_oi_copilot = new XboxController(USB_COPILOT_PORT);
    m_grabberArm = new GrabberArm(PORT_GRABBER_MOTOR, PORT_GRABBER_ENC_A, PORT_GRABBER_ENC_B);
    m_oi_pilot = new OI();

    // chooser.addOption("My Auto", new MyAutoCommand());
    SmartDashboard.putData("Auto mode", m_chooser);

    // Preferences pref = Preferences.getInstance();
    // m_Elevator.SCALAR = pref.getDouble("Scalar", 1);

    //camera thread
    // Vision vis = new Vision();
    // Thread visionthread = new Thread(vis);
    // visionthread.start();

    //camera

    cam1 = CameraServer.getInstance().startAutomaticCapture(0);
    //cam2 = CameraServer.getInstance().startAutomaticCapture(1);

    cam1.setBrightness(30);
    //cam2.setBrightness(30);

    cam1.setResolution(320, 240);
    //cam2.setResolution(320, 240);
    // server = CameraServer.getInstance().getServer();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    // double h = 26.75;
    // GoToHeightAuto gtha = new GoToHeightAuto(h);
    // if (m_oi_pilot.joystick.getRawButton(10)) {
    //   gtha.run();
    // }

    //m_Elevator.updatePosition();
    // if(m_oi_pilot.joystick.getRawButton(1)) {
    //   server.setSource(cam1);
    // }
    // else if(m_oi_pilot.joystick.getRawButton(2)) {
    //   server.setSource(cam2);
    // }
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
