// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoIntake extends SequentialCommandGroup {
  /** Creates a new AutoIntake. */
  public AutoIntake(Feeder Feeder, Shooter Shooter) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new InstantCommand(() -> Shooter.STOP_Receiver()),
      new InstantCommand(() -> Shooter.STOP_Shooter()),
      new Intake(Feeder),
      new pasarDonita(Feeder, Shooter),
      new StopSF(Feeder, Shooter)
    );
  }
}
