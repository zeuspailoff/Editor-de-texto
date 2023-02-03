package diaz.abraham.Editor.Utilidades;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ToolBar {
    //-------------------- preparamos los botones de la barra de herramientas --------------------
    public static JButton addButton(URL url, Object objContenedor, String rotulo) {
        JButton button = new JButton(new ImageIcon(new ImageIcon(url)
                .getImage()
                .getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        button.setToolTipText(rotulo);
        ((Container) objContenedor).add(button);

        return button;

    }
}
