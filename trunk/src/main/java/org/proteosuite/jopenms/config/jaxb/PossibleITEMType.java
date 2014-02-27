
package org.proteosuite.jopenms.config.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PossibleITEMType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PossibleITEMType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="int"/>
 *     &lt;enumeration value="double"/>
 *     &lt;enumeration value="float"/>
 *     &lt;enumeration value="string"/>
 *     &lt;enumeration value="int-pair"/>
 *     &lt;enumeration value="double-pair"/>
 *     &lt;enumeration value="input-prefix"/>
 *     &lt;enumeration value="output-prefix"/>
 *     &lt;enumeration value="input-file"/>
 *     &lt;enumeration value="output-file"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PossibleITEMType")
@XmlEnum
public enum PossibleITEMType {

    @XmlEnumValue("int")
    INT("int"),
    @XmlEnumValue("double")
    DOUBLE("double"),
    @XmlEnumValue("float")
    FLOAT("float"),
    @XmlEnumValue("string")
    STRING("string"),
    @XmlEnumValue("int-pair")
    INT_PAIR("int-pair"),
    @XmlEnumValue("double-pair")
    DOUBLE_PAIR("double-pair"),
    @XmlEnumValue("input-prefix")
    INPUT_PREFIX("input-prefix"),
    @XmlEnumValue("output-prefix")
    OUTPUT_PREFIX("output-prefix"),
    @XmlEnumValue("input-file")
    INPUT_FILE("input-file"),
    @XmlEnumValue("output-file")
    OUTPUT_FILE("output-file");
    private final String value;

    PossibleITEMType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PossibleITEMType fromValue(String v) {
        for (PossibleITEMType c: PossibleITEMType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
