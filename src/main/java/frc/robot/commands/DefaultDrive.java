// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveSubsystem;

public class DefaultDrive extends CommandBase {
  // props
  private final DriveSubsystem m_drive;
  private final RobotContainer m_robotContainer;

  private final Double MAX_SPEED = 0.7;
  
  /** Creates a new DefaultDrive. */
  public DefaultDrive(DriveSubsystem m_drive, RobotContainer robotContainer) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.m_drive = m_drive;
    this.m_robotContainer = robotContainer;

    addRequirements(m_drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
     this.m_drive.tankDrive2(MAX_SPEED * (this.m_robotContainer.getStick().getThrottle()), -MAX_SPEED * (this.m_robotContainer.getStick().getY()));
  }
  //test function for autonomous, not used yet
  public void autoDrive(boolean stop) 
  {
    if (!stop) {
      this.m_drive.tankDrive2(0, 0);
    } else {
      this.m_drive.tankDrive2(-0.5, 0.5);
    }
  }

  public void backward()
  {
    this.m_drive.tankDrive2(0.6, -0.6);
  }

  
  public void stop()
  {
    this.m_drive.tankDrive2(0, 0);
  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
