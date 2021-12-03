/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.kDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.*;
import com.kauailabs.navx.frc.AHRS;

public class DriveSubsystem extends SubsystemBase {

  TalonSRX rightMotor = new TalonSRX(kDrive.kRightMotorPort);
  TalonSRX leftMotor = new TalonSRX(kDrive.kLeftMotorPort);

  AHRS navX = new AHRS(SerialPort.Port.kMXP); 
  
  PIDController drivePID = new PIDController(1, 0, 0);
  PIDController turnPID = new PIDController(1, 0, 0);

  public DriveSubsystem() {
    leftMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 100);
  }

  public void resetEncoders() {
    leftMotor.getSensorCollection().setQuadraturePosition(0, 100);
  }

  public void resetGyro() {
    navX.reset();
  }

  public boolean isMoving() {
    return (navX.getVelocityX() + navX.getVelocityX()) > 0.5;
  }

  public void calibrate() {
    navX.calibrate();
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
    int pv = (leftMotor.getSensorCollection().getQuadraturePosition() / kDrive.COUNTS_PER_REV);
    leftMotor.set(ControlMode.PercentOutput, drivePID.calculate(pv, setpoint));
    rightMotor.set(ControlMode.PercentOutput, drivePID.calculate(pv, setpoint));
  }

  public void PIDTurn(int setpoint) {
    leftMotor.set(ControlMode.PercentOutput, turnPID.calculate(navX.getAngle(), setpoint));
    rightMotor.set(ControlMode.PercentOutput, turnPID.calculate(navX.getAngle(), setpoint));
  }

  public double getRotations() {
    return (leftMotor.getSensorCollection().getQuadraturePosition() / kDrive.COUNTS_PER_REV);
  }

  public double getDegrees() {
    return navX.getAngle();
  }
}
