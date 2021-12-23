package me.nosta.cachecache.elements;

public enum RoleEnum {
	CHASSEUR("Chasseur", ""),
	CIVIL("Civil", ""),
	ANGE("Ange", ""),
	ASTRONAUTE("Astronaute", ""),
	CAPITAINE("Capitaine", ""),
	ESPION("Espion", ""),
	JUMEAU("Jumeau", ""),
	NINJA("Ninja", ""),
	PRETRE("Prêtre", ""),
	REBELLE("Rebelle", ""),
	SNIPER("Sniper", ""),
	VETERAN("Vétéran", "");
	
	private String name;
	private String description;
	
	private RoleEnum(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public String getName() {return this.name;}
	public String getDescription() {return this.description;}
}
