package Vista;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.parser.PathHandler;
import org.apache.batik.parser.PathParser;
import org.w3c.dom.*;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import org.apache.batik.util.XMLResourceDescriptor;

public class EscalarPathSVG {

    public static void main(String[] args) throws Exception {
        String rutaEntrada = "src/main/resources/peru.svg";
        String rutaSalida = "C:\\Users\\Asus\\Downloads\\peru_paths_redimensionado.svg";

        // Tamaño deseado en centímetros
        double nuevoAnchoCm = 6.0;
        double nuevoAltoCm = 8.0;

        // Cargar documento SVG
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
        Document doc = factory.createDocument(new File(rutaEntrada).toURI().toString());

        Element svgRoot = doc.getDocumentElement();

        // Obtener dimensiones originales
        String widthStr = svgRoot.getAttribute("width");
        String heightStr = svgRoot.getAttribute("height");
        
        // Extraer valores numéricos (asumiendo unidades en px)
        double originalWidth = Double.parseDouble(widthStr.replaceAll("[^0-9.]", ""));
        double originalHeight = Double.parseDouble(heightStr.replaceAll("[^0-9.]", ""));

        // Calcular factores de escala
        double escalaX = (nuevoAnchoCm * 37.8) / originalWidth; // 1cm ≈ 37.8px
        double escalaY = (nuevoAltoCm * 37.8) / originalHeight;

        // 1. Primero ajustar el viewBox (si existe) para mantener las proporciones
        if (svgRoot.hasAttribute("viewBox")) {
            String[] viewBoxParts = svgRoot.getAttribute("viewBox").split(" ");
            double vbWidth = Double.parseDouble(viewBoxParts[2]);
            double vbHeight = Double.parseDouble(viewBoxParts[3]);
            
            // Mantener relación de aspecto
            double scale = Math.min(escalaX, escalaY);
            svgRoot.setAttribute("viewBox", 
                viewBoxParts[0] + " " + viewBoxParts[1] + " " + 
                (vbWidth * scale) + " " + (vbHeight * scale));
        }

        // 2. Establecer nuevas dimensiones físicas
        svgRoot.setAttribute("width", nuevoAnchoCm + "cm");
        svgRoot.setAttribute("height", nuevoAltoCm + "cm");

        // 3. Escalar todos los paths (opcional, dependiendo del caso)
        NodeList paths = svgRoot.getElementsByTagName("path");
        for (int i = 0; i < paths.getLength(); i++) {
            Element path = (Element) paths.item(i);
            if (path.hasAttribute("d")) {
                String d = path.getAttribute("d");
                String dEscalado = escalarPathD(d, escalaX, escalaY);
                path.setAttribute("d", dEscalado);
            }
        }

        // 4. Asegurar que el SVG mantenga la relación de aspecto
        svgRoot.setAttribute("preserveAspectRatio", "xMidYMid meet");

        // Guardar resultado
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(rutaSalida));
        transformer.transform(source, result);

