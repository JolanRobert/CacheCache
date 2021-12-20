package cachecache;

public enum Role {
	CHASSEUR("Chasseur", ""),
	CIVIL("Civil", ""),
	INFILTRE("Infiltré", ""),
	VETERAN("Vétéran", ""),
	REBELLE("Rebelle", ""),
	SNIPER("Sniper", ""),
	FRERE("Frères (2)", ""),
	VOYANTE("Voyante", ""),
	MEDECIN("Médecin", ""),
	NINJA("Ninja", ""),
	CAPITAINE("Capitaine", ""),
	ANGE("Ange", ""),
	MAGICIEN("Magicien", ""),
	ASTRONAUTE("Astronaute", "");	
	
	private String name;
	private String description;
	
	private Role(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public String getName() {return this.name;}
	public String getDescription() {return this.description;}
}
