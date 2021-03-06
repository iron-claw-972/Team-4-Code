/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class PIDTurn extends CommandBase {
  
  private final DriveSubsystem m_drive;
  private final int setpoint;

  public PIDTurn(DriveSubsystem subsystem, int degrees) {
    m_drive = subsystem;
    setpoint = degrees;
    addRequirements(m_drive);
  }

  @Override
  public void initialize() {
    m_drive.resetGyro();    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drive.PIDTurn(setpoint);
  }

  @Override
  public boolean isFinished() {
    return (m_drive.getDegrees() < setpoint + 5) && (m_drive.getDegrees() > setpoint - 5);
  }
}
