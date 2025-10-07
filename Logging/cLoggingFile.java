package Logging;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;

public class cLoggingFile extends cLoggingDecorator 
{
    FileWriter mLogFile;
    String mLogFilePath;

    public cLoggingFile()
    {
        super(null);
    }
    public cLoggingFile(eLogLevel lvl)
    {
        super(null, lvl);
    }
    public cLoggingFile(String filepath, eLogLevel lvl)
    {
        super(null, lvl);
        mLogFilePath = filepath;
    }
    public cLoggingFile(cLoggingBase logger, String filepath, eLogLevel lvl)
    {
        super(logger, lvl);
        mLogFilePath = filepath;
    }
    public cLoggingFile(cLoggingBase logger, String filepath)
    {
        super(logger);
        mLogFilePath = filepath;
    }

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
            if(mLogFile == null)
            {
                Path path = Path.of(mLogFilePath).toAbsolutePath();
                if(!Files.exists(path))
                {
                    Files.createDirectories(path.getParent());
                }
                else
                {
                    Files.delete(path);
                }
                mLogFile = new FileWriter(mLogFilePath, false);
            }
            if(mLogFile != null)
            {
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
}