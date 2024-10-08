// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.Constants;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.math.controller.PIDController;

public class Aim extends Command {
  private Shooter Shooter;
  private Limelight Limelight;
  private PIDController limelightSpeakerPID;
  /** Creates a new Aim. */
  public Aim(Shooter Shooter, Limelight Limelight) {
    this.Shooter = Shooter;
    this.Limelight = Limelight;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Shooter, Limelight);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    limelightSpeakerPID =  new PIDController(0.01, 0.009,  0);

    if (Limelight.getArea() > 0) {
      Shooter.manualControl(limelightSpeakerPID.calculate(Limelight.getTY(), 0));
    } else {
      //System.out.println("ATlock");
      Shooter.setPivot_Position(Constants.ShooterConstants.POS_speaker);
    }

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
