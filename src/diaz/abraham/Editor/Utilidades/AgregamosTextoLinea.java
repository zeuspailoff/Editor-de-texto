package diaz.abraham.Editor.Utilidades;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class AgregamosTextoLinea {
    public static void append(String linea, JTextPane areatexto){
        try{
            Document doc = areatexto.getDocument();
            doc.insertString(doc.getLength(),linea, null);
        }catch (BadLocationException exc){
            exc.printStackTrace();
        }
    }
}
