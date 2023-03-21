package frc.robot.commands;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.simulation.DriverStationSim;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Robot;
import frc.robot.Constants.Drive;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PIDGyroTurn extends PIDCommand {
	

	/**
	 * Instantiate a turn object. Angle is in gryoscope native units.
	 * @param targetAngle
	 * The gyroscope target units (from robot current heading, not from absolute orientation)
	 * @param percentVBus
	 * The maximum turning voltage bus proportion
	 */
    public PIDGyroTurn(Drivetrain m_subsystem, double targetAngle) {
        super( 
			new PIDController(Drive.kP_turn, Drive.kI_turn, Drive.kD_turn),
			m_subsystem::getGyroYaw,
			targetAngle,
			output -> m_subsystem.arcadeDrive(0, 0.2 * output),
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