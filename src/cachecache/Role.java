package cachecache;

public enum Role {
	CHASSEUR("Chasseur"),
	INFILTRE("Infiltr�"),
	VETERAN("V�t�ran"),
	REBELLE("Rebelle"),
	SNIPER("Sniper"),
	FRERE("Fr�re"),
	VOYANTE("Voyante"),
	MEDECIN("M�decin"),
	NINJA("Ninja"),
	CAPITAINE("Capitaine"),
	ANGE("Ange"),
	MAGICIEN("Magicien"),
	ASTRONAUTE("Astronaute");
	
	private String description;
	
	private Role(String description) {
		this.description = description;
	}
	
	public String getDescription() {return this.description;}
}
