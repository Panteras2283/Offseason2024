// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.Constants;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.LED_Driver;
import frc.robot.subsystems.Shooter;

public class HailMary extends Command {
  private Shooter Shooter;
  private Feeder Feeder;
  private LED_Driver LED_Driver;
  /** Creates a new HailMary. */
  public HailMary(Shooter Shooter, Feeder Feeder, LED_Driver LED_Driver) {
    this.Shooter = Shooter;
    this.Feeder = Feeder;
    this.LED_Driver = LED_Driver;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Shooter, Feeder, LED_Driver);
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

     if(Shooter.shareSpeed()>=Constants.ShooterConstants.S_SPKLeft_vel - 5){
      LED_Driver.setPreset6();
      Shooter.shootNote();
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
