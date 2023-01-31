package diaz.abraham.Editor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

class Panel extends JPanel {
    public Panel() {
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
        agregaItem("Nuevo Archivo", "archivo", "nuevo");
        agregaItem("Abrir Archivo", "archivo", "abrir");
        agregaItem("Guardar", "archivo", "guardar");
        agregaItem("Guardar Como", "archivo", "guardarComo");
        //--------------------elementos menu editar
        agregaItem("Eliminar", "editar", "");
        agregaItem("Atras", "editar", "");
        editar.addSeparator();
        agregaItem("Cortar", "editar", "");
        agregaItem("Pegar", "editar", "");
        agregaItem("Copiar", "editar", "");
        //-------------------- elementos menu seleccion
        agregaItem("Seleccionar Todo", "seleccion", "");
        //--------------------elementos menu ver
        agregaItem("Numeracion ", "ver", "");
        ver.add(apariencia);
        agregaItem("Normal", "apariencia", "");
        agregaItem("Dark", "apariencia", "");


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
    public void agregaItem(String rotulo, String menu, String accion) {
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
                        //-------------------- preparamos la lectura y apertura de archivos
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            creaPanel();
                            JFileChooser slectorArchivos = new JFileChooser();
                            slectorArchivos.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                            int resultado = slectorArchivos.showOpenDialog(listAreaTexto.get(
                                    tPane.getSelectedIndex()));

                            if (resultado == JFileChooser.APPROVE_OPTION) {
                                try {
                                    boolean existePath = false;

                                    for (int i = 0; i < tPane.getTabCount(); i++) {
                                        File f = slectorArchivos.getSelectedFile();

                                        if (listFile.get(i).getPath().equals(f.getPath())) existePath = true;
                                    }
                                    // si el archivo no esta abierto lo leemos y añadimos al area de texto en una nueva ventana
                                    if (!existePath) {
                                        File archivo = slectorArchivos.getSelectedFile();
                                        listFile.set(tPane.getSelectedIndex(), archivo);

                                        FileReader entrada = new FileReader(
                                                listFile.get(
                                                        tPane.getSelectedIndex())
                                                        .getPath());

                                        BufferedReader miReader = new BufferedReader(entrada);
                                        String linea = "";

                                        String titulo = listFile.get(tPane.getSelectedIndex()).getName();
                                        // el titulo se le agrega a la pestaña creada para el area de texto
                                        tPane.setTitleAt(tPane.getSelectedIndex(), titulo);
                                        // lee cada linea del archivo para despues imprimirlo
                                        while (linea != null) {
                                            linea = miReader.readLine();

                                            if (linea != null) AgregamosTextoLinea.append
                                                    (linea + "\n", listAreaTexto
                                                            .get(tPane.getSelectedIndex()));

                                        }
                                    }else {
                                        // si el archivo esta abierto vamos a recorrer las ventanas para irnos a la abierta
                                        for(int i=0; i<tPane.getTabCount(); i++) {
                                            File f = slectorArchivos.getSelectedFile();

                                            if (listFile.get(i).getPath().equals(f.getPath())){
                                                tPane.setSelectedIndex(i);


                                                listAreaTexto.remove(tPane.getTabCount()-1);
                                                listAreaScroll.remove(tPane.getTabCount()-1);
                                                listFile.remove(tPane.getTabCount()-1);
                                                tPane.remove(tPane.getTabCount()-1);
                                                contadorVentana--;

                                                break;

                                            }
                                        }
                                    }

                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }

                            }else{
                                // si el usuario cancela eliminamos la nueva ventana creada
                                int seleccion = tPane.getSelectedIndex();

                                if(seleccion != -1){

                                    listAreaTexto.remove(tPane.getTabCount()-1);
                                    listAreaScroll.remove(tPane.getTabCount()-1);
                                    listFile.remove(tPane.getTabCount()-1);
                                    tPane.remove(tPane.getTabCount()-1);
                                    contadorVentana--;

                                }
                            }


                        }
                    });
                    case "guardar" -> elementosMenu.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(listFile.get(tPane.getSelectedIndex()).getPath().equals("")){
                                JFileChooser guardarArchivo = new JFileChooser();
                                int opc = guardarArchivo.showOpenDialog(null);

                                if(opc == JFileChooser.APPROVE_OPTION){
                                    File archivo = guardarArchivo.getSelectedFile();
                                    listFile.set(tPane.getSelectedIndex(), archivo);
                                    tPane.setTitleAt(tPane.getSelectedIndex(), archivo.getName());

                                    try {
                                        FileWriter fw = new FileWriter(listFile.get(tPane.getSelectedIndex()).getPath());
                                        String texto = listAreaTexto.get(tPane.getSelectedIndex()).getText();

                                        for(int i = 0; i <texto.length(); i++){
                                            fw.write(texto.charAt(i));
                                        }

                                        fw.close();


                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }
                            }else {
                                try {
                                    FileWriter fw = new FileWriter(listFile.get(tPane.getSelectedIndex()).getPath());
                                    String texto = listAreaTexto.get(tPane.getSelectedIndex()).getText();


                                    for(int i = 0; i <texto.length(); i++){
                                        fw.write(texto.charAt(i));
                                    }

                                    fw.close();
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }

                            }

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
    public void creaPanel() {


        ventanaText = new JPanel();

        listFile.add(new File(""));
        listAreaTexto.add(new JTextPane());
        listAreaScroll.add(new JScrollPane(listAreaTexto.get(contadorVentana)));


        ventanaText.add(listAreaScroll.get(contadorVentana));
        tPane.addTab("nueva ventana", ventanaText);
        tPane.setSelectedIndex(contadorVentana);
        contadorVentana++;
        existeVentana = true;
    }

    private int contadorVentana = 0;//nos cuenta las ventanas de texto
    private boolean existeVentana = false;// comprobamos si exiten ventanas creadas
    private JTabbedPane tPane;
    private JPanel ventanaText;
    //private JTextPane areaText;
    private ArrayList<JTextPane> listAreaTexto;
    private ArrayList<JScrollPane> listAreaScroll;
    private ArrayList<File> listFile;

    //-------------------- creamos opciones del menu----------------
    private JMenuBar menu;
    private JMenu archivo, editar, seleccion, ver, apariencia;
    private JMenuItem elementosMenu;
}
