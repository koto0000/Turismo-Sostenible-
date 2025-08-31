package Vista;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.util.SVGConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import org.apache.batik.util.XMLResourceDescriptor;

public class RedimensionarSVG {

    public static void main(String[] args) throws Exception {
        String rutaEntrada = "src/main/resources/peru1.svg";
        String rutaSalida = "C:\\Users\\Asus\\Downloads\\peru_redimensionado.svg";

        // Leer el documento SVG
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
        Document doc = factory.createDocument(new File(rutaEntrada).toURI().toString());

        // Obtener el nodo <svg>
        Element svgRoot = doc.getDocumentElement();

        // Establecer dimensiones (en centímetros)
        svgRoot.setAttribute(SVGConstants.SVG_WIDTH_ATTRIBUTE, "6cm");
        svgRoot.setAttribute(SVGConstants.SVG_HEIGHT_ATTRIBUTE, "9cm");

        // Verificar y establecer viewBox si no existe
        if (!svgRoot.hasAttribute(SVGConstants.SVG_VIEW_BOX_ATTRIBUTE)) {
            svgRoot.setAttribute(SVGConstants.SVG_VIEW_BOX_ATTRIBUTE, "0 0 600 900"); // Ajusta estos valores si es necesario
        }

        // Guardar el SVG modificado
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(rutaSalida));
        transformer.transform(source, result);

        System.out.println("SVG redimensionado guardado en: " + rutaSalida);
    }
}

