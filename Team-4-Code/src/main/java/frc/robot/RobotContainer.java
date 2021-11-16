/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.DriveSubsystem;

import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.JoystickConstants;


/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  private final DriveSubsystem m_drive = new DriveSubsystem();

  private final IntakeSubsystem m_robotIntake = new IntakeSubsystem();
  private final OuttakeSubsystem m_robotOuttake = new OuttakeSubsystem();


  private final RunCommand m_autoCommand = new RunCommand(() -> m_drive.tankDrive(5,5), m_drive);

  // The driver's controller

  static Joystick controller = new Joystick(JoystickConstants.kControllerPort);

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(controller, JoystickConstants.kA)
      .whenHeld(new InstantCommand(() -> m_robotIntake.intakeBalls(10), m_robotIntake))
      .whenReleased(new InstantCommand(m_robotIntake::stopIntakeBalls, m_robotIntake));

    new JoystickButton(controller, JoystickConstants.kY)
      .whenHeld(new InstantCommand(() -> m_robotOuttake.outtakeBalls(10), m_robotOuttake))
      .whenReleased(new InstantCommand(m_robotOuttake::stopOuttakeBalls, m_robotOuttake));
  }

  public static double getController(int port) {
    // get a joystick axis
    return controller.getRawAxis(port);
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
