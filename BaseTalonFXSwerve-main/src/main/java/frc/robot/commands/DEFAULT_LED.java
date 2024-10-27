// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.*;

public class DEFAULT_LED extends Command {
  private LED_Driver LED_Driver;
  private Feeder Feeder;
  /** Creates a new DEFAULT_LED. */
  public DEFAULT_LED(LED_Driver LED_Driver, Feeder Feeder) {
    this.LED_Driver = LED_Driver;
    this.Feeder = Feeder;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(LED_Driver);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Optional<Alliance> ally = DriverStation.getAlliance();
if (ally.isPresent()) {
    if (ally.get() == Alliance.Red) {
      if (Feeder.getSensor()||Feeder.getSensor2() == false){
        LED_Driver.setPreset3();
      }else{
        LED_Driver.setPreset1();
      }
    }
    if (ally.get() == Alliance.Blue) {
      if (Feeder.getSensor()||Feeder.getSensor2() == false){
        LED_Driver.setPreset3();
      }else{
        LED_Driver.setPreset2();
      }
    }
}
else {
    LED_Driver.setPreset4();
}
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
