package com.tradingbot.binancebot.util;

import java.util.Scanner;

public class Helper {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static String showInputField(String text){
        System.out.print(text);
        return SCANNER.nextLine();
    }

}
