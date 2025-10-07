package Data;

import java.util.ArrayList;
import java.util.Random;

import Logging.cLoggingBase;

/**
 * Represents a galaxy containing stars in the StarViewer application.
 * This class extends cObjectBase to inherit naming functionality and manages
 * a collection of stars within the galaxy. It supports various galaxy types
 * according to the Hubble classification system.
 * 
 * @author Stephen Hyberger
 * @version 1.0
 */
public class cGalaxy extends cObjectBase
{
    /**
     * Enumeration of galaxy types based on the Hubble classification system.
     * Includes elliptical, lenticular, spiral, barred spiral, and irregular galaxy types.
     */
    public enum eGalaxyType
    {
        /** Elliptical galaxy type 0 (most round) */
        kElliptical0,
        /** Elliptical galaxy type 1 */
        kElliptical1,
        /** Elliptical galaxy type 2 */
        kElliptical2,
        /** Elliptical galaxy type 3 */
        kElliptical3,
        /** Elliptical galaxy type 4 */
        kElliptical4,
        /** Elliptical galaxy type 5 */
        kElliptical5,
        /** Elliptical galaxy type 6 */
        kElliptical6,
        /** Elliptical galaxy type 7 (most elongated) */
        kElliptical7,
        /** Lenticular galaxy (S0) */
        kLinticular,
        /** Spiral galaxy type Sa */
        kSpiralA,
        /** Spiral galaxy type Sb */
        kSpiralB,
        /** Spiral galaxy type Sc */
        kSpiralC,
        /** Barred spiral galaxy type SBa */
        kSpiralBarredA,
        /** Barred spiral galaxy type SBb */
        kSpiralBarredB,
        /** Barred spiral galaxy type SBc */
        kSpiralBarredC,
        /** Irregular galaxy */
        kIrregular
    }

    /**
     * List of stars contained in this galaxy.
     */
    private ArrayList<cStar> mStars;
    
    /**
     * The classification type of this galaxy.
     */
    private eGalaxyType mType;
    
    /**
     * Logger instance for this galaxy.
     */
    private cLoggingBase mLogger;

    /**
     * Constructs a galaxy with the specified name and type.
     * Uses the standby logger as the default logger.
     * 
     * @param name the name of the galaxy
     * @param type the classification type of the galaxy
     */
    public cGalaxy(String name, eGalaxyType type)
    {
        super(name);
        mStars = new ArrayList<cStar>();
        mType = type;
        mLogger = cLoggingBase.GetStandbyLogger();
    }
    
    /**
     * Constructs a galaxy with the specified name and default type (SBc).
     * Uses the standby logger as the default logger.
     * 
     * @param name the name of the galaxy
     */
    public cGalaxy(String name) 
    { 
        this(name, eGalaxyType.kSpiralBarredC); 
        mLogger = cLoggingBase.GetStandbyLogger();
    }
    
    /**
     * Constructs a galaxy with the specified name, default type (SBc), and logger.
     * 
     * @param name the name of the galaxy
     * @param logger the logger to use for this galaxy
     */
    public cGalaxy(String name, cLoggingBase logger)
    {
        this(name, eGalaxyType.kSpiralBarredC);
        mLogger = logger;
    }
    
    /**
     * Constructs a galaxy with the specified name, type, and logger.
     * 
     * @param name the name of the galaxy
     * @param type the classification type of the galaxy
     * @param logger the logger to use for this galaxy
     */
    public cGalaxy(String name, eGalaxyType type, cLoggingBase logger)
    {
        super(name);
        mStars = new ArrayList<cStar>();
        mType = type;
        mLogger = logger;
    }


    /**
     * Clears all stars from the galaxy.
     * Also clears all planets from each star before removing them.
     */
    public void ClearStars()
    {
        mLogger.LogTrace("Clearing stars from galaxy " + this.ToString());
        for(cStar s : mStars)
        {
            s.ClearPlanets();
        }
        mStars.clear();
    }

