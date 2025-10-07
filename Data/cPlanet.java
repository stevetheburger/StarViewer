package Data;

import java.util.ArrayList;
import java.util.Random;

import Logging.cLoggingBase;

public class cPlanet extends cObjectBase
{
    public enum ePlanetType
    {
        kTerrestrial,
        kGasGiant,
        kIceGiant
    }

    private ePlanetType mType;
    private ArrayList<cPlanet> mMoons;
    private cLoggingBase mLogger;

    public cPlanet(String name, ePlanetType type)
    {
        super(name);
        mType = type;
        mMoons = new ArrayList<cPlanet>();
        mLogger = cLoggingBase.GetStandbyLogger();
    }
    public cPlanet(String name) 
    { 
        this(name, ePlanetType.kTerrestrial); 
        mLogger = cLoggingBase.GetStandbyLogger();
    }
    public cPlanet(String name, ePlanetType type, cLoggingBase logger)
    {
        super(name);
        mType = type;
        mMoons = new ArrayList<cPlanet>();
        mLogger = logger;
    }
    public cPlanet(String name, cLoggingBase logger)
    {
        this(name, ePlanetType.kTerrestrial);
        mLogger = logger;
    }

    //Methods to manipulate the array of planets.
    //Method to clear all moons from the planet.
    public void ClearMoons()
    {
        mLogger.LogTrace("Clearing moons from planet " + this.ToString());
        for(cPlanet p : mMoons)
        {
            p.ClearMoons();
        }
        mMoons.clear();
    }
    //Append a moon to the end of the array.
    public void AddMoon(cPlanet moon) throws IllegalArgumentException
    {
        if(moon != null)
        {
            if(!mMoons.contains(moon))
            {
                mLogger.LogTrace("Adding moon " + moon.ToString() + " to planet " + this.ToString());
                mMoons.add(moon);
            }
            else
            {
                String message = "Cannot add " + moon.ToString() + " to planet " + this.ToString() + ": moon already in planet";
                mLogger.LogWarning(message);
            }
        }
        else
        {
            String message = "Cannot add null moon to planet " + this.ToString();
            mLogger.LogError(message);
            throw new IllegalArgumentException(message);
        }
    }

    //Insert a moon at a specific index in the array.
    public void AddMoon(cPlanet moon, int index) throws IndexOutOfBoundsException, IllegalArgumentException
    {
        if(moon != null)
        {
            if(index >= 0 && index <= mMoons.size())
            {

                if(!mMoons.contains(moon))
                {
                    mLogger.LogTrace("Adding moon " + moon.ToString() + " to planet " + this.ToString());
                    mMoons.add(index, moon);
                }            
                else
                {
                    String message = "Cannot add " + moon.ToString() + " to planet " + this.ToString() + ": moon already in planet";
                    mLogger.LogWarning(message);
                }
            }
            else
            {
                String message = "Index of " + index + " is out of bounds for planet " + this.ToString();
                mLogger.LogError(message);
                throw new IndexOutOfBoundsException(message);
            }
        }
        else
        {
            String message = "Cannot add null moon to planet " + this.ToString();
            mLogger.LogError(message);
            throw new IllegalArgumentException(message);
        }
    }

    //Remove a moon from the array by reference.
    public void RemoveMoon(cPlanet moon) throws IllegalArgumentException
    {
        if(moon != null)
        {
            if(mMoons.contains(moon))
            {
                mLogger.LogTrace("Removing moon " + moon.ToString() + " from planet " + this.ToString());
                mMoons.remove(moon);
            }            
            else
            {
                String message = "Cannot remove " + moon.ToString() + " from planet " + this.ToString() + ": moon not in planet";
                mLogger.LogWarning(message);
            }
        }
        else
        {
            String message = "Cannot remove null moon from planet " + this.ToString();
            mLogger.LogError(message);
            throw new IllegalArgumentException(message);
        }
    }

    //Remove a moon from the array by index.
    public void RemoveMoon(int index) throws IndexOutOfBoundsException
    {
        if(index >= 0 && index < mMoons.size())
        {
            mLogger.LogTrace("Removing moon at index " + index + " from planet " + this.ToString());
            mMoons.remove(index);
        }
        else
        {
            String message = "Index " + index + " is out of bounds for planet " + this.ToString();
            mLogger.LogError(message);
            throw new IndexOutOfBoundsException(message);
        }
    }

    //Get a reference to a moon at the specified index.
    public cPlanet GetMoon(int index) throws IndexOutOfBoundsException
    {
        if(index >= 0 && index < mMoons.size())
        {
            mLogger.LogTrace("Getting moon at index " + index + " from planet " + this.ToString());
            return mMoons.get(index);
        }
        else
        {
            String message = "Index " + index + " is out of bounds for planet " + this.ToString();
            mLogger.LogError(message);
            throw new IndexOutOfBoundsException(message);
        }
    }

    //Get the number of moons in the system.
    public int GetMoonCount()
    {
        mLogger.LogTrace("Getting moon count from planet " + this.ToString());
        return mMoons.size();
    }

    //Get/Set the type of the planet.
    public ePlanetType GetPlanetType()
    {
        mLogger.LogTrace("Getting type from planet " + this.ToString());
        return mType;
    }
    public void SetPlanetType(ePlanetType type)
    {
        mLogger.LogTrace("Setting type of planet " + this.ToString() + " to " + type);
        mType = type;
    }

    //String representation of the planet. Gives name, type, and number of moons.
    public String ToString(boolean recursive)
    {
        String strOut = "Planet: " + this.mName + " : " + this.mType + " (" +  this.mMoons.size() + ")";
        if ((recursive)) {
            for(cPlanet m : mMoons)
            {
                strOut += "\n\t\t\t" + m.ToString(recursive);
            }
        }
        return strOut;
    }
    public String ToString()
    {
        return ToString(false);
    }

    //Static methods. No logging available here.
    //Get a random planet type.
    public static ePlanetType GetRandomPlanetType(Random rand)
    {
        ePlanetType typeOut = ePlanetType.kTerrestrial;
        int randInt = rand.nextInt(0, ePlanetType.values().length);
        typeOut = ePlanetType.values()[randInt];

        return typeOut;
    }

    public static ePlanetType GetRandomPlanetType()
    {
        ePlanetType typeOut = ePlanetType.kTerrestrial;
        int randInt = (new Random()).nextInt(0, ePlanetType.values().length);
        typeOut = ePlanetType.values()[randInt];

        return typeOut;
    }
}