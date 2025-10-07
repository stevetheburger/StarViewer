package Data;

import java.util.ArrayList;
import java.util.Random;
import Logging.cLoggingBase;

//Represents a universe containing galaxies
public class cUniverse 
{
    private ArrayList<cGalaxy> mGalaxies;
    private cLoggingBase mLogger;

    //Basic constructor.
    public cUniverse()
    {
        mGalaxies = new ArrayList<cGalaxy>();
        mLogger = cLoggingBase.GetStandbyLogger();
    }
    public cUniverse(cLoggingBase logger)
    {
        mGalaxies = new ArrayList<cGalaxy>();
        mLogger = logger;
    }

    //Methods to manipulate the array of galaxies.
    public void ClearGalaxies()
    {
        for(cGalaxy g : mGalaxies)
        {
            g.ClearStars();
        }
        mGalaxies.clear();
    }
    //Append a galaxy to the end of the array.
    public void AddGalaxy(cGalaxy galaxy) throws IllegalArgumentException
    {
        if(galaxy != null)
        {
            if(!mGalaxies.contains(galaxy))
            {
                mLogger.LogTrace("Adding " + galaxy.ToString() + " to universe");
                mGalaxies.add(galaxy);
            }            
            else
            {
                String message = "Cannot add " + galaxy.ToString() + " to universe: galaxy already in universe";
                mLogger.LogWarning(message);
            }
        }
        else
        {
            String message = "Cannot add null galaxy to universe.";
            mLogger.LogWarning(message);
            throw new IllegalArgumentException(message);
        }
    }

    //Insert a galaxy at a specific index in the array.
    public void AddGalaxy(cGalaxy galaxy, int index) throws IndexOutOfBoundsException, IllegalArgumentException
    {
        if(galaxy != null)
        {
            if(index >= 0 && index <= mGalaxies.size())
            {
                mLogger.LogTrace("Adding " + galaxy.ToString() + " to universe at index " + index);
                if(!mGalaxies.contains(galaxy))
                {
                    mGalaxies.add(index, galaxy);
                }            
                else
                {
                    String message = "Cannot add " + galaxy.ToString() + " to universe: galaxy already in universe";
                    mLogger.LogWarning(message);
                }
            }
            else
            {
                String message = "Index of " + index + " is out of bounds for universe";
                mLogger.LogError(message);
                throw new IndexOutOfBoundsException(message);
            }
        }
        else
        {
            String message = "Cannot add null galaxy to universe";
            mLogger.LogError(message);
            throw new IllegalArgumentException(message);
        }
    }

    //Remove a galaxy from the array by reference.
    public void RemoveGalaxy(cGalaxy galaxy) throws IllegalArgumentException
    {
        if(galaxy != null)
        {
            if(mGalaxies.contains(galaxy))
            {
                mLogger.LogTrace("Removing " + galaxy.ToString() + " from universe");
                mGalaxies.remove(galaxy);
            }            
            else
            {
                String message = "Cannot remove " + galaxy.ToString() + " from universe: galaxy not in universe";
                mLogger.LogWarning(message);
            }
        }
        else
        {
            String message = "Cannot remove null galaxy from universe";
            mLogger.LogError(message);
            throw new IllegalArgumentException(message);
        }
    }

    //Remove a galaxy from the array by index.
    public void RemoveGalaxy(int index) throws IndexOutOfBoundsException
    {
        if(index >= 0 && index < mGalaxies.size())
        {
            mLogger.LogTrace("Removing galaxy at index " + index + " from universe");
            mGalaxies.remove(index);
        }
        else
        {
            String message = "Index " + index + " is out of bounds for universe";
            mLogger.LogError(message);
            throw new IndexOutOfBoundsException(message);
        }
    }

    //Get a reference to a galaxy at the specified index.
    public cGalaxy GetGalaxy(int index) throws IndexOutOfBoundsException
    {
        if(index >= 0 && index < mGalaxies.size())
        {
            mLogger.LogTrace("Getting galaxy at index " + index + " from universe");
            return mGalaxies.get(index);
        }
        else
        {
            String message = "Index " + index + " is out of bounds for universe";
            mLogger.LogError(message);
            throw new IndexOutOfBoundsException(message);
        }
    }

    //Get the number of galaxies in the universe.
    public int GetGalaxyCount()
    {
        mLogger.LogTrace("Getting galaxy count from universe: " + this.ToString(false));
        return mGalaxies.size();
    }

