package Logging;

public class cLoggingDecorator extends cLoggingBase 
{
    private cLoggingBase mWrappedLogger;

    public cLoggingDecorator(cLoggingBase logger, eLogLevel lvl)
    {
        super(lvl);
        mWrappedLogger = logger;
    }
    public cLoggingDecorator(cLoggingBase logger)
    {
        super();
        mWrappedLogger = logger;
    }

    @Override
    public void Log(String message, eLogLevel level)
    {
        if(mWrappedLogger != null)
        {
            mWrappedLogger.Log(message, level);
        }
    }

    public void SetWrapped(cLoggingBase logger)
    {
        mWrappedLogger = logger;
    }
    public cLoggingBase GetWrapped()
    {
        return mWrappedLogger;
    }
}
