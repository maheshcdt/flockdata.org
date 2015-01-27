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

package org.flockdata.test.client;

import org.flockdata.helper.FlockException;
import org.flockdata.profile.ImportProfile;
import org.flockdata.transform.ClientConfiguration;
import org.flockdata.transform.FdReader;
import org.flockdata.transform.csv.CsvTagMapper;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by mike on 27/01/15.
 */
public class TestTabTags {
    @Test
    public void string_NestedTags() throws Exception {
        ImportProfile params = ClientConfiguration.getImportParams("/sectors.json");
        CsvTagMapper mapper = new CsvTagMapper();
        String[] headers = new String[]{"Catcode","Catname","Catorder","Industry","Sector","Sector Long"};
        String[] data = new String[]{"F2600","Private Equity & Investment Firms","F07","Securities & Investment","Finance/Insur/RealEst","Finance","Insurance & Real Estate"};
        Map<String, Object> json = mapper.setData(headers, data, params, reader);
        assertNotNull(json);
        assertNotNull(mapper);
        assertEquals("F2600", mapper.getCode());
        assertEquals("Private Equity & Investment Firms", mapper.getName());
        assertNotNull(mapper.getProperties().get("order"));
        assertEquals(1, mapper.getTargets().size());
    }
    FdReader reader = new FdReader() {
        @Override
        public String resolveCountryISOFromName(String name) throws FlockException {
            return name;
        }

        @Override
        public String resolve(String type, Map<String, Object> args) {
            return null;
        }
    };
}
