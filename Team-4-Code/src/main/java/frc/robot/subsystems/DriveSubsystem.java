/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.kDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.*;

public class DriveSubsystem extends SubsystemBase {

  TalonSRX rightMotor = new TalonSRX(kDrive.kRightMotorPort);
  TalonSRX leftMotor = new TalonSRX(kDrive.kLeftMotorPort);
  
  private PIDController pid = new PIDController(1, 0, 0);

  public DriveSubsystem() {
    leftMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 100);
  }

  public void resetEncoders() {
    leftMotor.getSensorCollection().setQuadraturePosition(0, 100);
  }

  public void tankDrive(double leftPower, double rightPower) {
    leftMotor.set(ControlMode.PercentOutput, leftPower);
    rightMotor.set(ControlMode.PercentOutput, rightPower);
  }

  public void arcadeDrive(double throttle, double turn) {
    leftMotor.set(ControlMode.PercentOutput, throttle + turn);
    rightMotor.set(ControlMode.PercentOutput, throttle - turn);
  }

  public void PIDDrive(int setpoint) {
    int pv = leftMotor.getSensorCollection().getQuadraturePosition();
    leftMotor.set(ControlMode.PercentOutput, pid.calculate(pv, setpoint));
    rightMotor.set(ControlMode.PercentOutput, pid.calculate(pv, setpoint));
  }  
}
