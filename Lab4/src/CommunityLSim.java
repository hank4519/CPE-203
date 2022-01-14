import java.util.*;

public final class CommunityLSim {

  ArrayList<Player> players; 
  int numPeeps;
  Random random = new Random();
  ArrayList<Integer> whoPlay;
  //you will need to add more instance variables

  public CommunityLSim( int numP) {
		numPeeps = numP;
		//create the players
  		players = new ArrayList<Player>();
		//generate a community of 30
		for (int i = 0; i < numPeeps; i++) {
			if (i < numPeeps/2.0)
				players.add(new Player(PlayerKind.POORLY_PAID, (float)(99+Math.random()))); 
			else
				players.add(new Player(PlayerKind.WELL_PAID, (float)(100.1+Math.random()))); 
		}
	
	}

	public int getSize() {
		return numPeeps;
	}

	public Player getPlayer(int i) {
		return players.get(i);
	}

	public void addPocketChange() {
		for (Player player: players){
			if(player.getKind() == PlayerKind.WELL_PAID){
				player.setMoney(player.getMoney()+0.1f);
			}
			else{
				player.setMoney(player.getMoney() + 0.03f);
			}
		}
	}
	public ArrayList<Integer> getWhoPlay(){
	  Collections.sort(whoPlay);
	  return whoPlay;
	}
	public void reDoWhoPlays() {
		whoPlay = new ArrayList<>();
		randomUniqIndx((int)Math.ceil(numPeeps/2*0.6), 0, numPeeps/2);
		randomUniqIndx((int)Math.ceil(numPeeps/2*0.4), numPeeps/2, numPeeps);
	}

	/* generate some random indices for who might play the lottery 
		in a given range of indices */ 
 	public void randomUniqIndx(int numI, int startRange, int endRange) {
		while(numI >0){
			int index = random.nextInt(endRange-startRange)+startRange;
			if (!whoPlay.contains(index)){
				whoPlay.add(index);
				numI--;
			}
		}
	}
    private void redistribute(){
		 int rand = random.nextInt(10);
		 if (rand <= 2){
			 int poorly_redis = random.nextInt(numPeeps/2);
			 players.get(poorly_redis).setMoney(players.get(poorly_redis).getMoney() + 1);
		 }
		 else{
			 int well_paid_redis = random.nextInt(numPeeps - numPeeps/2) + numPeeps/2;
			 players.get(well_paid_redis).setMoney(players.get(well_paid_redis).getMoney() + 1);
		 }
	}
	public void simulateYears(int numYears) {
  		/*now simulate lottery play for some years */
  		for (int year=0; year < numYears; year++) {
			reDoWhoPlays();
			addPocketChange();
			Game game = new Game();
			game.winningLotNumber();
			for (Integer index: whoPlay){
				players.get(index).playRandom();
				int match = game.match(players.get(index).getNumLottos());
				game.monetary(players.get(index), match);
				if (match == 0 || match == 1){
					redistribute();
				}
			}
    		// add code so that each member of the community who plays, plays 
			//right now just everyone updates their list of funds each year
			for (Player p : players) {
				p.updateMoneyEachYear();
			}
			System.out.println("After year " + year +": ");
			System.out.println("The person with the most money has: " + richest_person());
			System.out.println("The person with the least money has: " + poorest_person());
    	} //years
  }
  private float richest_person(){
		 float highest = 0f;
		 for (Player player: players){
			 if (player.getMoney() > highest)
				 highest = player.getMoney();
		 }
		 return highest;
  }
  private float poorest_person(){
		 float lowest = players.get(0).getMoney();
		 for (Player player: players){
			 if (player.getMoney() < lowest)
				 lowest = player.getMoney();
		 }
		 return lowest;
  }


}


/*
	private HashSet<Integer> randomUniqIndx(){
	  HashSet<Integer> index= new HashSet<>();
		//60% for poorly, 40% for well_paid
		while (index.size() < Math.ceil(numPeeps/2.0*0.6)){
			index.add(random.nextInt(numPeeps/2));
		}
		while (index.size() < Math.ceil(numPeeps/2.0)){
			index.add(random.nextInt(numPeeps/2)+numPeeps/2);
		}
		return index;
	}*/