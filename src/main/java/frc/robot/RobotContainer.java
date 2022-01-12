// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

// import OIs
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.DefaultDrive;
import frc.robot.subsystems.DriveSubsystem;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  private final DefaultDrive m_defaultDrive;
  private final DriveSubsystem m_drive;
  private static RobotContainer m_robotContainer;
  // OIs instance
  private final Joystick m_stick = new Joystick(0);
  private final Joystick m_bigStick = new Joystick(0);


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    m_drive = DriveSubsystem.getInstance(this); // intialize drive subsystem
    m_defaultDrive = new DefaultDrive(m_drive, this); // intialize command
    m_drive.setDefaultCommand(m_defaultDrive); // set default for drivesubsystem
  }


  public DefaultDrive get_defaultDrive(){
    return m_defaultDrive;
  }

  public static RobotContainer getInstance(){
    if(m_robotContainer == null){
      m_robotContainer = new RobotContainer();
    } 
    return m_robotContainer;
   }

   public Joystick stick() {
    return m_stick;
  }

  public Joystick bigStick() {
    return m_bigStick;
  }
  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */


  private void configureButtonBindings() {}

  public Joystick getStick() 
  {
    return this.m_stick;
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
