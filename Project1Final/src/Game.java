
public class Game {
	public String name;
	public int sales;
	public float[] color=new float[3];
	public Game() {

	}
	/**
	 * constructor for Game
	 * @param name name of game
	 * @param sales sales of game
	 */
	public Game(String name,int sales) {
		this.name=name;
		this.sales=sales;
	}
}
