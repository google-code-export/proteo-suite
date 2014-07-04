
package org.proteosuite.jopenms.config.jaxb;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.proteosuite.jopenms.config.jaxb package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _PARAMETERS_QNAME = new QName("", "PARAMETERS");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.proteosuite.jopenms.config.jaxb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PARAMETERS }
     * 
     */
    public PARAMETERS createPARAMETERS() {
        return new PARAMETERS();
    }

    /**
     * Create an instance of {@link NODE }
     * 
     */
    public NODE createNODE() {
        return new NODE();
    }

    /**
     * Create an instance of {@link LISTITEM }
     * 
     */
    public LISTITEM createLISTITEM() {
        return new LISTITEM();
    }

    /**
     * Create an instance of {@link ITEMLIST }
     * 
     */
    public ITEMLIST createITEMLIST() {
        return new ITEMLIST();
    }

    /**
     * Create an instance of {@link ITEM }
     * 
     */
    public ITEM createITEM() {
        return new ITEM();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PARAMETERS }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "PARAMETERS")
    public JAXBElement<PARAMETERS> createPARAMETERS(PARAMETERS value) {
        return new JAXBElement<PARAMETERS>(_PARAMETERS_QNAME, PARAMETERS.class, null, value);
    }

}
