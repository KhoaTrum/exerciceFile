package app;

//Objet présente une mauvaise ligne avec le message d'erreur
public class ErrorLineObject {
    String line; //Numéro de la ligne
    String value; //Valeur de la ligne
    String message; //Mesage d'erreur

    public ErrorLineObject(String numLineIn, String lineValueIn, String errorIn) {
        line = numLineIn;
        value = lineValueIn;
        message = errorIn;
    }
}
