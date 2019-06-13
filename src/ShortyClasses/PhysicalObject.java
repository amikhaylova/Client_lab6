package ShortyClasses;

public abstract class PhysicalObject {

    private String objectName;

    public PhysicalObject() {
        this.objectName = "Неизвестный";
    }

    public PhysicalObject(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public abstract double getMass();

}
