package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.autos.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.fasterxml.jackson.core.sym.Name;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    /* Controllers */
    private final Joystick driver = new Joystick(0);
    private final Joystick codriver = new Joystick(1);

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;

    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kBack.value);
    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kStart.value);
    private final JoystickButton intake = new JoystickButton(codriver, XboxController.Button.kY.value);
    private final JoystickButton aim = new JoystickButton(codriver, XboxController.Button.kLeftBumper.value);
    private final JoystickButton shoot = new JoystickButton(codriver, XboxController.Button.kRightBumper.value);
    private final JoystickButton amp = new JoystickButton(codriver, XboxController.Button.kA.value);
    private final JoystickButton cardinalN = new JoystickButton(driver, XboxController.Button.kY.value);
    private final JoystickButton cardinalS = new JoystickButton(driver, XboxController.Button.kA.value);
    private final JoystickButton cardinalO = new JoystickButton(driver, XboxController.Button.kX.value);
    private final JoystickButton cardinalE = new JoystickButton(driver, XboxController.Button.kB.value);
    private final JoystickButton source = new JoystickButton(codriver, XboxController.Button.kX.value);
    private final JoystickButton handoff = new JoystickButton(codriver, XboxController.Button.kB.value);

    private final POVButton climb = new POVButton(codriver, 90);
    private final POVButton sniper = new POVButton(codriver, 180);
    private final POVButton cardinalSource = new POVButton(driver, 270);

    /* Subsystems */
    private final Swerve s_Swerve = new Swerve();    
    private final Feeder s_Feeder = new Feeder();
    private final Shooter s_Shooter = new Shooter();
    private final Limelight s_Limelight = new Limelight();
    private final Climber s_Climber = new Climber();
    private final LED_Driver s_Led_Driver = new LED_Driver();



    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        s_Swerve.setDefaultCommand(
            new TeleopSwerve(
                s_Swerve, 
                () -> -driver.getRawAxis(translationAxis), 
                () -> -driver.getRawAxis(strafeAxis), 
                () -> -driver.getRawAxis(rotationAxis), 
                () -> robotCentric.getAsBoolean(),
                () -> cardinalN.getAsBoolean(),
                () -> cardinalS.getAsBoolean(),
                () -> cardinalE.getAsBoolean(),
                () -> cardinalO.getAsBoolean(),
                () -> cardinalSource.getAsBoolean()
            )
        );

        s_Feeder.setDefaultCommand(new DEFAULT_Feeder(s_Feeder,s_Shooter));
        s_Shooter.setDefaultCommand(new DEFAULT_Shooter(s_Shooter));

        s_Led_Driver.setDefaultCommand(new DEFAULT_LED(s_Led_Driver, s_Feeder));



        
        NamedCommands.registerCommand("Speaker Aim", new AutoSpeaker(s_Shooter, s_Limelight).withTimeout(5));
        NamedCommands.registerCommand("First Aim", new FirstSpeaker(s_Shooter).withTimeout(2));
        NamedCommands.registerCommand("Shoot Note", new InstantCommand(() -> s_Shooter.shootNote()));
        NamedCommands.registerCommand("Intake Note", new AutoIntake(s_Feeder, s_Shooter, s_Limelight).withTimeout(5));
        NamedCommands.registerCommand("Handoff Action", new pasarDonita(s_Feeder, s_Shooter).withTimeout(4));
        NamedCommands.registerCommand("Default Feeder", new DEFAULT_Feeder(s_Feeder, s_Shooter).withTimeout(4));
        NamedCommands.registerCommand("Default Shooter", new DEFAULT_Shooter(s_Shooter).withTimeout(4));


        // Configure the button bindings
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        /* Driver Buttons */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroHeading()));

        intake.onTrue(new Intake(s_Feeder));
        intake.onFalse(s_Feeder.getDefaultCommand());

        source.onTrue(new Source(s_Shooter));
        source.onFalse(s_Shooter.getDefaultCommand());
        
        amp.onTrue(new Amp(s_Shooter));
        amp.onFalse(s_Shooter.getDefaultCommand());

        aim.onTrue(new Speaker(s_Shooter, s_Limelight));
        aim.onFalse(s_Shooter.getDefaultCommand());

        sniper.onTrue(new Sniper_Speaker(s_Shooter, s_Limelight));
        sniper.onFalse(s_Shooter.getDefaultCommand());

        aim.onTrue(
            new TurretSwerve(
                s_Limelight,
                s_Swerve,
                () -> -driver.getRawAxis(translationAxis), 
                () -> -driver.getRawAxis(strafeAxis), 
                () -> -driver.getRawAxis(rotationAxis), 
                () -> robotCentric.getAsBoolean()
            )
        );

        aim.onFalse(
            s_Swerve.getDefaultCommand()
        );

        sniper.onTrue(
            new Sniper_TurretSwerve(
                s_Limelight,
                s_Swerve,
                () -> -driver.getRawAxis(translationAxis), 
                () -> -driver.getRawAxis(strafeAxis), 
                () -> -driver.getRawAxis(rotationAxis), 
                () -> robotCentric.getAsBoolean()
            )
        );

        sniper.onFalse(
            s_Swerve.getDefaultCommand()
        );
        
        shoot.onTrue(new InstantCommand(() -> s_Shooter.shootNote()));
        shoot.onFalse(new InstantCommand(() -> s_Shooter.STOP_Receiver()));

        climb.onFalse(new Climb(s_Climber, s_Shooter, s_Feeder));
        climb.onTrue(new InstantCommand(() -> s_Climber.up()));
        climb.onFalse(new InstantCommand(() -> s_Climber.down()));

        amp.onTrue(new InstantCommand(() -> s_Climber.stickOut()));
        amp.onFalse(new InstantCommand(() -> s_Climber.stickIn()));

        handoff.onTrue(new pasarDonita(s_Feeder, s_Shooter));
        handoff.onFalse(s_Shooter.getDefaultCommand());
        handoff.onFalse(s_Feeder.getDefaultCommand());

    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return new PathPlannerAuto("CENTER");
    }
}
