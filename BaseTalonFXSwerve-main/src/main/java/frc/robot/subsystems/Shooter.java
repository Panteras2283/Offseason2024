// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix6.hardware.TalonFX;

import frc.robot.Constants;

import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.revrobotics.RelativeEncoder;

import com.ctre.phoenix6.StatusCode;

import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.MotionMagicVoltage;

public class Shooter extends SubsystemBase {
  private final TalonFX pivotLeft_motor = new TalonFX(Constants.ShooterConstants.pivotLeftMotorID);
  private final TalonFX pivotRight_motor = new TalonFX(Constants.ShooterConstants.pivotRightMotorID);
  private final TalonFX receiveNote_motor = new TalonFX(Constants.ShooterConstants.receiveNoteMotorID);
  private final TalonFX shootNote_motor = new TalonFX(Constants.ShooterConstants.shootNoteMotorID);
  private final DigitalInput note_sensor = new DigitalInput(6);
  private final MotionMagicVoltage m_mmReq = new MotionMagicVoltage(0);


  /** Creates a new Shooter. */
  public Shooter() {
    TalonFXConfiguration configs = new TalonFXConfiguration();

    MotionMagicConfigs mm = configs.MotionMagic;
    mm.MotionMagicCruiseVelocity = 6000; // 5 rotations per second cruise
    mm.MotionMagicAcceleration = 600; // Take approximately 0.5 seconds to reach max vel
    // Take approximately 0.2 seconds to reach max accel 
    //mm.MotionMagicJerk = 10000;

    Slot0Configs slot0 = configs.Slot0;

    slot0.kP = Constants.ShooterConstants.kP;
    slot0.kI = Constants.ShooterConstants.kI;
    slot0.kD = Constants.ShooterConstants.kD;
    slot0.kV = 1.5;
    slot0.kS = 1.5; // Approximately 0.25V to get the mechanism moving

    FeedbackConfigs fdb = configs.Feedback;
    fdb.SensorToMechanismRatio = 12.8;

    StatusCode status = StatusCode.StatusCodeNotInitialized;
    for(int i = 0; i < 5; ++i) {
      status = pivotLeft_motor.getConfigurator().apply(configs);
      if (status.isOK()) break;
    }
    if (!status.isOK()) {
      System.out.println("Could not configure device. Error: " + status.toString());
    }

    StatusCode status2 = StatusCode.StatusCodeNotInitialized;
    for(int i = 0; i < 5; ++i) {
      status2 = pivotRight_motor.getConfigurator().apply(configs);
      if (status2.isOK()) break;
    }
    if (!status2.isOK()) {
      System.out.println("Could not configure device. Error: " + status2.toString());
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void receiveNote() {
    receiveNote_motor.set(0.5);
  }

  public void schootNote() {
    shootNote_motor.set(0.5);
  }

  public void STOP_Receiver() {
    receiveNote_motor.set(0);
  }

  public void STOP_Shooter() {
    shootNote_motor.set(0);
  }

  public void setLeftPivot_Position(double pos) {
    pivotLeft_motor.setControl(m_mmReq.withPosition(pos));
  }

  public void setRightPivot_Position(double pos) {
    pivotRight_motor.setControl(m_mmReq.withPosition(pos));
  }
}
