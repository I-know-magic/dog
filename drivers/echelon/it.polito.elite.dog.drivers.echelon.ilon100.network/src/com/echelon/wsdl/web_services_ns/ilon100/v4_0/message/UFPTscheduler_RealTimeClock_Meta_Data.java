/**
 * UFPTscheduler_RealTimeClock_Meta_Data.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.echelon.wsdl.web_services_ns.ilon100.v4_0.message;


/**
 * Generalized meta-data type of the 'UFPTscheduler'. It'll only be
 * requested (never used for a response). The request can contain one
 * 'UFPTscheduler_RealTimeClock_Meta_Data'.
 */
@SuppressWarnings({"rawtypes","unused"})
public class UFPTscheduler_RealTimeClock_Meta_Data  extends com.echelon.wsdl.web_services_ns.ilon100.v4_0.message.UFPTrealTimeClock_Data  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UFPTscheduler_RealTimeClock_Meta_Data() {
    }

    public UFPTscheduler_RealTimeClock_Meta_Data(
           com.echelon.wsdl.web_services_ns.ilon100.v4_0.message.E_Fault fault,
           java.lang.String UCPTname,
           java.lang.String UCPTannotation,
           java.lang.Short UCPThidden,
           java.lang.String UCPTaliasName,
           com.echelon.wsdl.web_services_ns.ilon100.v4_0.message.E_LonString UCPTitemStatus,
           java.util.Calendar UCPTlastUpdate,
           java.lang.String UCPTdescription,
           java.lang.String UCPTuri,
           java.util.Calendar UCPTstart,
           java.util.Calendar UCPTstop,
           com.echelon.wsdl.web_services_ns.ilon100.v4_0.message.UFPTrealTimeClock_Data_DpRef[] dataPoint) {
        super(
            fault,
            UCPTname,
            UCPTannotation,
            UCPThidden,
            UCPTaliasName,
            UCPTitemStatus,
            UCPTlastUpdate,
            UCPTdescription,
            UCPTuri,
            UCPTstart,
            UCPTstop,
            dataPoint);
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UFPTscheduler_RealTimeClock_Meta_Data)) return false;
        UFPTscheduler_RealTimeClock_Meta_Data other = (UFPTscheduler_RealTimeClock_Meta_Data) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj);
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UFPTscheduler_RealTimeClock_Meta_Data.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://wsdl.echelon.com/web_services_ns/ilon100/v4.0/message/", "UFPTscheduler_RealTimeClock_Meta_Data"));
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
