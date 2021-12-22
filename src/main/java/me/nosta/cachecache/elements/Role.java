package me.nosta.cachecache.elements;

public enum Role {
	CHASSEUR("Chasseur", "Vous êtes Chasseur"),
	CIVIL("Civil", "Vous êtes Civil"),
	INFILTRE("Infiltré", "Vous êtes Infiltré"),
	VETERAN("Vétéran", "Vous êtes Vétéran"),
	REBELLE("Rebelle", "Vous êtes Rebelle"),
	SNIPER("Sniper", "Vous êtes Sniper"),
	FRERE("Frères (2)", "Vous êtes Frère"),
	VOYANTE("Voyante", "Vous êtes Voyante"),
	MEDECIN("Médecin", "Vous êtes Médecin"),
	NINJA("Ninja", "Vous êtes Ninja"),
	CAPITAINE("Capitaine", "Vous êtes Capitaine"),
	ANGE("Ange", "Vous êtes Ange"),
	MAGICIEN("Magicien", "Vous êtes Magicien"),
	ASTRONAUTE("Astronaute", "Vous êtes Astronaute");
	
	private String name;
	private String description;
	
	private Role(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public String getName() {return this.name;}
	public String getDescription() {return this.description;}
}
