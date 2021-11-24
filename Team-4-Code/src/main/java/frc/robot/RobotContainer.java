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
import frc.robot.subsystems.OuttakeSubsystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Constants.kAuto;
import frc.robot.Constants.kIntake;
import frc.robot.Constants.kJoystick;
import frc.robot.Constants.kOuttake;
import frc.robot.commands.Align;
import frc.robot.commands.PIDDrive;
import frc.robot.commands.PIDTurn;
import frc.robot.commands.RunIntoWall;


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


  private final SequentialCommandGroup m_autoCommand = new SequentialCommandGroup(
      //drives forward a certain distance
      new PIDDrive(m_drive, kAuto.distToTable),
      //turns 90 degrees
      new PIDTurn(m_drive, 90),
      //align with wall (until this is proven to work will just run into wall)
      //new Align(m_drive),
      new RunIntoWall(m_drive, 5),
      //outtake balls
      new RunCommand(() -> m_robotOuttake.outtakeBalls(kOuttake.outtakeSpeed), m_robotOuttake)
      ); 

  // The driver's controller
  static Joystick controller = new Joystick(kJoystick.kControllerPort);

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
    //A to intake balls
    new JoystickButton(controller, kJoystick.kA)
      .whenHeld(new RunCommand(() -> m_robotIntake.intakeBalls(kIntake.intakeSpeed), m_robotIntake))
      .whenReleased(new InstantCommand(m_robotIntake::stopIntakeBalls, m_robotIntake));

    //B to disloge balls from intake
    new JoystickButton(controller, kJoystick.kB)
      .whenHeld(new RunCommand(() -> m_robotIntake.intakeBalls(kIntake.intakeDislodgeSpeed), m_robotIntake))
      .whenReleased(new InstantCommand(m_robotIntake::stopIntakeBalls, m_robotIntake));

    //Y to outtake balls
    new JoystickButton(controller, kJoystick.kY)
      .whenHeld(new RunCommand(() -> m_robotOuttake.outtakeBalls(kOuttake.outtakeSpeed), m_robotOuttake))
      .whenReleased(new InstantCommand(m_robotOuttake::stopOuttakeBalls, m_robotOuttake));

    //Y and B at the same time to calibrate navX
    new JoystickButton(controller, kJoystick.kY)
      .and(new JoystickButton(controller, kJoystick.kB))
      .whenActive(new RunCommand( m_drive::calibrate, m_drive ));
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
