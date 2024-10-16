// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.*;

public class pasarDonita extends Command {
  private Feeder Feeder;
  private Shooter Shooter;
  /** Creates a new pasarDonita. */
  public pasarDonita(Feeder Feeder, Shooter Shooter) {
    this.Feeder = Feeder;
    this.Shooter = Shooter;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Feeder, Shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Feeder.handoffFeeder();

    if (Feeder.sharePos() <= Constants.FeederConstants.POS_handoffLeft + 0.05) {
      Shooter.setPivot_Position(Constants.ShooterConstants.POS_handoff);
      Shooter.receiveNote();
    }

    if (Shooter.shareEncoder() <= Constants.ShooterConstants.POS_handoff + 0.05) {
      Feeder.deposit_donut();
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (Shooter.getSensor() == false) {
      return true;
    } else {
      return false;
    }
  }
}
