// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.motorcontrol.Victor;

public class ElevatorSubsystem extends SubsystemBase {
  /** Creates a new ElevatorSubsystem. */
  public static ElevatorSubsystem m_elevator;
  public final RobotContainer m_robotContainer;
  public Victor m_motorDaisyChain = new Victor(0);

  public ElevatorSubsystem(RobotContainer m_robotContainer) {
      this.m_robotContainer = m_robotContainer;
  }

  public static ElevatorSubsystem getInstance(RobotContainer rc) {
      if (m_elevator == null) {
        m_elevator = new ElevatorSubsystem(rc);
      }

      return m_elevator;
  }

  public void lift(double speed) {
    this.m_motorDaisyChain.set(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
