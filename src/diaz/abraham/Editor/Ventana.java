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
        //--------------------menu -----
        JPanel panelMenu = new JPanel();
        menu = new JMenuBar();
        archivo = new JMenu("Archivo");
        editar = new JMenu("Editar");
        seleccion = new JMenu("Seleccion");
        ver = new JMenu("Ver");
        apariencia = new JMenu("Apariencia");
        // creamos las opciones del menu
        menu.add(archivo);
        menu.add(editar);
        menu.add(seleccion);
        menu.add(ver);
        menu.add(apariencia);

        JMenuItem nuevo = new JMenuItem("Nuevo Archivo");
        JMenuItem abrir = new JMenuItem("Abrir Archivo");
        JMenuItem guardar = new JMenuItem("Guardar");
        JMenuItem guardarC = new JMenuItem("Guardar Como");

        archivo.add(nuevo);
        archivo.add(abrir);
        archivo.add(guardar);
        archivo.add(guardarC);

        panelMenu.add(menu);
        //--------------------area de texto------------------------
        tPane = new JTabbedPane();
        //--------------------añadimos los objetos a la vista del programa----------------
        creaPanel();
        add(panelMenu);
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
    private JMenuBar menu;
    private JMenu archivo, editar, seleccion, ver, apariencia;
}
