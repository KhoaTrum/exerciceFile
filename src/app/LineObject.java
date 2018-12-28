package app;

//Objet présente une bonne ligne sans erreur avec les valeurs importantes : numéro de référence, couleur et prix, taille
public class LineObject {
    String numReference;
    String color;
    String price;
    String size;

    public LineObject() {
        numReference = null;
        color = null;
        price = null;
        size = null;
    }

    public LineObject (String colorIn, String priceIn, String sizeIn, String numReferenceIn) {
        numReference = numReferenceIn;
        color = colorIn;
        price = priceIn;
        size = sizeIn;
    }
}
