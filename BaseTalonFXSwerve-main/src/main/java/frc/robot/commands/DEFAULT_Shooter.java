// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.*;

public class DEFAULT_Shooter extends Command {
  private Shooter Shooter;
  /** Creates a new DEFAULT_Shooter. */
  public DEFAULT_Shooter(Shooter Shooter) {
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
    Shooter.STOP_Receiver();
    Shooter.STOP_Shooter();
    Shooter.setPivot_Position(Constants.ShooterConstants.POS_init);
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