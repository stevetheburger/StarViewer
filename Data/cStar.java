package Data;

import java.util.ArrayList;
import java.util.Random;

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

    public cStar(String name, eStarType type)
    {
        super(name);
        mPlanets = new ArrayList<cPlanet>();
        mType = type;
    }
    public cStar(String name) 
    {
        this(name, eStarType.kClassG);
    }

    //Methods to manipulate the array of planets.
    //Append a planet to the end of the array.
    public void AddPlanet(cPlanet planet)
    {
        if(planet != null)
        {
            if(!mPlanets.contains(planet))
            {
                mPlanets.add(planet);
            }
            else
            {
                IO.println("Cannot add " + planet.ToString() + " to system " + this.ToString() + ": planet already in system");
            }
        }
        else
        {
            IO.println("Cannot add null planet to system " + this.ToString());
        }
    }

    //Insert a planet at a specific index in the array.
    public void AddPlanet(cPlanet planet, int index)
    {
        if(planet != null)
        {
            if(index >= 0 && index <= mPlanets.size())
            {

                if(!mPlanets.contains(planet))
                {
                    mPlanets.add(index, planet);
                }            
                else
                {
                    IO.println("Cannot add " + planet.ToString() + " to system " + this.ToString() + ": planet already in system");
                }
            }
            else
            {
                IO.println("Index of " + index + " is out of bounds for system " + this.ToString());
            }
        }
        else
        {
            IO.println("Cannot add null planet to system " + this.ToString());
        }
    }

    //Remove a planet from the array by reference.
    public void RemovePlanet(cPlanet planet)
    {
        if(planet != null)
        {
            if(mPlanets.contains(planet))
            {
                mPlanets.remove(planet);
            }            
            else
            {
                IO.println("Cannot remove " + planet.ToString() + " from system " + this.ToString() + ": planet not in system");
            }
        }
        else
        {
            IO.println("Cannot remove null planet from system " + this.ToString());
        }
    }

    //Remove a planet from the array by index.
    public void RemovePlanet(int index)
    {
        if(index >= 0 && index < mPlanets.size())
        {
            mPlanets.remove(index);
        }
        else
        {
            IO.println("Index " + index + " is out of bounds for system " + this.ToString());
        }
    }

    //Get a reference to a planet at the specified index.
    public cPlanet GetPlanet(int index)
    {
        if(index >= 0 && index < mPlanets.size())
        {
            return mPlanets.get(index);
        }
        else
        {
            IO.println("Index " + index + " is out of bounds for system " + this.ToString());
            return null;
        }
    }

    //Get the number of planets in the system.
    public int GetPlanetCount()
    {
        return mPlanets.size();
    }

    public eStarType GetStarType()
    {
        return mType;
    }
    public void SetStarType(eStarType type)
    {
        mType = type;
    }

    //String representation of the star. Gives name and type of star.
    public String ToString()
    {
        String strOut = "System: " + this.GetName() + " : " + this.GetStarType() + " (" +  this.GetPlanetCount()+ ")";
        for(cPlanet p : mPlanets)
        {
            strOut += "\n\t\t" + p.ToString() + "\n";
        }
        return strOut;
    }   

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