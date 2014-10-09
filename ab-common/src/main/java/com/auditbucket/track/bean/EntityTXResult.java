/*
 * Copyright (c) 2012-2014 "Monowai Developments Limited"
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

package com.auditbucket.track.bean;

import com.auditbucket.track.model.Entity;
import com.auditbucket.track.model.EntityLog;
import com.auditbucket.track.model.Log;


/**
 * User: Mike Holdsworth
 * Date: 16/06/13
 * Time: 6:12 PM
 */
public class EntityTXResult {

    private String auditKey;
    private String fortressName;
    private String fortressKey;
    private String documentType;
    private String callerRef;
    private Long lastSystemChange;
    private Long fortressWhen = 0l;

    private EntityLog entityLog;

    private EntityTXResult() {
    }


    public EntityTXResult(Entity entity, Log change, EntityLog log) {
        this();
        this.fortressWhen = log.getFortressWhen();
        this.auditKey = entity.getMetaKey();
        this.documentType = entity.getDocumentType();
        this.callerRef = entity.getCallerRef();
        this.fortressName = entity.getFortress().getName();
        this.fortressKey = entity.getFortress().getFortressKey();
        this.lastSystemChange = entity.getLastUpdate();
        this.entityLog = log;
    }

    public Object getEntityLog() {
        return entityLog;
    }

    public String getAuditKey() {
        return auditKey;
    }

    public String getFortressName() {
        return fortressName;
    }

    public String getFortressKey() {
        return fortressKey;
    }

    public String getDocumentType() {
        return documentType;
    }

    public long getLastSystemChange() {
        return lastSystemChange;
    }

    public String getCallerRef() {
        return callerRef;
    }
}