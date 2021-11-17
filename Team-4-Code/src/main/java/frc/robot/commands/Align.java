
package frc.robot.commands;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ArduinoConstants;
import frc.robot.subsystems.DriveSubsystem;

public class Align extends CommandBase {
    
    private final I2C arduino = new I2C(Port.kOnboard, ArduinoConstants.port);

    //three bytes (-125 to 127), one for left motor power, one for right, one for finished alignment
    //could probably use less
    private final byte[] dataReceived = new byte[3];
    private final DriveSubsystem m_drive;

    public Align(DriveSubsystem drive) {
        m_drive = drive;
        addRequirements(m_drive);
    }

    @Override
    public void initialize() {
        //send "align" on wire to start align process
        boolean success = arduino.transaction("align".getBytes(), "align".getBytes().length, dataReceived, 3);
        if (success) {
            System.out.println("Initialized, Transfer successful");
        } else {
            System.out.println("Initialized, Transfer NOT successful");
        }
    }

    @Override
    public void execute() {
        boolean success = arduino.read(ArduinoConstants.port, 3, dataReceived);
        if (success) {
            m_drive.tankDrive((int)(dataReceived[0]), (int)(dataReceived[1]));
        } else {
            System.out.println("Arduino Transfer Aborted");
        }
    }

    @Override
    public boolean isFinished() {
        return ((int)(dataReceived[2]) == 1);
    }

    @Override
    public void end(boolean interrupted) {
        m_drive.tankDrive(0, 0);
    }
}