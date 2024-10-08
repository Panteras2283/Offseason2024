package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;


public class TeleopSwerve extends Command {    
    private Swerve s_Swerve;    
    private DoubleSupplier translationSup;
    private DoubleSupplier strafeSup;
    private DoubleSupplier rotationSup;
    private BooleanSupplier robotCentricSup;
    private BooleanSupplier cardNorth;
    private BooleanSupplier cardSouth;
    private BooleanSupplier cardEast;
    private BooleanSupplier cardWest;

    public TeleopSwerve(
        Swerve s_Swerve,
        DoubleSupplier translationSup,
        DoubleSupplier strafeSup,
        DoubleSupplier rotationSup,
        BooleanSupplier robotCentricSup,
        BooleanSupplier cardNorth,
        BooleanSupplier cardSouth,
        BooleanSupplier cardEast,
        BooleanSupplier cardWest
    ) {
        this.s_Swerve = s_Swerve;
        addRequirements(s_Swerve);

        this.translationSup = translationSup;
        this.strafeSup = strafeSup;
        this.rotationSup = rotationSup;
        this.robotCentricSup = robotCentricSup;
        this.cardNorth = cardNorth;
        this.cardSouth = cardSouth;
        this.cardEast = cardEast;
        this.cardWest = cardWest;
    }

    @Override
    public void execute() {
        /* Get Values, Deadband*/
        double translationVal = MathUtil.applyDeadband(translationSup.getAsDouble(), Constants.stickDeadband);
        double strafeVal = MathUtil.applyDeadband(strafeSup.getAsDouble(), Constants.stickDeadband);
        double rotationVal = MathUtil.applyDeadband(rotationSup.getAsDouble(), Constants.stickDeadband);
        double targetAngle = 0;

/*10 OCTUBRE 2024 */

        /* Drive */
        if (cardNorth.getAsBoolean()==true || cardSouth.getAsBoolean()==true || cardEast.getAsBoolean()==true || cardWest.getAsBoolean()==true) {
            if (cardNorth.getAsBoolean()==true){
                targetAngle=0;
            }
            else if (cardSouth.getAsBoolean()==true){
                targetAngle=180;
            }
            else if (cardEast.getAsBoolean()==true){
                targetAngle=270;
            }
            else if (cardWest.getAsBoolean()==true){
                targetAngle=90;
            }

            s_Swerve.lockDrive(
                new Translation2d(translationVal, strafeVal).times(Constants.Swerve.maxSpeed),
                !robotCentricSup.getAsBoolean(),
                true,
                targetAngle
            );
        } else {
            s_Swerve.drive(
                new Translation2d(translationVal, strafeVal).times(Constants.Swerve.maxSpeed), 
                rotationVal * Constants.Swerve.maxAngularVelocity, 
                !robotCentricSup.getAsBoolean(), 
                true
            );
        }
    }
}