
package frc.robot.commands;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ArduinoConstants;
import frc.robot.subsystems.DriveSubsystem;

public class Align extends CommandBase {
    
    private final I2C arduino = new I2C(Port.kOnboard, ArduinoConstants.port);
    private final String data = "align";
    private final byte[] dataReceived = new byte[3];
    private final DriveSubsystem m_drive;

    public Align(DriveSubsystem drive) {
        m_drive = drive;
        addRequirements(m_drive);
    }

    @Override
    public void execute() {
        boolean success = arduino.transaction(data.getBytes(), data.getBytes().length, dataReceived, 3);
        if (success) {
            m_drive.tankDrive((int)(dataReceived[0]), (int)(dataReceived[1]));
        } else {
            System.out.println("Arduino Trasfer Aborted");
        }
    }

    public boolean isFinished() {
        return ((int)(dataReceived[2]) == 1);
    }

    @Override
    public void end(boolean interrupted) {
    }
}