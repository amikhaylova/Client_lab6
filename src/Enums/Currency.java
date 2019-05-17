package Enums;

import java.util.HashMap;
import java.util.Map;

public enum Currency {
    Santiky("сантик"), Rubles("рубль"), Dollars("доллар");

    private String name;

    Currency(String name) {
        this.name = name;
    }

    public String getLocalizedName() {
        return name;
    }

    private static final Map<String, Currency> lookup = new HashMap<String, Currency>();

    static {
        for (Currency currency : Currency.values()) {
            lookup.put(currency.getLocalizedName(), currency);
        }
    }


    public static Currency get(String name) {
        if (lookup.get(name) == null) {
            System.out.println("Нет такого значения в Currency. Установлено значение по умолчанию.");
            return(Currency.Santiky);
        } else{
            return lookup.get(name);
        }
    }
}
