package com.auditbucket.audit.repo.es.model;

import com.auditbucket.audit.model.IAuditChange;
import com.auditbucket.audit.model.IAuditHeader;
import com.auditbucket.registration.model.IFortress;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.util.Date;

/**
 * User: mike
 * Date: 25/04/13
 * Time: 4:33 PM
 */
public class AuditChange implements IAuditChange {
    // ToDo: Figure out naming standard for system variables
    @Id
    private String id;
    private String recordType;
    private String what;
    private String name;
    private Date when;
    private String fortressName;
    private String companyName;
    private String event;
    @Version
    private Long version;

    private String indexName;

    /**
     * extracts relevant header records to be used in indexing
     *
     * @param header auditHeader details (owner of this change)
     */
    public AuditChange(IAuditHeader header) {
        this();
        setName(header.getUID());
        this.recordType = header.getDataType();
        setFortress(header.getFortress());
        this.indexName = header.getIndexName();
    }

    public AuditChange() {
    }

    @Override
    @JsonIgnore
    public String getWhat() {
        return what;
    }

    @Override
    public void setWhat(String what) {
        this.what = what;
    }

    /**
     * @return Unique key in the index
     */
    @JsonIgnore
    public String getId() {
        return id;
    }

    private String child;

    @JsonIgnore
    public void setChild(String child) {
        this.child = child;
    }

    public String getChild() {
        return child;
    }

    private String parent;

    @JsonIgnore
    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getParent() {
        return parent;
    }


    public void setId(String id) {
        this.id = id;
    }


    private void setFortress(IFortress fortress) {
        this.setFortressName(fortress.getName());
        this.setCompanyName(fortress.getCompany().getName());

    }

    public String getName() {
        return name;
    }

    public void setName(String who) {
        this.name = who;
    }

    public Date getWhen() {
        return when;
    }

    @Override
    public void setVersion(long version) {
        this.version = version;
    }

    public void setWhen(Date when) {
        this.when = when;
    }

    public String getFortressName() {
        return fortressName;
    }

    @JsonIgnore
    public String getIndexName() {
        return indexName;
    }

    public void setFortressName(String fortressName) {
        this.fortressName = fortressName;
    }

    @JsonIgnore
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @JsonIgnore
    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @JsonIgnore
    public Long getVersion() {
        return version;
    }

    public String getRecordType() {
        return recordType;
    }

    protected void setRecordType(String recordType) {
        this.recordType = recordType;
    }
}
