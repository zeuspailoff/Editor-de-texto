package diaz.abraham.Editor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

//-------------------- creamos la ventana del programa
public class Ventana extends JFrame {
    public Ventana() {
        setBounds(300,300,600,600);
        setTitle("ThunderEdit");
        add(new Panel());
    }
}

