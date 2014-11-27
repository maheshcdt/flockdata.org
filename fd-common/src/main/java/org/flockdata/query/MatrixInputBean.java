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

package org.flockdata.query;

import java.util.ArrayList;

/**
 * User: mike
 * Date: 12/06/14
 * Time: 11:15 AM
 */
public class MatrixInputBean {
    private ArrayList<String> documents = null;
    private int minCount = 0;

    private boolean byKey = false;
    private ArrayList<String> concepts = null;
    private ArrayList<String> fromRlxs = null;
    private ArrayList<String> toRlxs = null;

    public ArrayList<String> getToRlxs() {
        return toRlxs;
    }

    public void setToRlxs(ArrayList<String> toRlxs) {
        this.toRlxs = toRlxs;
    }

    public ArrayList<String> getFromRlxs() {
        return fromRlxs;
    }

    public void setFromRlxs(ArrayList<String> fromRlxs) {
        this.fromRlxs = fromRlxs;
    }

    public ArrayList<String> getConcepts() {
        return concepts;
    }

    public void setConcepts(ArrayList<String> concepts) {
        this.concepts = concepts;
    }

    public int getMinCount() {
        return minCount;
    }

    public void setMinCount(int minCount) {
        this.minCount = minCount;
    }

    public ArrayList<String> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<String> documents) {
        this.documents = documents;
    }

    /**
     *
     * @return should the edges be keys or values
     */
    public boolean isByKey() {
        return byKey;
    }


}
