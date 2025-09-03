package com;

import java.util.Scanner;

public class UserInput {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String firstName = UserInput.getString("Введите имя");
        String lastName = UserInput.getString("Введите фамилию");
        int age = UserInput.getInt("Введите возраст");
        String email = UserInput.getString("Введите электронную почту");

        System.out.println("\n Данные пользователя:");
        System.out.println("Имя: " + firstName);
        System.out.println("Фамилия: " + lastName);
        System.out.println("Возраст: " + age);
        System.out.println("Email: " + email);
    }

    public static String getString(String message) {
        System.out.print(message + ": ");
        return scanner.nextLine();
    }

    public static int getInt(String message) {
        System.out.print(message + ": ");
        while (!scanner.hasNextInt()) {
            System.out.println("Ошибка! Введите целое число.");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }
}