package Logging;

import java.time.Clock;

public class cLoggingStandby extends cLoggingBase 
{   
    public cLoggingStandby()
    {
        super(eLogLevel.kDebug);
    }

    @Override
    public void Log(String message, cLoggingBase.eLogLevel level) 
    {
        //If set to not log skip.
        if(level == eLogLevel.kOff)
        {
            return;
        }

        if(level.ordinal() <= mLogLevel.ordinal())
        {
            IO.println("[" + Clock.systemUTC().instant().toString() + " : " + level.toString() + "]: " + message);
        }
    }
}
