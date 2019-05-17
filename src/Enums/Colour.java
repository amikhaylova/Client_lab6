package Enums;

import java.util.HashMap;
import java.util.Map;

public enum Colour {
    Blue("голубой"), Green("зеленый"), Yellow("желтый"), Red("красный"), Black("черный"), White("белый"), Leopard("леопардовый"), None ("нет цвета");

    private String name;

    Colour(String name) {
        this.name = name;
    }

    public String getLocalizedName() {
        return name;
    }

    private static final Map<String, Colour> lookup = new HashMap<String, Colour>();

    static {
        for (Colour colour : Colour.values()) {
            lookup.put(colour.getLocalizedName(), colour);
        }
    }


    public static Colour get(String name) throws IllegalArgumentException {
        if (lookup.get(name) == null) {
           System.out.println("Нет такого значения в Colour. Установлено значение по умолчанию.");
           return (Colour.None);
        } else{
            return lookup.get(name);
        }
    }
}
