/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class RunIntoWall extends CommandBase {
  
  private final DriveSubsystem m_drive;
  private final int power;

  public RunIntoWall(DriveSubsystem subsystem, int pow) {
    m_drive = subsystem;
    power = pow;
    addRequirements(m_drive);
  }

  @Override
  public void execute() {
    m_drive.tankDrive(power, power);
  }

  @Override
  public boolean isFinished() {
    return !m_drive.isMoving();
  }
}
