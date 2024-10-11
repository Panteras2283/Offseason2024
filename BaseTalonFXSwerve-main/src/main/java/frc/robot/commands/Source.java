// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.fasterxml.jackson.databind.introspect.ConcreteBeanPropertyBase;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;
import frc.robot.Constants;


public class Source extends Command {
  private Shooter Shooter;
  /** Creates a new Source. */
  public Source(Shooter Shooter) {
    this.Shooter = Shooter;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Shooter.setPivot_Position(Constants.ShooterConstants.POS_source);
    Shooter.receiveNote();
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
