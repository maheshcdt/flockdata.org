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

package com.auditbucket.search;

/**
 * Object to tie the keys between ab-engine and ab-search so that ab-engine can keep the document up-to-date
 * <p/>
 * User: Mike Holdsworth
 * Since: 13/07/13
 */
public class SearchResult {
    private String auditKey, fortress, searchKey, documentType;
    private long sysWhen;

    protected SearchResult() {
    }

//    public SearchResult(String what) {
//        throw new RuntimeException(what);
//    }

    SearchResult(String auditKey, String fortress, String searchKey, String documentType) {
        this.auditKey = auditKey;
        this.fortress = fortress;
        this.searchKey = searchKey;
        this.documentType = documentType;

    }

    public SearchResult(com.auditbucket.audit.model.SearchChange thisChange) {
        this(thisChange.getAuditKey(), thisChange.getFortressName(), thisChange.getSearchKey(), thisChange.getDocumentType());
        this.sysWhen = thisChange.getWhen();
    }

    /**
     * GUID for the auditKey
     *
     * @return string
     */
    public String getAuditKey() {
        return auditKey;
    }

    /**
     * name of the fortress that owns the auditKey
     *
     * @return string
     */
    public String getFortress() {
        return fortress;
    }

    /**
     * GUID for the search document
     *
     * @return string
     */
    public String getSearchKey() {
        return searchKey;
    }

    /**
     * useful for external caller to know what type of document was indexed
     *
     * @return
     */
    public String getDocumentType() {
        return documentType;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "auditKey='" + auditKey + '\'' +
                ", fortress='" + fortress + '\'' +
                ", documentType='" + documentType + '\'' +
                '}';
    }

    public long getSysWhen() {
        return sysWhen;
    }

    public void setSysWhen(long sysWhen) {
        this.sysWhen = sysWhen;
    }
}
