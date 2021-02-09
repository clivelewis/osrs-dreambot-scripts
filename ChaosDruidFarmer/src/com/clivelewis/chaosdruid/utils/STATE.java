package com.clivelewis.chaosdruid.utils;

public enum STATE {
	START("Starting"),

	WORLD_HOP("Changing World"),
	SHORT_BREAK("On short break"),
	LONG_BREAK("On long break"),

	WALK_TO_BANK("Walking to Bank"),
	WALK_TO_DRUIDS("Walking to Druids"),
	WALK_TO_DUNGEON("Walking to Dungeon"),

	BANK("Banking"),
	FIGHT("Fighting"),
	LOOT("Looting"),

	ANTI_PK("Running from Death")
	;


	private String description;

	STATE(String s) {
		this.description = s;
	}

	public String getDescription() {
		return description;
	}
}
