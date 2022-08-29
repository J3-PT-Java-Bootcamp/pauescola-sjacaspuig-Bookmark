package com.ironhack.bookmark_app.userinput;

import java.util.Scanner;

public class UserInput {
    public static String readText() {
        System.out.print("\n~$ ");
        return new Scanner(System.in).nextLine();
    }

    public static int getIntBetween(int min, int max) {
        final var scanner = new Scanner(System.in);
        do {
            try {
                int number = scanner.nextInt();
                if(number >= min && number <= max) return number;
            } catch(Exception e) {
                System.out.println("[!] Invalid number");
            }
        } while(true);
    }

    public static boolean getYesNo() {
        String res = readText().toLowerCase();
        return res.equals("y");
    }

    public static Integer getIntNumber() {
        final var scanner = new Scanner(System.in);
        do {
            try {
                int number = scanner.nextInt();
                return number;
            } catch(Exception e) {
                System.out.println("[!] Invalid number");
            }
        } while(true);
    }
}