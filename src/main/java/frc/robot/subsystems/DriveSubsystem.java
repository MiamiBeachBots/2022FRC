// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

public class DriveSubsystem extends SubsystemBase {
  /** Creates a new DriveSubsystem. */
  public static DriveSubsystem m_drive;

  private double rSpeed = 0;
  private double lSpeed = 0;
  private double kAngleSetpoint = 0.0;
  private final double delta = 0.05;
  double kP = 0.015;

  // getters
  public double getRspeed() {
    return this.rSpeed;
  }

  public double getLspeed() {
    return this.lSpeed;
  }

  private final RobotContainer m_robotContainer;

  Victor m_frontRight =
      new Victor(4); // added motor 1 and 2 but we should group them in speed controller groups and
  // call them right and left side them put them into differnetial drive
  Victor m_backLeft = new Victor(2);
  Victor m_backRight = new Victor(3);
  Victor m_frontLeft = new Victor(1);

  MotorControllerGroup m_left = new MotorControllerGroup(m_backLeft, m_frontLeft);
  MotorControllerGroup m_right = new MotorControllerGroup(m_frontRight, m_backRight);

  DifferentialDrive m_ddrive = new DifferentialDrive(m_left, m_right);

  public DriveSubsystem(RobotContainer robotContainer) {
    m_robotContainer = robotContainer;
  }

  public static DriveSubsystem getInstance(RobotContainer rc) {
    if (m_drive == null) {
      m_drive = new DriveSubsystem(rc);
    }
    return m_drive;
  }

  // TankDrive2 is name for just default drive tankdrive
  public void tankDrive2(double leftSpeed, double rightSpeed) {
    m_ddrive.tankDrive(leftSpeed, rightSpeed);
  }

  public void gyroDrive(double ySpeed) {
    // Testing this feature out right now, nothing is working.
    // I need to find an equation to convert an angle into an error so that we can
    // use it to adjust the steering
    double turningValue =
        (kAngleSetpoint
            - ((m_robotContainer.getGyro().getAngle()
                    + Math.copySign(15.0, m_robotContainer.getGyro().getAngle()))
                * kP));
    // double compensation = Math.copySign(turningValue, ySpeed);
    m_ddrive.arcadeDrive(turningValue, ySpeed);
  }

  // Not used. This is a test code usage
  public void rotateToAngle(double targetAngle) {
    double error = targetAngle - m_robotContainer.getGyro().getAngle();
    if (error > 1) {
      m_ddrive.tankDrive(0.3 + kP * error, 0.3 - kP * error);
    }
    m_ddrive.tankDrive(0.3 + kP * error, 0.3 - kP * -error);
  }

  public void backward() {
    m_ddrive.tankDrive(0.4, -0.4);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
