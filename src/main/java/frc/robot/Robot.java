// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoSink;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update thebuild.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private NetworkTableEntry cameraSelection;
  private RobotContainer m_robotContainer;

  private UsbCamera usbCamera1;
  private UsbCamera usbCamera2;
  private VideoSink server;

  private int cameraCounter = 2;
  private boolean autoDriveCounter = true;
  private boolean autoShootCounter = true;

  private AnalogInput ultrasonic1 = new AnalogInput(0);
  // private AnalogInput ultrasonic2 = new AnalogInput(1);
  // private AnalogInput ultrasonic3 = new AnalogInput(2);
  private Timer robotTimer = new Timer();

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();

    usbCamera1 = CameraServer.startAutomaticCapture(0);
    usbCamera2 = CameraServer.startAutomaticCapture(1);

    server = CameraServer.getServer();
    m_robotContainer.getGyro().calibrate();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();
    robotTimer.start();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // Uses Ultrasonic sensor to stop 36 inches before the wall
    // Test Code to test the Ultrasonic
    if (robotTimer.get() <= 4) {
      m_robotContainer.getDefaultDrive().autoDrive(false);
      // m_robotContainer.doLift(1);
      // m_robotContainer.doElevatorLift(-1);
      m_robotContainer.doShoot(0.60);
    } else if (robotTimer.get() >= 4.5 && robotTimer.get() < 6) {
      m_robotContainer.doElevatorLift(-1);
      m_robotContainer.doShoot(0.60);
    } else if (robotTimer.get() >= 6 && robotTimer.get() < 10) {
      m_robotContainer.getDefaultDrive().backward();
      // m_robotContainer.doLift(0);
    } else {
      m_robotContainer.getDefaultDrive().stop();
    }

    // else if ((getRangeInches(ultrasonic1) <= 45) && robotTimer.get() >= 4.5) {
    //   m_robotContainer.getDefaultDrive().autoDrive(false);
    //   m_robotContainer.doLift(1);
    //   m_robotContainer.doElevatorLift(-0.9);
    //   m_robotContainer.doShoot(0.60);

    // } else {
    //   m_robotContainer.getDefaultDrive().autoDrive(true);
    // }

    SmartDashboard.putNumber("Auto Sensor", getRangeInches(ultrasonic1));
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

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    // This is an if statement to switch from default tank drive to gyroDrive, which should
    // straigten us out
    // There is a sequencing issue with the commands, the gyroDrive comamnd is getting overwritten
    // by the default drive
    if (m_robotContainer.stick().getRawButton(6)) {
      m_robotContainer.getGyroDrive().execute();
      System.out.println("Pressing Button 6");
    } else {
      m_robotContainer.getDefaultDrive().execute();
    }

    // If button press then reset the gyro
    if (m_robotContainer.stick().getRawButtonPressed(2)) {
      m_robotContainer.getGyro().reset();
    }
    if (m_robotContainer.stick().getRawButtonPressed(4)) {
      m_robotContainer.doShoot(1);
    }

    // Switches Camera input on Smartdashboard
    if (m_robotContainer.getStick().getRawButtonPressed(1)) {
      cameraCounter++;
      if (cameraCounter % 2 == 0) {
        System.out.println("Setting Camera 2");
        server.setSource(usbCamera2);
      } else {
        System.out.println("Setting Camera 1");
        server.setSource(usbCamera1);
      }
    }

    //   /*
    //   Big stick IO
    //   */
    //   //Intake Code
    if (m_robotContainer.getStick().getRawButton(8)) {
      m_robotContainer.doBackIntake(0.35); // Find out this value for best intake
    } else {
      m_robotContainer.doBackIntake(0);
    }

    if (m_robotContainer.getStick().getRawButton(7)) {
      m_robotContainer.doFrontIntake(0.45);
    } else {
      m_robotContainer.doFrontIntake(0);
    }
    if (m_robotContainer.getStick().getRawButton(4)) {
      m_robotContainer.doIntake(-0.45);
    }

    //   /*
    //   Auto system
    // */

    if (m_robotContainer.getBigStick().getRawButton(1)) {
      m_robotContainer.doElevatorLift(m_robotContainer.getBigStick().getY());
      m_robotContainer.doLift(1);
      m_robotContainer.doShoot(0.60);
      // m_robotContainer.getBigStick().getRawAxis(3)
    } else {
      m_robotContainer.doLift(0);
      m_robotContainer.doShoot(0);
      m_robotContainer.doElevatorLift(0);
    }

    // if(getRangeInches(ultrasonic1) <= 3) {
    //   m_robotContainer.doLift(1);
    //   m_robotContainer.doElevatorLift(0.3);
    // } else {
    //   m_robotContainer.doElevatorLift(0);
    //   m_robotContainer.doLift(0);
    // }

    // m_robotContainer.doElevatorLift(-m_robotContainer.getBigStick().getY());
    //   m_robotContainer.doShoot(m_robotContainer.getStick().getY());

    SmartDashboard.putNumber("Ultrasonic Sensor Distance", getRangeInches(ultrasonic1));
    // SmartDashboard.putNumber("Ultrasonic Top Sensor Distance", getRangeInches(ultrasonic2));
    SmartDashboard.putNumber("Throttle", m_robotContainer.getStick().getThrottle());
    SmartDashboard.putNumber("Gyro Rate", m_robotContainer.getGyro().getRate());
    SmartDashboard.putNumber("Gyro angle", m_robotContainer.getGyro().getAngle());
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
    System.out.println("Test phrase initialized");
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
    // testDrive();
    // testIntake();
    // testShoot();
    // testAutoSystem();
    // testSwitchCamera();
    testSmartDashboard();
  }

  @Override
  public void simulationInit() {
    System.out.println("Simulation just started");
    // robotTimer.start();
  }

  @Override
  public void simulationPeriodic() {
    System.out.println("Running simulation");
    System.out.println(m_robotContainer.getBigStick().getRawAxis(3));
  }

  public double getRangeCentimerters(AnalogInput ultrasonicParam) {
    return getVoltageVals(ultrasonicParam) * 0.125;
  }

  public double getRangeInches(AnalogInput ultrasonicParam) {
    return getVoltageVals(ultrasonicParam) * 0.0492 - 12;
  }

  public double getVoltageVals(AnalogInput ultrasonic) {
    double rawValue = ultrasonic.getValue();
    double k = 5 / RobotController.getVoltage5V();
    double voltage = rawValue * k;
    return voltage;
  }

  public void testDrive() {
    if (m_robotContainer.stick().getRawButton(6)) {
      m_robotContainer.getGyroDrive().execute();
      System.out.println("Pressing Button 6");
    } else {
      m_robotContainer.getDefaultDrive().execute();
    }

    // If button press then reset the gyro
    if (m_robotContainer.stick().getRawButtonPressed(5)) {
      m_robotContainer.getGyro().calibrate();
    }
  }

  public void testSwitchCamera() {
    // Switches Camera input on Smartdashboard
    if (m_robotContainer.getStick().getRawButtonPressed(1)) {
      cameraCounter++;
      if (cameraCounter % 2 == 0) {
        System.out.println("Setting Camera 2");
        server.setSource(usbCamera2);
      } else {
        System.out.println("Setting Camera 1");
        server.setSource(usbCamera1);
      }
    }
  }

  // public void testShoot() {
  //   if (m_robotContainer.getBigStick().getRawButton(1)) {
  //     m_robotContainer.doShoot(0.5);
  //     if(getRangeInches(ultrasonic2) <= 3) {
  //       m_robotContainer.doElevatorLift(0.3);
  //     }
  //   }
  // }

  public void testIntake() {
    m_robotContainer.doFrontIntake(m_robotContainer.getBigStick().getY());
    m_robotContainer.doBackIntake(m_robotContainer.getBigStick().getZ());
    // m_robotContainer.doShoot(m_robotContainer.getBigStick().getY());
  }

  // public void testAutoSystem() {
  //   if(getRangeInches(ultrasonic1) <= 5) {
  //     m_robotContainer.doLift(1);
  //     m_robotContainer.doElevatorLift(0.2);
  //   } else {
  //     m_robotContainer.doElevatorLift(0);
  //     m_robotContainer.doLift(0);
  //   }
  // }

  public void testSmartDashboard() {
    // SmartDashboard.putNumber("Ultrasonic Sensor Distance", getRangeInches(ultrasonic1));
    // SmartDashboard.putNumber("Ultrasonic Top Sensor Distance", getRangeInches(ultrasonic2));
    SmartDashboard.putNumber("Throttle", m_robotContainer.getStick().getThrottle());
    SmartDashboard.putNumber("Gyro Rate", m_robotContainer.getGyro().getRate());
    SmartDashboard.putNumber("Gyro angle", m_robotContainer.getGyro().getAngle());
  }
}
