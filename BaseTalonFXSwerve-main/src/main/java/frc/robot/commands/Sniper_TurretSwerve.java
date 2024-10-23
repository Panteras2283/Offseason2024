// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.Constants;
import frc.robot.subsystems.*;

public class Sniper_TurretSwerve extends Command {
  private Swerve s_Swerve;
  private Limelight l_Limelight;
  private DoubleSupplier translationSup;
  private DoubleSupplier strafeSup;
  private DoubleSupplier rotationSup;
  private BooleanSupplier robotCentricSup;
  public PIDController limelightPID;

  /** Creates a new TurretSwerve. */
  public Sniper_TurretSwerve(
    Limelight l_Limelight,
    Swerve s_Swerve, 
    DoubleSupplier translationSup, 
    DoubleSupplier strafeSup,
    DoubleSupplier rotationSup, 
    BooleanSupplier robotCentricSup
  ) {
    addRequirements(s_Swerve, l_Limelight);
    // Use addRequirements() here to declare subsystem dependencies.
    this.l_Limelight = l_Limelight;
    this.s_Swerve = s_Swerve;
    this.translationSup = translationSup;
    this.strafeSup = strafeSup;
    this.rotationSup = rotationSup;
    this.robotCentricSup = robotCentricSup;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    l_Limelight.SelectPipeline(1);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double translationVal = (Math.pow(MathUtil.applyDeadband(translationSup.getAsDouble(), Constants.stickDeadband), 1));
    double strafeVal = (Math.pow(MathUtil.applyDeadband(strafeSup.getAsDouble(), Constants.stickDeadband), 1));
    double rotationVal = (Math.pow(MathUtil.applyDeadband(rotationSup.getAsDouble(), Constants.stickDeadband), 1));
    double offsetX = translationVal*7;

    limelightPID = new PIDController(0.025, 0.0009, 0);
               
    
   

    if(l_Limelight.getArea()>0){ 
      s_Swerve.drive(
      new Translation2d(translationVal, strafeVal).times(Constants.Swerve.maxSpeed), 
      limelightPID.calculate(l_Limelight.getTX(), offsetX) * Constants.Swerve.maxAngularVelocity, 
      !robotCentricSup.getAsBoolean(), 
      true);
      System.out.println("DETECTANDO APRILTAG");
    }
    else{
        s_Swerve.drive(
        new Translation2d(translationVal, strafeVal).times(Constants.Swerve.maxSpeed), 
        rotationVal * Constants.Swerve.maxAngularVelocity, 
        !robotCentricSup.getAsBoolean(), 
        true);
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
