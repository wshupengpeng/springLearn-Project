
package webservice.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="logisticsCarriers" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="loginUserName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="trackPO" type="{http://po.webservices.pms.changan.com.cn/xsd}TrackPO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "logisticsCarriers",
    "loginUserName",
    "password",
    "trackPO"
})
@XmlRootElement(name = "addTrackData")
public class AddTrackData {

    @XmlElement(nillable = true)
    protected String logisticsCarriers;
    @XmlElement(nillable = true)
    protected String loginUserName;
    @XmlElement(nillable = true)
    protected String password;
    @XmlElement(nillable = true)
    protected TrackPO trackPO;

    /**
     * 获取logisticsCarriers属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogisticsCarriers() {
        return logisticsCarriers;
    }

    /**
     * 设置logisticsCarriers属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogisticsCarriers(String value) {
        this.logisticsCarriers = value;
    }

    /**
     * 获取loginUserName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoginUserName() {
        return loginUserName;
    }

    /**
     * 设置loginUserName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoginUserName(String value) {
        this.loginUserName = value;
    }

    /**
     * 获取password属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置password属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * 获取trackPO属性的值。
     * 
     * @return
     *     possible object is
     *     {@link TrackPO }
     *     
     */
    public TrackPO getTrackPO() {
        return trackPO;
    }

    /**
     * 设置trackPO属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link TrackPO }
     *     
     */
    public void setTrackPO(TrackPO value) {
        this.trackPO = value;
    }

}
