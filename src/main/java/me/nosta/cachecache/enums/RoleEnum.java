package me.nosta.cachecache.enums;

public enum RoleEnum {
	CHASSEUR("Chasseur", "Vous disposez de l'effet Speed I ainsi que d'un Poignard pouvant tuer instantanément les Survivants."),
	CIVIL("Civil", ""),
	ANGE("Ange", "Un adorateur est choisi aléatoirement parmi tous les autres Survivants, tant qu'il est en vie vous êtes insensible aux coups des Chasseurs. "+
			"Néanmoins si un Chasseur vous touche, votre adorateur recevra alors l'effet Glowing permanent. Lorsque votre adorateur meurt vous redevez vulnérable et recevez "+
			"l'effet Slowness II permanent."),
	ASTRONAUTE("Astronaute", "Vous disposez des effets Jump Boost III, Slow Falling I et Dolphin's Grace I."),
	CAPITAINE("Capitaine", "Vous pouvez "),
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
