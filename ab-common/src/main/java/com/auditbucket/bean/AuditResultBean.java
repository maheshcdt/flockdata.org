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

package com.auditbucket.bean;

import com.auditbucket.audit.model.AuditHeader;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * User: Mike Holdsworth
 * Since: 17/07/13
 */
public class AuditResultBean {
    private String status;
    private String fortressName;
    private String documentType;
    private String callerRef;
    private String auditKey;
    private String txReference;

    protected AuditResultBean() {
    }

    public AuditResultBean(String statusMessage) {
        this.status = statusMessage;
    }

    public AuditResultBean(String fortressName, String documentType, String callerRef, String auditKey, String txReference) {
        this.fortressName = fortressName;
        this.documentType = documentType;
        this.callerRef = callerRef;
        this.auditKey = auditKey;
        this.txReference = txReference;
    }

    public AuditResultBean(AuditHeader input, String txReference) {
        this(input.getFortress().getName(), input.getDocumentType(), input.getCallerRef(), input.getAuditKey(), txReference);
    }

    public String getFortressName() {
        return fortressName;
    }

    public String getDocumentType() {
        return documentType;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getCallerRef() {
        return callerRef;
    }

    public String getAuditKey() {
        return auditKey;
    }

    public void setAuditKey(String auditKey) {
        this.auditKey = auditKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTxReference() {
        return txReference;
    }


}
