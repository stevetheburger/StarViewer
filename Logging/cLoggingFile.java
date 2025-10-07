package Logging;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;

/**
 * A file logging decorator that writes log messages to a file.
 * This decorator extends the logging decorator pattern to provide file output functionality.
 * Messages are formatted with timestamps and log levels before being written to the specified file.
 * The file is created if it doesn't exist, and existing files are overwritten.
 * 
 * @author Stephen Hyberger
 * @version 1.0
 */
public class cLoggingFile extends cLoggingDecorator 
{
    /**
     * The FileWriter instance used to write to the log file.
     */
    FileWriter mLogFile;
    
    /**
     * The path to the log file.
     */
    String mLogFilePath;
    
    /**
     * Constructs a new file logger decorator with no wrapped logger, specified file path, and log level.
     * 
     * @param filepath the path to the log file
     * @param lvl the log level for this file logger
     */
    public cLoggingFile(String filepath, eLogLevel lvl)
    {
        super(null, lvl);
        mLogFilePath = filepath;
    }
    
    /**
     * Constructs a new file logger decorator with wrapped logger, file path, and log level.
     * 
     * @param logger the logger to wrap and delegate to
     * @param filepath the path to the log file
     * @param lvl the log level for this file logger
     */
    public cLoggingFile(cLoggingBase logger, String filepath, eLogLevel lvl)
    {
        super(logger, lvl);
        mLogFilePath = filepath;
    }
    
    /**
     * Constructs a new file logger decorator with wrapped logger, file path, and default log level.
     * 
     * @param logger the logger to wrap and delegate to
     * @param filepath the path to the log file
     */
    public cLoggingFile(cLoggingBase logger, String filepath)
    {
        super(logger);
        mLogFilePath = filepath;
    }

    /**
     * Logs a message to the file and delegates to the wrapped logger.
     * Creates the log file and necessary directories if they don't exist.
     * Existing log files are deleted and recreated.
     * Messages are formatted with UTC timestamp and log level information.
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

        try
        {
            //First time setup.
            if(mLogFile == null)
            {
                Path path = Path.of(mLogFilePath).toAbsolutePath();
                if(!Files.exists(path))
                {
                    //Ensure that the directory exists.
                    Files.createDirectories(path.getParent());
                }
                else
                {
                    //Overwrite existing file.
                    Files.delete(path);
                }
                mLogFile = new FileWriter(mLogFilePath, false);
            }
            else
            {
                //Check that level sent is at or below current log level.
                if (level.ordinal() <= mLogLevel.ordinal()) 
                {
                    mLogFile.write("[" + Clock.systemUTC().instant().toString() + " : "  + level.toString() + "]: " + message + "\n");
                }
            }
        } 
        catch (IOException e) 
        {
            mStandbyLogger.Log(e.getMessage(), eLogLevel.kError);
        }

        super.Log(message, level);
    }

    /**
     * Closes the file writer and releases resources.
     * Should be called when logging is complete to ensure all data is flushed and the file is properly closed.
     */
    public void Close()
    {
        try
        {
            if(mLogFile != null)
            {
                mLogFile.close();
                mLogFile = null;
            }
        }
        catch (IOException e) 
        {
            mStandbyLogger.Log(e.getMessage(), eLogLevel.kError);
        }
    }
}