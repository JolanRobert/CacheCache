package cachecache;

public enum Role {
	CHASSEUR("Chasseur"),
	INFILTRE("Infiltré"),
	VETERAN("Vétéran"),
	REBELLE("Rebelle"),
	SNIPER("Sniper"),
	FRERE("Frère"),
	VOYANTE("Voyante"),
	MEDECIN("Médecin"),
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
