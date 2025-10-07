package Logging;

/**
 * Abstract base class for all logging implementations in the system.
 * Provides a common interface and functionality for different types of loggers.
 * This class implements the foundation for a flexible logging system that supports
 * multiple log levels and different output destinations.
 * 
 * @author Stephen Hyberger
 * @version 1.0
 */
public abstract class cLoggingBase
{
    /**
     * Static instance of the standby logger used as a fallback.
     */
    protected static cLoggingStandby mStandbyLogger = new cLoggingStandby();

    /**
     * Enumeration of available logging levels.
     * Levels are ordered from lowest to highest priority.
     * When a log level is set, all messages at that level and lower will be logged.
     */
    public enum eLogLevel
    {
        /** No logging - all log messages are suppressed */
        kOff,
        /** Error level - critical issues that prevent normal operation */
        kError,
        /** Warning level - potential issues that don't prevent operation */
        kWarning,
        /** Debug level - detailed information for debugging purposes */
        kDebug,
        /** Info level - general information about program execution */
        kInfo,
        /** Trace level - very detailed information for tracing execution flow */
        kTrace
    }

    /**
     * The current logging level for this logger instance.
     */
    protected eLogLevel mLogLevel;

    /**
     * Constructs a new logger with the specified log level.
     * 
     * @param level the logging level for this logger
     */
    public cLoggingBase(eLogLevel level)
    {
        mLogLevel = level;
    }
    
    /**
     * Constructs a new logger with the default log level (Info).
     */
    public cLoggingBase()
    {
        this(eLogLevel.kInfo);
    }

    /**
     * Gets the current logging level for this logger.
     * 
     * @return the current log level
     */
    public eLogLevel GetLogLevel()
    {
        return mLogLevel;
    }
    
    /**
     * Sets the logging level for this logger and the standby logger.
     * 
     * @param level the new logging level to set
     */
    public void SetLogLevel(eLogLevel level)
    {
        mStandbyLogger.SetLogLevel(level);
        mLogLevel = level;
    }

    /**
     * Abstract method that must be implemented by concrete logger classes.
     * Logs a message at the specified level.
     * 
     * @param message the message to log
     * @param level the level at which to log the message
     */
    public abstract void Log(String message, eLogLevel level);

    /**
     * Logs an error message.
     * 
     * @param message the error message to log
     */
    public void LogError    (String message)    { Log(message, eLogLevel.kError     ); }
    
    /**
     * Logs a warning message.
     * 
     * @param message the warning message to log
     */
    public void LogWarning  (String message)    { Log(message, eLogLevel.kWarning   ); }
    
    /**
     * Logs a debug message.
     * 
     * @param message the debug message to log
     */
    public void LogDebug    (String message)    { Log(message, eLogLevel.kDebug     ); }
    
    /**
     * Logs an informational message.
     * 
     * @param message the info message to log
     */
    public void LogInfo     (String message)    { Log(message, eLogLevel.kInfo      ); }
    
    /**
     * Logs a trace message.
     * 
     * @param message the trace message to log
     */
    public void LogTrace    (String message)    { Log(message, eLogLevel.kTrace     ); }

    /**
     * Gets the static standby logger instance.
     * The standby logger is used as a fallback when other loggers are not available.
     * 
     * @return the standby logger instance
     */
    public static cLoggingBase GetStandbyLogger()
    {
        return mStandbyLogger;
    }
}
