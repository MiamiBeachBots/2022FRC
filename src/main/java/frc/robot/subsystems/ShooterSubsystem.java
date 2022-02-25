// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj.Servo;

public class ShooterSubsystem extends SubsystemBase {
  /** Creates a new ShooterSubsystem. */
  public static ShooterSubsystem m_shoot;
  public final RobotContainer m_robotContainer;
  public ShooterSubsystem(RobotContainer robotContainer) {
    m_robotContainer = robotContainer;
  }

  Victor m_shooter = new Victor(6);
  //servo = new Servo(0);
  Servo servo1 = new Servo(9);
  //Servo servo2 = new Servo(8);

  public static ShooterSubsystem getInstance(RobotContainer rc){
    if (m_shoot == null) {
      m_shoot = new ShooterSubsystem(rc);
    }
    return m_shoot;
  }

  public void shoot(double shotSpeed){
    if (shotSpeed >= 1 && shotSpeed <= -1){
      m_shooter.set(shotSpeed);
    }
  }

  public void lift(double position){
    servo1.set(position);
    //servo2.set(position);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
