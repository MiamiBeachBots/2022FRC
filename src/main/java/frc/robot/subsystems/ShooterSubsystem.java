// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.Servo;
//import com.revrobotics.;

public class ShooterSubsystem extends SubsystemBase {
  /** Creates a new ShooterSubsystem. */
  public static ShooterSubsystem m_shoot;
  public final RobotContainer m_robotContainer;
  public ShooterSubsystem(RobotContainer robotContainer) {
    m_robotContainer = robotContainer;
  }

  PWMSparkMax m_shooter = new PWMSparkMax(7);
  PWMSparkMax m_shooter2 = new PWMSparkMax(6);

  Servo servo1 = new Servo(9);

  public static ShooterSubsystem getInstance(RobotContainer rc){
    if (m_shoot == null) {
      m_shoot = new ShooterSubsystem(rc);
    }
    return m_shoot;
  }

  public void shoot(double shotSpeed){
      m_shooter.set(-shotSpeed);
      m_shooter2.set(shotSpeed);
    
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
