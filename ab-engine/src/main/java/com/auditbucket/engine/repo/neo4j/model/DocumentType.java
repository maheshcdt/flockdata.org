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

import com.auditbucket.audit.model.IDocumentType;
import com.auditbucket.engine.registration.repo.neo4j.model.Company;
import com.auditbucket.registration.model.ICompany;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

/**
 * User: Mike Holdsworth
 * Date: 30/06/13
 * Time: 10:02 AM
 */
@NodeEntity
public class DocumentType implements IDocumentType {

    @GraphId
    Long id;

    @RelatedTo(elementClass = Company.class, type = "documents", direction = Direction.INCOMING)
    private ICompany company;

    @Indexed(indexName = "documentTypeName")
    private String name;

    protected DocumentType() {
    }

    public DocumentType(String documentTypeName, ICompany company) {
        this.name = documentTypeName;
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public ICompany getCompany() {
        return company;
    }
}