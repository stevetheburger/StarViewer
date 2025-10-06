package Data;

import java.util.ArrayList;
import java.util.Random;

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

    public cGalaxy(String name, eGalaxyType type)
    {
        super(name);
        mStars = new ArrayList<cStar>();
        mType = type;
    }
    public cGalaxy(String name) 
    { 
        this(name, eGalaxyType.kSpiralBarredC); 
    }


    //Methods to manipulate the array of stars.
    //Append a star to the end of the array.
    public void AddStar(cStar star)
    {
        if(star != null)
        {
            if(!mStars.contains(star))
            {
                mStars.add(star);
            }            
            else
            {
                IO.println("Cannot add " + star.ToString() + " to galaxy " + this.ToString() + ": star already in galaxy");
            }
        }
        else
        {
            IO.println("Cannot add null star to galaxy " + this.ToString());
        }
    }

    //Insert a star at a specific index in the array.
    public void AddStar(cStar star, int index)
    {
        if(star != null)
        {
            if(index >= 0 && index <= mStars.size())
            {

                if(!mStars.contains(star))
                {
                    mStars.add(index, star);
                }            
                else
                {
                    IO.println("Cannot add " + star.ToString() + " to galaxy " + this.ToString() + ": star already in galaxy");
                }
            }
            else
            {
                IO.println("Index of " + index + " is out of bounds for galaxy " + this.ToString());
            }
        }
        else
        {
            IO.println("Cannot add null star to galaxy " + this.ToString());
        }
    }

    //Remove a star from the array by reference.
    public void RemoveStar(cStar star)
    {
        if(star != null)
        {
            if(mStars.contains(star))
            {
                mStars.remove(star);
            }            
            else
            {
                IO.println("Cannot remove " + star.ToString() + " from galaxy " + this.ToString() + ": star not in galaxy");
            }
        }
        else
        {
            IO.println("Cannot remove null star from galaxy " + this.ToString());
        }
    }

    //Remove a star from the array by index.
    public void RemoveStar(int index)
    {
        if(index >= 0 && index < mStars.size())
        {
            mStars.remove(index);
        }
        else
        {
            IO.println("Index " + index + " is out of bounds for galaxy " + this.ToString());
        }
    }

    //Get a reference to a star at the specified index.
    public cStar GetStar(int index)
    {
        if(index >= 0 && index < mStars.size())
        {
            return mStars.get(index);
        }
        else
        {
            IO.println("Index " + index + " is out of bounds for galaxy " + this.ToString());
            return null;
        }
    }

    //Get the number of stars in the galaxy.
    public int GetStarCount()
    {
        return mStars.size();
    }

    //Get or set the type of the galaxy.
    public eGalaxyType GetType()
    {
        return mType;
    }
    public void SetType(eGalaxyType type)
    {
        mType = type;
    }

    //String representation of the galaxy. Gives name and count of stars.
    public String ToString()
    {
        String strOut = "Galaxy: " + this.GetName() + " : " + this.GetType() + " (" +  this.GetStarCount()+ ")";
        for(cStar s : mStars)
        {
            strOut += "\n\t" + s.ToString();
        }
        strOut += "\n";
        return strOut;
    }

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