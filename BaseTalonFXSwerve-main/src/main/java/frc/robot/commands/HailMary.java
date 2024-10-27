// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.Constants;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;

public class HailMary extends Command {
  private Shooter Shooter;
  private Feeder Feeder;
  /** Creates a new HailMary. */
  public HailMary(Shooter Shooter, Feeder Feeder) {
    this.Shooter = Shooter;
    this.Feeder = Feeder;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Shooter, Feeder);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Shooter.setPivot_Position(Constants.ShooterConstants.POS_HailMary);
    Feeder.intake_HM();
    Shooter.accelerate();
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
