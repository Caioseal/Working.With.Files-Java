package application;

import entities.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US); //Define the american standard to use 'dot' instead of 'coma'
        Scanner scanner = new Scanner(System.in);

        List<Product> productsList = new ArrayList<>();

        System.out.println("Enter a file path: ");
        String sourceFileString = scanner.nextLine(); //Receives the path of the document

        File sourceFile = new File(sourceFileString); //Create an object of the class File receiving the path
        String sourceFolderString = sourceFile.getParent();

        boolean success = new File(sourceFolderString + "\\out").mkdir();
        if (success) {
            System.out.println("Document created successfully");
        }

        String targetPath = sourceFolderString + "\\out\\summary.csv";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(sourceFileString))) { //Create a bufferReader receiving a File reader receiving the document's path
            String line = bufferedReader.readLine(); //Read each line of the document

            while (line != null) {
                String[] fields = line.split(","); //Create a list separating by coma
                String name = fields[0];
                double price = Double.parseDouble(fields[1]); //The list has strings. It's necessary to convert to double
                int quantity = Integer.parseInt(fields[2]);

                productsList.add(new Product(name, price, quantity)); //Add the new Product to a list.

                line = bufferedReader.readLine(); //Read the next line of the document
            }

            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(targetPath))){
                for (Product product: productsList) {
                    bufferedWriter.write(product.getName() + "," + String.format("%.2f", product.total()));
                    bufferedWriter.newLine();
                }
                System.out.println(targetPath + "CREATED!");
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        scanner.close();
    }
}