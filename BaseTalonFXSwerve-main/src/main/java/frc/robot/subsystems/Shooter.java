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
  private final TalonFX pivot_motor = new TalonFX(Constants.ShooterConstants.pivotMotorID);
  private final CANSparkMax receiveNote_motor = new CANSparkMax(Constants.ShooterConstants.receiveNoteMotorID, MotorType.kBrushless);
  private final CANSparkMax leftShoot = new CANSparkMax(Constants.ShooterConstants.LeftShootNoteMotorID, MotorType.kBrushless);
  private final CANSparkMax rightShoot = new CANSparkMax(Constants.ShooterConstants.RightShootNoteMotorID, MotorType.kBrushless);

  private final DigitalInput note_sensor = new DigitalInput(1);

  private final MotionMagicVoltage m_mmReq = new MotionMagicVoltage(0);
  private SparkPIDController m_pidControllerLeft;
  private SparkPIDController m_pidControllerRight;
  private RelativeEncoder m_encoderLeft;
  private RelativeEncoder m_encoderRight;

  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, AmpRPM, SpkRPMLeft, SpkRPMRight;


  /** Creates a new Shooter. */
  public Shooter() {
    leftShoot.restoreFactoryDefaults();
    m_pidControllerLeft = leftShoot.getPIDController();
    m_encoderLeft = leftShoot.getEncoder();
    kP = Constants.ShooterConstants.velP;//0.000654 
    kI = Constants.ShooterConstants.velI;
    kD = Constants.ShooterConstants.velD; 
    kIz = Constants.ShooterConstants.velIz; 
    kFF = Constants.ShooterConstants.velFF; 
    kMaxOutput = Constants.ShooterConstants.velMaxOut; 
    kMinOutput = Constants.ShooterConstants.velMinOut;
    AmpRPM = Constants.ShooterConstants.AMP_vel;
    SpkRPMLeft = Constants.ShooterConstants.SPKLeft_vel;

    m_pidControllerLeft.setP(kP);
    m_pidControllerLeft.setI(kI);
    m_pidControllerLeft.setD(kD);
    m_pidControllerLeft.setIZone(kIz);
    m_pidControllerLeft.setFF(kFF);
    m_pidControllerLeft.setOutputRange(kMinOutput, kMaxOutput);

    rightShoot.restoreFactoryDefaults();
    m_pidControllerRight = rightShoot.getPIDController();
    m_encoderRight = rightShoot.getEncoder();
    kP = Constants.ShooterConstants.velP;//0.000654 
    kI = Constants.ShooterConstants.velI;
    kD = Constants.ShooterConstants.velD; 
    kIz = Constants.ShooterConstants.velIz; 
    kFF = Constants.ShooterConstants.velFF; 
    kMaxOutput = Constants.ShooterConstants.velMaxOut; 
    kMinOutput = Constants.ShooterConstants.velMinOut;
    AmpRPM = Constants.ShooterConstants.AMP_vel;
    SpkRPMRight = Constants.ShooterConstants.SPKRight_vel;

    m_pidControllerRight.setP(kP);
    m_pidControllerRight.setI(kI);
    m_pidControllerRight.setD(kD);
    m_pidControllerRight.setIZone(kIz);
    m_pidControllerRight.setFF(kFF);
    m_pidControllerRight.setOutputRange(kMinOutput, kMaxOutput);

    TalonFXConfiguration configs = new TalonFXConfiguration();

    MotionMagicConfigs mm = configs.MotionMagic;
    mm.MotionMagicCruiseVelocity = 6000; // 5 rotations per second cruise
    mm.MotionMagicAcceleration = 60; // Take approximately 0.5 seconds to reach max vel
    // Take approximately 0.2 seconds to reach max accel 
    //mm.MotionMagicJerk = 5000;

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
      status = pivot_motor.getConfigurator().apply(configs);
      if (status.isOK()) break;
    }
    if (!status.isOK()) {
      System.out.println("Could not configure device. Error: " + status.toString());
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("Sensor Note", note_sensor.get());
    SmartDashboard.putNumber("Shooter Position", pivot_motor.getPosition().getValueAsDouble());

  }

  public void receiveNote() {
    receiveNote_motor.set(0.5);
    leftShoot.set(-0.75);
    rightShoot.set(0.75);
  }

  public void accelerate() {
    leftShoot.set(1);
    rightShoot.set(-1);
    /*m_pidControllerLeft.setReference(SpkRPMLeft, CANSparkMax.ControlType.kVelocity);
    m_pidControllerRight.setReference(SpkRPMRight, CANSparkMax.ControlType.kVelocity);*/
  }

  public void shootNote() {
    receiveNote_motor.set(-0.5);
  }

  public void STOP_Receiver() {
    receiveNote_motor.set(0);
  }

  public void STOP_Shooter() {
    leftShoot.set(0);
    rightShoot.set(0);
  }

  public void setPivot_Position(double pos) {
    pivot_motor.setControl(m_mmReq.withPosition(pos));
  }

  public void manualControl(double val) {
    pivot_motor.set(val);
  }

  public boolean getSensor() {
    return note_sensor.get();
  }

  public double shareEncoder(){
    return pivot_motor.getPosition().getValueAsDouble();
  }

}
