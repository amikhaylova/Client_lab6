package ShortyClasses;

import Enums.ClothesTypes;
import Enums.Colour;
import Enums.Currency;
import Enums.SocialStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;

public class ShortyManager {
    private Vector<Shorty> shortyVector;
    private Date date = new Date();

    public ShortyManager() {
        shortyVector = new Vector<Shorty>();
        load();
    }

    /**
     *To print some information about the collection to stdout
     */
    public void info() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("Тип коллекции: %s\n", Shorty.class.getName()))
                .append(String.format("Дата инициализации: %s\n", date))
                .append(String.format("Количество элементов в коллекции: %d", shortyVector.size()));
        System.out.println(stringBuilder);
    }

    /**
     * To print text description of all collection's elements to stdout
     */
    public void show() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Shorty s : shortyVector) {
            stringBuilder.append(String.format("%s\n", s.toString()));
        }
        System.out.println(stringBuilder);
    }

    public void sort() {
        Collections.sort(shortyVector);
    }

    /**
     * to reread the collection from the file (FILE_PATH)
     */
    public void load() {

        StringBuilder data = new StringBuilder();
        try {
            Scanner scanner = new Scanner(new File(System.getenv("FILE_PATH")));
            while (scanner.hasNextLine()) {
                data.append(scanner.nextLine());
            }
            JSONArray shorties = new JSONArray(data.toString());

            for (Object shorty : shorties) { //while (iterator.hasNext())
                try {
                    Shorty shortyForVector = jsonToShorty((JSONObject) shorty);
                    /*if (!shortyVector.contains(shortyForVector)) {
                        shortyVector.add(shortyForVector);
                    }*/

                    if (shortyVector.stream().noneMatch(x -> x.equals(shortyForVector))){
                        shortyVector.add(shortyForVector);
                    }

                } catch (ClassCastException | IllegalArgumentException e) {
                    System.err.println("Все плохо. " + e.getMessage());
                }
            }


        } catch (NullPointerException|FileNotFoundException e) {
            System.err.println("Файл не найден. Коллекция будет сохранена в файл по умолчанию.");
        }catch (JSONException e) {
        System.err.println("Проблемы с JSON. " + e.getMessage());
    }


    }

    /**
     * transforms JSONObject to instance of ShortyClasses.Shorty class
     * @param shorty - JSONObject representation of shorty
     * @return ShortyClasses.Shorty instance
     * @throws JSONException
     */
    private Shorty jsonToShorty(JSONObject shorty) throws JSONException {
        Shorty shortyForVector = new Shorty();

        if (shorty.has("name")){
            shortyForVector.setName(shorty.getString("name"));
        }

        try{
            if (shorty.has("budget")) {
                JSONObject budget = (JSONObject) shorty.get("budget");
                if (budget.has("amount")) {
                    shortyForVector.getBudget().setAmount(budget.getInt("amount"));
                }
                if (budget.has("currency")) {
                    shortyForVector.getBudget().setCurrency(Currency.get(budget.getString("currency")));
                }
            }
        }catch (ClassCastException e){
            System.out.println("Произошла ошибка: " + e.getMessage() + ". Было установлено значение по умолчанию.");
        }

        if (shorty.has("status")){
            shortyForVector.setStatus(SocialStatus.get(shorty.getString("status")));
        }
        if (shorty.has("look")){
            JSONObject look = (JSONObject) shorty.get("look");
            if(look.has("colour")){
                shortyForVector.getLook().setColour(Colour.get((String) look.get("colour")));
            }
            if(look.has("type")){
                shortyForVector.getLook().setType(ClothesTypes.get((String) look.get("type")));
            }
        }

        try{
            if (shorty.has("coords")) {
                JSONObject coords = (JSONObject) shorty.get("coords");
                if (coords.has("x") && coords.has("y")) {
                    shortyForVector.setCoords(new Coords(coords.getInt("x"), coords.getInt("y")));
                }
            }
        }catch (ClassCastException e){
            System.out.println("Произошла ошибка: " + e.getMessage() + ". Было установлено значение по умолчанию.");
        }

        return shortyForVector;
    }

    /**
     * To add new element in the collection
     * @param element string description of ShortyClasses.Shorty instance
     */
    public void add(String element) {
        try {
            JSONObject shortyJSON = new JSONObject(element);
            Shorty shortyForVector = jsonToShorty(shortyJSON);
            if (!shortyVector.contains(shortyForVector)) {
                shortyVector.add(shortyForVector);
                System.out.println("Новый коротышка добавлен в коллекцию.");
            }else{
                System.out.println("Новый коротышка не добавлен в коллекцию.");
            }
        } catch (JSONException e) {
            System.err.printf("Даже строку правильно написать не можете. %s\n", e.getMessage());
        }

    }

    /**
     * To remove the first element in the collection
     */
    public void remove_first() {
        if (shortyVector.size() > 0) {
            shortyVector.remove(0);
            System.out.println("Первый элемент коллекции удален.");
        } else {
            System.out.println("Невозможно удалить первый элемент, так как коллекция пуста.");
        }
    }

    /**
     * To remove the element from the collection using its' json string description
     * @param element - string description of ShortyClasses.Shorty instance
     */
    public void remove(String element) {
        try {
            JSONObject shortyJSON = new JSONObject(element);
            Shorty shortyForVector = jsonToShorty(shortyJSON);
            if(shortyVector.remove(shortyForVector)){
                System.out.println("Коротышка удален из коллекции.");
            }else{
                System.out.println("Коротышка не может быть удален из коллекции, так как его там нет.");
            }
        } catch (JSONException e) {
            System.err.printf("Даже строку правильно написать не можете. %s%n", e.getCause());
        }
    }

    /**
     * To add new element to the collection if its' value is less then the value of the least element in the collection
     * @param element - JSON text description of ShortyClasses.Shorty instance
     */
    public void add_if_min(String element) {
        try {
            JSONObject shortyJSON = new JSONObject(element);
            Shorty shortyForVector = jsonToShorty(shortyJSON);
            if (shortyVector.size() > 0) {
                Shorty minShorty = Collections.min(shortyVector);
                if (shortyForVector.compareTo(minShorty) < 0) {
                    shortyVector.add(shortyForVector);
                    System.out.println("Новый коротышка добавлен в коллекцию");
                } else{
                    System.out.println("Новый коротышка не добавлен в коллекцию, " +
                            "так как его значение больше, чем у наименьшего элемента этой коллекции.");
                }
            } else {
                shortyVector.add(shortyForVector);
                System.out.println("Новый коротышка добавлен в коллекцию");
            }

        } catch (JSONException e) {
            System.err.printf("Даже строку правильно написать не можете. %s%n", e.getCause());
        }
    }

    public void saveCollection(File file) {
        JSONArray shortyArray = new JSONArray();
        if (!shortyVector.isEmpty()) {
            for (Shorty shorty : shortyVector) {
                JSONObject shortyObject = new JSONObject();

                JSONObject shortyBudget = new JSONObject();
                shortyBudget.put("currency", shorty.getBudget().getCurrency().getLocalizedName());
                shortyBudget.put("amount", shorty.getBudget().getAmount());

                JSONObject shortyLook = new JSONObject();
                shortyLook.put("colour", shorty.getLook().getColour().getLocalizedName());
                shortyLook.put("type", shorty.getLook().getType().getLocalizedName());


                shortyObject.put("name", shorty.getName());
                shortyObject.put("budget", shortyBudget);
                shortyObject.put("status", shorty.getStatus().getLocalizedName());
                shortyObject.put("look", shortyLook);


                shortyArray.put(shortyObject);

                try (FileWriter fileWriter = new FileWriter(file)){//System.getenv("FILE_PATH"))) {
                    fileWriter.write(shortyArray.toString(4));
                    fileWriter.flush();
                } catch (IOException e) {
                    System.err.println("Проблема с файлом. " + e.getMessage());
                }

            }
        }else{
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write("");
                fileWriter.flush();
            } catch (IOException e) {
                System.err.println("Проблема с файлом. " + e.getMessage());
            }
        }
    }
}
