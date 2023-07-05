package marcosmendes;

import marcosmendes.entities.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        List<Product> productsList = new ArrayList<>();

        System.out.println("Enter source file path: ");
        String sourceFilePath = scanner.nextLine();

        File sourceFile = new File(sourceFilePath);
        String sourceFolderPath = sourceFile.getParent();

        new File(sourceFolderPath + "/out");
        String outputFilePath = sourceFolderPath + "/out/summary.csv";

        try (BufferedReader bufferReader = new BufferedReader(new FileReader(sourceFilePath))) {
            String csvItem = bufferReader.readLine();
            while (csvItem != null) {
                String[] fields = csvItem.split(",");

                String name = fields[0];
                double price = Double.parseDouble(fields[1]);
                int quantity = Integer.parseInt(fields[2]);

                productsList.add(new Product(name, price, quantity));
                csvItem = bufferReader.readLine();
            }

            generateOutputFile(outputFilePath, productsList);
        } catch(IOException error) {
            System.out.println("Error writing file: " + error.getMessage());
        }

        scanner.close();
    }

    private static void generateOutputFile(String outputFilePath, List<Product> productsList) {
        try (BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(outputFilePath))) {
            for (Product item: productsList) {
                String productName = item.getName();
                double totalPrice = item.totalPrice();

                bufferWriter.write(productName + "," + totalPrice);
                bufferWriter.newLine();
            }

            System.out.println(outputFilePath + " has been created with success!");
        } catch(IOException error) {
            System.out.println("Error writing file: " + error.getMessage());
        }
    }
}