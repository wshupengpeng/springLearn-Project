
package webservice.service;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;


/**
 * <p>TrackPO complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="TrackPO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://bean.po3.infoservice.com/xsd}PO">
 *       &lt;sequence>
 *         &lt;element name="arrivalEstimatedTime" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="cartonNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comeFrom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="goodsNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="goodsVolume" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="goodsWeight" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="latitude" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="longitude" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="operationDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="operationInstructions" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="operatorName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="operatorPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pmsNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="provincesCounties" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="putForwardWay" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="receiptCity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="receiver" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sendCity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sender" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="toStore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="trackingNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transportWay" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="waybill" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TrackPO", namespace = "http://po.webservices.pms.changan.com.cn/xsd", propOrder = {
    "arrivalEstimatedTime",
    "cartonNumber",
    "comeFrom",
    "descr",
    "goodsNumber",
    "goodsVolume",
    "goodsWeight",
    "latitude",
    "longitude",
    "operationDate",
    "operationInstructions",
    "operatorName",
    "operatorPhone",
    "pmsNumber",
    "provincesCounties",
    "putForwardWay",
    "receiptCity",
    "receiver",
    "sendCity",
    "sender",
    "status",
    "toStore",
    "trackingNumber",
    "transportWay",
    "waybill"
})
@Data
public class TrackPO
    extends PO
{

    @XmlElement(nillable = true)
//    @XmlSchemaType(name = "date")
    protected String arrivalEstimatedTime = "2023-06-15 13:33:36";
    @XmlElement(nillable = true)
    protected String cartonNumber = "";
    @XmlElement(nillable = true)
    protected String comeFrom = "";
    @XmlElement(nillable = true)
    protected String descr = "";
    @XmlElement(nillable = true)
    protected String goodsNumber= "";
    @XmlElement(nillable = true)
    protected String goodsVolume= "";
    @XmlElement(nillable = true)
    protected String goodsWeight= "";
    @XmlElement(nillable = true)
    protected String latitude= "";
    @XmlElement(nillable = true)
    protected String longitude= "";
    @XmlElement(nillable = true)
//    @XmlSchemaType(name = "date")
    protected String operationDate = "2023-06-15 13:33:36";
    @XmlElement(nillable = true)
    protected String operationInstructions= "";
    @XmlElement(nillable = true)
    protected String operatorName= "";
    @XmlElement(nillable = true)
    protected String operatorPhone= "";
    @XmlElement(nillable = true)
    protected String pmsNumber= "";
    @XmlElement(nillable = true)
    protected String provincesCounties= "";
    @XmlElement(nillable = true)
    protected String putForwardWay= "";
    @XmlElement(nillable = true)
    protected String receiptCity= "";
    @XmlElement(nillable = true)
    protected String receiver= "";
    @XmlElement(nillable = true)
    protected String sendCity= "";
    @XmlElement(nillable = true)
    protected String sender= "";
    @XmlElement(nillable = true)
    protected String status= "";
    @XmlElement(nillable = true)
    protected String toStore= "";
    @XmlElement(nillable = true)
    protected String trackingNumber= "";
    @XmlElement(nillable = true)
    protected String transportWay= "";
    @XmlElement(nillable = true)
    protected String waybill= "";


}