        System.out.println("SVG redimensionado correctamente guardado en:\n" + rutaSalida);
    }

    private static String escalarPathD(String d, double escalaX, double escalaY) throws Exception {
        StringBuilder resultado = new StringBuilder();

        PathParser parser = new PathParser();
        parser.setPathHandler(new PathHandler() {
            @Override public void startPath() {}
            @Override public void endPath() {}

            @Override
            public void movetoRel(float x, float y) { resultado.append("m ").append(x * escalaX).append(" ").append(y * escalaY).append(" "); }

            @Override
            public void movetoAbs(float x, float y) { resultado.append("M ").append(x * escalaX).append(" ").append(y * escalaY).append(" "); }

            @Override
            public void linetoRel(float x, float y) { resultado.append("l ").append(x * escalaX).append(" ").append(y * escalaY).append(" "); }

            @Override
            public void linetoAbs(float x, float y) { resultado.append("L ").append(x * escalaX).append(" ").append(y * escalaY).append(" "); }

            @Override
            public void linetoHorizontalRel(float x) { resultado.append("h ").append(x * escalaX).append(" "); }

            @Override
            public void linetoHorizontalAbs(float x) { resultado.append("H ").append(x * escalaX).append(" "); }

            @Override
            public void linetoVerticalRel(float y) { resultado.append("v ").append(y * escalaY).append(" "); }

            @Override
            public void linetoVerticalAbs(float y) { resultado.append("V ").append(y * escalaY).append(" "); }

            @Override
            public void closePath() { resultado.append("Z "); }

            @Override
            public void curvetoCubicRel(float x1, float y1, float x2, float y2, float x, float y) {
                resultado.append("c ")
                        .append(x1 * escalaX).append(" ").append(y1 * escalaY).append(" ")
                        .append(x2 * escalaX).append(" ").append(y2 * escalaY).append(" ")
                        .append(x * escalaX).append(" ").append(y * escalaY).append(" ");
            }

            @Override
            public void curvetoCubicAbs(float x1, float y1, float x2, float y2, float x, float y) {
                resultado.append("C ")
                        .append(x1 * escalaX).append(" ").append(y1 * escalaY).append(" ")
                        .append(x2 * escalaX).append(" ").append(y2 * escalaY).append(" ")
                        .append(x * escalaX).append(" ").append(y * escalaY).append(" ");
            }

            @Override
            public void curvetoQuadraticRel(float x1, float y1, float x, float y) {
                resultado.append("q ")
                        .append(x1 * escalaX).append(" ").append(y1 * escalaY).append(" ")
                        .append(x * escalaX).append(" ").append(y * escalaY).append(" ");
            }

            @Override
            public void curvetoQuadraticAbs(float x1, float y1, float x, float y) {
                resultado.append("Q ")
                        .append(x1 * escalaX).append(" ").append(y1 * escalaY).append(" ")
                        .append(x * escalaX).append(" ").append(y * escalaY).append(" ");
            }

            @Override
            public void arcRel(float rx, float ry, float xAxisRotation, boolean largeArcFlag, boolean sweepFlag, float x, float y) {
                resultado.append("a ")
                        .append(rx * escalaX).append(" ").append(ry * escalaY).append(" ")
                        .append(xAxisRotation).append(" ")
                        .append(largeArcFlag ? "1" : "0").append(" ")
                        .append(sweepFlag ? "1" : "0").append(" ")
                        .append(x * escalaX).append(" ").append(y * escalaY).append(" ");
            }

            @Override
            public void arcAbs(float rx, float ry, float xAxisRotation, boolean largeArcFlag, boolean sweepFlag, float x, float y) {
                resultado.append("A ")
                        .append(rx * escalaX).append(" ").append(ry * escalaY).append(" ")
                        .append(xAxisRotation).append(" ")
                        .append(largeArcFlag ? "1" : "0").append(" ")
                        .append(sweepFlag ? "1" : "0").append(" ")
                        .append(x * escalaX).append(" ").append(y * escalaY).append(" ");
            }

            @Override
            public void curvetoCubicSmoothRel(float x2, float y2, float x, float y) {
                resultado.append("s ")
                        .append(x2 * escalaX).append(" ").append(y2 * escalaY).append(" ")
                        .append(x * escalaX).append(" ").append(y * escalaY).append(" ");
            }

            @Override
            public void curvetoCubicSmoothAbs(float x2, float y2, float x, float y) {
                resultado.append("S ")
                        .append(x2 * escalaX).append(" ").append(y2 * escalaY).append(" ")
                        .append(x * escalaX).append(" ").append(y * escalaY).append(" ");
            }

            @Override
            public void curvetoQuadraticSmoothRel(float x, float y) {
                resultado.append("t ")
                        .append(x * escalaX).append(" ").append(y * escalaY).append(" ");
            }

            @Override
            public void curvetoQuadraticSmoothAbs(float x, float y) {
                resultado.append("T ")
                        .append(x * escalaX).append(" ").append(y * escalaY).append(" ");
            }
        });

        parser.parse(d);
        return resultado.toString().trim();
    }
}

