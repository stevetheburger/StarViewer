import Data.*;

public class cProgram 
{
    public static final String VERSION = "v0.0.0";

    public static void main(String[] args) 
    {
        IO.println("Starting StarViewer " + VERSION);

        cUniverse universe = null;

        if(args.length > 0)
        {
            //Not yet implemented: load a universe definition file.
            throw new UnsupportedOperationException("Loading a universe definition file is not yet implemented.");
        }
        else
        {
            //If no definition file is provided, create a default test universe.
            universe = cUniverse.TestUniverse();
        }

        IO.println(universe.ToString());

        IO.println("Press any key to exit.");
        IO.readln();

        IO.println("Thank you for using StarViewer.\nGoodbye!");
    }
}
