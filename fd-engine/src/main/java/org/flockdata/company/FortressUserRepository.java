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

package org.flockdata.company;

import org.flockdata.company.model.FortressUserNode;
import org.flockdata.company.model.SystemUserNode;
import org.flockdata.registration.model.FortressUser;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface FortressUserRepository extends GraphRepository<FortressUserNode> {

    @Query(value = "start fortress=node({0}) match fortress<-[r:BELONGS_TO]-fUser where fUser.name ={1} return fUser")
    SystemUserNode getAdminUser(long fortressId, String userName);
    
    @Query(elementClass = FortressUserNode.class,
            value = "start sysUser=node:sysUserName(name={0}) " +
                    "        match sysUser-[:ACCESSES]->company-[:OWNS]->fortress<-[:BELONGS_TO]-fortressUser " +
                    "where fortressUser.name ={2} and fortress.name={1} return fortressUser")
    FortressUser getFortressUser(String userName, String fortressName, String fortressUser);


}