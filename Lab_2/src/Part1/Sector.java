package Part1;

import java.util.Map;
import java.util.HashMap;
public class Sector {
    private String name;
    private Map <Integer, Double> emissions;
    public Sector(){
        name = "";
        emissions = new HashMap<>();
    }
    public Sector (String name, Map<Integer, Double> emissions){
        this.name = name;
        this.emissions = emissions;
    }
    public String getName (){
        return name;
    }
    public Map<Integer, Double> getEmissions(){
        return emissions;
    }
}
