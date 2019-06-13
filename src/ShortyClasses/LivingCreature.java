package ShortyClasses;

public abstract class LivingCreature extends PhysicalObject {

    private boolean isHugged = false;

    public boolean getIsHugged() {
        return isHugged;
    }

    public void setIsHugged(boolean hugged) {
        isHugged = hugged;
    }

    public LivingCreature() {
        super("Неизвестный");
    }

    public LivingCreature(String objectName) {
        super(objectName);
    }

}
