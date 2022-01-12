package com.company;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
//change get credentials to output an array size 2
// check the username is an email
public class Main {
    public static void main(String[] args) {
        boolean checker= false;
        while(!checker){
            String usertype=getInput("Press L to login or press R to register");
            if(usertype.equalsIgnoreCase("L")){
                String userInfo=getcredentials();
                String[] splitInfo=userInfo.split(",");
                checker=loginChecker(splitInfo[0],splitInfo[1]);

                /////make login checker;
            }
            else if(usertype.equalsIgnoreCase("R")){
                String userInfo=getcredentials();
                String[] splitInfo=userInfo.split(",");
                checker=registerChecker(splitInfo[1]);
                if(checker){
                    insertInfo(userInfo);
                }
            }
        }

        while(true){
            File booksFile = new File("Books.txt");
            String function=getInput("input \"!end\" at anytime to end program, press \"S\" to search for a book and \"I\" to insert a book");
            if(function.equalsIgnoreCase("I")){
                filewriter(booksFile,BookdetailsUSER());
            }else if(function.equalsIgnoreCase("S")){
                int position=searchDetails();
                String info=getInput("Insert the info of the book that you want to find");
                ArrayList<String> details=bookdetailsFile(booksFile,position,info);
            }else{
                System.out.println("Invalid Input");

            }
        }
    }
    public static Integer searchDetails(){
        String input="";
        while(true){
        input=getInput("Insert search method(ISBN;Book,Author,Genre)");
        if((input.equalsIgnoreCase("ISBN"))){
            return 1 ;
        }else if((input.equalsIgnoreCase("Book"))){
            return 0;
        }else if((input.equalsIgnoreCase("Author"))){
            return 2;
        }else if((input.equalsIgnoreCase("Genre"))){
            return 3;
        }
            System.out.println("Not a valid search method");
        }
    }
    public static ArrayList bookdetailsFile(File file,Integer position, String input){
        Scanner books=new Scanner(file.getName());
        ArrayList<String> booksFound= new ArrayList<>();
        while(books.hasNextLine()){
            String[] bookInfo=books.nextLine().split(",");
            if(bookInfo[position].equalsIgnoreCase(input)){
                booksFound.add(bookInfo.toString());

            }
        }
        if(booksFound.isEmpty()){
            booksFound.add("book not found");}
        return booksFound;
    }
    public static String BookdetailsUSER(){
        String inputs="";
        inputs+=getInput("Insert book name: ");
        inputs+=", "+getInput("Insert book ISBN: ");
        inputs+=", "+getInput("Insert book Author: ");
        inputs+=", "+getInput("Insert book Genre: ");
        return inputs;
    }
    public static Boolean anotherinput(){
        Scanner input= new Scanner(System.in);
        String answer = input.next();
        return answer.toLowerCase(Locale.ROOT).charAt(0) == 'y';
    }
    public static String getInput(String prompt){
        System.out.println(prompt);
        Scanner input= new Scanner(System.in);
        String Input=input.nextLine();
        if(Input.equalsIgnoreCase("!end")){
            System.exit(0);
        }
        return Input;
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
    public static String getcredentials(){
        boolean checker=false;
        String username="";
        String password;
        try{
            while(!checker){
            username=getInput("Insert username(email): ");
            if(username.contains("@")){
                checker=true;
            }else{
                System.out.println("Not a valid username");
            }
            }
            password=getInput("Insert password: ");
        }catch(Exception e){
            System.out.println("Error: "+e);
            return"";
        }
        return username+","+password;
    }
    public static boolean registerChecker(String username){
        File userFile= new File("userInfo.txt");
        try {
            userFile.createNewFile();
            Scanner scanner=new Scanner(userFile);
            String[] fileInfo= scanner.next().split(",");
            for (int i = 0; i < fileInfo.length; i+=2) {
                if(fileInfo[i].equalsIgnoreCase(username)){
                    System.out.println("username already in use");
                    return false;
                }
            }
            return true;
        }catch(IOException e){
            System.out.println("Error: "+e);
            return false;
        }catch(NoSuchElementException e){
            return true;
        }
    }
    public static void insertInfo(String userinfo){
        File userFile= new File("userinfo.txt");
        try{
            userFile.createNewFile();
            FileWriter file=new FileWriter(userFile.getName(),true);
            file.write(userinfo);
            file.close();
        }catch(IOException e){
            System.out.println("Error: "+e);
        }

    }
    public static boolean loginChecker(String username, String password){
        File userFile= new File("userinfo.txt");
        try{
            userFile.createNewFile();
            Scanner scanner=new Scanner(userFile);
            String[] fileInfo= scanner.next().split(",");
            for (int i = 0; i < fileInfo.length ; i+=2) {
                if(fileInfo[i].equals(username)&&fileInfo[i+1].equals(password)){
                    return true;
                }
            }
        }catch(IOException e){
            System.out.println("Error: "+e);
        }
        catch(NoSuchElementException e){
            System.out.println("username-password combination not found");
            return false;
        }
        System.out.println("username-password combination not found");
        return false;
    }
}