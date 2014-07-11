package gamePieces;

public class Coordinate {

	private int X;
	private int Y;
	
	public Coordinate(int X, int Y){
		this.X = X;
		this.Y = Y;
	}
	
	public int X(){
		return X;
	}
	
	public int Y(){
		return Y;
	}
	
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof Coordinate))
			return false;
		Coordinate temp = (Coordinate) obj;
		return temp.X == this.X && temp.Y == this.Y;
	}
	
	@Override
	public int hashCode(){
		return X ^ Y;
	}
	
	@Override
	public String toString(){
		return X + "," + Y;
	}
}