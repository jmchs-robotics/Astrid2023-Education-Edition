package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class DefaultTankDrive extends CommandBase {
  
  private final Drivetrain m_subsystem;
  private final XboxController m_stick;

  /**
   *
   * @param subsystem The subsystem used by this command.
   * @param stick The XBoxController used by this command.
   */
  public DefaultTankDrive(Drivetrain subsystem, XboxController stick) {
    m_subsystem = subsystem;
    m_stick = stick;
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double left = -m_stick.getLeftY();
    double right = -m_stick.getRightY();
    
    m_subsystem.tankDrive(left, right);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Try to bring the robot to a dead stop before starting the next command
    m_subsystem.stopMotors(); 
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
