package Part1;

import Part1.Emission;
import Part1.Sector;

import java.util.Map;
public class Util {
    public static int getYearWithHighestEmissions(Country country) {
        Map <Integer, Emission> map = country.getEmissions();
        double highest_emission = 0;
        int year=0;
        for (Map.Entry <Integer, Emission> entry: country.getEmissions().entrySet()){
            Emission emi = entry.getValue();
            double total = emi.getCH4()+emi.getCO2()+emi.getN2O();
            if (highest_emission == 0 ) {
                highest_emission = total;
                year = entry.getKey();
            }
            else if (total > highest_emission){
                highest_emission = total;
                year = entry.getKey();
            }
        }
        return year;
    }
    public static int getYearWithHighestEmissions (Sector sector){
        Map <Integer, Double> map = sector.getEmissions();
        int year = 0 ;
        double highest_emission = 0;
        for (Map.Entry<Integer, Double> emi: map.entrySet()){
            double total = emi.getValue();
            if (highest_emission == 0) {
                highest_emission = total;
                year = emi.getKey();
            }
            else if (total > highest_emission){
                highest_emission = total;
                year = emi.getKey();
            }
        }
        return year;
    }

}
