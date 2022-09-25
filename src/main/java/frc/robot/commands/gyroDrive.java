// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveSubsystem;

public class gyroDrive extends CommandBase {
  /** Creates a new gyroDrive. */
  private final DriveSubsystem m_drive;

  private final RobotContainer m_robotContainer;

  public gyroDrive(DriveSubsystem m_drive, RobotContainer robotContainer) {
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
    // this is in test phase
    // It's going super slow so I can see if its being run
    this.m_drive.gyroDrive(0.8 * (this.m_robotContainer.getStick().getY()));
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
