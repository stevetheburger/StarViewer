package Logging;

/**
 * A decorator implementation for logging that wraps another logger.
 * This class implements the Decorator pattern, allowing multiple loggers to be chained together.
 * Each decorator can add additional functionality while delegating the actual logging
 * to the wrapped logger instance.
 * 
 * @author Stephen Hyberger
 * @version 1.0
 */
public class cLoggingDecorator extends cLoggingBase 
{
    /**
     * The wrapped logger instance that this decorator delegates to.
     */
    private cLoggingBase mWrappedLogger;

    /**
     * Constructs a new logging decorator with a specified wrapped logger and log level.
     * 
     * @param logger the logger to wrap and delegate to
     * @param lvl the log level for this decorator
     */
    public cLoggingDecorator(cLoggingBase logger, eLogLevel lvl)
    {
        super(lvl);
        mWrappedLogger = logger;
    }
    
    /**
     * Constructs a new logging decorator with a specified wrapped logger and default log level.
     * 
     * @param logger the logger to wrap and delegate to
     */
    public cLoggingDecorator(cLoggingBase logger)
    {
        super();
        mWrappedLogger = logger;
    }

    /**
     * Logs a message by delegating to the wrapped logger.
     * If no wrapped logger is set, the message is not logged.
     * 
     * @param message the message to log
     * @param level the level at which to log the message
     */
    @Override
    public void Log(String message, eLogLevel level)
    {
        if(mWrappedLogger != null)
        {
            mWrappedLogger.Log(message, level);
        }
    }

    /**
     * Sets the wrapped logger instance.
     * 
     * @param logger the new logger to wrap and delegate to
     */
    public void SetWrapped(cLoggingBase logger)
    {
        mWrappedLogger = logger;
    }
    
    /**
     * Gets the currently wrapped logger instance.
     * 
     * @return the wrapped logger, or null if none is set
     */
    public cLoggingBase GetWrapped()
    {
        return mWrappedLogger;
    }
}
