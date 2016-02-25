/*
 * Dog - Core
 * 
 * Copyright (c) 2009-2014 Dario Bonino and Luigi De Russis
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
// Generated on: 2014.02.15 at 03:00:00 PM CEST 
//

package it.polito.elite.dog.core.library.jaxb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * <p>
 * Java class for configstate complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="configstate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="statevalues" type="{http://elite.polito.it/dogHomeConfiguration}statevalues"/>
 *       &lt;/sequence>
 *       &lt;attribute name="class" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "configstate", propOrder = { "statevalues" })
public class Configstate implements Cloneable
{
	
	@XmlElement(required = true)
	@JsonIgnore
	protected Statevalues statevalues;
	
	// workaround for properly serialize a JSON message: it avoids a "redundant"
	// array
	@XmlTransient
	@JsonProperty("statevalues")
	protected List<Statevalue> statevalueList;
	
	@XmlAttribute(name = "class", required = true)
	@JsonProperty("class")
	protected String clazz;
	
	/**
	 * Creates a new {@code Configstate} instance.
	 * 
	 */
	public Configstate()
	{
		// CC-XJC Version 2.0.1 Build 2012-03-02T12:09:12+0000
		super();
	}
	
	/**
	 * Creates a new {@code Configstate} instance by deeply copying a given
	 * {@code Configstate} instance.
	 * 
	 * 
	 * @param o
	 *            The instance to copy.
	 * @throws NullPointerException
	 *             if {@code o} is {@code null}.
	 */
	public Configstate(final Configstate o)
	{
		// CC-XJC Version 2.0.1 Build 2012-03-02T12:09:12+0000
		super();
		if (o == null)
		{
			throw new NullPointerException("Cannot create a copy of 'Configstate' from 'null'.");
		}
		// CClassInfo: it.polito.elite.dog.core.library.jaxb.Statevalues
		this.statevalues = ((o.statevalues == null) ? null : ((o.getStatevalues() == null) ? null : o.getStatevalues()
				.clone()));
		// CBuiltinLeafInfo: java.lang.String
		this.clazz = ((o.clazz == null) ? null : o.getClazz());
		
		// 'Statevalue' collection.
		if (o.statevalueList != null)
		{
			copyStatevalues(o.getStatevalueList(), this.getStatevalueList());
		}
	}
	
	/**
	 * Gets the value of the statevalues property.
	 * 
	 * @return possible object is {@link Statevalues }
	 * 
	 */
	public Statevalues getStatevalues()
	{
		return statevalues;
	}
	
	/**
	 * Sets the value of the statevalues property.
	 * 
	 * @param value
	 *            allowed object is {@link Statevalues }
	 * 
	 */
	public void setStatevalues(Statevalues value)
	{
		this.statevalues = value;
	}
	
	/**
	 * Gets the value of the clazz property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getClazz()
	{
		return clazz;
	}
	
	/**
	 * Sets the value of the clazz property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setClazz(String value)
	{
		this.clazz = value;
	}
	
	/**
	 * Workaround for properly serialize a JSON message; it is not considered in
	 * XML
	 * 
	 * @return the statevalueList
	 */
	public List<Statevalue> getStatevalueList()
	{
		if (statevalueList == null)
		{
			statevalueList = new ArrayList<Statevalue>();
		}
		return this.statevalueList;
	}
	
	/**
	 * Workaround for properly serialize a JSON message; it is not considered in
	 * XML
	 * 
	 * @param statevalueList
	 *            the statevalueList to set
	 */
	public void setStatevalueList(List<Statevalue> statevalueList)
	{
		this.statevalueList = statevalueList;
	}
	
	/**
	 * Copies all values of property {@code StatevalueList} deeply.
	 * 
	 * @param source
	 *            The source to copy from.
	 * @param target
	 *            The target to copy {@code source} to.
	 * @throws NullPointerException
	 *             if {@code target} is {@code null}.
	 */
	private static void copyStatevalues(final List<Statevalue> source, final List<Statevalue> target)
	{
		// CC-XJC Version 2.0.1 Build 2012-03-02T12:09:12+0000
		if ((source != null) && (!source.isEmpty()))
		{
			for (final Iterator<?> it = source.iterator(); it.hasNext();)
			{
				final Object next = it.next();
				if (next instanceof Statevalue)
				{
					// CClassInfo:
					// it.polito.elite.dog.core.library.jaxb.Statevalue
					target.add(((Statevalue) next).clone());
					continue;
				}
				// Please report this at
				// https://apps.sourceforge.net/mantisbt/ccxjc/
				throw new AssertionError(
						(("Unexpected instance '" + next) + "' for property 'StatevalueList' of class 'it.polito.elite.dog.core.library.jaxb.Configstate'."));
			}
		}
	}
	
	/**
	 * Creates and returns a deep copy of this object.
	 * 
	 * 
	 * @return A deep copy of this object.
	 */
	@Override
	public Configstate clone()
	{
		try
		{
			{
				// CC-XJC Version 2.0.1 Build 2012-03-02T12:09:12+0000
				final Configstate clone = ((Configstate) super.clone());
				// CClassInfo: it.polito.elite.dog.core.library.jaxb.Statevalues
				clone.statevalues = ((this.statevalues == null) ? null : ((this.getStatevalues() == null) ? null : this
						.getStatevalues().clone()));
				// CBuiltinLeafInfo: java.lang.String
				clone.clazz = ((this.clazz == null) ? null : this.getClazz());
				// 'Statevalue' collection.
				if (this.statevalueList != null)
				{
					clone.statevalueList = null;
					copyStatevalues(this.getStatevalueList(), clone.getStatevalueList());
				}
				return clone;
			}
		}
		catch (CloneNotSupportedException e)
		{
			// Please report this at
			// https://apps.sourceforge.net/mantisbt/ccxjc/
			throw new AssertionError(e);
		}
	}
	
}
