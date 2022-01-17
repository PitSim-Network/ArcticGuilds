package dev.kyro.arcticguilds.enums;

public enum GuildRank {
	OWNER("Owner", "owner"),
	CO_OWNER("Co-Owner", "coowner"),
	MODERATOR("Moderator", "moderator"),
	MEMBER("Member", "member"),
	RECRUIT("Recruit", "recruit");

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
}
