package org.proteosuite.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.proteosuite.ProteoSuiteView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ActionListenerCheckUpdate implements ActionListener {
	private static final String LATEST_VERSION_NUM_URL = "http://www.proteosuite.org/datasets/D-000-PublicFiles/updates.xml";

	@Override
	public void actionPerformed(ActionEvent evt) {
		if (!ProteoSuiteView.SYS_UTILS.CheckURL(LATEST_VERSION_NUM_URL)) {
			JOptionPane
					.showMessageDialog(
							null,
							"Unable to connect. Check that you have internet connection or \nupdate your version manually (http://code.google.com/p/proteo-suite/)",
							"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// Read files using XPath xml parser
		try {
			DocumentBuilderFactory domFactory = DocumentBuilderFactory
					.newInstance();
			domFactory.setNamespaceAware(true);
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			Document doc = builder.parse(LATEST_VERSION_NUM_URL);
			XPath xpath = XPathFactory.newInstance().newXPath();

			// Reading the version
			XPathExpression expr = xpath.compile("/ProteoSuite");
			String ver = "";
			NodeList nodes = (NodeList) expr.evaluate(doc,
					XPathConstants.NODESET);
			for (int iI = 0; iI < nodes.getLength(); iI++) {
				Node node = nodes.item(iI);
				if (node.getNodeType() == Node.ELEMENT_NODE) {

					Element element = (Element) node;
					NodeList nodelist = element.getElementsByTagName("version");
					Element element1 = (Element) nodelist.item(0);
					NodeList fstNm = element1.getChildNodes();
					if (fstNm.getLength() <= 0) {
						ver = "";
					} else {
						ver = fstNm.item(0).getNodeValue();
					}
				}
			}
			if (ProteoSuiteView.PROTEOSUITE_VERSION.equals(ver)) {
				JOptionPane.showMessageDialog(null,
						"Your ProteoSuite version is up to date!!!",
						"Information", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane
						.showMessageDialog(
								null,
								"There is a new version of ProteoSuite available at http://code.google.com/p/proteo-suite/\n Update this manually. We will have an automatic download soon.",
								"Information", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}
}
