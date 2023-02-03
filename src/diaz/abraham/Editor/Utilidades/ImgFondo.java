package diaz.abraham.Editor.Utilidades;

import javax.swing.*;
import java.awt.*;

public class ImgFondo extends JPanel {
    private Image img;
    @Override
    public void paint(Graphics g) {
        img = new ImageIcon(getClass().getResource("/diaz/abraham/Img/rayo.jpg")).getImage();
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);

        setOpaque(false);
        super.paint(g);
    }
}
