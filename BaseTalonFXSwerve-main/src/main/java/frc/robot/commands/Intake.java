// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.*;


public class Intake extends Command {
  private Feeder Feeder;
  /** Creates a new Intake. */
  public Intake(Feeder Feeder) {
    this.Feeder = Feeder;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Feeder);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Feeder.intake_Note();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (Feeder.getSensor() == true || Feeder.getSensor2() == false) {
      return true;
    } else {
      return false;
    }
  }
}
