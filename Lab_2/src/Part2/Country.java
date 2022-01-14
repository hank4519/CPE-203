package Part2;

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
    public int getYearWithHighestEmissions(){
        double highest = 0;
        int year = 0;
        for (Map.Entry<Integer, Emission> entry: emissions.entrySet()){
            Emission emi = entry.getValue();
            double total = emi.getCH4()+emi.getCO2()+emi.getN2O();
            if (highest == 0 ){
                highest = total;
                year = entry.getKey();
            }
            else {
                if(total > highest) {
                    highest = total;
                    year = entry.getKey();
                }
            }
        }
        return year;
    }
}
