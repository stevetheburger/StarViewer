package Data;

public class cObjectBase 
{
    private String mName;

    public cObjectBase(String name)
    {
        mName = name;
    }

    public void SetName(String name)
    {
        mName = name;
    }
    public String GetName()
    {
        return mName;
    }

    public String ToString()
    {
        return mName;
    }
}