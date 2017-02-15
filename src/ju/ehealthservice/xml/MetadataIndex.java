/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ju.ehealthservice.xml;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import ju.ehealthservice.beans.HealthService;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MetadataIndex {

	private Document doc;
	private File metadata;
	private NodeList nList;
	private Element eElement, rootElement;
	private ArrayList<Element> x;
	
	private static final String path = "/HealthService/Patient[@id=]";
	
	private static final int pos = 27;
	
	public MetadataIndex(String fileName) {
		this.metadata = new File(fileName);
		this.nList = null;
		this.eElement = null;
		this.x  = new ArrayList<>();
	}
	
	public boolean createFile() {
		if(!metadata.exists()) {
			try {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				doc = docBuilder.newDocument();
				rootElement = doc.createElement("HealthService");
				doc.appendChild(rootElement);
				return writeFile();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
				return false;
			} 
		} else {
			return true;
		}
	}
	
	private boolean writeFile() {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(metadata.getPath());
			transformer.transform(source, result);
			return true;
		} catch (TransformerConfigurationException e) {
			// 	TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (TransformerException e) {
			// 	TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean appendData(HealthService ob) {
		if(readFile()) {
                        Element patient = doc.createElement("Patient");
			rootElement.appendChild(patient);
			Attr ID = doc.createAttribute("id");
			ID.setValue(String.valueOf(ob.getID()));
			patient.setAttributeNode(ID);
			Element name = doc.createElement("Name");
			name.appendChild(doc.createTextNode(ob.getName()));
			patient.appendChild(name);
			Element age = doc.createElement("Age");
			age.appendChild(doc.createTextNode(String.valueOf(ob.getAge())));
			patient.appendChild(age);
			Element mobile = doc.createElement("Mobile");
			mobile.appendChild(doc.createTextNode(ob.getMobile()));
			patient.appendChild(mobile);
                        Element image = doc.createElement("Image");
			image.appendChild(doc.createTextNode(Boolean.toString(ob.isImage())));
			patient.appendChild(image);
			return writeFile();
		} else {
			return false;
		}
	}
	
	
	public boolean searchData(int ID) {
		if(readFile()) {
			StringBuffer  sbf = new StringBuffer(path);
			sbf.insert(pos, ID);
			try {
				XPathFactory xPathfactory = XPathFactory.newInstance();
				XPath xpath = xPathfactory.newXPath();
				XPathExpression expr = xpath.compile(sbf.toString());
				NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
				if(nl.getLength() == 1) {
					nList = nl;
					return true;
				} else {
					return false;
				}
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
	}
	
	
	private boolean readFile() {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(metadata);
			rootElement = doc.getDocumentElement();
			return true;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public HealthService getData(int ID) {
		HealthService ob = new HealthService();
		for(int i = 0 ; i < nList.getLength() ; i++ ) {
			Node nNode = nList.item(i);
			if(nNode.getNodeType() == Node.ELEMENT_NODE) {
				eElement = (Element) nNode;
				ob.setID(ID);
				Node nameNode = eElement.getElementsByTagName("Name").item(0);
				ob.setName(nameNode.getTextContent());
				Node mobileNode = eElement.getElementsByTagName("Mobile").item(0);
				ob.setMobile(mobileNode.getTextContent());
				Node ageNode = eElement.getElementsByTagName("Age").item(0);
				ob.setAge(Integer.parseInt(ageNode.getTextContent()));
                                Node imageNode = eElement.getElementsByTagName("Image").item(0);
                                ob.setImage(Boolean.parseBoolean(imageNode.getTextContent()));
				break;
			}
		}
		return ob;
	}
	
	public ArrayList<HealthService> getData(String Name) {
		ArrayList<HealthService> y = new ArrayList<>();
		for(Element eElement : x) {
			HealthService ob = new HealthService();
			ob.setID(Integer.parseInt(eElement.getAttribute("id")));
			Node nameNode = eElement.getElementsByTagName("Name").item(0);
			ob.setName(nameNode.getTextContent());
			Node mobileNode = eElement.getElementsByTagName("Mobile").item(0);
			ob.setMobile(mobileNode.getTextContent());
			Node ageNode = eElement.getElementsByTagName("Age").item(0);
			ob.setAge(Integer.parseInt(ageNode.getTextContent()));
                        Node imageNode = eElement.getElementsByTagName("Image").item(0);
                        ob.setImage(Boolean.parseBoolean(imageNode.getTextContent()));
			y.add(ob);
		}
                return y;
	}
	
	public int searchData(String Name) {
		if(readFile()) {
                    x.clear();
			NodeList nList = rootElement.getElementsByTagName("Patient");
			for(int i = 0 ; i < nList.getLength() ; i++ ) {
				Node nNode = nList.item(i);
				if(nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) nNode;
					Node nameNode = e.getElementsByTagName("Name").item(0);
					if(nameNode.getTextContent().equalsIgnoreCase(Name)) {
						x.add(e);
					} 
				}
			}
		}
		return x.size();
	}
	
	public boolean updateAge(int Age) {
		Node ageNode = eElement.getElementsByTagName("Age").item(0);
		ageNode.setTextContent(String.valueOf(Age));
		return writeFile();
	}
	
	public boolean updateMobile(String Mobile) {
		Node mobileNode = eElement.getElementsByTagName("Mobile").item(0);
		mobileNode.setTextContent(Mobile);
		return writeFile();
	}
	
	public boolean updateName(String Name) {
		Node nameNode = eElement.getElementsByTagName("Name").item(0);
		nameNode.setTextContent(Name);
		return writeFile();
	}
        
        public boolean updateImage(boolean image) {
		Node imageNode = eElement.getElementsByTagName("Image").item(0);
		imageNode.setTextContent(String.valueOf(image));
		return writeFile();
	}
}
