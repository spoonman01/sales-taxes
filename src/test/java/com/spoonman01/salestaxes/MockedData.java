package com.spoonman01.salestaxes;

import java.util.List;

public class MockedData {

    // INPUT

    public static List<String> mockedInputNoImport(){
        return List.of("2 book at 12.49",
                "1 music CD at 14.99",
                "1 chocolate bar at 0.85");
    }

    public static List<String> mockedInputAllImport(){
        return List.of("1 imported box of chocolates at 10.00",
                "1 imported bottle of perfume at 47.50");
    }

    public static List<String> mockedInputMixedImport(){
        return List.of("1 imported bottle of perfume at 27.99",
                "1 bottle of perfume at 18.99",
                "1 packet of headache pills at 9.75",
                "3 imported box of chocolates at 11.25");
    }

    // OUTPUT

    public static String mockedOutputNoImport(){
        return "2 book: 24.98\n" +
                "1 music CD: 16.49\n" +
                "1 chocolate bar: 0.85\n" +
                "Sales Taxes: 1.50\n" +
                "Total: 42.32";
    }

    public static String mockedOutputAllImport(){
        return "1 imported box of chocolates: 10.50\n" +
                "1 imported bottle of perfume: 54.65\n" +
                "Sales Taxes: 7.65\n" +
                "Total: 65.15";
    }

    public static String mockedOutputMixedImport(){
        return "1 imported bottle of perfume: 32.19\n" +
                "1 bottle of perfume: 20.89\n" +
                "1 packet of headache pills: 9.75\n" +
                "3 imported box of chocolates: 35.55\n" +
                "Sales Taxes: 7.90\n" +
                "Total: 98.38";
    }
}
