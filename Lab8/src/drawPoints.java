import processing.core.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.List;
import java.util.stream.Collectors;

public class drawPoints extends PApplet {

	public void settings() {
    size(500, 500);
	}

	public void setup() {
    	background(180);
    	noLoop();
  	}

  	public void draw() {

   	double x, y;

   	String[] lines = loadStrings("drawMe.txt");
   	println("there are " + lines.length);
  		for (int i=0; i < lines.length; i++) {
			if (lines[i].length() > 0) {
				String[] words = lines[i].split(",");
				x = Double.parseDouble(words[0]);
				y = Double.parseDouble(words[1]);
				println("xy: " + x + " " + y);
				ellipse((int) x, (int) y, 1, 1);
			}
		}
  	}
	public static List<Point> getPoints() throws FileNotFoundException {
		List<Point> list = new LinkedList<>();
		File inputFile = new File("positions.txt");
		Scanner scan = new Scanner(inputFile);
		while (scan.hasNextLine()){
			String[] data = scan.nextLine().split(", ");
			Point pt = new Point(Double.parseDouble(data[0]), Double.parseDouble(data[1]), Double.parseDouble(data[2]));
			list.add(pt);
		}
		scan.close();
		return list.stream()
				.filter(pt -> pt.getZ() <= 2.0 )
				.map(pt -> pt.scaleDown(0.5))
				.map(pt -> pt.translate(new int [] {-150, -37}))
				.collect(Collectors.toList());
	}
  	public static void main(String args[]) {
		try {
			List<Point> collection = getPoints();
			PrintWriter out = new PrintWriter("drawMe.txt");
			for(Point pt: collection){
				out.println(pt.getX()+ ", " + pt.getY() + ", " + pt.getZ());
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        PApplet.main("drawPoints");
	}
}
