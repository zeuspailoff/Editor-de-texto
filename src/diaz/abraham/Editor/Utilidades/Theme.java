package diaz.abraham.Editor.Utilidades;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.ArrayList;

public class Theme {
    public static void fondo(int contador, String tipo, ArrayList<JTextPane> list){
        if(tipo.equals("N")){
            for(int i = 0; i < contador; i++){

                list.get(i).selectAll();
                StyleContext sc = StyleContext.getDefaultStyleContext();
                    //-------------------- color texto
                AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.BLACK);
                //-------------------- TIPO DE TEXTO --------------------
                aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Arial");

                list.get(i).setCharacterAttributes(aset,false);
                list.get(i).setBackground(Color.WHITE);
            }
        }else if(tipo.equals("D")){
            for(int i = 0; i < contador; i++){
                list.get(i).selectAll();
                StyleContext sc = StyleContext.getDefaultStyleContext();
                //-------------------- color texto
                AttributeSet aset = sc.addAttribute(
                        SimpleAttributeSet.EMPTY,
                        StyleConstants.Foreground,
                        Color.WHITE);
                //-------------------- TIPO DE TEXTO --------------------
                aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Arial");

                list.get(i).setCharacterAttributes(aset,false);
                list.get(i).setBackground(new Color(32,33,36));
            }
        }

    }
}
