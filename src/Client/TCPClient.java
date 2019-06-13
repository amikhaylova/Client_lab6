package Client;

import ShortyClasses.Coords;
import Main.Main;
import ShortyClasses.Shorty;
import Enums.ClothesTypes;
import Enums.Colour;
import Enums.Currency;
import Enums.SocialStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPClient {

    private String requested_command = " ";
    private Shorty requested_shorty = null;
    private boolean get_answer = false;

    String login = "";
    String state = "";
    boolean is_authorized = false;
    boolean is_registrated = false;
    String password = "";
    String email = "";
    Scanner sc = new Scanner(System.in);
    String answer = "";
    String to_reg = "";

    private void authorization (DataOutputStream oos, DataInputStream ois) throws IOException{
        System.out.println("У вас уже есть аккаунт? (yes/no)");
        if (sc.hasNextLine()){
            String answer = sc.nextLine();
            while(!(answer.equals("yes")||answer.equals("no"))){
                System.out.println("Пожалуйста, введите yes или no.");
                answer = sc.nextLine();
            }if (answer.equals("yes")){
                while(!is_authorized){
                    authorize(oos,ois);
                    if (to_reg.equals("yes")){
                        answer = " ";
                        to_reg = " ";
                        break;
                    }
                }
            }if (answer.equals("no")){
                while(!is_registrated){
                    registrate(oos, ois);
                }
                System.out.println("Авторизуйтесь, пожалуйста, с вашими новыми данными.");
                while(!is_authorized){
                    authorize(oos,ois);
                    if (to_reg.equals("yes")){
                        answer = " ";
                        to_reg = " ";
                        break;
                    }
                }
            }
        }

    }

    private void authorize (DataOutputStream oos, DataInputStream ois) throws IOException{
        state = "authorization";
        write(oos, state);
        System.out.println("Введите ваш логин:");
        if (sc.hasNext()){
            login = sc.nextLine();
            write(oos, login);
            answer = ois.readUTF();
            if (answer.equals("Данного логина не существует.")){
                System.out.println("Данного логина не существует.");
                System.out.println("Хотите вернуться к главному меню? (yes/no)");
                to_reg = sc.nextLine();
                while(!(to_reg.equals("yes")||to_reg.equals("no"))){
                    System.out.println("Пожалуйста, введите yes или no.");
                    to_reg = sc.nextLine();
                }
                return;
            }
            System.out.println("Введите ваш пароль:");
            password = sc.nextLine();
            write (oos, password);
            answer = ois.readUTF();
            System.out.println(answer);
            if (answer.equals("Авторизация прошла успешно")){
                is_authorized = true;
            }
        }

    }

    private void registrate (DataOutputStream oos, DataInputStream ois) throws IOException{
        state = "registration";
        write(oos, state);
        System.out.println("Введите логин, который хотите использовать для входа на сайт: ");
        login = sc.nextLine();
        System.out.println("Введите адрес электронной почты, на который необходимо отправить пароль: ");
        email = sc.nextLine();
        write(oos, login);
        write(oos, email);
        answer = ois.readUTF();
        System.out.println(answer);
        if(!answer.equals("Данный логин занят")){
            is_registrated = true;
        }
    }


    public void start() throws InterruptedException, IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));) {
            boolean toKillConnection = false;

            while (!toKillConnection) {

                Socket socket = null;
                boolean isFinished = false;

                while (!isFinished) {
                    socket = new Socket();

                    try {
                        socket.connect(new InetSocketAddress(InetAddress.getByName(Main.hostName), Main.port), 100000);
                        isFinished = true;
                    } catch (SocketTimeoutException | SocketException e) {
                        System.out.println("Клиент еще не подключился к серверу, но он пытается.");
                        Thread.sleep(1000);
                    } catch (UnknownHostException e){
                        System.out.println("Произошла ошибка: " + e + ". Перезапустите приложение и укажите существующий сервер.");
                        System.exit(0);
                    }
                }

                System.out.println("Клиент подключился к серверу.");

                try (
                        DataOutputStream oos1 = new DataOutputStream(socket.getOutputStream());
                        ObjectOutputStream oos2 = new ObjectOutputStream(oos1);
                        DataInputStream ois = new DataInputStream(socket.getInputStream());) {

                    String pr_login = login;

                    while(!is_authorized){
                        authorization(oos1,ois);
                    }


                    is_registrated = false;
                    is_authorized = false;

                    if (!requested_command.equals(" ") && !get_answer && (pr_login.equals(login))){
                        get_answer = false;
                        write(oos1,requested_command);
                        if ((requested_command.equals("add"))||(requested_command.equals("add_if_min"))||(requested_command.equals("remove"))){
                            oos2.writeObject(requested_shorty);
                        }
                        System.out.println("Ответ на последнюю запрошенную команду: ");
                        System.out.println(ois.readUTF());
                        get_answer = true;
                    }

                    System.out.println("Введите команду: ");

                    while (!socket.isOutputShutdown() && !toKillConnection) {



                        if (br.ready()) {
                            String clientInput = br.readLine();
                            String clientCommand = "";
                           try {
                               clientCommand = getCommand(clientInput);
                           } catch (NullPointerException e){
                               System.out.println("Коллекция будет сохранена.");
                               clientCommand = "save";
                               System.exit(0);
                           }




                            if ((clientCommand != null) && !clientCommand.equals("add") && !clientCommand.equals("add_if_min") && !clientCommand.equals("remove")) {
                                requested_command = clientCommand;
                                get_answer = false;
                                write(oos1, clientCommand);
                            } else if (clientCommand == null) {
                                while (clientCommand == null) {
                                    System.out.println("Введенной команды не существует. Чтобы посмотреть список команд, введите help.");
                                    System.out.println("Введите новую команду: ");
                                    clientInput = br.readLine();
                                    clientCommand = getCommand(clientInput);
                                }

                                if (!clientCommand.equals("add") && !clientCommand.equals("add_if_min") && !clientCommand.equals("remove")){
                                    requested_command = clientCommand;
                                    get_answer = false;
                                    write(oos1, clientCommand);
                                }
                            }


                            if (clientCommand.equals("add") || clientCommand.equals("add_if_min") || clientCommand.equals("remove")) {
                                StringBuilder fullCommand = new StringBuilder(clientInput);
                                String removedCommand = commandChecker(clientInput);
                                String JSONString = fullCommand.toString().replaceFirst(removedCommand, "").trim();
                                Shorty clientShorty = getShorty(JSONString);
                                requested_shorty = clientShorty;
                                requested_command = clientCommand;
                                get_answer = false;
                                write(oos1, clientCommand);
                                oos2.writeObject(clientShorty);
                            }


                            if (!clientCommand.equals("quit")) {
                                while (ois.available() == 0) {
                                    Thread.sleep(100);
                                }
                                System.out.println(ois.readUTF());
                                get_answer = true;
                            } else {
                                toKillConnection = true;
                                socket.close();
                            }

                        }
                    }

                } catch (UnknownHostException e) {
                    System.out.println("Произошла ошибка: " + e.getMessage());
                } catch (SocketException e) {
                    System.out.println("Ой, с сервером что-то произошло. Соединение разорвано.");
                } catch (IOException e) {
                    System.out.println("Произошла ошибка: " + e.getMessage());
                }
            }
        }
    }

    private String getCommand(String clientInput) {
        String input = clientInput.trim();
        switch (input) {
            case ("help"):
                return ("help");
            case ("info"):
                return ("info");
            case ("remove_first"):
                return ("remove_first");
            case ("show"):
                return ("show");
            case ("load"):
                return ("load");
            case ("save"):
                return ("save");
            case ("quit"):
                return ("quit");
        }
        String removedCommand = commandChecker(input);
        if (removedCommand != null) {
            switch (removedCommand) {
                case ("add"):
                    return ("add");
                case ("add_if_min"):
                    return ("add_if_min");
                case ("remove"):
                    return ("remove");
            }
        }
        return null;
    }

    private String commandChecker(String checkedString) {
        String[] commandsWithElement = {"add_if_min", "remove", "add"};
        String[] s = checkedString.split("\\{");

        if (s[0].equals(checkedString)) {
            return null;
        }

        for (String command : commandsWithElement) {
            if (s[0].trim().equals(command)) {
                return command;
            }
        }
        return null;
    }

    private Shorty getShorty(String JSONString) {
        try {
            JSONObject shortyJSON = new JSONObject(JSONString);
            return jsonToShorty(shortyJSON);
        } catch (JSONException e) {
            System.out.println("Произошла ошибка, связанная с заданием объекта в формате JSON: " + e.getMessage());
            return null;
        }

    }

    private Shorty jsonToShorty(JSONObject shorty) {
        try {
            Shorty shortyForClient = new Shorty();

            if (shorty.has("name")) {
                shortyForClient.setName(shorty.getString("name"));
            }


            if (shorty.has("status")) {
                shortyForClient.setStatus(SocialStatus.get(shorty.getString("status")));
            }

            if (shorty.has("budget")) {
                JSONObject budget = (JSONObject) shorty.get("budget");
                if (budget.has("amount")) {
                    shortyForClient.getBudget().setAmount(budget.getInt("amount"));
                }
                if (budget.has("currency")) {
                    shortyForClient.getBudget().setCurrency(Currency.get(budget.getString("currency")));
                }
            }

            if (shorty.has("look")) {
                JSONObject look = (JSONObject) shorty.get("look");
                if (look.has("colour")) {
                    shortyForClient.getLook().setColour(Colour.get((String) look.get("colour")));
                }
                if (look.has("type")) {
                    shortyForClient.getLook().setType(ClothesTypes.get((String) look.get("type")));
                }
            }

            if (shorty.has("coords")) {
                JSONObject coords = (JSONObject) shorty.get("coords");
                if (coords.has("x") && coords.has("y")) {
                    shortyForClient.setCoords(new Coords(coords.getDouble("x"), coords.getDouble("y")));
                }
            }

            shortyForClient.setMass(shortyForClient.getMass());

            return shortyForClient;

        } catch (JSONException e) {
            System.out.println("Произошла ошибка, связанная с заданием объекта в формате JSON: " + e.getMessage());
            return null;
        }
    }

    private void write (DataOutputStream oos, String string) throws IOException{
        oos.writeUTF(string);
        oos.writeUTF(string);
        oos.writeUTF(string);
        oos.writeUTF(string);
        oos.writeUTF(string);
        oos.writeUTF(string);
    }
}