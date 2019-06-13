package ShortyClasses;

import Enums.ClothesTypes;
import Enums.Colour;
import Enums.Currency;
import Enums.SocialStatus;
import Exceptions.HugException;
import Exceptions.SomePeopleException;
import Interfaces.Huggable;
import Interfaces.Interactable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Shorty extends LivingCreature implements Interactable, Huggable, Comparable<Shorty>, Serializable {

    private String name;
    private Money budget;
    private SocialStatus status;
    private Clothes look;
    private double mass;
    private Coords coords;
    private LocalDateTime timeOfCreation;
    private int shorty_id;
    private String user_login;

    public String getUser_login(){
        return user_login;
    }

    public void setUser_login(String login){
        this.user_login = login;
    }


    public String getName() {
        return name;
    }

    public Money getBudget() {
        return budget;
    }

    public SocialStatus getStatus() {
        return status;
    }

    public Clothes getLook() {
        return look;
    }

    public Coords getCoords() {
        return coords;
    }

    public LocalDateTime getTimeOfCreation() {
        return timeOfCreation;
    }

    public int getShorty_id(){
        return shorty_id;
    }

    public void setShorty_id(int id){
        this.shorty_id = id;
    }

    public void setTimeOfCreation(LocalDateTime timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBudget(Money budget) {
        this.budget = budget;
    }

    public void setStatus(SocialStatus status) {
        this.status = status;
    }

    public void setLook(Clothes look) {
        this.look = look;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public void setMass (double mass){
        this.mass = mass;
    }

   /* public String create_id(){
        UUID uniqueKey = UUID.randomUUID();
        return uniqueKey.toString();
    }*/

    public Shorty() {
        super("Коротышка");
        this.name = "Неизвестный";
        this.budget = new Money(Currency.Santiky, 0);
        this.status = SocialStatus.None;
        this.look = new Clothes(Colour.None, ClothesTypes.None);
        this.mass = this.getMass();
        this.coords = new Coords();
        this.timeOfCreation = LocalDateTime.now();
        //this.shorty_id = create_id();
    }

    public Shorty(String name, int budget, Currency currency, SocialStatus status) {
        super("Коротышка");
        this.timeOfCreation = LocalDateTime.now();
        //this.shorty_id = create_id();
        this.name = name;
        if (budget >= 0) {
            this.budget = new Money(currency, budget);
        } else {
            System.out.println("Переменная budget не может быть отрицательной, поэтому количество денег было распознано как 0.");
            this.budget = new Money(currency, 0);
        }
        this.status = status;
      /*  System.out.printf("Создан новый объект: %s по имени %s. Его бюджет составляет: %s %s. Социальный статус: %s.%n",
                this.getObjectName(), this.name, this.budget.getAmount(), this.budget.getCurrency().getLocalizedName(), this.status.getLocalizedName());*/
        this.look = null;
    }

    public void putOnClothes(Clothes look) {
        if (this.look == null) {
            this.look = look;
          /*  printMessage_Clothes("О, %s %s наконец обзавелся новым луком (раньше-то одежды у него не было...)%n" +
                    "Это %s цвета %s. Красота-то какая!%n", look);*/
            /*System.out.printf("О, %s %s наконец обзавелся новым луком (раньше-то одежды у него не было...)%n" +
                            "Это %s цвета %s. Красота-то какая!%n",
                    this.status.getLocalizedName(), this.name, look.getType().getLocalizedName(), look.getColour().getLocalizedName());*/
        } else {
            this.look = look;
            /*printMessage_Clothes("О, %s %s сменил лук! Это %s цвета %s. У этого коротышки определенно есть вкус.%n", look);*/
           /* System.out.printf("О, %s %s сменил лук! Это %s цвета %s. У этого коротышки определенно есть вкус.%n",
                    this.status.getLocalizedName(), this.name, look.getType().getLocalizedName(), look.getColour().getLocalizedName());*/
        }
    }


    public void putOffClothes(Clothes look) {

        if (this.look == null) {
            printMessage_Clothes("Сожалеем, но %s %s не может снять %s цвета %s, так как у него вообще нет одежды.%n", look);
//            System.out.printf("Сожалеем, но %s %s не может снять %s цвета %s, так как у него вообще нет одежды.%n",
//                    this.status.getLocalizedName(), this.name, look.getType().getLocalizedName(), look.getColour().getLocalizedName());
        } else if (look == this.look) {
            printMessage_Clothes("%s %s снимает предмет одежды, а именно: %s, цвет: %s. Теперь он замерзнет. Зима уже близко.%n", look);
            /*System.out.printf("%s %s снимает предмет одежды, а именно: %s, цвет: %s. Теперь он замерзнет. Зима уже близко.%n",
                    this.status.getLocalizedName(), this.name, look.getType().getLocalizedName(), look.getColour().getLocalizedName());*/
            this.look = null;
        } else if (!(look == this.look) && (this.look.getColour() == look.getColour()) && (this.look.getType() == look.getType())) {
            printMessage_Clothes("%s %s не может снять %s цвета %s,  так как это не его одежда, хотя на нем надета очень похожая вещь!%n", look);
            /*System.out.printf("%s %s не может снять %s цвета %s,  так как это не его одежда, хотя на нем надета очень похожая вещь!%n",
                    this.status.getLocalizedName(), this.name, look.getType().getLocalizedName(), look.getColour().getLocalizedName());*/
        } else {
            printMessage_Clothes("%%s %s не может снять %s цвета %s,  так как это не его одежда.%n", look);
           /* System.out.printf("%s %s не может снять %s цвета %s,  так как это не его одежда.%n",
                    this.status.getLocalizedName(), this.name, look.getType().getLocalizedName(), look.getColour().getLocalizedName());*/
        }
    }

    private void printMessage_Clothes(String message, Clothes look) {
        System.out.printf(message, this.status.getLocalizedName(), this.name, look.getType().getLocalizedName(), look.getColour().getLocalizedName());
    }

    public void moneyExchange(Shorty shortyMoneyGiver, int amount) {
        if (amount >= 0) {
            if (shortyMoneyGiver.budget.getAmount() == 0) {
                System.out.printf("%s %s не может отжать деньги у %s %s, так как у последнего персонажа нечего отжимать.%n",
                        this.status.getLocalizedName(), this.name, shortyMoneyGiver.status.getLocalizedName(), shortyMoneyGiver.name);
            } else if (shortyMoneyGiver.budget.getAmount() > amount) {
                shortyMoneyGiver.decreaseBudget(amount);
                this.increaseBudget(amount);
                System.out.printf("%s %s отжимает деньги у %s %s.%n" +
                                "Теперь бюджет %s составляет: %s %s.%n" +
                                "Бюджет %s составляет: %s %s.%n",
                        this.status.getLocalizedName(), this.name, shortyMoneyGiver.status.getLocalizedName(), shortyMoneyGiver.name,
                        this.name, this.budget.getAmount(), this.budget.getCurrency().getLocalizedName(),
                        shortyMoneyGiver.name, shortyMoneyGiver.budget.getAmount(), shortyMoneyGiver.budget.getCurrency().getLocalizedName());
            } else if (shortyMoneyGiver.budget.getAmount() < amount) {
                System.out.printf("%s %s хотел отжать у %s %s больше денег, чем у него было.%n" +
                                "Но мы не дадим совершиться несправедливости!%n" +
                                "Поэтому %s отожмет только %s %s.%n",
                        this.status.getLocalizedName(), this.name, shortyMoneyGiver.status.getLocalizedName(), shortyMoneyGiver.name,
                        this.name, shortyMoneyGiver.budget.getAmount(), shortyMoneyGiver.budget.getCurrency().getLocalizedName());
                this.increaseBudget(shortyMoneyGiver.budget.getAmount());
                shortyMoneyGiver.budget.decreaseAmount(shortyMoneyGiver.budget.getAmount());
                System.out.printf("Теперь бюджет %s составляет: %s %s.%n" +
                                "Бюджет %s составляет: %s %s.%n",
                        this.name, this.budget.getAmount(), this.budget.getCurrency().getLocalizedName(),
                        shortyMoneyGiver.name, shortyMoneyGiver.budget.getAmount(), shortyMoneyGiver.budget.getCurrency().getLocalizedName());
            } else if (shortyMoneyGiver.budget.getAmount() == amount) {
                shortyMoneyGiver.decreaseBudget(amount);
                this.increaseBudget(amount);
                System.out.printf("%s %s отжимает все деньги до последней копейки у %s %s.%n" +
                                "Теперь бюджет %s составляет: %s %s.%n" +
                                "Бюджет %s составляет: %s %s.%n",
                        this.status.getLocalizedName(), this.name, shortyMoneyGiver.status.getLocalizedName(), shortyMoneyGiver.name,
                        this.name, this.budget.getAmount(), this.budget.getCurrency().getLocalizedName(),
                        shortyMoneyGiver.name, shortyMoneyGiver.budget.getAmount(), shortyMoneyGiver.budget.getCurrency().getLocalizedName());
            }
        } else {
            System.out.println("Переменная amount не может быть отрицательной.");
            System.out.printf("Бюджет %s по-прежнему составляет: %s %s.%n" +
                            "Бюджет %s по-прежнему составляет: %s %s.%n",
                    this.name, this.budget.getAmount(), this.budget.getCurrency().getLocalizedName(),
                    shortyMoneyGiver.name, shortyMoneyGiver.budget.getAmount(), shortyMoneyGiver.budget.getCurrency().getLocalizedName());
        }
    }

    public void increaseBudget(int amount) {
        if (amount >= 0) {
            this.budget.setAmount(this.budget.increaseAmount(amount));
        } else {
            System.out.println("Переменная amount не может быть отрицательной.");
            this.budget.setAmount(this.budget.increaseAmount(0));
        }
    }

    public void decreaseBudget(int amount) {
        if (amount >= 0) {
            if (this.budget.getAmount() - amount < 0) {
                this.budget.setAmount(0);
            } else if (this.budget.getAmount() - amount >= 0) {
                this.budget.setAmount(this.budget.decreaseAmount(amount));
            }
        } else {
            System.out.println("Переменная amount не может быть отрицательной.");
            this.budget.setAmount(this.budget.increaseAmount(0));
        }
    }


    @Override
    public void interact(PhysicalObject object) {
        if (object instanceof Shorty) {
            System.out.printf("%s %s %s определенно собирается взаимодейстоввать с другим Коротышкой.%n" +
                    "Скорее всего, он хочет отнять у него одежду.%n", this.getObjectName(), this.status.getLocalizedName(), this.name);
        } else if (object instanceof Clothes) {
            System.out.printf("%s %s %s определенно собирается взаимодейстовать с одеждой.%n" +
                    "Он хочет снять ее или надеть.%n", this.getObjectName(), this.status.getLocalizedName(), this.name);
        } else if (object instanceof Door) {
            System.out.printf("%s %s %s определенно собирается взаимодейстоввать с дверью.%n" +
                    "Возможно, он хочет ее открыть или постучать.%n", this.getObjectName(), this.status.getLocalizedName(), this.name);
        } else if (object instanceof Money) {
            System.out.printf("%s %s %s определенно собирается взаимодейстоввать с деньгами.%n" +
                    "Его бюджет в скором времени пополнится или уменьшится.%n", this.getObjectName(), this.status.getLocalizedName(), this.name);
        } else {
            System.out.println("Коротышки пока еще глупенькие и только учатся - они не знают такого объекта и не могут с ним взаимодейтсвовать.%n");
        }
    }

    @Override
    public boolean startHugging(LivingCreature hugger) {
        if (this.getIsHugged() == false) {
            this.setIsHugged(true);
            if (!(hugger instanceof Granny)) {
                throw new HugException("HugException обнаружено. Обнимает не бабушка, а кто-то другой. hugger: ", hugger);
            } else {
                System.out.printf("Все в порядке, обнимает бабушка. Степень любви к внуку: %s.%n", ((Granny) hugger).degreeOfGrannyLove.getLocalizedName());
            }
        } else {
            System.out.printf("Невозможно обнять. %s %s находится в процессе обнимашек.%n", this.getObjectName(), this.getName());
        }
        return this.getIsHugged();
    }


    @Override
    public boolean stopHugging() {
        this.setIsHugged(false);
        return this.getIsHugged();
    }

    @Override
    public double getMass() {
        final double MASS_SHORTY = 6.66;
        double mass;
        if (look == null) {
            mass = MASS_SHORTY + budget.getMass();
        } else mass = MASS_SHORTY + budget.getMass() + look.getMass();
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
            Shorty other = (Shorty) obj;
            return ((this.name.equals(other.name)) && (this.budget.equals(other.budget)) && (this.status.equals(other.status)) && (this.look.equals(other.look)) && (this.coords.equals(other.coords)));
        }

    }

    @Override
    public String toString() {
        return (String.format("Имя: %s; Бюджет: (%s); Социальный статус: %s; Одежда: (%s); Масса: (%s); Дата создания объекта: (%s); Координаты: (%s) ",
                this.name, this.budget, this.status.getLocalizedName(),
                this.look, this.mass, this.timeOfCreation, this.coords));
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, budget, status);
    }

    @Override
    public int compareTo(Shorty o) {
        if (this.name.equals(o.name)) {
            return this.budget.compareTo(o.budget);
        } else {
            return this.name.compareTo(o.name);
        }
    }

    public enum DegreeOfLove {
        RARE("так себе"), MEDIUM("в меру"), WELL_DONE("слишком много");

        private String name;

        DegreeOfLove(String name) {
            this.name = name;
        }

        public String getLocalizedName() {
            return name;
        }
    }//Внутренний класс (вложенный static)

    public static class Granny extends Shorty {
        private DegreeOfLove degreeOfGrannyLove;
        private String grandChildName;

        public Granny(DegreeOfLove degreeOfGrannyLove, Shorty shorty) {
            setObjectName("Бабушка");
            this.degreeOfGrannyLove = degreeOfGrannyLove;
            grandChildName = shorty.getName();
            System.out.printf("Создана бабушка. Внук: %s. Степень любви к внуку: %s.%n", shorty.getName(), this.degreeOfGrannyLove.getLocalizedName());
        }

        @Override
        public double getMass() {
            return Math.random() * 100;
        }
    }//Вложенный класс (non-static)


    public void toTalk() throws SomePeopleException {

        System.out.printf("%s начал говорить.%n", this.getName());

        class Topic {
            void changeTopic() {
                System.out.println("Тема разговора сменилась.");
            }

            void continueTopic() {
                System.out.println("Тема разговора продолжена.");
            }
        }//Знакомьтесь, бесполезный локальный класс

        Topic topic = new Topic();

           /* if (this.toBeSomePeople == false) {
                System.out.println("Можно спокойно говорить. Я не некоторые люди и тему менять мне не нужно.");
            } else {
                topic.changeTopic();
                throw new SomePeopleException("Тема сменилась, так как SomePeopleException обнаружено. Говорящий - те люди. Имя говорящего и значение поля toBeSomePeople : ", this.getName(), this.toBeSomePeople);
            }*/


    }

}
