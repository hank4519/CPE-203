package Part1;

import java.util.HashMap;
import java.util.Map;

public class Country {
    private String name;
    private Map <Integer, Emission> emissions;
    public Country(){
        name ="";
        emissions = new HashMap<>();
    }
    public Country(String name, Map<Integer, Emission> emissions ){
        this.name = name;
        this.emissions = emissions;
    }
    public String getName(){
        return name;
    }
    public Map <Integer, Emission> getEmissions(){
        return emissions;
    }

}
