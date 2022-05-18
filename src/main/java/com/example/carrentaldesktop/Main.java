package com.example.carrentaldesktop;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        if (Arrays.asList(args).contains("--stat")) {
            Statisztika.start(args);
        } else {
            CarApplication.main(args);
        }
    }
}
