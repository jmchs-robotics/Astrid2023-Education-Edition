package frc.robot;

import frc.robot.Constants.*;
import frc.robot.commands.*;
import frc.robot.commands.autonomous.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

    public static RobotContainer m_robotContainer = new RobotContainer();

    // The robot's subsystems
    public final Drivetrain m_drive = new Drivetrain();
    public final LArmSubsystem m_LArm = new LArmSubsystem();
    public final HookSubsystem m_Hook = new HookSubsystem();
    //public final IntakeSubsystem m_Intake = new IntakeSubsystem();

    // Joysticks
    private final XboxController subStick = new XboxController(1);
    private final XboxController driveStick = new XboxController(0);
    private final JoystickButton driveA = new JoystickButton(driveStick, XboxController.Button.kA.value);
    private final JoystickButton driveB = new JoystickButton(driveStick, XboxController.Button.kB.value);    
    private final JoystickButton driveX = new JoystickButton(driveStick, XboxController.Button.kX.value); 
    private final JoystickButton driveY = new JoystickButton(driveStick, XboxController.Button.kY.value); 
    private final JoystickButton driveLB = new JoystickButton(driveStick, XboxController.Button.kLeftBumper.value);
    private final JoystickButton driveRB = new JoystickButton(driveStick, XboxController.Button.kRightBumper.value);
    private final JoystickButton subA = new JoystickButton(subStick, XboxController.Button.kA.value);    
    private final JoystickButton subB = new JoystickButton(subStick, XboxController.Button.kB.value);   
    private final JoystickButton subX = new JoystickButton(subStick, XboxController.Button.kX.value);  
    private final JoystickButton subY = new JoystickButton(subStick, XboxController.Button.kY.value);  
    private final JoystickButton subLB = new JoystickButton(subStick, XboxController.Button.kLeftBumper.value); 
    private final JoystickButton subRB = new JoystickButton(subStick, XboxController.Button.kRightBumper.value);
    private final JoystickButton subStart = new JoystickButton(subStick, XboxController.Button.kStart.value);

    private final double t = LArm.timeout;
    private final double h = Hook.timeout;

    // The container for the robot. Configures subsystems, OI devices, and commands.
    private RobotContainer() {

        // SmartDashboard Command Buttons
        SmartDashboard.putData("Extend Hook", new ExtendHook(m_Hook).withTimeout(h));
        SmartDashboard.putData("Retract Hook", new RetractHook(m_Hook).withTimeout(h));
        SmartDashboard.putData("Push Dump Arm", new PushArms(m_LArm).withTimeout(t));
        SmartDashboard.putData("Pull Dump Arm", new PullArms(m_LArm).withTimeout(t));
        //SmartDashboard.putData("Push Climb Arms", new PushClimbArm(m_LArm).withTimeout(t));
        //SmartDashboard.putData("Pull Climb Arms", new PullClimbArm(m_LArm).withTimeout(t));
        //SmartDashboard.putData("Push Spare Arm", new RaiseIntake(m_LArm).withTimeout(t));
        //SmartDashboard.putData("Pull Spare Arms", new LowerIntake(m_LArm).withTimeout(t));
        SmartDashboard.putData("Drive Straight", new DriveStraight(m_drive, 0.5));
        SmartDashboard.putData("Turn", new GyroTurn(m_drive, 90, 0.2,0.05));
        SmartDashboard.putData("Raise to Mid", new MidClimb(m_Hook).withTimeout(10));

        configureButtonBindings();

        configureDefaultCommands();
    }

    public static RobotContainer getInstance() {
        return m_robotContainer;
    }

    private void configureButtonBindings() {
        /*
        driveA.whenPressed(new auto1() ,true);
            SmartDashboard.putData("driveA",new auto1() );
                
        driveB.whenPressed(new auto1() ,true);
            SmartDashboard.putData("driveB",new auto1() );
                
        driveX.whenPressed(new auto1() ,true);
            SmartDashboard.putData("driveX",new auto1() );

        driveY.whenPressed(new auto1() ,true);
            SmartDashboard.putData("driveY",new auto1() );
        */
              
        driveLB.whenHeld(
            new ExtendHook(m_Hook)
        );
            
        driveRB.whenHeld(
            new RetractHook(m_Hook)
        );

        /*
        subA.whenPressed(
            new PushClimbArm(m_LArm).withTimeout(t)
        ); 

        subY.whenPressed(
            new RaiseIntake(m_LArm).withTimeout(t)
        );

        subX.whenPressed(
            new LowerIntake(m_LArm).withTimeout(t)
        );

        subB.whenPressed(
            new PullClimbArm(m_LArm).withTimeout(t)
        );
        */

        subLB.onTrue(
            new PullArms(m_LArm).withTimeout(t)
        );

        subRB.onTrue(
            new PushArms(m_LArm).withTimeout(t)
        );
        
        subStart.whileTrue(
            new MidClimb(m_Hook)
        );
    }


    private void configureDefaultCommands() {

        m_drive.setDefaultCommand(new DefaultArcadeDrive(m_drive, driveStick));
        m_Hook.setDefaultCommand(new DefaultHookArcade(m_Hook, subStick));
        //m_Intake.setDefaultCommand(new DefaultIntake(m_Intake, driveStick));
        
    }

    /**
     * @return The driver stick 
     */

    public XboxController getdriveStick() {
        return driveStick;
        }

    /**
     * @return The sub stick
     */

    public XboxController getsubStick() {
        return subStick;
        }

    /**
     * @return Selected autonomous command
     */

    public Command getAutonomousCommand(String a) {
        Paths p = new Paths(m_drive, m_Hook, m_LArm);
        
        //Default set command
        Command autoCommand = new SequentialCommandGroup(p.Dump());

        //Autonomous options
        switch(a) {
        case "dump":
            autoCommand = new SequentialCommandGroup(p.Dump());
            break;
        case "left":
            autoCommand = new SequentialCommandGroup(p.ManualScore("left"));
            break;
        case "right":
            autoCommand = new SequentialCommandGroup(p.ManualScore("right"));
            break;
        case "center":
            autoCommand = new SequentialCommandGroup(p.ManualScore("center")); 
            break;
        case "taxi":
            autoCommand = new SequentialCommandGroup(p.Taxi()); 
            break;        
        }   

        return autoCommand;
    }

    public Command getDriveMode(String d) {
        
        Command mode = new DefaultArcadeDrive(m_drive, driveStick);

        //Autonomous options
        switch(d) {
        case "a":
            mode = new DefaultArcadeDrive(m_drive, driveStick);
            break;
        case "t":
            mode = new DefaultTankDrive(m_drive, driveStick);
            break;
        }   

        return mode;
    }
}

