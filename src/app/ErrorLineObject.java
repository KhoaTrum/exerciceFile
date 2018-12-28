package app;

//Objet présente une mauvaise ligne avec le message d'erreur
public class ErrorLineObject {
    String numLine;
    String line;
    String messError;

    public ErrorLineObject() {
        numLine = null;
        line = null;
        messError = null;
    }

    public ErrorLineObject(String numLineIn, String lineIn, String errorIn) {
        numLine = numLineIn;
        line = lineIn;
        messError = errorIn;
    }
}
