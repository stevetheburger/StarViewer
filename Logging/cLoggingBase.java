package Logging;

public abstract class cLoggingBase
{
    protected static cLoggingStandby mStandbyLogger = new cLoggingStandby();

    public enum eLogLevel
    {
        kOff,
        kError,
        kWarning,
        kDebug,
        kInfo,
        kTrace
    }

    protected eLogLevel mLogLevel;

    public cLoggingBase(eLogLevel level)
    {
        mLogLevel = level;
    }
    public cLoggingBase()
    {
        this(eLogLevel.kInfo);
    }

    public eLogLevel GetLogLevel()
    {
        return mLogLevel;
    }
    public void SetLogLevel(eLogLevel level)
    {
        mStandbyLogger.SetLogLevel(level);
        mLogLevel = level;
    }

    public abstract void Log(String message, eLogLevel level);

    public void LogError    (String message)    { Log(message, eLogLevel.kError     ); }
    public void LogWarning  (String message)    { Log(message, eLogLevel.kWarning   ); }
    public void LogDebug    (String message)    { Log(message, eLogLevel.kDebug     ); }
    public void LogInfo     (String message)    { Log(message, eLogLevel.kInfo      ); }
    public void LogTrace    (String message)    { Log(message, eLogLevel.kTrace     ); }

    public static cLoggingBase GetStandbyLogger()
    {
        return mStandbyLogger;
    }
}
