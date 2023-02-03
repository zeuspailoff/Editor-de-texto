package diaz.abraham.Editor;

import diaz.abraham.Editor.Utilidades.ImgFondo;

import javax.swing.*;
import java.awt.*;




//-------------------- creamos la ventana del programa
public class Ventana extends JFrame {
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit()
                .getImage(ClassLoader.getSystemResource("diaz/abraham/Img/icons8-electricity-hazard-48.png"));

        return retValue;
    }


    public Ventana() {



        setBounds(300,300,600,600);
        setTitle("ThunderEdit");
        add(new Panel(this));
        setIconImage(getIconImage());


    }
}

