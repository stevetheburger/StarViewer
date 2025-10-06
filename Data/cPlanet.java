package Data;

import java.util.ArrayList;
import java.util.Random;

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

    public cPlanet(String name, ePlanetType type)
    {
        super(name);
        mType = type;
        mMoons = new ArrayList<cPlanet>();
    }
    public cPlanet(String name) 
    { 
        this(name, ePlanetType.kTerrestrial); 
    }

    //Methods to manipulate the array of planets.
    //Append a moon to the end of the array.
    public void AddMoon(cPlanet moon)
    {
        if(moon != null)
        {
            if(!mMoons.contains(moon))
            {
                mMoons.add(moon);
            }
            else
            {
                IO.println("Cannot add " + moon.ToString() + " to system " + this.ToString() + ": moon already in system");
            }
        }
        else
        {
            IO.println("Cannot add null moon to system " + this.ToString());
        }
    }

    //Insert a moon at a specific index in the array.
    public void AddMoon(cPlanet moon, int index)
    {
        if(moon != null)
        {
            if(index >= 0 && index <= mMoons.size())
            {

                if(!mMoons.contains(moon))
                {
                    mMoons.add(index, moon);
                }            
                else
                {
                    IO.println("Cannot add " + moon.ToString() + " to system " + this.ToString() + ": moon already in system");
                }
            }
            else
            {
                IO.println("Index of " + index + " is out of bounds for system " + this.ToString());
            }
        }
        else
        {
            IO.println("Cannot add null moon to system " + this.ToString());
        }
    }

    //Remove a moon from the array by reference.
    public void RemoveMoon(cPlanet moon)
    {
        if(moon != null)
        {
            if(mMoons.contains(moon))
            {
                mMoons.remove(moon);
            }            
            else
            {
                IO.println("Cannot remove " + moon.ToString() + " from system " + this.ToString() + ": moon not in system");
            }
        }
        else
        {
            IO.println("Cannot remove null moon from system " + this.ToString());
        }
    }

    //Remove a moon from the array by index.
    public void RemoveMoon(int index)
    {
        if(index >= 0 && index < mMoons.size())
        {
            mMoons.remove(index);
        }
        else
        {
            IO.println("Index " + index + " is out of bounds for system " + this.ToString());
        }
    }

    //Get a reference to a moon at the specified index.
    public cPlanet GetMoon(int index)
    {
        if(index >= 0 && index < mMoons.size())
        {
            return mMoons.get(index);
        }
        else
        {
            IO.println("Index " + index + " is out of bounds for system " + this.ToString());
            return null;
        }
    }

    //Get the number of moons in the system.
    public int GetMoonCount()
    {
        return mMoons.size();
    }

    public ePlanetType GetPlanetType()
    {
        return mType;
    }
    public void SetPlanetType(ePlanetType type)
    {
        mType = type;
    }

    //String representation of the planet. Gives name, type, and number of moons.
    public String ToString()
    {
        String strOut = "Planet: " + this.GetName() + " : " + this.GetPlanetType() + " (" +  this.GetMoonCount()+ ")";
        for(cPlanet m : mMoons)
        {
            strOut += "\n\t\t\t" + m.ToString();
        }
        return strOut;
    }

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