package diaz.abraham.Editor;

import javax.swing.*;
import java.awt.*;




//-------------------- creamos la ventana del programa
public class Ventana extends JFrame {
    @Override
    public Image getIconImage() {

        return Toolkit.getDefaultToolkit()
                .getImage(ClassLoader.getSystemResource("diaz/abraham/Img/icons8-electricity-hazard-48.png"));
    }


    public Ventana() {



        setBounds(300,300,600,600);
        setTitle("ThunderEdit");
        add(new Panel(this));
        setIconImage(getIconImage());


    }
}