    //String representation of the universe. Gives count of galaxies.
    public String ToString(boolean recursive)
    {
        String strOut = "Universe: (" + this.mGalaxies.size() + ")\n";
        if(recursive)
        {
            for(cGalaxy g : mGalaxies)
            {
                strOut += "\n" + g.ToString(recursive);
            }
        }
        return strOut;
    }
    public String ToString()
    {
        return ToString(true);
    }

    public void RandomUniverse(long seed, int minGalaxies, int maxGalaxies, int minStars, int maxStars, int minPlanets, int maxPlanets, int minMoons, int maxMoons) throws IllegalArgumentException
    {
        //Validate parameters.
        //Ensure that minimums are within specs.
        if(minGalaxies < 1 || minStars < 1 || minPlanets < 1 || minMoons < 0)
        {
            String message = "Cannot create random universe: minimums must be at least 1 (except moons, which can be 0)";
            mLogger.LogError(message);
            throw new IllegalArgumentException(message);
        }
        //Ensure that maximums are within specs.
        if(minGalaxies >= maxGalaxies || minStars >= maxStars || minPlanets >= maxPlanets || minMoons >= maxMoons)
        {
            String message = "Cannot create random universe: minimums must be less than or equal to maximums";
            mLogger.LogError(message);
            throw new IllegalArgumentException(message);
        }

        mLogger.LogTrace("Creating random universe with parameters:");
        mLogger.LogTrace("Seed: " + seed);
        mLogger.LogTrace("Galaxies: " + minGalaxies + " to " + maxGalaxies);
        mLogger.LogTrace("Stars per galaxy: " + minStars + " to " + maxStars);
        mLogger.LogTrace("Planets per star: " + minPlanets + " to " + maxPlanets);
        mLogger.LogTrace("Moons per planet: " + minMoons + " to " + maxMoons);

        //Make sure to clear the existing universe first.
        ClearGalaxies();

        //Seed random.
        Random rand;
        if(seed == 0)
        {
            rand = new Random();
        }
        else
        {
            rand = new Random(seed);
        }

        mLogger.LogInfo("Generating universe");

        //Generate the the galaxies.
        for(int g = 0; g < rand.nextInt(minGalaxies, maxGalaxies + 1); g++)
        {
            mLogger.LogInfo("Generating galaxy");
            cGalaxy galaxy = new cGalaxy("Galaxy_" + (g + 1), cGalaxy.GetRandomGalaxyType(rand), mLogger);
            
            //Inside galaxy, generate stars.
            for(int s = 0; s < rand.nextInt(minStars, maxStars + 1); s++)
            {
                mLogger.LogInfo("Generating star");
                cStar star = new cStar("Star_" + (s + 1), cStar.GetRandomStarType(rand), mLogger);

                //Inside system, generate planets.
                for(int p = 0; p < rand.nextInt(minPlanets, maxPlanets + 1); p++)
                {
                    mLogger.LogInfo("Generating planet");
                    cPlanet planet = new cPlanet("Planet_" + (p + 1), cPlanet.GetRandomPlanetType(rand), mLogger);

                    //For planets, generate moons. This doesn't generate moons for moons.
                    for(int m = 0; m < rand.nextInt(minMoons, maxMoons + 1); m++)
                    {
                        mLogger.LogInfo("Generating moon");
                        cPlanet moon = new cPlanet("Moon_" + (m + 1), cPlanet.GetRandomPlanetType(rand), mLogger);
                        mLogger.LogInfo("Moon generated: " + moon.ToString());
                        planet.AddMoon(moon);
                    }
                    mLogger.LogInfo("Planet generated: " + planet.ToString());
                    star.AddPlanet(planet);
                }
                mLogger.LogInfo("Star generated: " + star.ToString());
                galaxy.AddStar(star);
            }
            mLogger.LogInfo("Galaxy generated: " + galaxy.ToString());
            AddGalaxy(galaxy);
        }

        mLogger.LogInfo("Universe generated");
    }

    //A default random universe generator that does not take a seed. Will be very likely be different each time.
    public void RandomUniverse(int minGalaxies, int maxGalaxies, int minStars, int maxStars, int minPlanets, int maxPlanets, int minMoons, int maxMoons) throws IllegalArgumentException
    {
        RandomUniverse(0, minGalaxies, maxGalaxies, minStars, maxStars, minPlanets, maxPlanets, minMoons, maxMoons);
    }

    //Create a default random test universe.
    public void TestUniverse()
    {
        //Create two to three test galaxies. Create nine to sixteen test systems for each with one to twelve planets each, with optional 0-6 moons each.
        RandomUniverse("default".hashCode(), 2, 3, 9, 16, 1, 12, 0, 6);
    }
}