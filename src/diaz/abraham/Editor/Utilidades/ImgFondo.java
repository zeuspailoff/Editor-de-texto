package diaz.abraham.Editor.Utilidades;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ImgFondo extends JPanel {
    @Override
    public void paint(Graphics g) {
        Image img = new ImageIcon(Objects.requireNonNull(getClass().getResource("/diaz/abraham/Img/rayo.jpg"))).getImage();
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);

        setOpaque(false);
        super.paint(g);
    }
}
