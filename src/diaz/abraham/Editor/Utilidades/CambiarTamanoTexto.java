package diaz.abraham.Editor.Utilidades;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.util.ArrayList;

public class CambiarTamanoTexto {
    public static void tamanoTexto(int tamano, int contador, ArrayList<JTextPane> list){
        for(int i = 0; i < contador; i++){
            //Seleccionamos todo el texto
            list.get(i).selectAll();

            StyleContext sc = StyleContext.getDefaultStyleContext();
            //-------------------- color texto cambiamos tamaño texto --------------------
            AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.FontSize, tamano);
            //-------------------- lo aplicamos al area de texto ------------------------
            list.get(i).setCharacterAttributes(aset, false);

        }
    }
}
