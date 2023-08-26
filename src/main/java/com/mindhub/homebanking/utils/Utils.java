package com.mindhub.homebanking.utils;

public final class Utils {
    public Utils(){}

     public static String getNumberAccount() {
        int min = 0;
        int max = 99999999;
        return "VIN" + String.valueOf((int)Math.floor(Math.random() * (max - min + 1) + min));
    }

    public static  String getNumberCard(){
        int min = 0;
        int max = 9999;

        StringBuilder cardNumber = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            String result = String.valueOf((int)Math.floor(Math.random() * (max - min + 1) + min));
            cardNumber.append("-").append(result);
        }

        return cardNumber.substring(1);
    }
    public static  int getNumberCVV(){
        int min = 100;
        int max = 999;

        return (int)Math.floor(Math.random() * (max - min + 1) + min);
    }
}
