/*
 * Copyright (c) 2012-2014 "FlockData LLC"
 *
 * This file is part of FlockData.
 *
 * FlockData is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FlockData is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FlockData.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.flockdata.track.service;

import org.flockdata.helper.NotFoundException;
import org.flockdata.registration.model.Fortress;
import org.flockdata.registration.model.Tag;
import org.flockdata.search.model.TagCloud;
import org.flockdata.search.model.TagCloudParams;
import org.flockdata.track.bean.ContentInputBean;
import org.flockdata.helper.FlockException;
import org.flockdata.registration.bean.TagInputBean;
import org.flockdata.registration.model.Company;
import org.flockdata.search.model.EsSearchResult;
import org.flockdata.search.model.QueryParams;
import org.flockdata.track.bean.EntityInputBean;
import org.flockdata.track.bean.EntitySummaryBean;
import org.flockdata.track.bean.TrackResultBean;
import org.flockdata.track.model.Entity;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * User: mike
 * Date: 6/09/14
 * Time: 2:46 PM
 */
public interface MediationFacade {
    Tag createTag(Company company, TagInputBean tagInput) throws FlockException, ExecutionException, InterruptedException;

    Collection<Tag> createTags(Company company, List<TagInputBean> tagInputs) throws FlockException, ExecutionException, InterruptedException;

    Future<Collection<TrackResultBean>> trackEntitiesAsync(Company company, List<EntityInputBean> inputBeans) throws FlockException, IOException, ExecutionException, InterruptedException;

    Collection<TrackResultBean> trackEntities(Fortress fortress, List<EntityInputBean> inputBeans, int listSize) throws FlockException, IOException, ExecutionException, InterruptedException;

    TrackResultBean trackEntity(Company company, EntityInputBean inputBean) throws FlockException, IOException, ExecutionException, InterruptedException;

    TrackResultBean trackEntity(Fortress fortress, EntityInputBean inputBean) throws FlockException, IOException, ExecutionException, InterruptedException;

    TrackResultBean trackLog(Company company, ContentInputBean input) throws FlockException, IOException, ExecutionException, InterruptedException;

    Future<Long> reindex(Company company, String fortressName) throws FlockException;

    void reindexByDocType(Company company, String fortressName, String docType) throws FlockException;

    EntitySummaryBean getEntitySummary(Company company, String metaKey) throws FlockException;

    EsSearchResult search(Company company, QueryParams queryParams);

    TagCloud getTagCloud(Company company, TagCloudParams tagCloudParams) throws NotFoundException;

    void purge(Company company, String name) throws FlockException;

    void cancelLastLog(Company company, Entity entity) throws IOException, FlockException;

    Collection<TrackResultBean> trackEntities(Company company, List<EntityInputBean> entityInputBeans) throws InterruptedException, ExecutionException, FlockException, IOException;

    void mergeTags(Company company, Tag source, Tag target);

    void createAlias(Company company, String label, Tag source, String akaValue);
}