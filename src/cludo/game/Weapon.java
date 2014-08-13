package cludo.game;

public class Weapon {
	public enum WeaponType {
		CANDLESTICK, DAGGER, LEAD_PIPE, REVOLVER, ROPE, SPANNER
	}
	
	private WeaponType type;
	
	
	public Weapon(WeaponType type){
		this.type = type;
	}
	
	
}
