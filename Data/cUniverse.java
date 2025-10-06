package Data;

import java.util.ArrayList;
import java.util.Random;

//Represents a universe containing galaxies
public class cUniverse 
{
    private ArrayList<cGalaxy> mGalaxies;

    //Basic constructor.
    public cUniverse()
    {
        mGalaxies = new ArrayList<cGalaxy>();
    }

    //Methods to manipulate the array of galaxies.
    //Append a galaxy to the end of the array.
    public void AddGalaxy(cGalaxy galaxy)
    {
        if(galaxy != null)
        {
            if(!mGalaxies.contains(galaxy))
            {
                mGalaxies.add(galaxy);
            }            
            else
            {
                IO.println("Cannot add " + galaxy.ToString() + " to universe: galaxy already in universe");
            }
        }
        else
        {
            IO.println("Cannot add null galaxy to universe.");
        }
    }

    //Insert a galaxy at a specific index in the array.
    public void AddGalaxy(cGalaxy galaxy, int index)
    {
        if(galaxy != null)
        {
            if(index >= 0 && index <= mGalaxies.size())
            {

                if(!mGalaxies.contains(galaxy))
                {
                    mGalaxies.add(index, galaxy);
                }            
                else
                {
                    IO.println("Cannot add " + galaxy.ToString() + " to universe: galaxy already in universe");
                }
            }
            else
            {
                IO.println("Index of " + index + " is out of bounds for universe");
            }
        }
        else
        {
            IO.println("Cannot add null galaxy to universe");
        }
    }

    //Remove a galaxy from the array by reference.
    public void RemoveGalaxy(cGalaxy galaxy)
    {
        if(galaxy != null)
        {
            if(mGalaxies.contains(galaxy))
            {
                mGalaxies.remove(galaxy);
            }            
            else
            {
                IO.println("Cannot remove " + galaxy.ToString() + " from universe: galaxy not in universe");
            }
        }
        else
        {
            IO.println("Cannot remove null galaxy from universe");
        }
    }

    //Remove a galaxy from the array by index.
    public void RemoveGalaxy(int index)
    {
        if(index >= 0 && index < mGalaxies.size())
        {
            mGalaxies.remove(index);
        }
        else
        {
            IO.println("Index " + index + " is out of bounds for universe");
        }
    }

    //Get a reference to a galaxy at the specified index.
    public cGalaxy GetGalaxy(int index)
    {
        if(index >= 0 && index < mGalaxies.size())
        {
            return mGalaxies.get(index);
        }
        else
        {
            IO.println("Index " + index + " is out of bounds for universe");
            return null;
        }
    }

    //Get the number of galaxies in the universe.
    public int GetGalaxyCount()
    {
        return mGalaxies.size();
    }

    //String representation of the universe. Gives count of galaxies.
    public String ToString()
    {
        String strOut = "Summary:\n";
        for(cGalaxy g : mGalaxies)
        {
            strOut += "\n" + g.ToString();
        }
        return strOut;
    }

    public static cUniverse RandomUniverse(long seed, int minGalaxies, int maxGalaxies, int minStars, int maxStars, int minPlanets, int maxPlanets, int minMoons, int maxMoons)
    {

        if(minGalaxies < 1 || minStars < 1 || minPlanets < 1 || minMoons < 0)
        {
            IO.println("Cannot create random universe: minimums must be at least 1 (except moons, which can be 0)");
            return null;
        }
        if(minGalaxies >= maxGalaxies || minStars >= maxStars || minPlanets >= maxPlanets || minMoons >= maxMoons)
        {
            IO.println("Cannot create random universe: minimums must be less than or equal to maximums");
            return null;
        }

        Random rand;
        if(seed == 0)
        {
            rand = new Random();
        }
        else
        {
            rand = new Random(seed);
        }

        //IO.println("Generating universe");
        cUniverse universe = new cUniverse();
        for(int g = 0; g < rand.nextInt(minGalaxies, maxGalaxies + 1); g++)
        {
            //IO.println("Generating galaxy");
            cGalaxy galaxy = new cGalaxy("Galaxy_" + (g + 1), cGalaxy.GetRandomGalaxyType(rand));
            for(int s = 0; s < rand.nextInt(minStars, maxStars + 1); s++)
            {
                //IO.println("Generating star");
                cStar star = new cStar("Star_" + (s + 1), cStar.GetRandomStarType(rand));
                for(int p = 0; p < rand.nextInt(minPlanets, maxPlanets + 1); p++)
                {
                    //IO.println("Generating planet");
                    cPlanet planet = new cPlanet("Planet_" + (p + 1), cPlanet.GetRandomPlanetType(rand));
                    for(int m = 0; m < rand.nextInt(minMoons, maxMoons + 1); m++)
                    {
                        //IO.println("Generating moon");
                        cPlanet moon = new cPlanet("Moon_" + (m + 1), cPlanet.GetRandomPlanetType(rand));
                        //IO.println("Moon generated: " + moon.ToString());
                        planet.AddMoon(moon);
                    }
                    //IO.println("Planet generated: " + planet.ToString());
                    star.AddPlanet(planet);
                }
                //IO.println("Star generated: " + star.ToString());
                galaxy.AddStar(star);
            }
            //IO.println("Galaxy generated: " + galaxy.ToString());
            universe.AddGalaxy(galaxy);
        }

        //IO.println("Universe generated");
        return universe;
    }
    public static cUniverse RandomUniverse(int minGalaxies, int maxGalaxies, int minStars, int maxStars, int minPlanets, int maxPlanets, int minMoons, int maxMoons)
    {
        return RandomUniverse(0, minGalaxies, maxGalaxies, minStars, maxStars, minPlanets, maxPlanets, minMoons, maxMoons);
    }

    //Create a default random test universe.
    public static cUniverse TestUniverse()
    {
        //Create two to three test galaxies. Create nine to sixteen test systems for each with one to twelve planets each, with optional 0-6 moons each.
        cUniverse universe = RandomUniverse("default".hashCode(), 2, 3, 9, 16, 1, 12, 0, 6);
        return universe;
    }
}