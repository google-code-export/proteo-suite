package org.proteosuite.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class PluginManager {

    /**
     * This method gets the plugins based on the selected pipeline
     *
     * @param technique
     * @param rawDataFormat
     * @param identFormat
     * @param outputFormat
     * @return Returns an array with the different plugins
     *
     */
    public static String[] getPlugins(String technique, String rawDataFormat,
            String identFormat, String outputFormat) {

        final List<List<String>> alPlugins = new ArrayList<List<String>>();
        String[] sPipeline;
        sPipeline = new String[4];

        // ... Read files using XML parser (Creates an Array of ArrayList) ...//
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File pluginFile = new File(System.getProperty("user.dir") + "/config.xml");
            Document document = db.parse(pluginFile);

            NodeList nodeList = document
                    .getElementsByTagName("pluginLoadIdentFiles");
            for (int x = 0, size = nodeList.getLength(); x < size; x++) {
                alPlugins.add(Arrays.asList(nodeList.item(x).getAttributes()
                        .getNamedItem("type").getNodeValue(), nodeList.item(x)
                        .getAttributes().getNamedItem("id").getNodeValue(),
                        nodeList.item(x).getAttributes().getNamedItem("value")
                        .getNodeValue(), nodeList.item(x)
                        .getAttributes().getNamedItem("desc")
                        .getNodeValue()));
            }

            nodeList = document.getElementsByTagName("pluginLoadRawFiles");
            for (int x = 0, size = nodeList.getLength(); x < size; x++) {
                alPlugins.add(Arrays.asList(nodeList.item(x).getAttributes()
                        .getNamedItem("type").getNodeValue(), nodeList.item(x)
                        .getAttributes().getNamedItem("id").getNodeValue(),
                        nodeList.item(x).getAttributes().getNamedItem("value")
                        .getNodeValue(), nodeList.item(x)
                        .getAttributes().getNamedItem("desc")
                        .getNodeValue()));
            }

            nodeList = document.getElementsByTagName("pluginQuantitation");
            for (int x = 0, size = nodeList.getLength(); x < size; x++) {
                alPlugins.add(Arrays.asList(nodeList.item(x).getAttributes()
                        .getNamedItem("type").getNodeValue(), nodeList.item(x)
                        .getAttributes().getNamedItem("id").getNodeValue(),
                        nodeList.item(x).getAttributes().getNamedItem("value")
                        .getNodeValue(), nodeList.item(x)
                        .getAttributes().getNamedItem("desc")
                        .getNodeValue()));
            }

            nodeList = document.getElementsByTagName("pluginOutput");
            for (int x = 0, size = nodeList.getLength(); x < size; x++) {
                alPlugins.add(Arrays.asList(nodeList.item(x).getAttributes()
                        .getNamedItem("type").getNodeValue(), nodeList.item(x)
                        .getAttributes().getNamedItem("id").getNodeValue(),
                        nodeList.item(x).getAttributes().getNamedItem("value")
                        .getNodeValue(), nodeList.item(x)
                        .getAttributes().getNamedItem("desc")
                        .getNodeValue()));
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        // Using the array list we need to find the pipeline and
        // corresponding plugins for ident files, raw files, technique, and output files.
        
        for (List<String> subList : alPlugins) {
            if (subList.get(1).toLowerCase().equals(identFormat.toLowerCase())) {
                sPipeline[0] = subList.get(2);
            } else if (subList.get(1).toLowerCase().equals(rawDataFormat.toLowerCase())) {
                sPipeline[1] = subList.get(2);
            } else if (subList.get(1).toLowerCase().equals(technique.toLowerCase())) {
                sPipeline[2] = subList.get(2);
            } else if (subList.get(1).toLowerCase().equals(outputFormat.toLowerCase())) {
                sPipeline[3] = subList.get(2);
            }
        }      

        return sPipeline;
    }
}
