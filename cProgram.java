import Data.*;
import Logging.*;
import Logging.cLoggingBase.eLogLevel;

public class cProgram 
{
    public static final String VERSION = "v0.0.0";

    public static void main(String[] args) 
    {
        cLoggingDecorator logger = null;
        eLogLevel lvl = eLogLevel.kWarning;
        String filepath = "";

        if(args.length > 0)
        {
            for(int i = 0; i < args.length;)
            {
                String arg = args[i];
                if(arg.equals("-lvl"))
                {
                    if(i + 1 < args.length)
                    {
                        switch(args[i + 1].toLowerCase())
                        {
                            case "error":
                                lvl = eLogLevel.kError;
                                break;
                            case "warning":
                                lvl = eLogLevel.kWarning;
                                break;
                            case "debug":
                                lvl = eLogLevel.kDebug;
                                break;
                            case "info":
                                lvl = eLogLevel.kInfo;
                                break;
                            case "trace":
                                lvl = eLogLevel.kTrace;
                                break;
                            case "none":
                            default:
                                lvl = eLogLevel.kOff;
                                break;
                        }
                    }
                    i+=2;
                }
                else if(arg.equals("-log"))
                {
                    filepath= args[i + 1];
                    i+=2;
                }
                else if(arg.equals("-help"))
                {
                    IO.println("StarViewer Help:");
                    IO.println("  -version    Display the current version.");
                    IO.println("  -lvl        Set the logging level.");
                    IO.println("  -log        Enable text file logging and set the file path.");
                    IO.println("  -help       Display this help message.");
                    
                    //Terminate after help message.
                    return;
                }
                else if(arg.equals("-version"))
                {
                    IO.println("StarViewer " + VERSION);
                    
                    //Terminate after version message.
                    return;
                }
                else
                {
                    IO.println("Unknown argument: " + arg);
                    IO.println("Use -help for a list of valid arguments.");

                    //Terminate after unknown argument.
                    return; 
                }
            }
        }

        if(!filepath.isEmpty())
        {
            logger = new cLoggingConsole(new cLoggingFile(filepath, lvl), eLogLevel.kWarning);
        }
        else
        {
            logger = new cLoggingConsole(lvl);
        }

        IO.println("Starting StarViewer " + VERSION);

        cUniverse universe = new cUniverse(logger);
        universe.TestUniverse();

        IO.println(universe.ToString());

        IO.println("Press any key to exit.");
        IO.readln();

        IO.println("Thank you for using StarViewer.\nGoodbye!");
    }
}
