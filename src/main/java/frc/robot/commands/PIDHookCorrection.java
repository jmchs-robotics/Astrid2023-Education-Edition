package frc.robot.commands;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.Drive;
import frc.robot.Constants.Hook;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.HookSubsystem;

/**
 *
 */
public class PIDHookCorrection extends PIDCommand {
	

	/**
	 * Instantiate a turn object. Angle is in gryoscope native units.
	 * @param targetAngle
	 * The gyroscope target units (from robot current heading, not from absolute orientation)
	 * @param percentVBus
	 * The maximum turning voltage bus proportion
	 */
    public PIDHookCorrection(HookSubsystem m_subsystem) {
        super( 
			new PIDController(Hook.kP_align, Hook.kI_align, Hook.kD_align),
			m_subsystem::getHookDifference,
			0,
			vBus -> m_subsystem.setBoth(vBus),
			m_subsystem
		);

		getController().enableContinuousInput(-180, 180);
		getController().setTolerance(Drive.kTurnToleranceDeg, Drive.kTurnRateToleranceDegPerS);
    }

    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        return getController().atSetpoint();
    }
}