package Vista;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class JTextFieldRedondeado extends JTextField {
    private Shape shape;

    public JTextFieldRedondeado(int size) {
        super(size);
        setOpaque(false);
        setBorder(new EmptyBorder(5, 15, 5, 15)); // ¡Aquí!
        setFont(new Font("Arial", Font.PLAIN, 14));
        setForeground(Color.BLACK);
        setBackground(Color.WHITE);
    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        g2.dispose();
        super.paintComponent(g); // ← se deja al final
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.GRAY); // Color del borde
        g2.setStroke(new BasicStroke(1.5f)); // Grosor del borde
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
        g2.dispose();
    }

    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 30, 30);
        }
        return shape.contains(x, y);
    }
}
