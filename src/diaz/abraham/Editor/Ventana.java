package diaz.abraham.Editor;

import javax.swing.*;
//-------------------- creamos la ventana del programa
public class Ventana extends JFrame {
    public Ventana() {
        setBounds(300,300,600,600);
        setTitle("ThunderEdit");
        add(new Panel());
    }
}

class Panel extends JPanel {
    public Panel(){
        //--------------------area de texto------------------------
        tPane = new JTabbedPane();
        //-------------------- fin de area de texto----------------
        creaPanel();
        add(tPane);
    }
    //-------------------- creamos las ventanas de texto------------------------
    public void creaPanel(){
        ventanaText = new JPanel();
        areaText = new JTextPane();

        ventanaText.add(areaText);
        tPane.addTab("nueva ventana",ventanaText);
    }
    private JTabbedPane tPane;
    private  JPanel ventanaText;
    private JTextPane areaText;
    //-------------------- fin de la creacion ventanas de texto----------------
}
