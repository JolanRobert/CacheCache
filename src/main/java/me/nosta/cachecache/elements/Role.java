package me.nosta.cachecache.elements;

public enum Role {
	CHASSEUR("Chasseur", ""),
	CIVIL("Civil", ""),
	INFILTRE("Infiltr�", ""),
	VETERAN("V�t�ran", ""),
	REBELLE("Rebelle", ""),
	SNIPER("Sniper", ""),
	FRERE("Fr�res (2)", ""),
	VOYANTE("Voyante", ""),
	MEDECIN("M�decin", ""),
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
