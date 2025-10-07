import Data.*;
import Logging.*;
import Logging.cLoggingBase.eLogLevel;

/**
 * The main program class for StarViewer.
 * This class handles command-line arguments, sets up logging, and initializes the universe.
 * 
 * @version 1.0
 * @author Stephen Hyberger
 */
public class cProgram 
{
    /**
     * The current version of the StarViewer application.
     */
    public static final String VERSION = "v0.0.0";

    /**
     * The main entry point for the StarViewer application.
     * Parses command-line arguments, sets up logging, and initializes the universe.
     * 
     * @param args command-line arguments
     */
    public static void main(String[] args) 
    {
        cLoggingDecorator logger = null;
        eLogLevel lvl = eLogLevel.kWarning;
        String filepath = "";

        //Parse command line arguments.
        if(args.length > 0)
        {
            for(int i = 0; i < args.length;)
            {
                String arg = args[i];
                //Get the log level from the console.
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
                //Get the log file path from the console.
                else if(arg.equals("-log"))
                {
                    filepath = args[i + 1];
                    i+=2;
                }
                //Print help message.
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
                //Print version message.
                else if(arg.equals("-version"))
                {
                    IO.println("StarViewer " + VERSION);
                    
                    //Terminate after version message.
                    return;
                }
                //Default case for unknown argument.
                else
                {
                    IO.println("Unknown argument: " + arg);
                    IO.println("Use -help for a list of valid arguments.");

                    //Terminate after unknown argument.
                    return; 
                }
            }
        }

        //Setup logging.
        cLoggingFile file_logger = null;

        if(!filepath.isEmpty())
        {
            file_logger = new cLoggingFile(filepath, lvl);
            logger = new cLoggingConsole(file_logger, eLogLevel.kWarning);
        }
        else
        {
            logger = new cLoggingConsole(lvl);
        }

        //Start program.
        IO.println("Starting StarViewer " + VERSION);

        cUniverse universe = new cUniverse(logger);
        universe.TestUniverse();

        IO.println(universe.ToString());

        IO.println("Press any key to exit.");
        IO.readln();

        //Close file if it was opened.
        if(file_logger != null)
        {
            file_logger.Close();
        }

        IO.println("Thank you for using StarViewer.\nGoodbye!");
    }
}
