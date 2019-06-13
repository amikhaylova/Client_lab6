package Main;

import Client.TCPClient;

import java.io.IOException;
import java.util.Scanner;

public class Main {

public static int port = -1;
    public static String hostName;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (args.length!=2) {
            System.out.println("Пожалуйста, введите hostname.");
            hostName = sc.nextLine();
            System.out.println("Пожалуйста, введите port.");
            while(port < 0){
                try{
                    port = Integer.parseInt(sc.nextLine());
                    if (port < 0 ){
                        System.out.println("Port введен в неверном формате. Пожалуйста, введите положительное целое число.");
                    }
                }catch (NumberFormatException e){
                    System.out.println("Port введен в неверном формате. Пожалуйста, введите положительное целое число.");
                }
            }
        }else {
            hostName = args[0];
            try{
                port = Integer.parseInt(args[1]);
                if (port < 0 ){
                    System.out.println("Port введен в неверном формате. Пожалуйста, введите положительное целое число.");
                }
            }catch (NumberFormatException e){
                System.out.println("Port введен в неверном формате. Пожалуйста, введите положительное целое число.");
            }
            while(port < 0){
                try{
                    port = Integer.parseInt(sc.nextLine());
                    if (port < 0 ){
                        System.out.println("Port введен в неверном формате. Пожалуйста, введите положительное целое число.");
                    }
                }catch (NumberFormatException e){
                    System.out.println("Port введен в неверном формате. Пожалуйста, введите положительное целое число.");
                }
            }
        }



        try {
            TCPClient client = new TCPClient();
            client.start();
        } catch (InterruptedException e) {
            System.out.println("УРААААА! " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
