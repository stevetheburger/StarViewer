package Logging;

import java.time.Clock;

public class cLoggingConsole extends cLoggingDecorator 
{
    public cLoggingConsole()
    {
        super(null);
    }
    public cLoggingConsole(eLogLevel lvl)
    {
        super(null, lvl);
    }
    public cLoggingConsole(cLoggingBase logger, eLogLevel lvl)
    {
        super(logger, lvl);
    }
    public cLoggingConsole(cLoggingBase logger)
    {
        super(logger);
    }

    @Override
    public void Log(String message, eLogLevel level)
    {
        //If set to not log skip.
        if(level == eLogLevel.kOff)
        {
            return;
        }

        if(level.ordinal() <= mLogLevel.ordinal())
        {
            IO.print("[" + Clock.systemUTC().instant().toString() + " : " + level.toString() + "]: " + message + "\n");
        }
        super.Log(message, level);
    }
}
