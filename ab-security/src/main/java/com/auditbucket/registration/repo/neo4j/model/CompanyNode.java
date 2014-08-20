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

package com.auditbucket.registration.repo.neo4j.model;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

import com.auditbucket.registration.model.Company;
import com.fasterxml.jackson.annotation.JsonIgnore;

@NodeEntity
@TypeAlias(value ="ABCompany")
public class CompanyNode implements Company {
    @GraphId
    Long id;

    @Indexed
    private String name;

    @Indexed(unique = true)
    private String code;

    @Indexed
    String apiKey;

    protected CompanyNode() {
    }

    public CompanyNode(String companyName) {
        this(companyName, null);
    }

    public CompanyNode(String companyName, String apiKey) {
        this();
        setName(companyName);
        this.apiKey = apiKey;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        if (code == null)
            this.code = name.toLowerCase().replaceAll("\\s", "");
    }

    @Override
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String getApiKey() {
        return this.apiKey;
    }


    @Override
    public String toString() {
        return "CompanyNode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    // Lower case, no spaces
    @JsonIgnore
    public String getCode() {
        return code;
    }

    public void setId(long id) {
        this.id = id;
    }
}
