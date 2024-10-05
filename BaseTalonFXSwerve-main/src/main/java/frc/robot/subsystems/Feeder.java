// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import frc.robot.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.SparkRelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkBase.ControlType;

import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Feeder extends SubsystemBase {
  private final CANSparkMax intake_motor = new CANSparkMax(Constants.FeederConstants.intakeMotorID, MotorType.kBrushless);
  private final CANSparkMax pivotLeft_motor = new CANSparkMax(Constants.FeederConstants.pivotLeft_ID, MotorType.kBrushed);
  private final CANSparkMax pivotRight_motor = new CANSparkMax(Constants.FeederConstants.pivotRight_ID, MotorType.kBrushed);
  private RelativeEncoder m_EncoderLeft;
  private RelativeEncoder m_EncoderRight;
  private SparkPIDController PID_PivotControlLeft;
  private SparkPIDController PID_PivotControlRight;

  /** Creates a new Feeder. */
  public Feeder() {
    pivotLeft_motor.restoreFactoryDefaults();
    pivotRight_motor.restoreFactoryDefaults();
    intake_motor.restoreFactoryDefaults();

    pivotLeft_motor.setInverted(true);
    m_EncoderLeft = pivotLeft_motor.getEncoder(SparkRelativeEncoder.Type.kQuadrature,8092);
    PID_PivotControlLeft = pivotLeft_motor.getPIDController();
    PID_PivotControlLeft.setFeedbackDevice(m_EncoderLeft);

    PID_PivotControlLeft.setP(Constants.FeederConstants.pivotKP);
    PID_PivotControlLeft.setI(Constants.FeederConstants.pivotKI);
    PID_PivotControlLeft.setD(Constants.FeederConstants.pivotKD);
    PID_PivotControlLeft.setIZone(Constants.FeederConstants.pivotKIz);
    PID_PivotControlLeft.setFF(Constants.FeederConstants.pivotKFF);
    PID_PivotControlLeft.setOutputRange(Constants.FeederConstants.KMinOutput, Constants.FeederConstants.kMaxOutput);

    pivotRight_motor.setInverted(false);
    m_EncoderRight = pivotRight_motor.getEncoder(SparkRelativeEncoder.Type.kQuadrature,8092);
    PID_PivotControlRight = pivotRight_motor.getPIDController();
    PID_PivotControlRight.setFeedbackDevice(m_EncoderRight);

    PID_PivotControlRight.setP(Constants.FeederConstants.pivotKP);
    PID_PivotControlRight.setI(Constants.FeederConstants.pivotKI);
    PID_PivotControlRight.setD(Constants.FeederConstants.pivotKD);
    PID_PivotControlRight.setIZone(Constants.FeederConstants.pivotKIz);
    PID_PivotControlRight.setFF(Constants.FeederConstants.pivotKFF);
    PID_PivotControlRight.setOutputRange(Constants.FeederConstants.KMinOutput, Constants.FeederConstants.kMaxOutput);

    int smartMotionLeft = 1;
    PID_PivotControlLeft.setSmartMotionMaxVelocity(Constants.FeederConstants.maxVel, smartMotionLeft);
    PID_PivotControlLeft.setSmartMotionMinOutputVelocity(Constants.FeederConstants.minVel, smartMotionLeft);
    PID_PivotControlLeft.setSmartMotionMaxAccel(Constants.FeederConstants.maxAcc, smartMotionLeft);
    PID_PivotControlLeft.setSmartMotionAllowedClosedLoopError(Constants.FeederConstants.allowedErr, smartMotionLeft);

    int smartMotionRight = 2;
    PID_PivotControlRight.setSmartMotionMaxVelocity(Constants.FeederConstants.maxVel, smartMotionRight);
    PID_PivotControlRight.setSmartMotionMinOutputVelocity(Constants.FeederConstants.minVel, smartMotionRight);
    PID_PivotControlRight.setSmartMotionMaxAccel(Constants.FeederConstants.maxAcc, smartMotionRight);
    PID_PivotControlRight.setSmartMotionAllowedClosedLoopError(Constants.FeederConstants.allowedErr, smartMotionRight);

  }

  public void intake_Note() {
    intake_motor.set(Constants.FeederConstants.intakeMotorSpeed);
    PID_PivotControlLeft.setReference(Constants.FeederConstants.POS_eatLeft, ControlType.kPosition);
    PID_PivotControlRight.setReference(Constants.FeederConstants.POS_eatRight, ControlType.kPosition);
  }

  public void saveFeeder() {
    intake_motor.set(0);
    PID_PivotControlLeft.setReference(Constants.FeederConstants.POS_initLeft, ControlType.kPosition);
    PID_PivotControlRight.setReference(Constants.FeederConstants.POS_initRight, ControlType.kPosition);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
