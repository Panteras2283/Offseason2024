// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix6.signals.Led1OffColorValue;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

public class Climb extends Command {
  /** Creates a new Climb. */
  private Climber Climber;
  private Shooter Shooter;
  private Feeder Feeder;
  private LED_Driver LED_Driver;
  public Climb(Climber Climber, Shooter Shooter, Feeder Feeder, LED_Driver LED_Driver) {
    this.Climber = Climber;
    this.Shooter = Shooter;
    this.Feeder = Feeder;
    this.LED_Driver = LED_Driver; 
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Feeder, Shooter, Climber, LED_Driver);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Shooter.setPivot_Position(Constants.ShooterConstants.POS_climb);
    Feeder.climb_position();
    LED_Driver.setPreset5();
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
