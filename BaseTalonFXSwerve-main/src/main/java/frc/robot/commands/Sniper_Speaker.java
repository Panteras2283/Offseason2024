// Copyright (c) FIRST andH other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.LED_Driver;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.math.controller.PIDController;;

public class Sniper_Speaker extends Command {
  private Shooter Shooter;
  private Limelight Limelight;
  private LED_Driver LED_Driver;
  public PIDController limelightPID;
  /** Creates a new Speaker. */
  public Sniper_Speaker(Shooter Shooter, Limelight Limelight, LED_Driver LED_Driver) {
    this.Shooter = Shooter;
    this.Limelight = Limelight;
    this.LED_Driver = LED_Driver;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Shooter, LED_Driver);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    limelightPID = new PIDController(0.01, 0.0001, 0);
    
    if (Limelight.getArea() > 0) {
      Shooter.manualControl(limelightPID.calculate(Limelight.getTY(), 0));
    }else {
      Shooter.setPivot_Position(Constants.ShooterConstants.POS_snipSpk);
    }

    Shooter.SNIPER_accelerate();

     if(Shooter.shareSpeed()>=Constants.ShooterConstants.S_SPKLeft_vel - 5){
      LED_Driver.setPreset6();
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
