
package org.proteosuite.jopenms.config.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ITEMLISTType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ITEMLISTType">
 *   &lt;complexContent>
 *     &lt;extension base="{}AbstractITEMType">
 *       &lt;sequence>
 *         &lt;element name="LISTITEM" type="{}LISTITEMType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ITEMLISTType", propOrder = {
    "listitem"
})
public class ITEMLIST
    extends AbstractITEM
{

    @XmlElement(name = "LISTITEM")
    protected List<LISTITEM> listitem;

    /**
     * Gets the value of the listitem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listitem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLISTITEM().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LISTITEM }
     * 
     * 
     */
    public List<LISTITEM> getLISTITEM() {
        if (listitem == null) {
            listitem = new ArrayList<LISTITEM>();
        }
        return this.listitem;
    }

}
