package Classes;

import Enums.Currency;

import java.io.Serializable;
import java.util.Objects;

public class Money extends PhysicalObject implements Comparable<Money>, Serializable {
    private Currency currency;
    private int amount = 0;

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        if (amount >= 0) {
            this.amount = amount;
        } else {
            System.out.println("Переменная amount не может быть отрицательной.");
        }
    }

    public Money(int amount) {
        super("Деньги");
        this.setAmount(amount);
        this.currency = Currency.Rubles;
    }

    public Money(Currency currency, int amount) {
        super("Деньги");
        this.currency = currency;
        this.setAmount(amount);
    }

    public int increaseAmount(int value) {
        if (value >= 0) {
            this.amount += value;
        } else {
            System.out.println("Переменная value не может быть отрицательной.");
        }
        return this.amount;
    }

    public int decreaseAmount(int value) {
        if (value >= 0) {
            this.amount -= value;
        } else {
            System.out.println("Переменная value не может быть отрицательной.");
        }
        return this.amount;
    }

    @Override
    public double getMass() {
        double mass = 0;
        final double DOLLAR_MASS = 0.0025;
        final double RUBLE_MASS = 0.0082;
        final double SANTIK_MASS = 0.009;

        if (currency == Currency.Dollars) {
            mass = amount * DOLLAR_MASS;
        } else if (currency == Currency.Rubles) {
            mass = amount * RUBLE_MASS;
        } else if (currency == Currency.Santiky) {
            mass = amount * SANTIK_MASS;
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
            Money other = (Money) obj;
            return ((((Integer) this.amount).equals((Integer) other.amount)) && (this.currency.equals(other.currency)));
        }

    }

    @Override
    public String toString() {
        return ("Валюта: " + currency.getLocalizedName() + ". Количество: " + amount + ".");
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }

    private Integer moneyCast(Money money) {
        final int K_DOL_TO_SAN = 5;
        final int K_RUB_TO_SAN = 3;
        if (money.currency == Currency.Rubles) {
            return money.amount * K_RUB_TO_SAN;
        } else if (money.currency == Currency.Dollars) {
            return money.amount * K_DOL_TO_SAN;
        } else {
            return money.amount;
        }
    }

    @Override
    public int compareTo(Money o) {
        return moneyCast(this).compareTo(moneyCast(o));
    }
}
