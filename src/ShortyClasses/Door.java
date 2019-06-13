package ShortyClasses;

import Interfaces.Openable;

import java.io.Serializable;
import java.util.Objects;

public class Door extends PhysicalObject implements Openable,Serializable {

    private boolean isOpen;
    private DoorKnob doorKnob;

    public Door() {
        super("Дверь");
        boolean is_open = false;
        System.out.printf("Создан новый объект: %s.%n",
                this.getObjectName());
        doorKnob = this.new DoorKnob();
        System.out.println("К двери приделана ручка.");
    }

    @Override
    public double getMass() {
        final double TREE_DENSITY = 300; //кг на м^3
        final double DOOR_HEIGHT = 2;
        final double DOOR_DEPTH = 0.07;
        final double DOOR_WIDTH = 0.5;
        return DOOR_DEPTH * DOOR_HEIGHT * DOOR_WIDTH * TREE_DENSITY;
    }

    @Override
    public boolean open(Shorty shorty) {
        if ((isOpen == false) && (doorKnob.isBroken == false)) {
            isOpen = true;
            System.out.println(shorty.getStatus().getLocalizedName() + " " + shorty.getName() + " открывает дверь.");
        } else if ((isOpen == false) && (doorKnob.isBroken == true)){
            System.out.printf("%s %s пытается открыть дверь и терпит неудачу, так как дверная ручка не работает. Видимо, она сломана.%n",
                    shorty.getStatus().getLocalizedName(), shorty.getName());
        } else{
            System.out.printf("%s %s пытается открыть дверь и терпит неудачу, так как она уже открыта. Видимо, кто-то слишком мало спал.%n",
                    shorty.getStatus().getLocalizedName(), shorty.getName());
        }
        return isOpen;
    }

    public void faceTheDoor(Shorty shorty) {
        if (isOpen) {
            System.out.printf("%s %s %s заглядывает в дверь.%n",
                    shorty.getObjectName(), shorty.getStatus().getLocalizedName(), shorty.getName());
        } else {
            System.out.printf("%s %s %s пытается заглянуть в дверь, но ударяется головой, потому что она закрыта.%n",
                    shorty.getObjectName(), shorty.getStatus().getLocalizedName(), shorty.getName());
        }
    }

    private class DoorKnob{
        boolean isBroken;

         DoorKnob () {
            if (Math.random()< 0.05) {
                isBroken = true;
            } else {
                isBroken = false;
            }
        }
    }

    public void repairDoor(){
        doorKnob.isBroken = false;
        System.out.println("Дверь починили.");
    }

    public boolean getOpen() {
        return isOpen;
    }

    public void knockOnTheDoor(Shorty shorty) {
        if (!(isOpen)) {
            System.out.printf("%s стучит в дверь. Тук-тук! Это я, %s %s.%n",
                    shorty.getObjectName(), shorty.getStatus().getLocalizedName(), shorty.getName());
        } else {
            System.out.printf("%s стучит в открытую дверь дверь. Тук-тук! Это я, %s %s.%n" +
                            "И все-таки это странно - стучать в открытую дверь.%n",
                    shorty.getObjectName(), shorty.getStatus().getLocalizedName(), shorty.getName());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        } else {
            Door other = (Door) obj;
            return (this.isOpen == other.isOpen);
        }

    }

    @Override
    public String toString() {
        String door_condition;
        if (isOpen == true) {
            door_condition = "Дверь открыта.";
        } else {
            door_condition = "Дверь закрыта";
        }
        return (door_condition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isOpen);
    }

    public void setOpen(boolean open) {
        this.isOpen = open;
    }
}
