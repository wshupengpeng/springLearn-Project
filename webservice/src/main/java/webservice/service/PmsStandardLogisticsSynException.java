
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
 *         &lt;element name="pmsStandardLogisticsSynException" type="{http://pms.changan.com.cn}Exception" minOccurs="0"/>
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
    "pmsStandardLogisticsSynException"
})
@XmlRootElement(name = "pmsStandardLogisticsSynException")
public class PmsStandardLogisticsSynException {

    @XmlElement(nillable = true)
    protected Exception pmsStandardLogisticsSynException;

    /**
     * 获取pmsStandardLogisticsSynException属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Exception }
     *     
     */
    public Exception getPmsStandardLogisticsSynException() {
        return pmsStandardLogisticsSynException;
    }

    /**
     * 设置pmsStandardLogisticsSynException属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Exception }
     *     
     */
    public void setPmsStandardLogisticsSynException(Exception value) {
        this.pmsStandardLogisticsSynException = value;
    }

}
