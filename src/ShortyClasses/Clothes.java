package ShortyClasses;

import Enums.ClothesTypes;
import Enums.Colour;

import java.io.Serializable;
import java.util.Objects;

public class Clothes extends PhysicalObject implements Serializable {
    private Colour colour;
    private ClothesTypes type;

    public Clothes(Colour colour, ClothesTypes type) {
        super("Одежда");
        this.colour = colour;
        this.type = type;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public ClothesTypes getType() {
        return type;
    }

    public void setType(ClothesTypes type) {
        this.type = type;
    }

    @Override
    public double getMass() {
        double mass = 0.0;
        final double HAT_MASS = 0.7;
        final double JEANS_MASS = 1.5;
        final double LEGGINGS_MASS = 0.47;
        final double SKIRT_MASS = 0.99;
        final double SOCKS_MASS = 0.23;
        final double SWEATSHIRT_MASS = 1.11;
        final double TSHIRT_MASS = 0.73;

        if (this.type == ClothesTypes.Hat) {
            mass = HAT_MASS;
        } else if (this.type == ClothesTypes.Jeans) {
            mass = JEANS_MASS;
        } else if (this.type == ClothesTypes.Leggings) {
            mass = LEGGINGS_MASS;
        } else if (this.type == ClothesTypes.Skirt) {
            mass = SKIRT_MASS;
        } else if (this.type == ClothesTypes.Socks) {
            mass = SOCKS_MASS;
        } else if (this.type == ClothesTypes.Sweatshirt) {
            mass = SWEATSHIRT_MASS;
        } else if (this.type == ClothesTypes.Tshirt) {
            mass = TSHIRT_MASS;
        }
        return mass;
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
            Clothes other = (Clothes) obj;
            return ((this.colour == other.colour) && (this.type == other.type));
        }

    }

    @Override
    public String toString() {
        return ("Тип одежды: " + type.getLocalizedName() + ". Цвет одежды: " + colour.getLocalizedName() + ".");
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, colour);

    }
}
