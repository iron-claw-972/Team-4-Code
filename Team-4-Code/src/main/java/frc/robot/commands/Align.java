
package frc.robot.commands;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ArdConstants;
import frc.robot.subsystems.DriveSubsystem;

public class Align extends CommandBase {
    
    // how is ardunio spelled
    private final I2C arduino = new I2C(Port.kOnboard, ArdConstants.port);

    private final byte[] dataReceived = new byte[ArdConstants.numData];

    private final DriveSubsystem m_drive;

    public Align(DriveSubsystem drive) {
        m_drive = drive;
        addRequirements(m_drive);
    }

    @Override
    public void initialize() {
        //send "align" on wire to start align process
        boolean success = arduino.transaction(ArdConstants.startSend, ArdConstants.startSend.length, dataReceived, dataReceived.length);
        if (success) {
            System.out.println("Initialized, Transfer successful");
        } else {
            System.out.println("Initialized, Transfer NOT successful");
        }
    }

    @Override
    public void execute() {
        boolean success = arduino.readOnly(dataReceived, dataReceived.length);
        if (success) {
            m_drive.tankDrive((int)(dataReceived[0]), (int)(dataReceived[1]));
        } else {
            System.out.println("Arduino Transfer Aborted");
        }
    }

    @Override
    public boolean isFinished() {
        return ((int)(dataReceived[0]) == 0 && (int)(dataReceived[1]) == 0 );
    }

    @Override
    public void end(boolean interrupted) {
        m_drive.tankDrive(0, 0);
    }
}