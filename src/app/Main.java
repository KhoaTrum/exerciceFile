package app;
//Packages à importer afin d'utiliser les objets

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
/*
      //Saisir le fichier d'entrée
      System.out.print("Veuillez saisir le chemin du fichier en entrée et enter : ");
      String fileName = sc.nextLine();
      File file = new File (fileName);

      while (!file.isFile()) {
    	  System.out.print("Fichier n'existe pas, ressayez le chemin du fichier en entrée : ");
    	  fileName = sc.nextLine();
    	  file = new File (fileName);
      }

      //Saisir le type du fichier de sortie
	  System.out.print("Veuillez saisir le type du fichier en sortie (xml/json) : ");
	  String outFileType = sc.nextLine();
      while(!outFileType.equals("xml") && !outFileType.equals("json")) {
    	  System.out.print("Veuillez saisir xml ou json : ");
    	  outFileType = sc.nextLine();
      }
  */
        String inputFilePathName = "C:\\Users\\DKP\\Desktop\\TestKhoa.txt";
        String outFilePathName = "C:\\Users\\DKP\\Desktop\\TestKhoa.xml";
        File file = new File (inputFilePathName);
        String outFileType = "xml";

        BufferedReader buffin = null;
        boolean readOk = true;

        String line = null;
        int numLine = 0;
        ArrayList<LineObject> lineObjectList = new ArrayList<LineObject>();
        ArrayList<ErrorLineObject> errorLineObjectList = new ArrayList<ErrorLineObject>();

        try {
            buffin = new BufferedReader (new FileReader(file));
            while((line = buffin.readLine()) != null) {
                String[] elementList = line.split(";");

                int numElement = 0;
                String messError = null;

                for (String element:elementList) {
                    //System.out.println(element);
                    if(!checkNotElementErr(numElement, element)){
                        messError = createMessError(numElement, messError);
                    }
                    numElement++;
                }
                if(messError==null) {
                    LineObject lineObject = new LineObject(elementList[1], elementList[2], elementList[3], elementList[0]);
                    lineObjectList.add(lineObject);
                }else {
                    ErrorLineObject errorLineObject = new ErrorLineObject(String.valueOf(numLine), line, messError);
                    errorLineObjectList.add(errorLineObject);
                }
                numLine++;
            }

        } catch (FileNotFoundException e) {
            System.out.println("Le fichier en entrée n'est pas trouvé");
            readOk = false;
        } catch (IOException e) {
            System.out.println("Il y a un problème du lecture du fichier en entrée");
            readOk = false;
        }

        if(readOk) {
            WriteFileService.write(outFileType, outFilePathName, lineObjectList, errorLineObjectList, inputFilePathName);
        }

    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static String createMessError(int numElement, String messError) {
        String typeElement = null;
        String newMessError;
        switch(numElement) {
            case 0 : typeElement = "numReference";
                break;
            case 1 : typeElement = "color";
                break;
            case 2 : typeElement = "price";
                break;
            case 3 : typeElement = "size";
                break;
        }
        if(messError == null) {
            newMessError = "Incorrect value for " + typeElement;
        }else {
            newMessError = messError + ", " + typeElement;
        }
        return newMessError;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
//Vérifier si l'élément d'une ligne n'a pas d'erreur.
//Paramètres d'entrée = [le numéro d'ordre de l'élément, l'élement]
    private static boolean checkNotElementErr(int num, String element) {
        boolean hasNotError;

        switch(num) {
            case 0 : hasNotError = checkNotNumReferenceErr(element);
                break;
            case 1 : hasNotError = checkNotColorErr(element);
                break;
            case 2 : hasNotError = checkNotPriceErr(element);
                break;
            case 3 : hasNotError = checkNotSizeErr(element);
                break;
            default: hasNotError = true;
                break;
        }
        return hasNotError;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
//Vérifier la couleur
    private static boolean checkNotColorErr(String element) {
        if (!element.equals("R") && !element.equals("G") && !element.equals("B")) {
            return false;
        }
        return true;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
//Vérifier le numéro Référence
    private static boolean checkNotNumReferenceErr (String element) {
        if (element.length()!=10) { //Ce n'est pas un numéro de 10 chiffres
            return false;
        }

        try {
            Integer.parseInt(element); //Vérifier si c'est bien un numéro entier
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
//Vérifier la taille
    private static boolean checkNotSizeErr (String element) {
        try {
            Integer.parseInt(element); //Vérifier si c'est bien un numéro entier
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
//Vérifier le prix
    private static boolean checkNotPriceErr(String element) {
        try {
            Float.parseFloat(element); //Vérifier si c'est bien un numéro float
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}


