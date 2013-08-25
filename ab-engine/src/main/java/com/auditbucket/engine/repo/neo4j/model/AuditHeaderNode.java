/*
 * Copyright (c) 2012-2013 "Monowai Developments Limited"
 *
 * This file is part of AuditBucket.
 *
 * AuditBucket is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AuditBucket is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AuditBucket.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.auditbucket.engine.repo.neo4j.model;

import com.auditbucket.audit.model.*;
import com.auditbucket.bean.AuditHeaderInputBean;
import com.auditbucket.registration.model.Fortress;
import com.auditbucket.registration.model.FortressUser;
import com.auditbucket.registration.repo.neo4j.model.FortressNode;
import com.auditbucket.registration.repo.neo4j.model.FortressUserNode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.neo4j.graphdb.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Transient;
import org.springframework.data.neo4j.annotation.*;

import java.util.*;


/**
 * User: Mike Holdsworth
 * Date: 14/04/13
 * Time: 10:56 AM
 */
@NodeEntity(useShortNames = true)
public class AuditHeaderNode implements AuditHeader {
    @Transient
    private Logger log = LoggerFactory.getLogger(AuditHeaderNode.class);

    @GraphId
    private Long id;

    @RelatedTo(elementClass = FortressUserNode.class, type = "created", direction = Direction.INCOMING, enforceTargetType = true)
    private FortressUserNode createdBy;

    @RelatedTo(elementClass = FortressUserNode.class, type = "lastChanged", direction = Direction.OUTGOING)
    private FortressUserNode lastWho;

    @RelatedTo(elementClass = FortressNode.class, type = "audit", direction = Direction.INCOMING)
    @Fetch
    private FortressNode fortress;

    @RelatedTo(type = "classifies", direction = Direction.INCOMING)
    @Fetch
    private DocumentTypeNode documentType;

    @RelatedToVia(elementClass = AuditTagRelationship.class, type = "tagValue", direction = Direction.INCOMING)
    private Set<TagValue> tagValues;

    @RelatedTo(type = "lastChange", direction = Direction.OUTGOING)
    private AuditChangeNode lastChange;

    public static final String UUID_KEY = "auditKey";

    @Indexed(indexName = UUID_KEY, unique = true)
    private String auditKey;

    private String name;

    private long dateCreated;

    @Indexed(indexName = "callerRef")
    private String callerRef;

    private long fortressDate;
    long lastUpdated = 0;

    @Indexed(indexName = "searchKey")
    String searchKey = null;

    private boolean searchSuppressed;

    AuditHeaderNode() {
        auditKey = UUID.randomUUID().toString();
        DateTime now = new DateTime().toDateTime(DateTimeZone.UTC);
        this.dateCreated = now.toDate().getTime();
        this.lastUpdated = dateCreated;
    }

    public AuditHeaderNode(@NotEmpty FortressUser createdBy, @NotEmpty AuditHeaderInputBean auditInput, @NotEmpty DocumentType documentType) {
        this();
        this.fortress = (FortressNode) createdBy.getFortress();
        this.documentType = (DocumentTypeNode) documentType;
        String docType = (documentType != null ? getDocumentType() : "");
        this.name = (callerRef == null ? docType : (docType + "." + callerRef).toLowerCase());

        callerRef = auditInput.getCallerRef();
        if (callerRef != null)
            callerRef = callerRef.toLowerCase();

        Date when = auditInput.getWhen();

        if (when == null)
            fortressDate = new DateTime(dateCreated, DateTimeZone.forTimeZone(TimeZone.getTimeZone(fortress.getTimeZone()))).getMillis();
        else
            fortressDate = when.getTime();

        this.createdBy = (FortressUserNode) createdBy;
        this.lastWho = (FortressUserNode) createdBy;

        this.suppressSearch(auditInput.isSuppressSearch());

    }


    @JsonIgnore
    public Long getId() {
        return id;
    }

    @Override
    public String getAuditKey() {
        return auditKey;
    }

    @Override
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Fortress getFortress() {
        return fortress;
    }

    @Override
    @JsonIgnore
    public String getName() {
        return name;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * returns lower case representation of the documentType.name
     */
    @Override
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDocumentType() {
        return documentType.getName().toLowerCase();
    }

    @Override
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public FortressUser getLastUser() {
        return lastWho;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public void setLastUser(FortressUser user) {
        lastWho = (FortressUserNode) user;
    }

    @Override
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public FortressUser getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedUser(FortressUser user) {
        createdBy = (FortressUserNode) user;
    }

    private void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated.getTime();
    }

    @Override
    @JsonIgnore
    public String getIndexName() {
        if (fortress != null && fortress.getCompany() != null) {
            return new StringBuilder().append(fortress.getCompany().getName().toLowerCase()).append(".").append(fortress.getName().toLowerCase()).toString();
        } else {

            if (log.isErrorEnabled()) {
                log.error("IndexName could not be identified. AuditHeader [" + getAuditKey() + "]" + " Company=[" + fortress.getCompany() + "] fortress=[" + fortress.getName() + "]");
            }
            return null;
        }
    }

    /**
     * If not set by the caller, then the value is set to "now" in the Fortress.getTimeZone.
     *
     * @return Date created in the fortress
     */
    @Override
    public Long getFortressCreated() {
        return fortressDate;
    }

    /**
     * @return when this was created in AuditBucket in UTC
     */
    public Long getABCreated() {
        return dateCreated;
    }

    @Override
    public String toString() {
        return "AuditHeaderNode{" +
                "id=" + id +
                ", auditKey='" + auditKey + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public void bumpUpdate() {
        lastUpdated = new DateTime().toDateTime(DateTimeZone.UTC).toDateTime().getMillis();
    }

    /**
     * if set to true, then this change will not be indexed in the search engine
     * even if the fortress allows it
     *
     * @param searchSuppressed boolean
     */
    public void suppressSearch(boolean searchSuppressed) {
        this.searchSuppressed = searchSuppressed;

    }

    public boolean isSearchSuppressed() {
        return searchSuppressed;
    }

    @JsonIgnore
    public void setSearchKey(String parentKey) {
        this.searchKey = parentKey;
    }

    @JsonIgnore
    public String getSearchKey() {
        return this.searchKey;
    }


    @Override
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getCallerRef() {
        return this.callerRef;
    }

    public void setTagValues(Set<TagValue> tagValues) {
        this.tagValues = tagValues;
    }

    @JsonIgnore
    public Set<TagValue> getTagValues() {
        return tagValues;
    }

    public Map<String, String> getTagMap() {
        Map<String, String> result = new HashMap<>();
        if (tagValues != null)
            for (TagValue tagValue : tagValues) {
                result.put(tagValue.getTag().getName(), tagValue.getTagValue());
            }
        return result;
    }

    @Override
    public void setLastChange(AuditChange change) {
        this.lastChange = (AuditChangeNode) change;
    }

    @JsonIgnore
    public AuditChange getLastChange() {
        return this.lastChange;
    }

    @Override
    public void addTagValue(TagValue tagValue) {
        this.tagValues.add(tagValue);

    }
}
