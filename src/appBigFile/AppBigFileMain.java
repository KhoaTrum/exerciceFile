package appBigFile;
//Packages à importer afin d'utiliser les objets

import app.ErrorLineObject;
import app.LineObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AppBigFileMain {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        //Saisir le fichier d'entrée
        System.out.print("Veuillez saisir le chemin du fichier en entrée et enter : ");
        String inputFilePathName = sc.nextLine();
        File file = new File (inputFilePathName);

        while (!file.isFile()) {
            System.out.print("Fichier n'existe pas, ressayez le chemin du fichier en entrée : ");
            inputFilePathName = sc.nextLine();
            file = new File (inputFilePathName);
        }

        //Saisir le type du fichier de sortie
        System.out.print("Veuillez saisir le type du fichier en sortie (xml/json) : ");
        String outFileType = sc.nextLine();
        while(!outFileType.equals("xml") && !outFileType.equals("json")) {
            System.out.print("Veuillez saisir xml ou json : ");
            outFileType = sc.nextLine();
        }

        //Saisir le fichier d'entrée
        System.out.print("Veuillez saisir le chemin du fichier en sortie et enter : ");
        String outFilePathName = sc.nextLine();

        BufferedReader buffin = null; //Initialiser le buffered pour le lecture

        String line = null;
        int numLine = 0;

        ArrayList<LineObject> lineObjectList = new ArrayList<LineObject>();
        ArrayList<ErrorLineObject> errorLineObjectList = new ArrayList<ErrorLineObject>();

        String referencesListString;
        String errorsListString;

        try {
            buffin = new BufferedReader (new FileReader(file));
            while((line = buffin.readLine()) != null) {
                String[] elementList = line.split(";");

                int numElement = 0;
                String messError = null;

                for (String element:elementList) {
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
        } catch (IOException e) {
            System.out.println("Il y a un problème du lecture du fichier en entrée");
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    //Ajouter le nouvel element d'erreur au message d'erreur
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
        if (!"R".equals(element) && !"G".equals(element) && !"B".equals(element)) {
            return false;
        }
        return true;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
//Vérifier le numéro Référence
    private static boolean checkNotNumReferenceErr (String element) {
        try {
            Integer.parseInt(element); //Vérifier si c'est bien un numéro entier
        } catch (NumberFormatException e) {
            return false;
        }
        return element.length()==10; //Vérifier si c'est un numéro de 10 chiffres
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