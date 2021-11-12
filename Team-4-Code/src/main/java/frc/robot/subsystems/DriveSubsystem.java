/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.*;


public class DriveSubsystem extends SubsystemBase {
  
  TalonSRX Motor1 = new TalonSRX(DriveConstants.kMotor1Port);
  TalonSRX Motor2 = new TalonSRX(DriveConstants.kMotor2Port);

  public void tankDrive(double leftPower, double rightPower) {
    Motor1.set(ControlMode.PercentOutput, leftPower);
    Motor2.set(ControlMode.PercentOutput, rightPower);
  }
  public void arcadeDrive(double throttle, double turn) {
    Motor1.set(ControlMode.PercentOutput, throttle + turn);
    Motor2.set(ControlMode.PercentOutput, throttle - turn);
  }
}
