/*
 * Copyright (c) 2012-2015 "FlockData LLC"
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

package org.flockdata.test.engine.suites;

/**
 * Suite for Web services
 * Created by mike on 2/04/15.
 */

import org.flockdata.test.engine.endpoint.CompanyTestEP;
import org.flockdata.test.engine.endpoint.TestAPISecurity;
import org.flockdata.test.engine.endpoint.TestAuthenticationEP;
import org.flockdata.test.engine.functional.*;
import org.flockdata.test.engine.unit.TestApiKeyHelper;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestEndPoints.class,
        TestNeoRestInterface.class,
        TestAuthenticationEP.class,
        TestAdminCalls.class,
        TestAPISecurity.class,
        TestApiKeyHelper.class,
        TestProfileRegistration.class,
        TestApiKeyInterceptor.class,
        CompanyTestEP.class
})
public class WebServices {
    public WebServices(){}
}