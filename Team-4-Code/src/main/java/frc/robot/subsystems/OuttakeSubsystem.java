/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.kOuttake;


public class OuttakeSubsystem extends SubsystemBase {
  TalonSRX motor1 = new TalonSRX(kOuttake.kOuttakeMotorPort);

  public OuttakeSubsystem() {

  }

  public void outtakeBalls(double power){
    motor1.set(ControlMode.PercentOutput, power);
  }

  public void stopOuttakeBalls(){
    motor1.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
