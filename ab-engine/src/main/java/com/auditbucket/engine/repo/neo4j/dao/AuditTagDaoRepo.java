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

package com.auditbucket.engine.repo.neo4j.dao;

import com.auditbucket.audit.model.AuditHeader;
import com.auditbucket.audit.model.AuditTag;
import com.auditbucket.engine.repo.neo4j.AuditTagRepo;
import com.auditbucket.engine.repo.neo4j.model.AuditTagRelationship;
import com.auditbucket.registration.model.Tag;
import org.neo4j.graphdb.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * User: Mike Holdsworth
 * Date: 28/06/13
 * Time: 11:07 PM
 */
@Repository("auditTagDAO")
public class AuditTagDaoRepo implements com.auditbucket.dao.AuditTagDao {
    @Autowired
    Neo4jTemplate template;

    @Autowired
    AuditTagRepo auditTagRepo;

    @Override
    public AuditTag save(AuditHeader auditHeader, Tag tag, String type) {
        // type should be the Neo4J Node type when V2 is released.
        AuditTagRelationship atv = new AuditTagRelationship(auditHeader, tag, type);
        Node headerNode = template.getNode(auditHeader.getId());
        Node tagNode = template.getNode(tag.getId());
        //Primary exploration relationship
        template.createRelationshipBetween(tagNode, headerNode, type, null);

        // Only keeping this so that we can efficiently find all the tags being used by a header/tag combo
        // could be simplified to all tags attached to a single Tag node.
        return template.save(atv);
    }

    @Override
    public Set<AuditTag> find(Tag tagName, String type) {
        if (tagName == null)
            return null;
        return auditTagRepo.findTagValues(tagName.getId(), type);
    }

    @Override
    public Set<AuditHeader> findTagAudits(Tag tag) {
        if (tag == null)
            return null;
        return auditTagRepo.findTagAudits(tag.getId());
    }


    @Override
    public Set<AuditTag> getAuditTags(Long id) {
        return auditTagRepo.findAuditTags(id);
    }

    public void update(Set<AuditTag> newValues) {
        for (AuditTag iTagValue : newValues) {
            auditTagRepo.save((AuditTagRelationship) iTagValue);
        }
    }

}
