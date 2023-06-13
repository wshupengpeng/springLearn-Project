
package webservice.service;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

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
public class TrackPO
    extends PO
{

    @XmlElement(nillable = true)
    @XmlSchemaType(name = "date")
    protected Date arrivalEstimatedTime = new Date();
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
    @XmlSchemaType(name = "date")
    protected Date operationDate = new Date();
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

    /**
     * 获取arrivalEstimatedTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public Date getArrivalEstimatedTime() {
        return arrivalEstimatedTime;
    }

    /**
     * 设置arrivalEstimatedTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setArrivalEstimatedTime(Date value) {
        this.arrivalEstimatedTime = value;
    }

    /**
     * 获取cartonNumber属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCartonNumber() {
        return cartonNumber;
    }

    /**
     * 设置cartonNumber属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCartonNumber(String value) {
        this.cartonNumber = value;
    }

    /**
     * 获取comeFrom属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComeFrom() {
        return comeFrom;
    }

    /**
     * 设置comeFrom属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComeFrom(String value) {
        this.comeFrom = value;
    }

    /**
     * 获取descr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescr() {
        return descr;
    }

    /**
     * 设置descr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescr(String value) {
        this.descr = value;
    }

    /**
     * 获取goodsNumber属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoodsNumber() {
        return goodsNumber;
    }

    /**
     * 设置goodsNumber属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoodsNumber(String value) {
        this.goodsNumber = value;
    }

    /**
     * 获取goodsVolume属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoodsVolume() {
        return goodsVolume;
    }

    /**
     * 设置goodsVolume属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoodsVolume(String value) {
        this.goodsVolume = value;
    }

    /**
     * 获取goodsWeight属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoodsWeight() {
        return goodsWeight;
    }

    /**
     * 设置goodsWeight属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoodsWeight(String value) {
        this.goodsWeight = value;
    }

    /**
     * 获取latitude属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * 设置latitude属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLatitude(String value) {
        this.latitude = value;
    }

    /**
     * 获取longitude属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * 设置longitude属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLongitude(String value) {
        this.longitude = value;
    }

    /**
     * 获取operationDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public Date getOperationDate() {
        return operationDate;
    }

    /**
     * 设置operationDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOperationDate(Date value) {
        this.operationDate = value;
    }

    /**
     * 获取operationInstructions属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationInstructions() {
        return operationInstructions;
    }

    /**
     * 设置operationInstructions属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationInstructions(String value) {
        this.operationInstructions = value;
    }

    /**
     * 获取operatorName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * 设置operatorName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperatorName(String value) {
        this.operatorName = value;
    }

    /**
     * 获取operatorPhone属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperatorPhone() {
        return operatorPhone;
    }

    /**
     * 设置operatorPhone属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperatorPhone(String value) {
        this.operatorPhone = value;
    }

    /**
     * 获取pmsNumber属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPmsNumber() {
        return pmsNumber;
    }

    /**
     * 设置pmsNumber属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPmsNumber(String value) {
        this.pmsNumber = value;
    }

    /**
     * 获取provincesCounties属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvincesCounties() {
        return provincesCounties;
    }

    /**
     * 设置provincesCounties属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvincesCounties(String value) {
        this.provincesCounties = value;
    }

    /**
     * 获取putForwardWay属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPutForwardWay() {
        return putForwardWay;
    }

    /**
     * 设置putForwardWay属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPutForwardWay(String value) {
        this.putForwardWay = value;
    }

    /**
     * 获取receiptCity属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiptCity() {
        return receiptCity;
    }

    /**
     * 设置receiptCity属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiptCity(String value) {
        this.receiptCity = value;
    }

    /**
     * 获取receiver属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * 设置receiver属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiver(String value) {
        this.receiver = value;
    }

    /**
     * 获取sendCity属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendCity() {
        return sendCity;
    }

    /**
     * 设置sendCity属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSendCity(String value) {
        this.sendCity = value;
    }

    /**
     * 获取sender属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSender() {
        return sender;
    }

    /**
     * 设置sender属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSender(String value) {
        this.sender = value;
    }

    /**
     * 获取status属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置status属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * 获取toStore属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToStore() {
        return toStore;
    }

    /**
     * 设置toStore属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToStore(String value) {
        this.toStore = value;
    }

    /**
     * 获取trackingNumber属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrackingNumber() {
        return trackingNumber;
    }

    /**
     * 设置trackingNumber属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrackingNumber(String value) {
        this.trackingNumber = value;
    }

    /**
     * 获取transportWay属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransportWay() {
        return transportWay;
    }

    /**
     * 设置transportWay属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransportWay(String value) {
        this.transportWay = value;
    }

    /**
     * 获取waybill属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWaybill() {
        return waybill;
    }

    /**
     * 设置waybill属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWaybill(String value) {
        this.waybill = value;
    }

}
