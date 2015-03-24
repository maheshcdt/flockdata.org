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

package org.flockdata.engine.track.service;

import org.flockdata.engine.track.EntityDaoNeo;
import org.flockdata.helper.FlockException;
import org.flockdata.kv.bean.KvContentBean;
import org.flockdata.kv.service.KvService;
import org.flockdata.registration.model.Fortress;
import org.flockdata.registration.service.CompanyService;
import org.flockdata.track.bean.ContentInputBean;
import org.flockdata.track.bean.TrackResultBean;
import org.flockdata.track.model.Entity;
import org.flockdata.track.model.EntityLog;
import org.flockdata.track.service.FortressService;
import org.flockdata.track.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * User: mike
 * Date: 21/06/14
 * Time: 12:56 PM
 */
@Service
@Transactional
public class LogServiceNeo4j implements LogService {
    private Logger logger = LoggerFactory.getLogger(LogServiceNeo4j.class);

    @Autowired
    private KvService kvManager;

    @Autowired
    FortressService fortressService;

    @Autowired
    CompanyService companyService;

    @Autowired
    EntityDaoNeo entityDao;

    @Autowired
    LogRetryService logRetryService;

    @Override
    @Async ("fd-log")
    public Future<Collection<TrackResultBean>> processLogs(Fortress fortress, Iterable<TrackResultBean> resultBeans) throws FlockException, IOException, ExecutionException, InterruptedException {

        Collection<TrackResultBean> logResults = new ArrayList<>();
        for (TrackResultBean resultBean : resultBeans) {
            logResults.add(processLogFromResult(fortress, resultBean));
        }

        return new AsyncResult<>(logResults);

    }

    @Transactional
    TrackResultBean processLogFromResult(Fortress fortress, TrackResultBean resultBean) throws FlockException, IOException, ExecutionException, InterruptedException {
        if (resultBean.getContentInput() == null)
            return resultBean;

//        if ( !resultBean.getEntity().getFortress().isStoreEnabled())
//            return resultBean; // DAT-349 - No log store

        ContentInputBean contentInputBean = resultBean.getContentInput();
        logger.debug("writeLog {}", contentInputBean);
        TrackResultBean result = logRetryService.writeLog(fortress, resultBean);
        if (result.getLogResult().getStatus() == ContentInputBean.LogStatus.NOT_FOUND)
            throw new FlockException("Unable to find Entity ");

        if (resultBean.getContentInput() != null
                && !resultBean.getLogResult().isLogIgnored()) {
            if ( resultBean.getEntityInputBean() == null || !resultBean.getEntityInputBean().isTrackSuppressed()) {
//                if ( resultBean.getLogResult().getLogToIndex() == null )
                //kvManager.getContent(resultBean.getEntity(), resultBean.getLogResult().getLogToIndex().getLog());
                KvContentBean kvContentBean = new KvContentBean(resultBean);
                kvManager.doWrite(resultBean.getEntity(), kvContentBean);
            }
        }
        return result;
    }

    @Override
    public TrackResultBean writeLog(Entity entity, ContentInputBean input) throws FlockException, IOException, ExecutionException, InterruptedException {

        TrackResultBean resultBean = new TrackResultBean(entity);
        resultBean.setContentInput(input);
        ArrayList<TrackResultBean> logs = new ArrayList<>();
        logs.add(resultBean);
        Collection<TrackResultBean> results = processLogs(entity.getFortress(), logs).get();
        return results.iterator().next();
    }

    @Override
    @Transactional
    public EntityLog getLastLog(Entity entity) throws FlockException {
        if (entity == null || entity.getId() == null)
            return null;
        logger.trace("Getting lastLog MetaID [{}]", entity.getId());
        return entityDao.getLastLog(entity.getId());
    }

}
