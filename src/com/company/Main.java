package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

// hash passwords
//fix booksDetailsFile function
public class Main {
    public static void main(String[] args) {
        boolean checker = false;
        while (!checker) {
            File userFile = new File("userInfo.txt");
            createFile(userFile);
            String usertype = getInput("Press L to login or press R to register");
            if (usertype.equalsIgnoreCase("L")) {
                String userInfo = getCredentials();
                String[] splitInfo = userInfo.split("---");
                checker = loginChecker(splitInfo[0], splitInfo[1]);
            } else if (usertype.equalsIgnoreCase("R")) {
                String userInfo = getCredentials();
                String[] splitInfo = userInfo.split("---");
                boolean used = UsernameInUse(splitInfo[1]);
                if (!used) {
                    System.out.println("User registered");
                    fileWriter(userFile, userInfo);
                    checker = true;
                }
            } else {
                System.out.println("Invalid input");
            }
        }
        System.out.println("You are logged in");
        while (true) {
            File booksFile = new File("Books.txt");
            createFile(booksFile);
            String function = getInput("input \"!end\" at anytime to end program, press \"S\" to search for a book, \"I\" to insert a book\n" +
                    "input \"B\" to borrow a book");
            if (function.equalsIgnoreCase("I")) {
                fileWriter(booksFile, bookDetailsUSER());
            } else if (function.equalsIgnoreCase("S")) {
                int position = searchDetails();
                String info = getInput("Insert the info of the book that you want to find");
                ArrayList<String> booksFound = bookDetailsFile(booksFile, position, info);
                for (int i = 0; i < booksFound.size(); i++) {
                    System.out.println(booksFound.get(i));
                }
            } else if (function.equalsIgnoreCase("B")) {
                String bookInfo = bookDetailsUSER();
            } else {
                System.out.println("Invalid Input");

            }
        }
    }

    public static void deleteBook(File file,String book){
        File tempFile= new File("tempFile.txt");
        createFile(tempFile);
        try{
        Scanner books= new Scanner(file);
        while(books.hasNextLine()){
            String tempBook =books.nextLine();
            if(!book.equals(tempBook)){
            fileWriter(tempFile,book);}
        }
        tempFile.renameTo(file);
        file.delete();
        }catch(FileNotFoundException e){
            System.out.println("Error: "+e);
        }
    }

    public static String borrow(File file, String book) {
        try {
            String new_info="";
            Scanner books = new Scanner(file);
            while (books.hasNext()) {
                String current_book=books.nextLine();
                if (current_book.equalsIgnoreCase(book)) {
                    String[] bookInfo=current_book.split("---");
                    for (int i = 0; i < bookInfo.length-1; i++) {
                        new_info=new_info+bookInfo[i]+"---";
                    }
                    new_info=new_info+"borrowed";
                    deleteBook(file,book);
                    fileWriter(file,new_info);
                    //edit book in file to say borrowed by making a general book editor
                    return "book borrowed";
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e);
        }

        return "book not found/book already borrowed";
    }


    public static void createFile(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Error: " + e);
                System.exit(0);
            }
        }
    }

    public static Integer searchDetails() {
        String input;
        while (true) {
            input = getInput("Insert search method(ISBN;Book,Author,Genre)");
            if ((input.equalsIgnoreCase("ISBN"))) {
                return 1;
            } else if ((input.equalsIgnoreCase("Book"))) {
                return 0;
            } else if ((input.equalsIgnoreCase("Author"))) {
                return 2;
            } else if ((input.equalsIgnoreCase("Genre"))) {
                return 3;
            }
            System.out.println("Not a valid search method");
        }
    }

    public static ArrayList bookDetailsFile(File file, Integer position, String input) {
        ArrayList<String> booksFound = new ArrayList<>();
        try{
        Scanner books = new Scanner(file);
        books.next();
        while (books.hasNextLine()) {
            String[] bookInfo = books.nextLine().split("---");
            if (bookInfo[position].equalsIgnoreCase(input)) {
                String book = bookInfo[0];
                ;
                for (int i = 1; i < bookInfo.length - 1; i++) {
                    book = book + ", " + bookInfo[i];
                }
                booksFound.add(book);
            }
        }
        if (booksFound.isEmpty()) {
            booksFound.add("no books found");
        }
        return booksFound;}catch(FileNotFoundException e){
            System.out.println("Error: "+e);
        }
        return booksFound;
    }

    public static String bookDetailsUSER() {
        String inputs = "";
        inputs += getInput("Insert book name: ");
        inputs += "---" + getInput("Insert book ISBN: ");
        inputs += "---" + getInput("Insert book Author: ");
        inputs += "---" + getInput("Insert book Genre: ");
        return inputs + "---Not Borrowed";
    }

    public static String getInput(String prompt) {
        System.out.println(prompt);
        Scanner input = new Scanner(System.in);
        String Input = input.nextLine();
        if (Input.equalsIgnoreCase("!end")) {
            System.exit(0);
        }
        return Input;
    }

    public static void fileWriter(File file, String input) {
        try {
            FileWriter myWriter = new FileWriter(file.getName(), true);
            myWriter.write(input + "\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
    }

    public static String getCredentials() {
        try {
            while (true) {
                String username = getInput("Insert username(email): ");
                if (username.contains("@")) {
                    if (!username.contains("-")) {
                        String password = getInput("Insert password: ");
                        if (!password.contains("-")) {
                            return username + "---" + password;
                        }
                    }
                    System.out.println("Passwords\\Usernames can't have the character \"-\"");
                } else {
                    System.out.println("Not a valid username");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return "null(-)---null(-)";
        }
    }

    public static boolean UsernameInUse(String username) {
        Scanner scanner = new Scanner("userInfo.txt");
        try {
            String[] fileInfo = scanner.next().split("---");
            for (int i = 0; i < fileInfo.length; i += 2) {
                if (fileInfo[i].equalsIgnoreCase(username)) {
                    System.out.println("username already in use");
                    return true;
                }
            }
            return false;
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    public static boolean loginChecker(String username, String password) {
        File userFile = new File("userinfo.txt");
        try {
            userFile.createNewFile();
            Scanner scanner = new Scanner(userFile);
            String[] fileInfo = scanner.next().split("---");
            for (int i = 0; i < fileInfo.length; i += 2) {
                if (fileInfo[i].equals(username) && fileInfo[i + 1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        } catch (NoSuchElementException e) {
            System.out.println("username-password combination not found");
            return false;
        }
        System.out.println("username-password combination not found");
        return false;
    }
}