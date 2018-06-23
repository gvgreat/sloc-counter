//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.0 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.12.27 at 09:50:06 AM IST 
//


package org.sloc.config;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the generated package. 
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

    @SuppressWarnings("nls")
    private final static QName _FileTypes_QNAME = new QName("", "file-types");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {//
    }

    /**
     * Create an instance of {@link FileType }
     * 
     */
    public FileType createFileType() {
        return new FileType();
    }

    /**
     * Create an instance of {@link FileTypes }
     * 
     */
    public FileTypes createFileTypes() {
        return new FileTypes();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "file-types")
    public JAXBElement<FileTypes> createFileTypes(FileTypes value) {
        return new JAXBElement<FileTypes>(_FileTypes_QNAME, FileTypes.class, null, value);
    }

}