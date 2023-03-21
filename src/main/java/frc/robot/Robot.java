package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.RetractHook;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in 
 * the project.
 */
public class Robot extends TimedRobot {

    private Command m_autonomousCommand;

    public static RobotContainer m_robotContainer;

    private final SendableChooser<String> startPosChooser = new SendableChooser<>();	
    //private final SendableChooser<String> driveChooser = new SendableChooser<>();	

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        RobotMap.init();

        startPosChooser.setDefaultOption("Dump", "dump");
        startPosChooser.addOption("Right", "right");
        startPosChooser.addOption("Left", "left");
        startPosChooser.addOption("Center", "center");
        startPosChooser.addOption("Taxi", "taxi");
        startPosChooser.addOption("Drive Test", "test");

        //driveChooser.setDefaultOption("Arcade", "a");
        //driveChooser.addOption("Tank", "t");
		
		// 'print' the Chooser to the dashboard
        //SmartDashboard.putData("Drive Chosen", driveChooser);

        m_robotContainer = RobotContainer.getInstance();
    }

    /**
    * This function is called every robot packet, no matter the mode. Use this for items like
    * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
    *
    * <p>This runs after the mode specific periodic functions, but before
    * LiveWindow and SmartDashboard integrated updating.
    */
    @Override
    public void robotPeriodic() {
        
        //Print Test Statements
        SmartDashboard.putNumber("Gyro Yaw Value: ", m_robotContainer.m_drive.getGyroYaw());
        SmartDashboard.putString("LArm Values: ", m_robotContainer.m_LArm.getPistonValue());
        SmartDashboard.putNumber("Left Hook Encoder Value: ", m_robotContainer.m_Hook.getLeftEncoderValue());
        SmartDashboard.putNumber("Right Hook Encoder Value: ", m_robotContainer.m_Hook.getRightEncoderValue());
        SmartDashboard.putNumber("Left Drive Encoder Value: ", m_robotContainer.m_drive.getLeftEncoderPos(0));
        SmartDashboard.putNumber("Right Drive Encoder Value: ", m_robotContainer.m_drive.getRightEncoderPos(0));

        SmartDashboard.putData("Path Chosen", startPosChooser);

        CommandScheduler.getInstance().run();
    }


    /**
    * This function is called once each time the robot enters Disabled mode.
    */
    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

    /**
    * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
    */
    @Override
    public void autonomousInit() {
        String startPos = startPosChooser.getSelected();
		
        m_autonomousCommand = m_robotContainer.getAutonomousCommand( startPos);

        // schedule the autonomous command (example)
        if (m_autonomousCommand != null) {
            m_autonomousCommand.schedule();
        }
    }

    /**
    * This function is called periodically during autonomous.
    */
    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {

        //String driveMode = driveChooser.getSelected();

        if (m_autonomousCommand != null) {
            m_autonomousCommand.cancel();
        }

        //m_robotContainer.m_drive.setDefaultCommand(m.robotCongetDriveMode(driveMode));

        m_robotContainer.m_Hook.resetEncoderValue();
        m_robotContainer.m_drive.resetEncoders();
        m_robotContainer.m_drive.resetGyro();

    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {

    }

    @Override
    public void testInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }

    /**
    * This function is called periodically during test mode.
    */
    @Override
    public void testPeriodic() {
    }

}