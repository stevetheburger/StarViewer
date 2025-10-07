package Logging;

import java.time.Clock;

/**
 * A console logging decorator that outputs log messages to the console.
 * This decorator extends the logging decorator pattern to provide console output functionality.
 * Messages are formatted with timestamps and log levels before being printed to the console.
 * 
 * @author Stephen Hyberger
 * @version 1.0
 */
public class cLoggingConsole extends cLoggingDecorator 
{
    /**
     * Constructs a new console logger decorator with no wrapped logger and default log level.
     */
    public cLoggingConsole()
    {
        super(null);
    }
    
    /**
     * Constructs a new console logger decorator with no wrapped logger and specified log level.
     * 
     * @param lvl the log level for this console logger
     */
    public cLoggingConsole(eLogLevel lvl)
    {
        super(null, lvl);
    }
    
    /**
     * Constructs a new console logger decorator with a wrapped logger and specified log level.
     * 
     * @param logger the logger to wrap and delegate to
     * @param lvl the log level for this console logger
     */
    public cLoggingConsole(cLoggingBase logger, eLogLevel lvl)
    {
        super(logger, lvl);
    }
    
    /**
     * Constructs a new console logger decorator with a wrapped logger and default log level.
     * 
     * @param logger the logger to wrap and delegate to
     */
    public cLoggingConsole(cLoggingBase logger)
    {
        super(logger);
    }

    /**
     * Logs a message to the console and delegates to the wrapped logger.
     * Messages are formatted with UTC timestamp and log level information.
     * The format is: [timestamp : level]: message
     * 
     * @param message the message to log
     * @param level the level at which to log the message
     */
    @Override
    public void Log(String message, eLogLevel level)
    {
        //If set to not log skip.
        if(level == eLogLevel.kOff)
        {
            return;
        }

        //Check that level sent is at or below current log level.
        if(level.ordinal() <= mLogLevel.ordinal())
        {
            IO.print("[" + Clock.systemUTC().instant().toString() + " : " + level.toString() + "]: " + message + "\n");
        }
        super.Log(message, level);
    }
}
