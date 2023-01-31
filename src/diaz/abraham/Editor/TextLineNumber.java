package diaz.abraham.Editor;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.text.*;

/**
 *  clase para añadir tabla de numeracion al area de texto
 */
public class TextLineNumber extends JPanel
        implements CaretListener, DocumentListener, PropertyChangeListener
{
    public final static float LEFT = 0.0f;
    public final static float CENTER = 0.5f;
    public final static float RIGHT = 1.0f;

    private final static Border OUTER = new MatteBorder(0, 0, 0, 2, Color.GRAY);

    private final static int HEIGHT = Integer.MAX_VALUE - 1000000;

    //  componente de texto

    private JTextComponent component;

    //  propiedades con opciones de cambio

    private boolean updateFont;
    private int borderGap;
    private Color currentLineForeground;
    private float digitAlignment;
    private int minimumDisplayDigits;




    private int lastDigits;
    private int lastHeight;
    private int lastLine;

    private HashMap<String, FontMetrics> fonts;

    /**
     *	crea lineas
     *
     *
     *  @param component  //componente de texto
     *
     */
    public TextLineNumber(JTextComponent component)
    {
        this(component, 3);
    }

    /**
     *	crea lineas de texto con componentes
     *
     *  @param component
     *  @param minimumDisplayDigits
     */
    public TextLineNumber(JTextComponent component, int minimumDisplayDigits)
    {
        this.component = component;

        setFont( component.getFont() );

        setBorderGap( 5 );
        setCurrentLineForeground( Color.RED );
        setDigitAlignment( RIGHT );
        setMinimumDisplayDigits( minimumDisplayDigits );

        component.getDocument().addDocumentListener(this);
        component.addCaretListener( this );
        component.addPropertyChangeListener("font", this);
    }

    /**
     *
     *
     */
    public boolean getUpdateFont()
    {
        return updateFont;
    }

    /**
     *
     *
     *  @param updateFont
     */
    public void setUpdateFont(boolean updateFont)
    {
        this.updateFont = updateFont;
    }

    /**
     *  borde
     *
     *  @return borde en picels
     */
    public int getBorderGap()
    {
        return borderGap;
    }

    /**
     *  el valor por defecto del borde sera 5
     *
     *  @param borderGap  coge los picels
     */
    public void setBorderGap(int borderGap)
    {
        this.borderGap = borderGap;
        Border inner = new EmptyBorder(0, borderGap, 0, borderGap);
        setBorder( new CompoundBorder(OUTER, inner) );
        lastDigits = 0;
        setPreferredWidth();
    }

    /**
     * color
     *
     *  @return color usado en linea numeros
     */
    public Color getCurrentLineForeground()
    {
        return currentLineForeground == null ? getForeground() : currentLineForeground;
    }

    /**
     *  color por defecto sera rojo
     *
     *  @param currentLineForeground  color rojo
     */
    public void setCurrentLineForeground(Color currentLineForeground)
    {
        this.currentLineForeground = currentLineForeground;
    }

    /**
     *  cogemos objero de alineacion
     *
     *  @return alineamos digitos
     */
    public float getDigitAlignment()
    {
        return digitAlignment;
    }

    /**
     *  especificamos los valores horizontales
     *  <ul>
     *  <li>TextLineNumber.LEFT
     *  <li>TextLineNumber.CENTER
     *  <li>TextLineNumber.RIGHT (default)
     *	</ul>
     *
     */
    public void setDigitAlignment(float digitAlignment)
    {
        this.digitAlignment =
                digitAlignment > 1.0f ? 1.0f : digitAlignment < 0.0f ? -1.0f : digitAlignment;
    }

    /**
     *  asignamos el minimo de digitos
     *
     *  @return minimos de digitos a mostrar
     */
    public int getMinimumDisplayDigits()
    {
        return minimumDisplayDigits;
    }

    /**
     *  el minimo de digitos por defecto sera 3
     *
     *  @param minimumDisplayDigits  numeros de dijitos por referencia
     */
    public void setMinimumDisplayDigits(int minimumDisplayDigits)
    {
        this.minimumDisplayDigits = minimumDisplayDigits;
        setPreferredWidth();
    }

    /**
     *  calculamos el maximo en linea numeros
     */
    private void setPreferredWidth()
    {
        Element root = component.getDocument().getDefaultRootElement();
        int lines = root.getElementCount();
        int digits = Math.max(String.valueOf(lines).length(), minimumDisplayDigits);



        if (lastDigits != digits)
        {
            lastDigits = digits;
            FontMetrics fontMetrics = getFontMetrics( getFont() );
            int width = fontMetrics.charWidth( '0' ) * digits;
            Insets insets = getInsets();
            int preferredWidth = insets.left + insets.right + width;

            Dimension d = getPreferredSize();
            d.setSize(preferredWidth, HEIGHT);
            setPreferredSize( d );
            setSize( d );
        }
    }

    /**
     *  dibujamos los numeros
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        //	se determina el espacio en la linea numeros

        FontMetrics fontMetrics = component.getFontMetrics( component.getFont() );
        Insets insets = getInsets();
        int availableWidth = getSize().width - insets.left - insets.right;



        Rectangle clip = g.getClipBounds();
        int rowStartOffset = component.viewToModel( new Point(0, clip.y) );
        int endOffset = component.viewToModel( new Point(0, clip.y + clip.height) );

        while (rowStartOffset <= endOffset)
        {
            try
            {
                if (isCurrentLine(rowStartOffset))
                    g.setColor( getCurrentLineForeground() );
                else
                    g.setColor( getForeground() );


                String lineNumber = getTextLineNumber(rowStartOffset);
                int stringWidth = fontMetrics.stringWidth( lineNumber );
                int x = getOffsetX(availableWidth, stringWidth) + insets.left;
                int y = getOffsetY(rowStartOffset, fontMetrics);
                g.drawString(lineNumber, x, y);

                // nos movemos al siguiente

                rowStartOffset = Utilities.getRowEnd(component, rowStartOffset) + 1;
            }
            catch(Exception e) {break;}
        }
    }


    private boolean isCurrentLine(int rowStartOffset)
    {
        int caretPosition = component.getCaretPosition();
        Element root = component.getDocument().getDefaultRootElement();

        if (root.getElementIndex( rowStartOffset ) == root.getElementIndex(caretPosition))
            return true;
        else
            return false;
    }

    /*
     *	cogemos el numero pintado y devolvemos String vacio
     */
    protected String getTextLineNumber(int rowStartOffset)
    {
        Element root = component.getDocument().getDefaultRootElement();
        int index = root.getElementIndex( rowStartOffset );
        Element line = root.getElement( index );

        if (line.getStartOffset() == rowStartOffset)
            return String.valueOf(index + 1);
        else
            return "";
    }

    /*
     *  determinamos el lado x al dibujar
     */
    private int getOffsetX(int availableWidth, int stringWidth)
    {
        return (int)((availableWidth - stringWidth) * digitAlignment);
    }

    /*
     * determinamos el lado y al dibujar
     */
    private int getOffsetY(int rowStartOffset, FontMetrics fontMetrics)
            throws BadLocationException
    {


        Rectangle r = component.modelToView( rowStartOffset );
        int lineHeight = fontMetrics.getHeight();
        int y = r.y + r.height;
        int descent = 0;



        if (r.height == lineHeight)  // fuente por defecto
        {
            descent = fontMetrics.getDescent();
        }
        else  // revisamos los cambios
        {
            if (fonts == null)
                fonts = new HashMap<String, FontMetrics>();

            Element root = component.getDocument().getDefaultRootElement();
            int index = root.getElementIndex( rowStartOffset );
            Element line = root.getElement( index );

            for (int i = 0; i < line.getElementCount(); i++)
            {
                Element child = line.getElement(i);
                AttributeSet as = child.getAttributes();
                String fontFamily = (String)as.getAttribute(StyleConstants.FontFamily);
                Integer fontSize = (Integer)as.getAttribute(StyleConstants.FontSize);
                String key = fontFamily + fontSize;

                FontMetrics fm = fonts.get( key );

                if (fm == null)
                {
                    Font font = new Font(fontFamily, Font.PLAIN, fontSize);
                    fm = component.getFontMetrics( font );
                    fonts.put(key, fm);
                }

                descent = Math.max(descent, fm.getDescent());
            }
        }

        return y - descent;
    }

    //
//  implementamos interface
//
    @Override
    public void caretUpdate(CaretEvent e)
    {
        //  colocamos simbolo de iteraccion

        int caretPosition = component.getCaretPosition();
        Element root = component.getDocument().getDefaultRootElement();
        int currentLine = root.getElementIndex( caretPosition );



        if (lastLine != currentLine)
        {

            getParent().repaint();
            lastLine = currentLine;
        }
    }

    //
//  implementamos interface
//
    @Override
    public void changedUpdate(DocumentEvent e)
    {
        documentChanged();
    }

    @Override
    public void insertUpdate(DocumentEvent e)
    {
        documentChanged();
    }

    @Override
    public void removeUpdate(DocumentEvent e)
    {
        documentChanged();
    }


    private void documentChanged()
    {


        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    int endPos = component.getDocument().getLength();
                    Rectangle rect = component.modelToView(endPos);

                    if (rect != null && rect.y != lastHeight)
                    {
                        setPreferredWidth();

                        getParent().repaint();
                        lastHeight = rect.y;
                    }
                }
                catch (BadLocationException ex) { /* nada para hacer*/ }
            }
        });
    }

    //
//  implementamos porpidedades para escuchar cambios
//
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof Font)
        {
            if (updateFont)
            {
                Font newFont = (Font) evt.getNewValue();
                setFont(newFont);
                lastDigits = 0;
                setPreferredWidth();
            }
            else
            {

                getParent().repaint();
            }
        }
    }
}
