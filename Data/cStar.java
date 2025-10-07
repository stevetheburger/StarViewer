package Data;

import java.util.ArrayList;
import java.util.Random;

import Logging.cLoggingBase;

/**
 * Represents a star system containing planets in the StarViewer application.
 * This class extends cObjectBase to inherit naming functionality and manages
 * a collection of planets orbiting the star. It supports various stellar
 * classifications according to the Morgan-Keenan system.
 * 
 * @author Stephen Hyberger
 * @version 1.0
 */
public class cStar extends cObjectBase
{
    /**
     * Enumeration of stellar classification types based on the Morgan-Keenan system.
     * Classifications are ordered from hottest to coolest stellar types.
     */
    public enum eStarType
    {
        /** O-type star: Very hot, blue, massive stars */
        kClassO, 
        /** B-type star: Hot, blue-white stars */
        kClassB, 
        /** A-type star: Hot, white stars */
        kClassA, 
        /** F-type star: Yellow-white stars */
        kClassF, 
        /** G-type star: Yellow stars like our Sun */
        kClassG, 
        /** K-type star: Orange, cooler stars */
        kClassK, 
        /** M-type star: Red, coolest main sequence stars */
        kClassM
    }

    /**
     * List of planets orbiting this star.
     */
    private ArrayList<cPlanet> mPlanets;
    
    /**
     * The stellar classification of this star.
     */
    private eStarType mType;
    
    /**
     * Logger instance for this star system.
     */
    private cLoggingBase mLogger;

    /**
     * Constructs a star with the specified name and stellar type.
     * Uses the standby logger as the default logger.
     * 
     * @param name the name of the star
     * @param type the stellar classification of the star
     */
    public cStar(String name, eStarType type)
    {
        super(name);
        mPlanets = new ArrayList<cPlanet>();
        mType = type;
        mLogger = cLoggingBase.GetStandbyLogger();
    }
    
    /**
     * Constructs a star with the specified name and default type (G-class).
     * Uses the standby logger as the default logger.
     * 
     * @param name the name of the star
     */
    public cStar(String name) 
    {
        this(name, eStarType.kClassG);
        mLogger = cLoggingBase.GetStandbyLogger();
    }
    
    /**
     * Constructs a star with the specified name, type, and logger.
     * 
     * @param name the name of the star
     * @param type the stellar classification of the star
     * @param logger the logger to use for this star system
     */
    public cStar(String name, eStarType type, cLoggingBase logger)
    {
        super(name);
        mPlanets = new ArrayList<cPlanet>();
        mType = type;
        mLogger = logger;
    }
    
    /**
     * Constructs a star with the specified name, default type (G-class), and logger.
     * 
     * @param name the name of the star
     * @param logger the logger to use for this star system
     */
    public cStar(String name, cLoggingBase logger)
    {
        this(name, eStarType.kClassG);
        mLogger = logger;
    }

    /**
     * Clears all planets from the star system.
     * Also clears all moons from each planet before removing them.
     */
    public void ClearPlanets()
    {
        mLogger.LogTrace("Clearing planets from system " + this.ToString());
        for(cPlanet p : mPlanets)
        {
            p.ClearMoons();
        }
        mPlanets.clear();
    }
    
    /**
     * Appends a planet to the end of the star system's planet list.
     * If the planet is already in the system, a warning is logged but no exception is thrown.
     * 
     * @param planet the planet to add to the star system
     * @throws IllegalArgumentException if the planet is null
     */
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

    /**
     * Inserts a planet at a specific index in the star system's planet list.
     * If the planet is already in the system, a warning is logged but no exception is thrown.
     * 
     * @param planet the planet to add to the star system
     * @param index the index at which to insert the planet
     * @throws IndexOutOfBoundsException if the index is out of bounds
     * @throws IllegalArgumentException if the planet is null
     */
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

    /**
     * Removes a planet from the star system by reference.
     * If the planet is not in the system, a warning is logged but no exception is thrown.
     * 
     * @param planet the planet to remove from the star system
     * @throws IllegalArgumentException if the planet is null
     */
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

    /**
     * Removes a planet from the star system by index.
     * 
     * @param index the index of the planet to remove
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
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

    /**
     * Gets a reference to a planet at the specified index.
     * 
     * @param index the index of the planet to retrieve
     * @return the planet at the specified index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
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

    /**
     * Gets the number of planets in the star system.
     * 
     * @return the count of planets orbiting this star
     */
    public int GetPlanetCount()
    {
        mLogger.LogTrace("Getting planet count from system " + this.ToString());
        return mPlanets.size();
    }

    /**
     * Gets the stellar classification of this star.
     * 
     * @return the stellar type of this star
     */
    public eStarType GetStarType()
    {
        mLogger.LogTrace("Getting type from system " + this.ToString());
        return mType;
    }
    
    /**
     * Sets the stellar classification of this star.
     * 
     * @param type the new stellar type to set
     */
    public void SetStarType(eStarType type)
    {
        mLogger.LogTrace("Setting type of system " + this.ToString() + " to " + type);
        mType = type;
    }

    /**
     * Returns a string representation of the star system with optional recursive details.
     * 
     * @param recursive if true, includes detailed information about all orbiting planets
     * @return a string representation showing the star name, type, planet count, and optionally detailed planet information
     */
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
    
    /**
     * Returns a string representation of the star system without recursive details.
     * 
     * @return a basic string representation of the star system
     */
    public String ToString()
    {
        return ToString(false);
    }

    /**
     * Gets a random stellar type using the provided random number generator.
     * This is a static utility method that can be used without creating a star instance.
     * 
     * @param rand the random number generator to use
     * @return a randomly selected stellar type
     */
    public static eStarType GetRandomStarType(Random rand)
    {
        eStarType typeOut = eStarType.kClassG;
        int randInt = rand.nextInt(0, eStarType.values().length);
        typeOut = eStarType.values()[randInt];

        return typeOut;
    }
    
    /**
     * Gets a random stellar type using a new random number generator.
     * This is a static utility method that can be used without creating a star instance.
     * 
     * @return a randomly selected stellar type
     */
    public static eStarType GetRandomStarType()
    {
        eStarType typeOut = eStarType.kClassG;
        int randInt = (new Random()).nextInt(0, eStarType.values().length);
        typeOut = eStarType.values()[randInt];

        return typeOut;
    }
}