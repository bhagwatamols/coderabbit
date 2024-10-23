package com.splwg.cm.domain;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;
import java.io.IOException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class CmXmlData {

    public static void main(String[] args) {
        try {
            // Load the existing XML document
            File inputFile = new File("example.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(inputFile);

            // Optional but recommended for manipulating the XML document
            document.getDocumentElement().normalize();

            // Append new data
            appendData(document);

            // Save the modified document
            saveDocument(document, "modified_example.xml");

        } catch (ParserConfigurationException | IOException | org.xml.sax.SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private static void appendData(Document document) {
        // Get the root element
        Element rootElement = document.getDocumentElement();

        // Create a new element to append
        Element newElement = document.createElement("newElement");
        newElement.appendChild(document.createTextNode("New Data"));

        // Append the new element to the root element
        rootElement.appendChild(newElement);
    }

    private static void saveDocument(Document document, String outputPath) throws TransformerException {
        // Save the modified document to a new file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(outputPath));
        transformer.transform(source, result);

        System.out.println("XML document saved successfully to " + outputPath);
    }
}