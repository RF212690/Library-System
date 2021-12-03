package com.company;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        File booksFile = new File("Books.txt");
        ArrayList<String> books= new ArrayList<>();
        removecontent(booksFile);
        System.out.println("Do you have any books to input? Y or N");
        boolean anotherBook =anotherbook();
        while(anotherBook){
            books.add(Bookdetails());
            System.out.println("Do you have any other books to input? Y or N");
            anotherBook =anotherbook();
        }
        for (String book : books) {
            filewriter(booksFile, book);
        }
        System.out.println("Your books have been stored");

    }
    public static String Bookdetails(){
        String inputs="";
        inputs+=getInput("Insert book name: ");
        inputs+=", "+getInput("Insert book ISBN: ");
        inputs+=", "+getInput("Insert book Author: ");
        inputs+=", "+getInput("Insert book Genre: ");
        return inputs;
    }
    public static Boolean anotherbook(){
        Scanner input= new Scanner(System.in);
        String answer = input.next();
        return answer.toLowerCase(Locale.ROOT).charAt(0) == 'y';
    }
    public static String getInput(String prompt){
        System.out.println(prompt);
        Scanner input= new Scanner(System.in);
        return input.nextLine();
    }
    public static void removecontent(File file){
        try{
            FileWriter myWriter= new FileWriter(file.getName(),false);
            myWriter.write("");
            myWriter.close();
        }catch(IOException e){
            System.out.println("An error occurred: "+e);
        }
    }
    public static void filewriter(File file,String input){
        try{
            FileWriter myWriter= new FileWriter(file.getName(),true);
            myWriter.write(input+"\n");
            myWriter.close();
        }catch(IOException e){
            System.out.println("An error occurred: "+e);
        }
    }
}