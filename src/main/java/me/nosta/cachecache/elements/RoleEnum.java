package me.nosta.cachecache.elements;

public enum RoleEnum {
	CHASSEUR("Chasseur", ""),
	CIVIL("Civil", ""),
	ESPION("Espion", ""),
	VETERAN("Vétéran", ""),
	REBELLE("Rebelle", ""),
	SNIPER("Sniper", ""),
	JUMEAU("Jumeau", ""),
	VOYANTE("Voyante", ""),
	PRETRE("Prêtre", ""),
	NINJA("Ninja", ""),
	CAPITAINE("Capitaine", ""),
	ANGE("Ange", ""),
	MAGE("Mage", ""),
	ASTRONAUTE("Astronaute", "");
	
	private String name;
	private String description;
	
	private RoleEnum(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public String getName() {return this.name;}
	public String getDescription() {return this.description;}
}
