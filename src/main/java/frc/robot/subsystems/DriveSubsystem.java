// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Victor;

public class DriveSubsystem extends SubsystemBase {
  /** Creates a new DriveSubsystem. */

  public static DriveSubsystem m_drive;
  private double rspeed = 0;
  private double lspeed = 0;
  private final double delta = 0.05;
  private final double maxSpeed = 0.8;

  double kP = 0.0075;

  private final RobotContainer m_robotContainer;

  Victor m_frontRight = new Victor(1); //added motor 1 and 2 but we should group them in speed controller groups and call them right and left side them put them into differnetial drive
  Victor m_backLeft = new Victor(2);
  Victor m_backRight = new Victor(3);
  Victor m_frontLeft = new Victor(4);

  MotorControllerGroup m_left = new MotorControllerGroup(m_backLeft, m_frontLeft);
  MotorControllerGroup m_right = new MotorControllerGroup(m_frontRight, m_backRight);

  DifferentialDrive m_ddrive = new DifferentialDrive(m_left, m_right);

  public DriveSubsystem(RobotContainer robotContainer) {
  m_robotContainer = robotContainer;
  }

  
  public static DriveSubsystem getInstance(RobotContainer rc){
    if (m_drive == null){
      m_drive = new DriveSubsystem(rc);
    }
    return m_drive;
  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    m_ddrive.tankDrive(leftSpeed, rightSpeed);
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
