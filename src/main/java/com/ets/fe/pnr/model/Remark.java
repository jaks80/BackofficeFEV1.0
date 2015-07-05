package com.ets.fe.pnr.model;

import com.ets.fe.PersistentObject;
import com.ets.fe.pnr.model.Pnr;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Creation Date: 30 Sep 2010
 *
 * @author Yusuf
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Remark extends PersistentObject implements Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private Date dateTime;
    @XmlElement
    private String text;    
    @XmlElement
    private Pnr pnr;

    public Remark() {

    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Pnr getPnr() {
        return pnr;
    }

    public void setPnr(Pnr pnr) {
        this.pnr = pnr;
    }
}
