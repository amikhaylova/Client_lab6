package Enums;

import java.util.HashMap;
import java.util.Map;

public enum ClothesTypes {
    Hat("шляпа"), Leggings("лосины"), Tshirt("футболка"), Jeans("джинсы"), Socks("носки"), Sweatshirt("свитшот"), Skirt("юбка"), None("нет одежды");

    private String name;

    ClothesTypes(String name) {
        this.name = name;
    }

    public String getLocalizedName() {
        return name;
    }

    private static final Map<String, ClothesTypes> lookup = new HashMap<String, ClothesTypes>();

    static {
        for (ClothesTypes type : ClothesTypes.values()) {
            lookup.put(type.getLocalizedName(), type);
        }
    }

    public static ClothesTypes get(String name) {
        if (lookup.get(name) == null) {
            System.out.println("Нет такого значения в ClothesType. Установлено значение по умолчанию.");
            return (ClothesTypes.None);
        } else {
            return lookup.get(name);
        }
    }

}
