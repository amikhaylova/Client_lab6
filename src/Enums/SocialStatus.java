package Enums;

import java.util.HashMap;
import java.util.Map;

public enum SocialStatus {
    Deadbeat("тунеядец"), Policeman("полицейский"), Prisoner("зэк"), Artist("художник"), Doctor("доктор"), Vtshnik("втшник"), None("неопределившийся"), Musician("музыкант");

    private String name;

    SocialStatus(String name) {
        this.name = name;
    }

    public String getLocalizedName() {
        return name;
    }

    private static final Map<String, SocialStatus> lookup = new HashMap<String, SocialStatus>();

    static {
        for (SocialStatus status : SocialStatus.values()) {
            lookup.put(status.getLocalizedName(), status);
        }
    }


    public static SocialStatus get(String name) {
        if (lookup.get(name) == null) {
            System.out.println("Нет такого значения в Enums.SocialStatus. Установлено значение по умолчанию.");
            return (SocialStatus.None);
        } else {
            return lookup.get(name);
        }
    }
}
