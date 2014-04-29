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

package com.auditbucket.helper;

/**
 * Created with IntelliJ IDEA.
 * User: mike
 * Date: 7/03/14
 * Time: 6:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class DatagioTagException extends RuntimeException {
    public DatagioTagException() {
    }

    public DatagioTagException(String message) {
        super(message);
    }

    public DatagioTagException(String message, Throwable t) {
        super(message, t);
    }
}