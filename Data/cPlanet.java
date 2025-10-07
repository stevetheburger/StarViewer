package Data;

import java.util.ArrayList;
import java.util.Random;

import Logging.cLoggingBase;

/**
 * Represents a planet with potential moons in the StarViewer application.
 * This class extends cObjectBase to inherit naming functionality and manages
 * a collection of moons orbiting the planet. It supports various planetary
 * classifications based on composition and structure.
 * 
 * @author Stephen Hyberger
 * @version 1.0
 */
public class cPlanet extends cObjectBase
{
    /**
     * Enumeration of planetary types based on composition and structure.
     */
    public enum ePlanetType
    {
        /** Terrestrial planet: Rocky, Earth-like planets */
        kTerrestrial,
        /** Gas giant: Large planets composed mainly of hydrogen and helium */
        kGasGiant,
        /** Ice giant: Planets with significant amounts of water, methane, and ammonia ices */
        kIceGiant
    }

    /**
     * The classification type of this planet.
     */
    private ePlanetType mType;
    
    /**
     * List of moons orbiting this planet.
     */
    private ArrayList<cPlanet> mMoons;
    
    /**
     * Logger instance for this planet.
     */
    private cLoggingBase mLogger;

    /**
     * Constructs a planet with the specified name and type.
     * Uses the standby logger as the default logger.
     * 
     * @param name the name of the planet
     * @param type the classification type of the planet
     */
    public cPlanet(String name, ePlanetType type)
    {
        super(name);
        mType = type;
        mMoons = new ArrayList<cPlanet>();
        mLogger = cLoggingBase.GetStandbyLogger();
    }
    
    /**
     * Constructs a planet with the specified name and default type (Terrestrial).
     * Uses the standby logger as the default logger.
     * 
     * @param name the name of the planet
     */
    public cPlanet(String name) 
    { 
        this(name, ePlanetType.kTerrestrial); 
        mLogger = cLoggingBase.GetStandbyLogger();
    }
    
    /**
     * Constructs a planet with the specified name, type, and logger.
     * 
     * @param name the name of the planet
     * @param type the classification type of the planet
     * @param logger the logger to use for this planet
     */
    public cPlanet(String name, ePlanetType type, cLoggingBase logger)
    {
        super(name);
        mType = type;
        mMoons = new ArrayList<cPlanet>();
        mLogger = logger;
    }
    
    /**
     * Constructs a planet with the specified name, default type (Terrestrial), and logger.
     * 
     * @param name the name of the planet
     * @param logger the logger to use for this planet
     */
    public cPlanet(String name, cLoggingBase logger)
    {
        this(name, ePlanetType.kTerrestrial);
        mLogger = logger;
    }

    /**
     * Clears all moons from the planet.
     * Also recursively clears all moons from each moon before removing them.
     */
    public void ClearMoons()
    {
        mLogger.LogTrace("Clearing moons from planet " + this.ToString());
        for(cPlanet p : mMoons)
        {
            p.ClearMoons();
        }
        mMoons.clear();
    }
    
    /**
     * Appends a moon to the end of the planet's moon list.
     * If the moon is already orbiting the planet, a warning is logged but no exception is thrown.
     * 
     * @param moon the moon to add to the planet
     * @throws IllegalArgumentException if the moon is null
     */
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

    /**
     * Inserts a moon at a specific index in the planet's moon list.
     * If the moon is already orbiting the planet, a warning is logged but no exception is thrown.
     * 
     * @param moon the moon to add to the planet
     * @param index the index at which to insert the moon
     * @throws IndexOutOfBoundsException if the index is out of bounds
     * @throws IllegalArgumentException if the moon is null
     */
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

    /**
     * Removes a moon from the planet by reference.
     * If the moon is not orbiting the planet, a warning is logged but no exception is thrown.
     * 
     * @param moon the moon to remove from the planet
     * @throws IllegalArgumentException if the moon is null
     */
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

    /**
     * Removes a moon from the planet by index.
     * 
     * @param index the index of the moon to remove
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
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

    /**
     * Gets a reference to a moon at the specified index.
     * 
     * @param index the index of the moon to retrieve
     * @return the moon at the specified index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
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

    /**
     * Gets the number of moons orbiting the planet.
     * 
     * @return the count of moons orbiting this planet
     */
    public int GetMoonCount()
    {
        mLogger.LogTrace("Getting moon count from planet " + this.ToString());
        return mMoons.size();
    }

    /**
     * Gets the classification type of the planet.
     * 
     * @return the planetary type of this planet
     */
    public ePlanetType GetPlanetType()
    {
        mLogger.LogTrace("Getting type from planet " + this.ToString());
        return mType;
    }
    
    /**
     * Sets the classification type of the planet.
     * 
     * @param type the new planetary type to set
     */
    public void SetPlanetType(ePlanetType type)
    {
        mLogger.LogTrace("Setting type of planet " + this.ToString() + " to " + type);
        mType = type;
    }

    /**
     * Returns a string representation of the planet with optional recursive details.
     * 
     * @param recursive if true, includes detailed information about all orbiting moons
     * @return a string representation showing the planet name, type, moon count, and optionally detailed moon information
     */
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
    
    /**
     * Returns a string representation of the planet without recursive details.
     * 
     * @return a basic string representation of the planet
     */
    public String ToString()
    {
        return ToString(false);
    }

    /**
     * Gets a random planetary type using the provided random number generator.
     * This is a static utility method that can be used without creating a planet instance.
     * 
     * @param rand the random number generator to use
     * @return a randomly selected planetary type
     */
    public static ePlanetType GetRandomPlanetType(Random rand)
    {
        ePlanetType typeOut = ePlanetType.kTerrestrial;
        int randInt = rand.nextInt(0, ePlanetType.values().length);
        typeOut = ePlanetType.values()[randInt];

        return typeOut;
    }

    /**
     * Gets a random planetary type using a new random number generator.
     * This is a static utility method that can be used without creating a planet instance.
     * 
     * @return a randomly selected planetary type
     */
    public static ePlanetType GetRandomPlanetType()
    {
        ePlanetType typeOut = ePlanetType.kTerrestrial;
        int randInt = (new Random()).nextInt(0, ePlanetType.values().length);
        typeOut = ePlanetType.values()[randInt];

        return typeOut;
    }
}