package br.com.nexus.main.core.launches.spigot.enums;

public enum EconomyEnum {

    Money("Money", "§a", "§2$", true, new String[]{"dinheiro"}), // money
    Cash("Cash", "§6", "❂",true, new String[]{"cashs"}), // cash
    Crystal("Crystals", "§5", "✫",true, new String[]{"crystal", "crystais"}), // ranks
    Magia("Magias", "§4", "❈",false, new String[]{"magia", "poder"}), // (Consegue para upar os encantamentos da mina) Gemas
    Coins("Coins", "§b", "✸",false, new String[]{"coin"}), // pagando
    Token("Tokens", "§3", "✪",true, new String[]{"token"}), // boss
    Alma("Almas", "§d", "", true, new String[]{"soul", "souls"});

    private final String economyName;
    private final String color;
    private final boolean isSent;
    private final String emblem;
    private final String[] alieases;

    EconomyEnum(String economyName, String color, String emblem, boolean isSent, String[] alieases) {
        this.color = color;
        this.economyName = economyName;
        this.isSent = isSent;
        this.emblem = emblem;
        this.alieases = alieases;
    }

    public boolean isSent() {
        return isSent;
    }

    public String getEconomyName() {
        return economyName;
    }

    public String getEconomyColor() {
        return color;
    }

    public String getEmblem() {
        return emblem;
    }

    public String[] getAlieases() {
        return alieases;
    }
}
