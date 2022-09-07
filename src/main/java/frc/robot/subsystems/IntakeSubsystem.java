// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.motorcontrol.Victor;

public class IntakeSubsystem extends SubsystemBase {
  /** Creates a new Intake. */

  public static IntakeSubsystem m_intake;
  public final RobotContainer m_robotContainer;
  public Victor m_intakeMotorFront = new Victor(5);
  public Victor m_intakeMotorBack = new Victor(8);

  public IntakeSubsystem(RobotContainer m_robotContainer) {
    this.m_robotContainer = m_robotContainer;
  }

  public static IntakeSubsystem getInstance(RobotContainer rc){
    if (m_intake == null) {
      m_intake = new IntakeSubsystem(rc);
    }
    return m_intake;
  }

  public void frontIntake(double liftSpeed) 
  {
    m_intakeMotorFront.set(liftSpeed);
  }

  public void backIntake(double liftSpeed) {
    m_intakeMotorBack.set(liftSpeed);
  }

  public void intake(double liftSpeed) {
    m_intakeMotorBack.set(liftSpeed);
    m_intakeMotorFront.set(liftSpeed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
