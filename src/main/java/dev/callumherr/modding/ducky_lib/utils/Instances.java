package dev.callumherr.modding.ducky_lib.utils;

public class Instances
{

    public static Instances instance;
    public static Instances get(){return instance;}

    //This doesn't need to be registered in the main block entity register class as this is just a base for registering our multiblocks.
    public Instances()
    {
        instance = this;
    }

    public static Instances register()
    {
         return new Instances();
    }
}
