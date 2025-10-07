package Logging;

import java.time.Clock;

/**
 * A standby logger implementation that outputs log messages to the console.
 * This logger serves as the default fallback logger and includes timestamps
 * in UTC format with each log message.
 * 
 * @author Stephen Hyberger
 * @version 1.0
 */
public class cLoggingStandby extends cLoggingBase 
{   
    /**
     * Constructs a new standby logger with the default debug log level.
     */
    public cLoggingStandby()
    {
        super(eLogLevel.kDebug);
    }

    /**
     * Logs a message to the console with timestamp and level information.
     * Messages are only logged if the specified level is at or below the current log level.
     * The format is: [timestamp : level]: message
     * 
     * @param message the message to log
     * @param level the level at which to log the message
     */
    @Override
    public void Log(String message, cLoggingBase.eLogLevel level) 
    {
        //If set to not log skip.
        if(level == eLogLevel.kOff)
        {
            return;
        }

        //Check that level sent is at or below current log level.
        if(level.ordinal() <= mLogLevel.ordinal())
        {
            IO.println("[" + Clock.systemUTC().instant().toString() + " : " + level.toString() + "]: " + message);
        }
    }
}
