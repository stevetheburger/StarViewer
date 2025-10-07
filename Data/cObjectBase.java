package Data;

/**
 * Base class for all named objects in the system.
 * Provides common functionality for objects that have a name and can be converted to string representation.
 * This class serves as the foundation for all astronomical objects in the StarViewer application.
 * 
 * @author Stephen Hyberger
 * @version 1.0
 */
public class cObjectBase 
{
    /**
     * The name of this object.
     */
    protected String mName;

    /**
     * Constructs a new named object with the specified name.
     * 
     * @param name the name to assign to this object
     */
    public cObjectBase(String name)
    {
        mName = name;
    }

    /**
     * Sets the name of this object.
     * 
     * @param name the new name for this object
     */
    public void SetName(String name)
    {
        mName = name;
    }
    
    /**
     * Gets the name of this object.
     * 
     * @return the name of this object
     */
    public String GetName()
    {
        return mName;
    }

    /**
     * Returns a string representation of this object.
     * By default, this returns the object's name.
     * 
     * @return the string representation of this object
     */
    public String ToString()
    {
        return mName;
    }
}