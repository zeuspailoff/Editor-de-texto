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

        //--------------------elementos menu archivo
        agregaItem("Nuevo Archivo","archivo","nuevo");
        agregaItem("Abrir Archivo","archivo", "abrir");
        agregaItem("Guardar", "archivo", "guardar");
        agregaItem("Guardar Como","archivo", "guardarComo");
        //--------------------elementos menu editar
        agregaItem("Eliminar", "editar","" );
        agregaItem("Atras", "editar", "");
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
        //--------------------añadimos los objetos a la vista del programa----------------
        creaPanel();
        add(panelMenu);
        add(tPane);
    }
    //--------------------agregamos las opciones del menu
    public void agregaItem(String rotulo, String menu, String accion){
        elementosMenu = new JMenuItem(rotulo);
        if(menu.equals("archivo")){
            archivo.add(elementosMenu);
        } else if (menu.equals("editar")) {
            editar.add(elementosMenu);
        } else if (menu.equals("seleccion")) {
            seleccion.add(elementosMenu);
        }else if (menu.equals("ver")){
            ver.add(elementosMenu);
        }else if (menu.equals("apariencia")){
            apariencia.add(elementosMenu);
        }

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
    private JMenuItem elementosMenu;
}
