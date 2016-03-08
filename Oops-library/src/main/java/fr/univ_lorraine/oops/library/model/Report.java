package fr.univ_lorraine.oops.library.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String userReporting;
    private String reason;
    private String complement;
    @Temporal(TemporalType.TIMESTAMP)
    private Date reportingDate;
    private boolean moderated;
    private boolean justified;
    private String userReported;
    
    public Report() {
       
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserReporting() {
        return userReporting;
    }

    public void setUserReporting(String userReporting) {
        this.userReporting = userReporting;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getReportingDate() {
        return reportingDate;
    }

    public void setReportingDate(Date reportingDate) {
        this.reportingDate = reportingDate;
    }

    public void setModerated(boolean moderated) {
        this.moderated = moderated;
    }

    public boolean isModerated() {
        return moderated;
    }

    public boolean isJustified() {
        return justified;
    }

    public void setJustified(boolean justified) {
        this.justified = justified;
    }
    
    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getUserReported() {
        return userReported;
    }

    public void setUserReported(String userReported) {
        this.userReported = userReported;
    }
    
}
