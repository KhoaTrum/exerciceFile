package app;

import java.util.ArrayList;

//Classe présente l'objet du fichier json en sortie
public class JasonFileObject {
    String inputFile;
    ArrayList<LineObject> references;
    ArrayList<ErrorLineObject> errors;

    public JasonFileObject(String file,  ArrayList<LineObject> lineObjectList, ArrayList<ErrorLineObject> errorLineObjectList){
        inputFile = file;
        references = lineObjectList;
        errors = errorLineObjectList;
    }
}
