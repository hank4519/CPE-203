package Part3;

import java.util.HashMap;
import java.util.Map;
import java.util.List; 

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
    public static Sector sectorWithBiggestChangeInEmissions(List<Sector> sectors, int startYear, int endYear){
        Sector sector = new Sector();
        double highest_avg = 0; 
        for ( int i = 0 ; i < sectors.size() ;i++ ){
            Sector sec = sectors.get(i); 
            Map <Integer, Double> emissions = sec.getEmissions();
            double start = emissions.get(startYear);
            double end = emissions.get(endYear);
            if (highest_avg == 0 ) {
                highest_avg = (end - start) ;
                sector = sec; 
            }
            else{
                double temp = (end - start) ;
                if (highest_avg < temp ){
                    highest_avg = temp; 
                    sector = sec; 
                }
            }
        }
        System.out.println(highest_avg);
        return sector;
    }
}
