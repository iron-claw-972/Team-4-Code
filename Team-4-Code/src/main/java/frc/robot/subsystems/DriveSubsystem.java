/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;
import frc.robot.Constants.DriveConstants;


public class DriveSubsystem extends SubsystemBase {
  
  TalonSRX rightMotor = new TalonSRX(DriveConstants.kRightMotorPort);
  TalonSRX leftMotor = new TalonSRX(DriveConstants.kLeftMotorPort);

  public void tankDrive(double leftPower, double rightPower) {
    leftMotor.set(ControlMode.PercentOutput, leftPower);
    rightMotor.set(ControlMode.PercentOutput, rightPower);
  }
  public void arcadeDrive(double throttle, double turn) {
    leftMotor.set(ControlMode.PercentOutput, throttle + turn);
    rightMotor.set(ControlMode.PercentOutput, throttle - turn);
  }

}
