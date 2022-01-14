package Part3;


import java.util.HashMap;
import java.util.Map;
import java.util.List;

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
    public static Country countryWithHighestCH4InYear(List<Country> countries, int year){
        Country country = new Country();
        double highest = 0;
        for (int i = 0; i < countries.size(); i++ ){
            Country ctr = countries.get(i);
            Map <Integer, Emission> emissions = ctr.getEmissions();
            double ch4 = emissions.get(year).getCH4();
            if (highest == 0) {
               highest = ch4;
               country = ctr;
            }
            else{
                if ( ch4 > highest){
                    highest = ch4;
                    country = ctr;
                }
            }
        }
        System.out.println(highest);
        return country;
    }
    public static Country countryWithHighestChangeInEmissions(List<Country> countries, int startYear, int endYear){
        Country country = new Country();
        double highest = 0;
        for (int i = 0 ; i < countries.size(); i++ ) {
            Country ctr = countries.get(i);
            Map <Integer, Emission> emis = ctr.getEmissions();
            double start = emis.get(startYear).getTotalEmi();
            double end = emis.get(endYear).getTotalEmi();
            if (highest == 0 ){
                highest = end - start;
                country = ctr;
            }
            else{
                if (highest < end - start) {
                    highest = end - start;
                    country = ctr;
                }
            }
        }
        System.out.println(highest);
        return country;
    }
}
