
package org.proteosuite.jopenms.config.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PARAMETERSType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PARAMETERSType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{}ITEMS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="NODE" type="{}NODEType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="version" type="{}versionString" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PARAMETERSType", propOrder = {
    "items",
    "node"
})
@XmlRootElement(name = "PARAMETERS")
public class PARAMETERS {

    @XmlElements({
        @XmlElement(name = "ITEM", type = ITEM.class),
        @XmlElement(name = "ITEMLIST", type = ITEMLIST.class)
    })
    protected List<AbstractITEM> items;
    @XmlElement(name = "NODE")
    protected List<NODE> node;
    @XmlAttribute(name = "version")
    protected String version;

    /**
     * Gets the value of the items property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the items property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getITEMS().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ITEM }
     * {@link ITEMLIST }
     * 
     * 
     */
    public List<AbstractITEM> getITEMS() {
        if (items == null) {
            items = new ArrayList<AbstractITEM>();
        }
        return this.items;
    }

    /**
     * Gets the value of the node property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the node property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNODE().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NODE }
     * 
     * 
     */
    public List<NODE> getNODE() {
        if (node == null) {
            node = new ArrayList<NODE>();
        }
        return this.node;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

}
