package app;

import java.io.*;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.google.gson.Gson;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


//Classe service est pour écrire le fichier en sortie selon le type du fichier
public class WriteFileService {
    public static void write (String type, String path, ArrayList<LineObject> lineObjectList, ArrayList<ErrorLineObject> errorLineObjectList, String inputFilePathName) {
        if(type.equals("xml")) {
            writeXml(path, lineObjectList, errorLineObjectList, inputFilePathName);
        }

        if(type.equals("json")) {
            writeJson(path, lineObjectList, errorLineObjectList, inputFilePathName);
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////:
    //Ecrire xml avec la méthode "DOM parser"
    private static void writeXml(String path, ArrayList<LineObject> lineObjectList, ArrayList<ErrorLineObject> errorLineObjectList, String inputFilePathName) {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;

        try {
            //Créer un objet document xml
            documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            //premier element "report" = root
            Element report = document.createElement("report");
            document.appendChild(report); //Report est la 1ère noeud du document

            //Element "inputFile"
            Element inputFile = document.createElement("inputFile");
            inputFile.appendChild(document.createTextNode(inputFilePathName)); //Set texte au "inputFile"
            report.appendChild(inputFile); //"report" est la mère du "inputFile"

            //Element "references" au dessous du "report"
            Element references = document.createElement("references");
            report.appendChild(references);

            //Element "errors" au dessous du "report"
            Element errors = document.createElement("errors");
            report.appendChild(errors);

            //Créer des éléments "reference" au-dessous du "references"
            for(int i=0; i<lineObjectList.size(); i++) {
                LineObject line = lineObjectList.get(i);
                //Créer un élement "reference"
                Element reference = document.createElement("reference");
                reference.setAttribute("color", line.color); //attribut color
                reference.setAttribute("price", line.price); //attribut price
                reference.setAttribute("size", line.size); 	 //attribut size
                reference.setAttribute("numReference", line.numReference); //attribut numReference
                references.appendChild(reference);
            }

            //Créer les éléments "error" au-dessous du "errors"
            for(int i=0; i<errorLineObjectList.size(); i++) {
                ErrorLineObject errorLine = errorLineObjectList.get(i);
                //Créer un élement "error"
                Element error = document.createElement("error");
                error.setAttribute("line", errorLine.line); //attribut line
                error.setAttribute("message", errorLine.message); //attribut message
                error.appendChild(document.createTextNode(errorLine.value)); // Texte = ligne
                errors.appendChild(error);
            }

            //Créer le fichier xml en sortie
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer(); //Créer un objet transformer
            DOMSource domSource = new DOMSource(document); //Source DOM pour la transformation xml
            StreamResult streamResult = new StreamResult(new File(path)); //Steam en sortie pour la transformation

            transformer.transform(domSource, streamResult); //transformer l'objet DOM à un fichier xml

            System.out.println("Fichier xml en sortie est créé");

        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TransformerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    //Ecrire le fichier json en sortie
    private static void writeJson(String path, ArrayList<LineObject> lineObjectList, ArrayList<ErrorLineObject> errorLineObjectList, String inputFileName) {
        Gson gson = new Gson();

        try {
            BufferedWriter buffout = new BufferedWriter(new FileWriter(new File (path)));
            JasonFileObject jsFileObject = new JasonFileObject(inputFileName,lineObjectList,errorLineObjectList);
            buffout.write(gson.toJson(jsFileObject));
            buffout.close();
            System.out.println("Fichier json en sortie est créé");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
