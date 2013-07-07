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

package com.auditbucket.core.helper;

import com.auditbucket.core.registration.service.SystemUserService;
import com.auditbucket.registration.model.ICompany;
import com.auditbucket.registration.model.ISystemUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * User: mike
 * Date: 17/04/13
 * Time: 10:11 PM
 */
@Service
public class SecurityHelper {
    @Autowired
    private SystemUserService sysUserService;

    public String isValidUser() {
        return getUserName(true, true);
    }

    public String getLoggedInUser() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        if (a == null)
            throw new SecurityException("User is not authenticated");
        return a.getName();
    }

    public String getUserName(boolean exceptionOnNull, boolean isSysUser) {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        if (a == null)
            if (exceptionOnNull)
                throw new SecurityException("User is not authenticated");
            else
                return null;

        if (isSysUser) {
            ISystemUser su = getSysUser(a.getName());
            if (su == null)
                throw new IllegalArgumentException("Not authorised");
        }
        return a.getName();
    }

    public ISystemUser getSysUser(boolean exceptionOnNull) {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        if (a == null)
            if (exceptionOnNull)
                throw new SecurityException("User is not authenticated");
            else
                return null;

        return sysUserService.findByName(a.getName());
    }

    public ISystemUser getSysUser(String loginName) {
        return sysUserService.findByName(loginName);
    }

    public ICompany getCompany() {
        String userName = getLoggedInUser();
        ISystemUser su = sysUserService.findByName(userName);

        if (su == null)
            throw new SecurityException("Not authorised");

        return su.getCompany();
    }
}
