package diaz.abraham.Editor;

import javax.swing.*;

public class verNumeracion {
    public static void Numeracion(Boolean numeros, JTextPane areatexto, JScrollPane areaScroll){
        if(numeros){
            areaScroll.setRowHeaderView(new TextLineNumber(areatexto));
        }else{
            areaScroll.setRowHeaderView(null);
        }
    }
}
