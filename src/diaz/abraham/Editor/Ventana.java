package diaz.abraham.Editor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

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

        //--------------------elementos menu archivo
        agregaItem("Nuevo Archivo","archivo","nuevo");
        agregaItem("Abrir Archivo","archivo", "abrir");
        agregaItem("Guardar", "archivo", "guardar");
        agregaItem("Guardar Como","archivo", "guardarComo");
        //--------------------elementos menu editar
        agregaItem("Eliminar", "editar","" );
        agregaItem("Atras", "editar", "");
        editar.addSeparator();
        agregaItem("Cortar", "editar", "");
        agregaItem("Pegar", "editar", "");
        agregaItem("Copiar", "editar", "");
        //-------------------- elementos menu seleccion
        agregaItem("Seleccionar Todo","seleccion", "");
        //--------------------elementos menu ver
        agregaItem("Numeracion ", "ver", "");
        ver.add(apariencia);
        agregaItem("Normal","apariencia", "");
        agregaItem("Dark","apariencia", "");


        panelMenu.add(menu);
        //--------------------area de texto------------------------
        tPane = new JTabbedPane();
        listFile = new ArrayList<File>();
        listAreaTexto = new ArrayList<JTextPane>();
        listAreaScroll = new ArrayList<JScrollPane>();
        //--------------------añadimos los objetos a la vista del programa----------------

        add(panelMenu);
        add(tPane);
    }
    //--------------------agregamos las opciones del menu
    public void agregaItem(String rotulo, String menu, String accion){
        elementosMenu = new JMenuItem(rotulo);
        switch (menu) {
            case "archivo" -> {
                archivo.add(elementosMenu);
                switch (accion) {
                    case "nuevo" -> elementosMenu.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            creaPanel();
                        }
                    });
                    case "abrir" -> elementosMenu.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {

                        }
                    });
                    case "guardar" -> elementosMenu.addActionListener(e ->new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {

                        }
                    });
                    case "guardarComo" -> elementosMenu.addActionListener(e -> new ActionListener(){

                        @Override
                        public void actionPerformed(ActionEvent e) {

                        }
                    });
                }
            }
            case "editar" -> editar.add(elementosMenu);
            case "seleccion" -> seleccion.add(elementosMenu);
            case "ver" -> ver.add(elementosMenu);
            case "apariencia" -> apariencia.add(elementosMenu);
        }

    }
    //-------------------- creamos las ventanas de texto------------------------
    public void creaPanel(){


        ventanaText = new JPanel();

        listFile.add(new File(""));
        listAreaTexto.add(new JTextPane());
        listAreaScroll.add(new JScrollPane(listAreaTexto.get(contadorVentana)));


        ventanaText.add(listAreaScroll.get(contadorVentana));
        tPane.addTab("nueva ventana",ventanaText);
        contadorVentana++;
        existeVentana = true;
    }
    private int contadorVentana = 0;//nos cuenta las ventanas de texto
    private boolean existeVentana = false;// comprobamos si exiten ventanas creadas
    private JTabbedPane tPane;
    private  JPanel ventanaText;
    //private JTextPane areaText;
    private ArrayList<JTextPane> listAreaTexto;
    private ArrayList<JScrollPane> listAreaScroll;
    private ArrayList<File> listFile;

    //-------------------- creamos opciones del menu----------------
    private JMenuBar menu;
    private JMenu archivo, editar, seleccion, ver, apariencia;
    private JMenuItem elementosMenu;
}
