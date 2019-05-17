package Classes;

import Enums.ClothesTypes;
import Enums.Colour;
import Enums.Currency;
import Enums.SocialStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.*;

public class TCPClient {


    public void start() throws InterruptedException, IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));) {
            boolean toKillConnection = false;

            while (!toKillConnection) {

                Socket socket = null;


                boolean isFinished = false;

                //System.out.println("isFinished " + isFinished);

                while (!isFinished) {

                  //  System.out.println("Зашел в вайл не фигишед");

                    socket = new Socket();

                    //System.out.println("Создал новый сокет");

                    try {
                      //  System.out.println("В трае у сокета");
                        socket.connect(new InetSocketAddress(InetAddress.getByName(Main.hostName), Main.port), 100000);
                        isFinished = true;
                        //System.out.println("isFinished2 " + isFinished);
                    } catch (SocketTimeoutException | SocketException e) {
                        System.out.println("Клиент еще не подключился к серверу, но он пытается.");
                        Thread.sleep(1000);
                    } catch (UnknownHostException e){
                        System.out.println("Произошла ошибка: " + e + ". Перезапустите приложение и укажите существующий сервер.");
                        System.exit(0);
                    }

                   // System.out.println("Вышел из трая с сокетами");
                }

                System.out.println("Клиент подключился к серверу.");

                try (
                        DataOutputStream oos1 = new DataOutputStream(socket.getOutputStream());
                        ObjectOutputStream oos2 = new ObjectOutputStream(oos1);
                        DataInputStream ois = new DataInputStream(socket.getInputStream());) {


                    System.out.println("Введите команду: ");

                    while (!socket.isOutputShutdown() && !toKillConnection) {



                        if (br.ready()) {

                           // System.out.println("В бр ис реди");

//                                System.out.println("Пробую считать клиентский ввод");
                                String clientInput = br.readLine();
  //                             System.out.println("Введен " + clientInput);


                            String clientCommand = "";

                           try {
                               clientCommand = getCommand(clientInput);
                               //System.out.println("Полученная команада: " + clientCommand);
                           } catch (NullPointerException e){
                               System.out.println("Коллекция будет сохранена.");
                               clientCommand = "save";
                               System.exit(0);
                           }




                            if (clientCommand != null) {
                                //System.out.println("Если это был не нал");
                                oos1.writeUTF(clientCommand);
                                oos1.writeUTF(clientCommand);
                                oos1.writeUTF(clientCommand);
                                oos1.writeUTF(clientCommand);
                                oos1.writeUTF(clientCommand);
                                oos1.writeUTF(clientCommand);
                              // System.out.println("Отправил в оос1");
                            } else {
                                while (clientCommand == null) {
                                    System.out.println("Введенной команды не существует. Чтобы посмотреть список команд, введите help.");
                                    System.out.println("Введите новую команду: ");
                                    clientInput = br.readLine();
                                    //System.out.println(clientInput);
                                    clientCommand = getCommand(clientInput);
                                }
                                oos1.writeUTF(clientCommand);
                                oos1.writeUTF(clientCommand);
                                oos1.writeUTF(clientCommand);
                                oos1.writeUTF(clientCommand);
                                oos1.writeUTF(clientCommand);
                                oos1.writeUTF(clientCommand);
                                //System.out.println("Отправил в оос1, хотя раньше был нал");
                            }

                            //System.out.println("Начинаю решать, отправлять ли объект");

                            if (clientCommand.equals("add") || clientCommand.equals("add_if_min") || clientCommand.equals("remove")) {
                                //String [] s = clientInput.split("\\{");
                                //String removedCommand = s [0];
                                StringBuilder fullCommand = new StringBuilder(clientInput);
                                String removedCommand = commandChecker(clientInput);
                                String JSONString = fullCommand.toString().replaceFirst(removedCommand, "").trim();
                                Shorty clientShorty = getShorty(JSONString);
                                //System.out.println(clientShorty);
                               // System.out.println("Собираюсь записать объект");
                                oos2.writeObject(clientShorty);
                                //System.out.println("Записали объект");
                            }

                           // System.out.println("Клиент отправил сообщение серверу. ");
                            //System.out.println("Ожидаем ответ от сервера...");


// ждём чтобы сервер успел прочесть сообщение из сокета и ответить

//                    if (clientCommand.equalsIgnoreCase("quit")) {
//
//// если условие выхода достигнуто разъединяемся
//                        System.out.println("Client kill connections");
//                        Thread.sleep(2000);
//
//// смотрим что нам ответил сервер на последок перед закрытием ресурсов
//                        if (ois.read() > -1) {
//                            System.out.println("reading...");
//                            String in = ois.readUTF();
//                            System.out.println(in);
//                        }
//
//// после предварительных приготовлений выходим из цикла записи чтения
//                        break;
//                    }
                            if (!clientCommand.equals("quit")) {
                               // System.out.println("Если это был не квит");
                                while (ois.available() == 0) {
                                    Thread.sleep(100);
                                }

                                //System.out.println("Чтение ответа...");
                                System.out.println(ois.readUTF());
                            } else {
                                //System.out.println("Это был квит");
                                toKillConnection = true;
                                //System.out.println("ту кил коннекш" + toKillConnection);
                                socket.close();
                                //System.out.println("Сокет закрыли");
                                //System.out.printf("trying to get out of here...");
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
                    shortyForClient.setCoords(new Coords((Integer) coords.get("x"), (Integer) coords.get("y")));
                }
            }

            return shortyForClient;

        } catch (JSONException e) {
            System.out.println("Произошла ошибка, связанная с заданием объекта в формате JSON: " + e.getMessage());
            return null;
        }
    }

}