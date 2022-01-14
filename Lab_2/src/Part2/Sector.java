package Part2;

import java.util.HashMap;
import java.util.Map;

public class Sector {
    private String name;
    private Map <Integer, Double> emissions;
    public Sector(){
        name = "";
        emissions = new HashMap<>();
    }
    public Sector(String name, Map<Integer, Double> emissions){
        this.name = name;
        this.emissions = emissions;
    }
    public String getName (){
        return name;
    }
    public Map<Integer, Double> getEmissions(){
        return emissions;
    }
    public int getYearWithHighestEmissions (){
        int year = 0 ;
        double highest = 0;
        for (Map.Entry<Integer, Double> emi: emissions.entrySet()){
            double total = emi.getValue();
            if (highest == 0) {
                highest = total;
                year = emi.getKey();
            }
            else{
                if ( total > highest ){
                    highest = total;
                    year = emi.getKey();
                }
            }
        }
        return year;
    }

}
