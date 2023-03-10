package diaz.abraham.Editor;

import diaz.abraham.Editor.Utilidades.*;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

class Panel extends JPanel {

    @Override
    public void paint(Graphics g) {
        Image img = new ImageIcon(Objects.requireNonNull(getClass().getResource("/diaz/abraham/Img/rayo.jpg"))).getImage();
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);

        setOpaque(false);
        super.paint(g);
    }

    public Panel(JFrame borde) {



        BorderLayout border = new BorderLayout();
        setLayout(border);


        //--------------------menu -----
        JPanel panelMenu = new JPanel();

        panelMenu.setLayout(new BorderLayout());


        //-------------------- creamos opciones del menu----------------
        JMenuBar menu = new JMenuBar();
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
        agregaItem("Eliminar", "editar", "deshacer");
        agregaItem("Atras", "editar", "rehacer");
        editar.addSeparator();
        agregaItem("Cortar", "editar", "cortar");
        agregaItem("Pegar", "editar", "copiar");
        agregaItem("Copiar", "editar", "pegar");
        //-------------------- elementos menu seleccion
        agregaItem("Seleccionar Todo", "seleccion", "seleccion");
        //--------------------elementos menu ver
        agregaItem("Numeracion ", "ver", "numeracion");
        ver.add(apariencia);
        agregaItem("Normal", "apariencia", "normal");
        agregaItem("Dark", "apariencia", "dark");


        panelMenu.add(menu);
        //--------------------area de texto------------------------
        tPane = new JTabbedPane();
        listFile = new ArrayList<>();
        listAreaTexto = new ArrayList<>();
        listAreaScroll = new ArrayList<>();
        listManager = new ArrayList<>();// rastreamos los cambios del area de texto

        //-------------------- barra de herramientas --------------------
        JToolBar herramientas = new JToolBar(JToolBar.VERTICAL);
        url = Main.class.getResource("/diaz/abraham/Img/icons8-eliminar-propiedad-24.png");
        ToolBar.addButton(url, herramientas, "Cerrar ventana").addActionListener(e -> {
            int seleccion = tPane.getSelectedIndex();
            if (seleccion != -1){
                //--------------------si existen ventanas abiertas eliminamos la ventana seleccionada----
                listAreaScroll.get(tPane.getSelectedIndex()).setRowHeader(null);
                tPane.remove(seleccion);
                listAreaTexto.remove(seleccion);
                listAreaScroll.remove(seleccion);
                listManager.remove(seleccion);
                listFile.remove(seleccion);

                contadorVentana--;

                if(tPane.getSelectedIndex() == -1){
                    existeVentana = false;
                }
            }
        });

        url = Main.class.getResource("/diaz/abraham/Img/icons8-nueva-ventana-50.png");
        ToolBar.addButton(url, herramientas, "Nueva ventana").addActionListener(e -> creaPanel());
        //-------------------- Panel extra --------------------------------
        JPanel ventanaExtra = new JPanel();
        ventanaExtra.setLayout(new BorderLayout());
                // etiqueta izquierda panel extra
        JPanel ventanaIzquierda = new JPanel();
        labelAncla = new JLabel();

        url = Main.class.getResource("/diaz/abraham/Img/icons8-ancla-50.png");
        assert url != null;
        labelAncla.setIcon(new ImageIcon(new ImageIcon(url)
                        .getImage()
                        .getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        //--------------------a??adimos funcionalidad --------------------------------
        labelAncla.addMouseListener(new MouseAdapter(){

            public void mouseEntered(MouseEvent e) {//al pasar el raton encima cambia de imagen
                url = Main.class.getResource("/diaz/abraham/Img/icons8-ancla-50 (1).png");
                assert url != null;
                labelAncla.setIcon(new ImageIcon(new ImageIcon(url)
                        .getImage()
                        .getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
            }
            public void mouseExited(MouseEvent e){//si el raton sale de la etiqueta devuelve la imagen normal

                if (estadoAncla) {
                    url = Main.class.getResource("/diaz/abraham/Img/icons8-ancla-50 (1).png");
                }else{
                    url = Main.class.getResource("/diaz/abraham/Img/icons8-ancla-50.png");
                }
                assert url != null;
                labelAncla.setIcon(new ImageIcon(new ImageIcon(url)
                        .getImage()
                        .getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
            }
            public void mousePressed(MouseEvent e){// si damos click activamos las funciones ancla
                estadoAncla = !estadoAncla;
                borde.setAlwaysOnTop(estadoAncla);
            }
        });

        ventanaIzquierda.add(labelAncla);
        //--------------------funciones ventana central--------------------
        JPanel ventanaCentro = new JPanel();
        slider = new JSlider(10,40, 14);
        slider.setMajorTickSpacing(4);//separacion entre niveles slider
        slider.setMinorTickSpacing(2);
        slider.setPaintLabels(true);


        slider.addChangeListener(e -> CambiarTamanoTexto.tamanoTexto(slider.getValue(), contadorVentana, listAreaTexto));
        ventanaCentro.add(slider);

        ventanaExtra.add(ventanaIzquierda, BorderLayout.WEST);
        ventanaExtra.add(ventanaCentro, BorderLayout.CENTER);

        //--------------------menu emergente -------------------------------
        menuEmergentes = new JPopupMenu();

        JMenuItem cortar = new JMenuItem("cortar");
        JMenuItem copiar = new JMenuItem("copiar");
        JMenuItem pegar = new JMenuItem("pegar");

        cortar.addActionListener(new DefaultEditorKit.CutAction());
        copiar.addActionListener(new DefaultEditorKit.CopyAction());
        pegar.addActionListener(new DefaultEditorKit.PasteAction());

        menuEmergentes.add(cortar);
        menuEmergentes.add(copiar);
        menuEmergentes.add(pegar);

        //--------------------a??adimos los objetos a la vista del programa----------------

        add(panelMenu, BorderLayout.NORTH);
        add(tPane, BorderLayout.CENTER);
        add(herramientas, BorderLayout.WEST);
        add(ventanaExtra, BorderLayout.SOUTH);
    }




    //--------------------agregamos las opciones del menu
    public void agregaItem(String rotulo, String menu, String accion) {
        JMenuItem elementosMenu = new JMenuItem(rotulo);
        switch (menu) {
            case "archivo" -> {
                archivo.add(elementosMenu);
                switch (accion) {
                    case "nuevo" -> elementosMenu.addActionListener(e -> creaPanel());
                    case "abrir" -> //-------------------- preparamos la lectura y apertura de archivos
                            elementosMenu.addActionListener(e -> {
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
                                        // si el archivo no esta abierto lo leemos y a??adimos al area de texto en una nueva ventana
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
                                            // el titulo se le agrega a la pesta??a creada para el area de texto
                                            tPane.setTitleAt(tPane.getSelectedIndex(), titulo);
                                            // lee cada linea del archivo para despues imprimirlo
                                            while (linea != null) {
                                                linea = miReader.readLine();

                                                if (linea != null) AgregamosTextoLinea.append
                                                        (linea + "\n", listAreaTexto
                                                                .get(tPane.getSelectedIndex()));

                                            }
                                            Theme.fondo(contadorVentana, tipoFondo,slider.getValue() , listAreaTexto);
                                        } else {
                                            // si el archivo esta abierto vamos a recorrer las ventanas para irnos a la abierta
                                            for (int i = 0; i < tPane.getTabCount(); i++) {
                                                File f = slectorArchivos.getSelectedFile();

                                                if (listFile.get(i).getPath().equals(f.getPath())) {
                                                    tPane.setSelectedIndex(i);


                                                    listAreaTexto.remove(tPane.getTabCount() - 1);
                                                    listAreaScroll.remove(tPane.getTabCount() - 1);
                                                    listFile.remove(tPane.getTabCount() - 1);
                                                    tPane.remove(tPane.getTabCount() - 1);
                                                    contadorVentana--;

                                                    break;

                                                }
                                            }
                                        }

                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }

                                } else {
                                    // si el usuario cancela eliminamos la nueva ventana creada
                                    int seleccion = tPane.getSelectedIndex();

                                    if (seleccion != -1) {

                                        listAreaTexto.remove(tPane.getTabCount() - 1);
                                        listAreaScroll.remove(tPane.getTabCount() - 1);
                                        listFile.remove(tPane.getTabCount() - 1);
                                        tPane.remove(tPane.getTabCount() - 1);
                                        contadorVentana--;

                                    }
                                }


                            });
                    case "guardar" -> //--------------------
                            elementosMenu.addActionListener(e -> {
                                if (listFile.get(tPane.getSelectedIndex()).getPath().equals("")) {
                                    JFileChooser guardarArchivo = new JFileChooser();
                                    int opc = guardarArchivo.showOpenDialog(null);
                                    //--------------------preguntamos al usuario si guarda o cancela
                                    if (opc == JFileChooser.APPROVE_OPTION) {
                                        File archivo = guardarArchivo.getSelectedFile();
                                        listFile.set(tPane.getSelectedIndex(), archivo);
                                        tPane.setTitleAt(tPane.getSelectedIndex(), archivo.getName());
                                        //--------------------leemos y reescribimos el archivo en un o nuevo que guardamos
                                        try {
                                            FileWriter fw = new FileWriter(listFile.get(tPane.getSelectedIndex()).getPath());
                                            String texto = listAreaTexto.get(tPane.getSelectedIndex()).getText();

                                            for (int i = 0; i < texto.length(); i++) {
                                                fw.write(texto.charAt(i));
                                            }

                                            fw.close();


                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    }
                                } else {
                                    try {
                                        FileWriter fw = new FileWriter(listFile.get(tPane.getSelectedIndex()).getPath());
                                        String texto = listAreaTexto.get(tPane.getSelectedIndex()).getText();


                                        for (int i = 0; i < texto.length(); i++) {
                                            fw.write(texto.charAt(i));
                                        }

                                        fw.close();
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }

                                }

                            });
                    case "guardarComo" -> elementosMenu.addActionListener(e -> {
                        JFileChooser guardarArchivo = new JFileChooser();
                        int opc = guardarArchivo.showOpenDialog(null);

                        if (opc == JFileChooser.APPROVE_OPTION) {
                            File archivo = guardarArchivo.getSelectedFile();
                            listFile.set(tPane.getSelectedIndex(), archivo);
                            tPane.setTitleAt(tPane.getSelectedIndex(), archivo.getName());

                            try {
                                FileWriter fw = new FileWriter(listFile.get(tPane.getSelectedIndex()).getPath());
                                String texto = listAreaTexto.get(tPane.getSelectedIndex()).getText();

                                for (int i = 0; i < texto.length(); i++) {
                                    fw.write(texto.charAt(i));
                                }

                                fw.close();


                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    });

                }
            }
            case "editar" -> {
                editar.add(elementosMenu);
                switch (accion) {
                    case "deshacer" -> elementosMenu.addActionListener(e -> {
                        if (listManager.get(tPane.getSelectedIndex()).canUndo()) {
                            listManager.get(tPane.getSelectedIndex()).undo();
                        }
                    });
                    case "rehacer" -> elementosMenu.addActionListener(e -> {
                        if (listManager.get(tPane.getSelectedIndex()).canRedo()) {
                            listManager.get(tPane.getSelectedIndex()).redo();
                        }
                    });
                    case "cortar" -> elementosMenu.addActionListener(new DefaultEditorKit.CutAction());
                    case "copiar" -> elementosMenu.addActionListener(new DefaultEditorKit.CopyAction());
                    case "pegar" -> elementosMenu.addActionListener(new DefaultEditorKit.PasteAction());


                }
            }

            case "seleccion" -> {
                seleccion.add(elementosMenu);
                elementosMenu.addActionListener(e -> listAreaTexto.get(tPane.getSelectedIndex()).selectAll());
            }

            case "ver" -> {
                ver.add(elementosMenu);
                if (accion.equals("numeracion")) {
                    elementosMenu.addActionListener(e -> {

                        numeros = !numeros;
                        verNumeracion.viewNumeracion(contadorVentana, numeros, listAreaTexto, listAreaScroll);
                    });

                }
            }
            case "apariencia" -> {
                apariencia.add(elementosMenu);
                if(accion.equals("normal")){
                    elementosMenu.addActionListener(e -> {
                        tipoFondo = "N";
                        if(tPane.getTabCount() >0) Theme.fondo(contadorVentana, tipoFondo, slider.getValue(), listAreaTexto);
                    });
                } else if (accion.equals("dark")) {
                    elementosMenu.addActionListener(e -> {
                        tipoFondo = "D";
                        if(tPane.getTabCount()> 0) Theme.fondo(contadorVentana, tipoFondo, slider.getValue(), listAreaTexto);
                    });

                }
            }


        }


    }

    //-------------------- creamos las ventanas de texto------------------------
    public void creaPanel() {


        JPanel ventanaText = new JPanel();
        ventanaText.setLayout(new BorderLayout());
        listFile.add(new File(""));
        listAreaTexto.add(new JTextPane());
        listAreaScroll.add(new JScrollPane(listAreaTexto.get(contadorVentana)));
        listManager.add(new UndoManager());

        listAreaTexto.get(contadorVentana).getDocument().addUndoableEditListener(listManager.get(contadorVentana));

        listAreaTexto.get(contadorVentana).setComponentPopupMenu(menuEmergentes);

        ventanaText.add(listAreaScroll.get(contadorVentana), BorderLayout.CENTER);
        tPane.addTab("nueva ventana", ventanaText);

        verNumeracion.Numeracion(numeros, listAreaTexto.get(contadorVentana), listAreaScroll.get(contadorVentana));

        tPane.setSelectedIndex(contadorVentana);
        contadorVentana++;

        Theme.fondo(contadorVentana, tipoFondo, slider.getValue(), listAreaTexto);

        existeVentana = true;

    }
    private String tipoFondo = "W";
    private Boolean numeros = false;
    private int contadorVentana = 0;//nos cuenta las ventanas de texto
    private boolean existeVentana = false;// comprobamos si exiten ventanas creadas
    private final JTabbedPane tPane;
    //private JTextPane areaText;
    private final ArrayList<JTextPane> listAreaTexto;
    private final ArrayList<UndoManager> listManager;
    private final ArrayList<JScrollPane> listAreaScroll;
    private final ArrayList<File> listFile;

    private final JMenu archivo;
    private final JMenu editar;
    private final JMenu seleccion;
    private final JMenu ver;
    private final JMenu apariencia;
    private URL url;

    private final JLabel labelAncla;
    private Boolean estadoAncla = false;
    private final JSlider slider;

    private final JPopupMenu menuEmergentes;

}
