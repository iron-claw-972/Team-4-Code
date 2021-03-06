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

import frc.robot.Constants.kIntake;


public class IntakeSubsystem extends SubsystemBase {

  TalonSRX intakeMotor = new TalonSRX(kIntake.kIntakeMotorPort);

  public void intakeBalls(double power){
    intakeMotor.set(ControlMode.PercentOutput, power);
  }

  public void stopIntakeBalls(){
    intakeMotor.set(ControlMode.PercentOutput, 0);
  }
}
