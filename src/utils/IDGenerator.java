package utils;

public class IDGenerator {
    public static int id = 0;

    public static int generateID(){
        id++;
        return id;
    }
}
