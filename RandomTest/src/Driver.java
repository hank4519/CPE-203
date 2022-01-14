import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Driver {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1,2,3,4,5,6);
        int res =0;
        res = numbers.stream().mapToInt(Integer::intValue).map(x->x*2).sum();
        System.out.println(res);
        List <Integer> numbers2 = Arrays.asList(1,2,3,5,4,6,7,8,9,10);
        int res2= numbers2.stream().filter(x->x%2 ==0).filter(x-> x>3).collect(Collectors.toList()).get(0);
        System.out.println(res2);

    }
}
