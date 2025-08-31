package Vista;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StepProgressIndicator extends JPanel {

    private List<String> steps;
    private int currentStep;
    private boolean completado = false; // Bandera de finalización

    private Color activeColor = new Color(52, 182, 26);     // Verde moderno
    private Color inactiveColor = new Color(229, 229, 229); // Gris claro
    private Color borderColor = new Color(100, 100, 100);   // Gris para bordes
    private Color textColor = Color.DARK_GRAY;

    private int circleDiameter = 36;
    private int lineHeight = 4;

    public StepProgressIndicator(List<String> steps) {
        this.steps = new ArrayList<>(steps);
        this.currentStep = 0;
        setOpaque(false);
    }

    public void setCurrentStep(int step) {
        if (step >= 0 && step < steps.size()) {
            this.currentStep = step;
            this.completado = false;
            repaint();
        }
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public void nextStep() {
        if (currentStep < steps.size() - 1) {
            currentStep++;
            repaint();
        }
    }

    public void prevStep() {
        if (currentStep > 0) {
            currentStep--;
            repaint();
        }
    }

    public void marcarCompleto() {
        completado = true;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int stepCount = steps.size();
        int lineWidth = 300;
        int x = 0;
        int y = height / 2 - circleDiameter / 2;

        for (int i = 0; i < stepCount; i++) {
            boolean isCompleted = completado || i < currentStep;
            boolean isCurrent = i == currentStep && !completado;
            Color fillColor = isCompleted || isCurrent ? activeColor : inactiveColor;

            // Línea conectora
            if (i > 0) {
                int lineX = x - lineWidth;
                int lineY = y + circleDiameter / 2;
                g2.setColor(isCompleted ? activeColor : inactiveColor);
                g2.fillRoundRect(lineX, lineY - lineHeight / 2, lineWidth, lineHeight, 8, 8);
            }

            // Sombra
            g2.setColor(new Color(0, 0, 0, 30));
            g2.fillOval(x + 2, y + 2, circleDiameter, circleDiameter);

            // Círculo principal
            g2.setColor(fillColor);
            g2.fillOval(x, y, circleDiameter, circleDiameter);

            // Borde
            g2.setColor(borderColor);
            g2.drawOval(x, y, circleDiameter, circleDiameter);

            // Texto dentro del círculo
            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 14f));
            String label = isCompleted ? "✓" : String.valueOf(i + 1);
            FontMetrics fm = g2.getFontMetrics();
            int labelW = fm.stringWidth(label);
            int labelH = fm.getAscent();
            g2.drawString(label, x + (circleDiameter - labelW) / 2, y + (circleDiameter + labelH) / 2 - 2);

            // Texto debajo
            g2.setColor(textColor);
            g2.setFont(g2.getFont().deriveFont(12f));
            String stepLabel = steps.get(i);
            int textWidth = g2.getFontMetrics().stringWidth(stepLabel);
            g2.drawString(stepLabel, x + (circleDiameter - textWidth) / 2, y + circleDiameter + 20);

            x += circleDiameter + lineWidth;
        }

        g2.dispose();
    }

    public List<String> getSteps() {
        return new ArrayList<>(steps);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 100);
    }
}