    /**
     * Appends a star to the end of the galaxy's star list.
     * If the star is already in the galaxy, a warning is logged but no exception is thrown.
     * 
     * @param star the star to add to the galaxy
     * @throws IllegalArgumentException if the star is null
     */
    public void AddStar(cStar star) throws IllegalArgumentException
    {
        if(star != null)
        {
            if(!mStars.contains(star))
            {
                mLogger.LogTrace("Adding star " + star.ToString() + " to galaxy " + this.ToString());
                mStars.add(star);
            }            
            else
            {
                String message = "Cannot add " + star.ToString() + " to galaxy " + this.ToString() + ": star already in galaxy";
                mLogger.LogWarning(message);
            }
        }
        else
        {
            String message = "Cannot add null star to galaxy " + this.ToString();
            mLogger.LogError(message);
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Inserts a star at a specific index in the galaxy's star list.
     * If the star is already in the galaxy, a warning is logged but no exception is thrown.
     * 
     * @param star the star to add to the galaxy
     * @param index the index at which to insert the star
     * @throws IndexOutOfBoundsException if the index is out of bounds
     * @throws IllegalArgumentException if the star is null
     */
    public void AddStar(cStar star, int index) throws IndexOutOfBoundsException, IllegalArgumentException
    {
        if(star != null)
        {
            if(index >= 0 && index <= mStars.size())
            {
                mLogger.LogTrace("Adding star " + star.ToString() + " to galaxy " + this.ToString());
                if(!mStars.contains(star))
                {
                    mStars.add(index, star);
                }            
                else
                {
                    String message = "Cannot add " + star.ToString() + " to galaxy " + this.ToString() + ": star already in galaxy";
                    mLogger.LogWarning(message);
                }
            }
            else
            {
                String message = "Index of " + index + " is out of bounds for galaxy " + this.ToString();
                mLogger.LogError(message);
                throw new IndexOutOfBoundsException(message);
            }
        }
        else
        {
            String message = "Cannot add null star to galaxy " + this.ToString();
            mLogger.LogError(message);
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Removes a star from the galaxy by reference.
     * If the star is not in the galaxy, a warning is logged but no exception is thrown.
     * 
     * @param star the star to remove from the galaxy
     * @throws IllegalArgumentException if the star is null
     */
    public void RemoveStar(cStar star) throws IllegalArgumentException
    {
        if(star != null)
        {
            if(mStars.contains(star))
            {
                mLogger.LogTrace("Removing star " + star.ToString() + " from galaxy " + this.ToString());
                mStars.remove(star);
            }            
            else
            {
                String message = "Cannot remove " + star.ToString() + " from galaxy " + this.ToString() + ": star not in galaxy";
                mLogger.LogWarning(message);
            }
        }
        else
        {
            String message = "Cannot remove null star from galaxy " + this.ToString();
            mLogger.LogError(message);
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Removes a star from the galaxy by index.
     * 
     * @param index the index of the star to remove
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public void RemoveStar(int index) throws IndexOutOfBoundsException
    {
        if(index >= 0 && index < mStars.size())
        {
            mLogger.LogTrace("Removing star at index " + index + " from galaxy " + this.ToString());
            mStars.remove(index);
        }
        else
        {
            String message = "Index " + index + " is out of bounds for galaxy " + this.ToString();
            mLogger.LogError(message);
            throw new IndexOutOfBoundsException(message);
        }
    }

    /**
     * Gets a reference to a star at the specified index.
     * 
     * @param index the index of the star to retrieve
     * @return the star at the specified index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public cStar GetStar(int index) throws IndexOutOfBoundsException
    {
        if(index >= 0 && index < mStars.size())
        {
            mLogger.LogTrace("Getting star at index " + index + " from galaxy " + this.ToString());
            return mStars.get(index);
        }
        else
        {
            String message = "Index " + index + " is out of bounds for galaxy " + this.ToString();
            mLogger.LogError(message);
            throw new IndexOutOfBoundsException(message);
        }
    }

    /**
     * Gets the number of stars in the galaxy.
     * 
     * @return the count of stars in this galaxy
     */
    public int GetStarCount()
    {
        mLogger.LogTrace("Getting star count from galaxy " + this.ToString());
        return mStars.size();
    }

    /**
     * Gets the classification type of the galaxy.
     * 
     * @return the galaxy type
     */
    public eGalaxyType GetType()
    {
        mLogger.LogTrace("Getting type from galaxy " + this.ToString());
        return mType;
    }
    
    /**
     * Sets the classification type of the galaxy.
     * 
     * @param type the new galaxy type to set
     */
    public void SetType(eGalaxyType type)
    {
        mLogger.LogTrace("Setting type of galaxy " + this.ToString() + " to " + type);
        mType = type;
    }

    /**
     * Returns a string representation of the galaxy with optional recursive details.
     * 
     * @param recursive if true, includes detailed information about all contained stars
     * @return a string representation showing the galaxy name, type, star count, and optionally detailed star information
     */
    public String ToString(boolean recursive)
    {
        String strOut = "Galaxy: " + this.mName + " : " + this.mType + " (" +  this.mStars.size() + ")";
        if ((recursive)) {
            for(cStar s : mStars)
            {
                strOut += "\n\t" + s.ToString(recursive);
            } 
        }

        strOut += "\n";
        return strOut;
    }
    
    /**
     * Returns a string representation of the galaxy without recursive details.
     * 
     * @return a basic string representation of the galaxy
     */
    public String ToString()
    {
        return ToString(false);
    }

    /**
     * Gets a random galaxy type using the provided random number generator.
     * This is a static utility method that can be used without creating a galaxy instance.
     * 
     * @param rand the random number generator to use
     * @return a randomly selected galaxy type
     */
    public static eGalaxyType GetRandomGalaxyType(Random rand)
    {
        eGalaxyType typeOut = eGalaxyType.kIrregular;
        int randInt = rand.nextInt(0, eGalaxyType.values().length);
        typeOut = eGalaxyType.values()[randInt];

        return typeOut;
    }

    /**
     * Gets a random galaxy type using a new random number generator.
     * This is a static utility method that can be used without creating a galaxy instance.
     * 
     * @return a randomly selected galaxy type
     */
    public static eGalaxyType GetRandomGalaxyType()
    {
        eGalaxyType typeOut = eGalaxyType.kIrregular;
        int randInt = (new Random()).nextInt(0, eGalaxyType.values().length);
        typeOut = eGalaxyType.values()[randInt];

        return typeOut;
    }
}