package me.nosta.cachecache.enums;

public enum RoleEnum {
	CHASSEUR("Chasseur", "Vous disposez de l'effet Speed I ainsi que d'un Poignard pouvant tuer instantanément les Survivants."),
	CIVIL("Civil", "Vous ne disposez d'aucun pouvoir."),
	ANGE("Ange", "Un adorateur est choisi aléatoirement parmi tous les autres Survivants, tant qu'il est en vie vous êtes insensible aux coups des Chasseurs. "+
			"Néanmoins si un Chasseur vous touche, votre adorateur recevra alors l'effet Glowing permanent. Lorsque votre adorateur meurt vous redevez vulnérable et recevez "+
			"à votre tour l'effet Glowing permanent."),
	ASTRONAUTE("Astronaute", "Vous disposez des effets Jump Boost III, Slow Falling I et Dolphin's Grace I."),
	CAPITAINE("Capitaine", "Vous pouvez voir tous les Chasseurs dans un rayon de 30 blocs autour de vous. Vous pouvez également une fois dans la partie la "+
			"partie téléporter tous les Survivants sur votre position. Enfin, vous connaîtrez en avance vers 7m30s le point d'extraction des Survivants."),
	ESPION("Espion", "Vous pouvez communiquer des informations aux Chasseurs en précédant vos messages par \"$\". Vous disposez des mêmes pouvoirs que votre rôle "+
			"de couverture."),
	JUMEAU("Jumeau", "Vous avez l'effet Speed II lorsque vous vous trouvez à moins de 20 blocs de votre Jumeau. Si l'un de vous meurt, l'autre ne pourra pas le "+
			"supporter et se suicidera instantanément."),
	NINJA("Ninja", "Vous pouvez utiliser votre Camouflage 3 fois dans la partie (1m30s de cooldown), lorsque vous le faites vous gagnez temporairement les effets "+
			"Invisibilité et Speed II."),
	PRETRE("Prêtre", "Chaque fois qu'un Survivant meurt, un message vous en informe. Une fois dans la partie vous pouvez ressusciter un Survivant en tant que Civil "+
			"dans un temps limité (10s). Vous ne pouvez pas vous ressusciter vous-même."),
	REBELLE("Rebelle", "Vous disposez d'une Dague permettant de renvoyer les Chasseurs à leur spawn lorsque vous les touchez (3 fois max)."),
	SNIPER("Sniper", "Vous disposez d'un Fusil de précision Punch X, toutes les 50s vous recevez une flèche."),
	VETERAN("Vétéran", "Vous disposez de deux boucliers vous protégeant par deux fois contre les attaques des Chasseurs. Lorsqu'un Chasseur vous frappe, ce dernier "+
			"sera étourdi brièvement et vous perdrez un bouclier.");
	
	private String name;
	private String description;
	
	private RoleEnum(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public String getName() {return this.name;}
	public String getDescription() {return this.description;}
}
