/*
 * Dog - Core
 * 
 * Copyright (c) 2009-2013 Dario Bonino and Luigi De Russis
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.10.24 at 03:00:12 PM CEST 
//

package it.polito.elite.dog.core.library.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.codehaus.jackson.annotate.JsonProperty;


/**
 * <p>Java class for building complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="building">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="storey" type="{http://elite.polito.it/dogHomeConfiguration}storey" maxOccurs="unbounded"/>
 *           &lt;element name="flat" type="{http://elite.polito.it/dogHomeConfiguration}flat" maxOccurs="unbounded"/>
 *         &lt;/choice>
 *         &lt;element name="garden" type="{http://elite.polito.it/dogHomeConfiguration}garden" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="garage" type="{http://elite.polito.it/dogHomeConfiguration}garage" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="class" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "building", propOrder = {
    "storey",
    "flat",
    "garden",
    "garage"
})
public class Building {

    protected List<Storey> storey;
    @JsonProperty("flats") protected List<Flat> flat;
    protected List<Garden> garden;
    protected List<Garage> garage;
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "class")
    @JsonProperty("class")
    protected String clazz;

    /**
     * Gets the value of the storey property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the storey property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStorey().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Storey }
     * 
     * 
     */
    public List<Storey> getStorey() {
        if (storey == null) {
            storey = new ArrayList<Storey>();
        }
        return this.storey;
    }

    /**
     * Gets the value of the flat property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the flat property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFlat().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Flat }
     * 
     * 
     */
    public List<Flat> getFlat() {
        if (flat == null) {
            flat = new ArrayList<Flat>();
        }
        return this.flat;
    }

    /**
     * Gets the value of the garden property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the garden property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGarden().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Garden }
     * 
     * 
     */
    public List<Garden> getGarden() {
        if (garden == null) {
            garden = new ArrayList<Garden>();
        }
        return this.garden;
    }

    /**
     * Gets the value of the garage property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the garage property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGarage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Garage }
     * 
     * 
     */
    public List<Garage> getGarage() {
        if (garage == null) {
            garage = new ArrayList<Garage>();
        }
        return this.garage;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the clazz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClazz() {
        return clazz;
    }

    /**
     * Sets the value of the clazz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClazz(String value) {
        this.clazz = value;
    }

}
