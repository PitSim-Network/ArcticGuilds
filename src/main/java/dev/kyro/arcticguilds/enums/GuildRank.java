package dev.kyro.arcticguilds.enums;

public enum GuildRank {
	OWNER("Owner", "owner"),
	CO_OWNER("Co-Owner", "coowner"),
	OFFICER("Officer", "officer"),
	MEMBER("Member", "member"),
	RECRUIT("Recruit", "recruit");

	public static GuildRank INITIAL_RANK = RECRUIT;

	public String displayName;
	public String refName;

	GuildRank(String displayName, String refName) {
		this.displayName = displayName;
		this.refName = refName;
	}

	public int getPriority() {
		for(int i = 0; i < values().length; i++) {
			GuildRank rank = values()[i];
			if(rank != this) continue;
			return i;
		}
		return -1;
	}

	public static GuildRank getRank(String refName) {
		for(GuildRank value : values()) if(value.refName.equalsIgnoreCase(refName)) return value;
		return null;
	}

	public boolean isAtLeast(GuildRank rank) {
		return getPriority() >= rank.getPriority();
	}
}
