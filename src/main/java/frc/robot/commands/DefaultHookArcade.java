package frc.robot.commands;

import frc.robot.Constants.Hook;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.HookSubsystem;

import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class DefaultHookArcade extends CommandBase {
  
  private final HookSubsystem m_subsystem;
  private final XboxController m_stick;
  private double control;
  private double offset;

  /**
   *
   * @param subsystem The subsystem used by this command.
   * @param stick The XBoxController used by this command.
   */
  public DefaultHookArcade(HookSubsystem subsystem, XboxController stick) {
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
    control = -m_stick.getLeftY();
    offset = m_stick.getRightX();

    //hook limiter control
    m_subsystem.hookArcadeLimiter(control, offset);
        
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