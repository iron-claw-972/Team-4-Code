/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class PIDDrive extends CommandBase {
  
  private final DriveSubsystem m_drive;
  private final int setpoint;

  public PIDDrive(DriveSubsystem subsystem, int goal) {
    m_drive = subsystem;
    setpoint = goal;
    addRequirements(m_drive);
  }

  @Override
  public void initialize() {
    m_drive.resetEncoders();    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drive.PIDDrive(setpoint);
  }

  @Override
  public boolean isFinished() {
    return (m_drive.getRotations() < setpoint + 1) && (m_drive.getRotations() > setpoint - 1);
  }
}
