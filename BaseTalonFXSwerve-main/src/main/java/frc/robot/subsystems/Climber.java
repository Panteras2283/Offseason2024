// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Climber extends SubsystemBase {
  /** Creates a new Climber. */
  private static final int PH_CAN_ID = 2;
  private static int AMPforwardChannel =0;
  private static int AMPreverseChannel =1;
  private static int climberForwardChannel =14;
  private static int climberReverseChannel =15;
  PneumaticHub m_pH = new PneumaticHub(PH_CAN_ID);
  DoubleSolenoid m_doubleSolenoidAMP = m_pH.makeDoubleSolenoid(AMPforwardChannel,AMPreverseChannel);
  DoubleSolenoid m_doubleSolenoidCLIMBER = m_pH.makeDoubleSolenoid(climberForwardChannel,climberReverseChannel);


  public Climber() {
    m_doubleSolenoidAMP.set(DoubleSolenoid.Value.kOff);    
    m_doubleSolenoidCLIMBER.set(DoubleSolenoid.Value.kOff);

    m_pH.enableCompressorAnalog(60, 120);
  }

  public void up() {
    m_doubleSolenoidCLIMBER.set(DoubleSolenoid.Value.kForward);
  }

  public void down() {
    m_doubleSolenoidCLIMBER.set(DoubleSolenoid.Value.kReverse);
  }

  public void stickOut() {
    m_doubleSolenoidAMP.set(DoubleSolenoid.Value.kForward);
  }

  public void stickIn() {
    m_doubleSolenoidAMP.set(DoubleSolenoid.Value.kReverse);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
