package diaz.abraham.Editor.Utilidades;

import javax.swing.*;
import java.util.ArrayList;

public class verNumeracion {
    public static void Numeracion(Boolean numeros, JTextPane areatexto, JScrollPane areaScroll){
        if(numeros){
            areaScroll.setRowHeaderView(new TextLineNumber(areatexto));
        }else{
            areaScroll.setRowHeaderView(null);
        }
    }

    public  static void viewNumeracion(int contador, Boolean numeros, ArrayList<JTextPane> textArea, ArrayList<JScrollPane> Scroll){
        if(numeros){
            for(int i = 0; i < contador; i++){
                Scroll.get(i).setRowHeaderView(new TextLineNumber(textArea.get(i)));
            }
        }else{
            for(int i = 0 ; i< contador; i++){
                Scroll.get(i).setRowHeaderView(null);
            }
        }
    }
}
