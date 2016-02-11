package com.digitale.utils;

import com.digitale.connex.Inventory;

public class InventoryUtils {

	private static String decodeBind(int bind) {
		switch (bind) {
		case 0:
			return "No binding";
		case 1:
			return "Bind on use";
		case 2:
			return "Character Bound";

		}
		return null;
	}
	public static String makeItemInfo(final Inventory item) {
		return "\nCategory: "
				+ item.getCategory() + " " + "  Type:- "
				+ item.getSubcat() + "\n\n"
				+ item.getDescription() + "\n\n" + "Quality:"
				+ decodeQuality(item.getQuality()) + "   Lvl:"
				+ item.getLevel() + "  Mass:" + item.getMass()
				+ "kg\n" + "Value:" + item.getValue() + "$D"
				+ "   Contraband:" + item.getContraband()
				+ "\n" + "Effect:" + item.getEffect()
				+ "   Bind: " + decodeBind(item.getBind());
	}
	private static String decodeQuality(int quality) {
		switch (quality) {
		case 0:
			return "Commodity";
		case 1:
			return "Mundane";
		case 2:
			return "Commmon";
		case 3:
			return "Plain";
		case 4:
			return "Uncommon";
		case 5:
			return "Rare";
		case 6:
			return "Grand";
		case 7:
			return "Epic";
		case 8:
			return "Legendary";
		case 9:
			return "Mythical";

		}
		return null;
	}
}
