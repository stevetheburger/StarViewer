package Data;

import java.util.ArrayList;
import java.util.Random;

import Logging.cLoggingBase;

//Represents a galaxy containing stars
public class cGalaxy extends cObjectBase
{
    public enum eGalaxyType
    {
        kElliptical0,
        kElliptical1,
        kElliptical2,
        kElliptical3,
        kElliptical4,
        kElliptical5,
        kElliptical6,
        kElliptical7,
        kLinticular,
        kSpiralA,
        kSpiralB,
        kSpiralC,
        kSpiralBarredA,
        kSpiralBarredB,
        kSpiralBarredC,
        kIrregular
    }

    private ArrayList<cStar> mStars;
    private eGalaxyType mType;
    private cLoggingBase mLogger;

    public cGalaxy(String name, eGalaxyType type)
    {
        super(name);
        mStars = new ArrayList<cStar>();
        mType = type;
        mLogger = cLoggingBase.GetStandbyLogger();
    }
    public cGalaxy(String name) 
    { 
        this(name, eGalaxyType.kSpiralBarredC); 
        mLogger = cLoggingBase.GetStandbyLogger();
    }
    public cGalaxy(String name, cLoggingBase logger)
    {
        this(name, eGalaxyType.kSpiralBarredC);
        mLogger = logger;
    }
    public cGalaxy(String name, eGalaxyType type, cLoggingBase logger)
    {
        super(name);
        mStars = new ArrayList<cStar>();
        mType = type;
        mLogger = logger;
    }


    //Methods to manipulate the array of stars.
    //Method to clear all stars from the galaxy.
    public void ClearStars()
    {
        mLogger.LogTrace("Clearing stars from galaxy " + this.ToString());
        for(cStar s : mStars)
        {
            s.ClearPlanets();
        }
        mStars.clear();
    }

    //Append a star to the end of the array.
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

    //Insert a star at a specific index in the array.
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

    //Remove a star from the array by reference.
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

    //Remove a star from the array by index.
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

    //Get a reference to a star at the specified index.
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

    //Get the number of stars in the galaxy.
    public int GetStarCount()
    {
        mLogger.LogTrace("Getting star count from galaxy " + this.ToString());
        return mStars.size();
    }

    //Get or set the type of the galaxy.
    public eGalaxyType GetType()
    {
        mLogger.LogTrace("Getting type from galaxy " + this.ToString());
        return mType;
    }
    public void SetType(eGalaxyType type)
    {
        mLogger.LogTrace("Setting type of galaxy " + this.ToString() + " to " + type);
        mType = type;
    }

    //String representation of the galaxy. Gives name and count of stars.
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
    public String ToString()
    {
        return ToString(false);
    }

    //Static methods. No logging available here.
    //Get a random galaxy type.
    public static eGalaxyType GetRandomGalaxyType(Random rand)
    {
        eGalaxyType typeOut = eGalaxyType.kIrregular;
        int randInt = rand.nextInt(0, eGalaxyType.values().length);
        typeOut = eGalaxyType.values()[randInt];

        return typeOut;
    }

    public static eGalaxyType GetRandomGalaxyType()
    {
        eGalaxyType typeOut = eGalaxyType.kIrregular;
        int randInt = (new Random()).nextInt(0, eGalaxyType.values().length);
        typeOut = eGalaxyType.values()[randInt];

        return typeOut;
    }
}