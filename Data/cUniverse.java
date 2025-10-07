package Data;

import java.util.ArrayList;
import java.util.Random;
import Logging.cLoggingBase;

/**
 * Represents a universe containing galaxies in the StarViewer application.
 * This class manages a collection of galaxies and provides methods for adding, removing,
 * and manipulating galaxies within the universe. It also includes functionality for
 * generating random universes with specified parameters.
 * 
 * @author Stephen Hyberger
 * @version 1.0
 */
public class cUniverse 
{
    /**
     * List of galaxies contained in this universe.
     */
    private ArrayList<cGalaxy> mGalaxies;
    
    /**
     * Logger instance for this universe.
     */
    private cLoggingBase mLogger;

    /**
     * Constructs a new universe with an empty list of galaxies.
     * Uses the standby logger as the default logger.
     */
    public cUniverse()
    {
        mGalaxies = new ArrayList<cGalaxy>();
        mLogger = cLoggingBase.GetStandbyLogger();
    }
    
    /**
     * Constructs a new universe with an empty list of galaxies and a specified logger.
     * 
     * @param logger the logger to use for this universe
     */
    public cUniverse(cLoggingBase logger)
    {
        mGalaxies = new ArrayList<cGalaxy>();
        mLogger = logger;
    }

    /**
     * Clears all galaxies from the universe.
     * Also clears all stars from each galaxy before removing them.
     */
    public void ClearGalaxies()
    {
        for(cGalaxy g : mGalaxies)
        {
            g.ClearStars();
        }
        mGalaxies.clear();
    }
    
    /**
     * Appends a galaxy to the end of the universe's galaxy list.
     * If the galaxy is already in the universe, a warning is logged but no exception is thrown.
     * 
     * @param galaxy the galaxy to add to the universe
     * @throws IllegalArgumentException if the galaxy is null
     */
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

    /**
     * Inserts a galaxy at a specific index in the universe's galaxy list.
     * If the galaxy is already in the universe, a warning is logged but no exception is thrown.
     * 
     * @param galaxy the galaxy to add to the universe
     * @param index the index at which to insert the galaxy
     * @throws IndexOutOfBoundsException if the index is out of bounds
     * @throws IllegalArgumentException if the galaxy is null
     */
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

    /**
     * Removes a galaxy from the universe by reference.
     * If the galaxy is not in the universe, a warning is logged but no exception is thrown.
     * 
     * @param galaxy the galaxy to remove from the universe
     * @throws IllegalArgumentException if the galaxy is null
     */
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

    /**
     * Removes a galaxy from the universe by index.
     * 
     * @param index the index of the galaxy to remove
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
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

    /**
     * Gets a reference to a galaxy at the specified index.
     * 
     * @param index the index of the galaxy to retrieve
     * @return the galaxy at the specified index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
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

    /**
     * Gets the number of galaxies in the universe.
     * 
     * @return the count of galaxies in this universe
     */
    public int GetGalaxyCount()
    {
        mLogger.LogTrace("Getting galaxy count from universe: " + this.ToString(false));
        return mGalaxies.size();
    }

    /**
     * Returns a string representation of the universe with optional recursive details.
     * 
     * @param recursive if true, includes detailed information about all contained galaxies
     * @return a string representation showing the galaxy count and optionally detailed galaxy information
     */
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
    
    /**
     * Returns a string representation of the universe with full recursive details.
     * 
     * @return a detailed string representation of the universe and all its contents
     */
    public String ToString()
    {
        return ToString(true);
    }

    /**
     * Generates a random universe with the specified parameters and seed.
     * Clears any existing galaxies before generating new content.
     * 
     * @param seed the seed for random generation (0 for random seed)
     * @param minGalaxies minimum number of galaxies to generate (must be >= 1)
     * @param maxGalaxies maximum number of galaxies to generate (must be > minGalaxies)
     * @param minStars minimum number of stars per galaxy (must be >= 1)
     * @param maxStars maximum number of stars per galaxy (must be > minStars)
     * @param minPlanets minimum number of planets per star (must be >= 1)
     * @param maxPlanets maximum number of planets per star (must be > minPlanets)
     * @param minMoons minimum number of moons per planet (must be >= 0)
     * @param maxMoons maximum number of moons per planet (must be > minMoons)
     * @throws IllegalArgumentException if any parameters are invalid
     */
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

    /**
     * Generates a random universe with the specified parameters using a random seed.
     * This is a convenience method that calls the seeded version with seed = 0.
     * 
     * @param minGalaxies minimum number of galaxies to generate (must be >= 1)
     * @param maxGalaxies maximum number of galaxies to generate (must be > minGalaxies)
     * @param minStars minimum number of stars per galaxy (must be >= 1)
     * @param maxStars maximum number of stars per galaxy (must be > minStars)
     * @param minPlanets minimum number of planets per star (must be >= 1)
     * @param maxPlanets maximum number of planets per star (must be > minPlanets)
     * @param minMoons minimum number of moons per planet (must be >= 0)
     * @param maxMoons maximum number of moons per planet (must be > minMoons)
     * @throws IllegalArgumentException if any parameters are invalid
     */
    public void RandomUniverse(int minGalaxies, int maxGalaxies, int minStars, int maxStars, int minPlanets, int maxPlanets, int minMoons, int maxMoons) throws IllegalArgumentException
    {
        RandomUniverse(0, minGalaxies, maxGalaxies, minStars, maxStars, minPlanets, maxPlanets, minMoons, maxMoons);
    }

    /**
     * Creates a default test universe with predefined parameters.
     * Generates 2-3 galaxies, each with 9-16 star systems, 1-12 planets per system,
     * and 0-6 moons per planet. Uses a deterministic seed based on the string "default".
     */
    public void TestUniverse()
    {
        //Create two to three test galaxies. Create nine to sixteen test systems for each with one to twelve planets each, with optional 0-6 moons each.
        RandomUniverse("default".hashCode(), 2, 3, 9, 16, 1, 12, 0, 6);
    }
}