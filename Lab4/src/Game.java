import java.util.HashSet;
import java.util.Random;
public class Game {
    private HashSet<Integer> winNum;
    private Random rand=  new Random();

    public HashSet<Integer> getWinNum(){
        return winNum;
    }
    public void winningLotNumber(){
        winNum = new HashSet<>();
        while(winNum.size()<5){
            winNum.add(rand.nextInt(42)+1);
        }
    }
    public int match (HashSet<Integer> set){
        int num_of_matches = 0;
        for(Integer lotto_num: set){
            if (winNum.contains(lotto_num))
                num_of_matches++;
        }
        return num_of_matches;
    }
    public void monetary(Player player, int match){
        float gain = -1f;
        if (match == 2)
            gain+= 1f;
        else if (match == 3)
            gain+=10.86f;
        else if (match == 4)
            gain+= 197.53f;
        else if (match == 5)
            gain+= 212534.83f;
        player.setMoney(player.getMoney()+gain);
    }

}
