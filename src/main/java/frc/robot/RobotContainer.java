// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.ADIS16448_IMU;
// import OIs
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.DefaultDrive;
import frc.robot.commands.autoCommand;
import frc.robot.commands.backwardAuto;
import frc.robot.commands.gyroDrive;
import frc.robot.commands.stopCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  private final DefaultDrive m_defaultDrive;
  private final DriveSubsystem m_drive;
  private final ShooterSubsystem m_shoot;
  private final IntakeSubsystem m_intake;
  private final ElevatorSubsystem m_elevator;
  private final gyroDrive m_gyroDrive;
  private final autoCommand m_autoCommand;
  private final backwardAuto m_backwardAuto;
  private final stopCommand m_stopCommand;

  private static RobotContainer m_robotContainer;
  // OIs instance
  private final Joystick m_stick = new Joystick(0);
  private final Joystick m_bigStick = new Joystick(1);

  private final ADIS16448_IMU gyro =
      new ADIS16448_IMU(ADIS16448_IMU.IMUAxis.kZ, SPI.Port.kMXP, ADIS16448_IMU.CalibrationTime._1s);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    m_drive = DriveSubsystem.getInstance(this); // intialize drive subsystem
    m_defaultDrive = new DefaultDrive(m_drive, this); // intialize command
    m_gyroDrive = new gyroDrive(m_drive, this);
    // m_drive.setDefaultCommand(m_defaultDrive); // set default for drivesubsystem
    m_autoCommand = new autoCommand(m_drive, this);
    m_shoot = ShooterSubsystem.getInstance(this);
    m_intake = IntakeSubsystem.getInstance(this);
    m_elevator = ElevatorSubsystem.getInstance(this);
    m_backwardAuto = new backwardAuto(m_drive, this);
    m_stopCommand = new stopCommand(m_drive, this);
  }

  public DefaultDrive getDefaultDrive() {
    return m_defaultDrive;
  }

  public gyroDrive getGyroDrive() {
    return m_gyroDrive;
  }

  public void doGyroTurn() {
    // Not used yet, this is a test
    m_drive.rotateToAngle(180);
  }

  public void doLift(double position) {
    m_shoot.lift(position);
  }

  public void doElevatorLift(double speed) {
    m_elevator.lift(speed);
  }

  public void doFrontIntake(double speed) {
    m_intake.frontIntake(speed);
  }

  public void doBackIntake(double speed) {
    m_intake.backIntake(speed);
  }

  public void doIntake(double speed) {
    m_intake.intake(speed);
  }

  public void doShoot(double speed) {
    m_shoot.shoot(speed);
  }

  public static RobotContainer getInstance() {
    if (m_robotContainer == null) {
      m_robotContainer = new RobotContainer();
    }
    return m_robotContainer;
  }

  public Joystick stick() {
    return m_stick;
  }

  public ADIS16448_IMU getGyro() {
    return gyro;
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

  public Joystick getStick() {
    return this.m_stick;
  }

  public Joystick getBigStick() {
    return this.m_bigStick;
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

  public Command getBackwordCommand() {
    return m_backwardAuto;
  }

  public Command getStopCommand() {
    return m_stopCommand;
  }
}
