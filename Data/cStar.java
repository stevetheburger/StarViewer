package Data;

import java.util.ArrayList;
import java.util.Random;

import Logging.cLoggingBase;

public class cStar extends cObjectBase
{
    public enum eStarType
    {
        kClassO, 
        kClassB, 
        kClassA, 
        kClassF, 
        kClassG, 
        kClassK, 
        kClassM
    }

    private ArrayList<cPlanet> mPlanets;
    private eStarType mType;
    private cLoggingBase mLogger;

    public cStar(String name, eStarType type)
    {
        super(name);
        mPlanets = new ArrayList<cPlanet>();
        mType = type;
        mLogger = cLoggingBase.GetStandbyLogger();
    }
    public cStar(String name) 
    {
        this(name, eStarType.kClassG);
        mLogger = cLoggingBase.GetStandbyLogger();
    }
    public cStar(String name, eStarType type, cLoggingBase logger)
    {
        super(name);
        mPlanets = new ArrayList<cPlanet>();
        mType = type;
        mLogger = logger;
    }
    public cStar(String name, cLoggingBase logger)
    {
        this(name, eStarType.kClassG);
        mLogger = logger;
    }

    //Methods to manipulate the array of planets.
    //Method to clear all planets from the star.
    public void ClearPlanets()
    {
        mLogger.LogTrace("Clearing planets from system " + this.ToString());
        for(cPlanet p : mPlanets)
        {
            p.ClearMoons();
        }
        mPlanets.clear();
    }
    //Append a planet to the end of the array.
    public void AddPlanet(cPlanet planet) throws IllegalArgumentException
    {
        if(planet != null)
        {
            if(!mPlanets.contains(planet))
            {
                mLogger.LogTrace("Adding planet " + planet.ToString() + " to system " + this.ToString());
                mPlanets.add(planet);
            }
            else
            {
                String message = "Cannot add " + planet.ToString() + " to system " + this.ToString() + ": planet already in system";
                mLogger.LogWarning(message);
            }
        }
        else
        {
            String message = "Cannot add null planet to system " + this.ToString();
            mLogger.LogError(message);
            throw new IllegalArgumentException(message);
        }
    }

    //Insert a planet at a specific index in the array.
    public void AddPlanet(cPlanet planet, int index) throws IndexOutOfBoundsException, IllegalArgumentException
    {
        if(planet != null)
        {
            if(index >= 0 && index <= mPlanets.size())
            {

                if(!mPlanets.contains(planet))
                {
                    mLogger.LogTrace("Adding planet " + planet.ToString() + " to system " + this.ToString());
                    mPlanets.add(index, planet);
                }            
                else
                {
                    String message = "Cannot add " + planet.ToString() + " to system " + this.ToString() + ": planet already in system";
                    mLogger.LogWarning(message);
                }
            }
            else
            {
                String message = "Index of " + index + " is out of bounds for system " + this.ToString();
                mLogger.LogError(message);
                throw new IndexOutOfBoundsException(message);
            }
        }
        else
        {
            String message = "Cannot add null planet to system " + this.ToString();
            mLogger.LogError(message);
            throw new IllegalArgumentException(message);
        }
    }

    //Remove a planet from the array by reference.
    public void RemovePlanet(cPlanet planet) throws IllegalArgumentException
    {
        if(planet != null)
        {
            if(mPlanets.contains(planet))
            {
                mLogger.LogTrace("Removing planet " + planet.ToString() + " from system " + this.ToString());
                mPlanets.remove(planet);
            }            
            else
            {
                String message = "Cannot remove " + planet.ToString() + " from system " + this.ToString() + ": planet not in system";
                mLogger.LogWarning(message);
            }
        }
        else
        {
            String message = "Cannot remove null planet from system " + this.ToString();
            mLogger.LogError(message);
            throw new IllegalArgumentException(message);
        }
    }

    //Remove a planet from the array by index.
    public void RemovePlanet(int index) throws IndexOutOfBoundsException
    {
        if(index >= 0 && index < mPlanets.size())
        {
            mLogger.LogTrace("Removing planet at index " + index + " from system " + this.ToString());
            mPlanets.remove(index);
        }
        else
        {
            String message = "Index " + index + " is out of bounds for system " + this.ToString();
            mLogger.LogError(message);
            throw new IndexOutOfBoundsException(message);
        }
    }

    //Get a reference to a planet at the specified index.
    public cPlanet GetPlanet(int index) throws IndexOutOfBoundsException
    {
        if(index >= 0 && index < mPlanets.size())
        {
            mLogger.LogTrace("Getting planet at index " + index + " from system " + this.ToString());
            return mPlanets.get(index);
        }
        else
        {
            String message = "Index " + index + " is out of bounds for system " + this.ToString();
            mLogger.LogError(message);
            throw new IndexOutOfBoundsException(message);
        }
    }

    //Get the number of planets in the system.
    public int GetPlanetCount()
    {
        mLogger.LogTrace("Getting planet count from system " + this.ToString());
        return mPlanets.size();
    }

    public eStarType GetStarType()
    {
        mLogger.LogTrace("Getting type from system " + this.ToString());
        return mType;
    }
    public void SetStarType(eStarType type)
    {
        mLogger.LogTrace("Setting type of system " + this.ToString() + " to " + type);
        mType = type;
    }

    //String representation of the star. Gives name and type of star.
    public String ToString(boolean recursive)
    {
        String strOut = "System: " + this.mName + " : " + this.mType + " (" +  this.mPlanets.size() + ")";
        
        if ((recursive)) {
            for(cPlanet p : mPlanets)
            {
                strOut += "\n\t\t" + p.ToString(recursive);
            } 
        }
        return strOut;
    }  
    public String ToString()
    {
        return ToString(false);
    }

    //Static methods. No logging available here.
    //Get a random star type.
    public static eStarType GetRandomStarType(Random rand)
    {
        eStarType typeOut = eStarType.kClassG;
        int randInt = rand.nextInt(0, eStarType.values().length);
        typeOut = eStarType.values()[randInt];

        return typeOut;
    }
    public static eStarType GetRandomStarType()
    {
        eStarType typeOut = eStarType.kClassG;
        int randInt = (new Random()).nextInt(0, eStarType.values().length);
        typeOut = eStarType.values()[randInt];

        return typeOut;
    }
}