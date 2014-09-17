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

package com.auditbucket.company.endpoint;

import com.auditbucket.helper.ApiKeyHelper;
import com.auditbucket.helper.DatagioException;
import com.auditbucket.helper.SecurityHelper;
import com.auditbucket.registration.model.Company;
import com.auditbucket.registration.service.CompanyService;
import com.auditbucket.registration.service.RegistrationService;
import com.auditbucket.track.bean.DocumentResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * User: Mike Holdsworth
 * Date: 4/05/13
 * Time: 8:23 PM
 */
@RestController
@RequestMapping("/company")
public class CompanyEP {

	private static final Logger logger = LoggerFactory
			.getLogger(CompanyEP.class);

    @Autowired
    CompanyService companyService;

    @Autowired
    com.auditbucket.track.service.SchemaService schemaService;

    @Autowired
    SecurityHelper securityHelper;

    @Autowired
    RegistrationService registrationService;

    @RequestMapping(value = "/", produces = "application/json", method = RequestMethod.GET)

    public Collection<Company> findCompanies(String apiKey, @RequestHeader(value = "Api-Key", required = false) String apiHeaderKey) throws DatagioException {
        return companyService.findCompanies(ApiKeyHelper.resolveKey(apiHeaderKey, apiKey));
    }

    @RequestMapping(value = "/{companyName}", method = RequestMethod.GET)

    public ResponseEntity<Company> getCompany(@PathVariable("companyName") String companyName,
                                              String apiKey, @RequestHeader(value = "Api-Key", required = false) String apiHeaderKey) throws DatagioException {
        // curl -u mike:123 -X GET http://localhost:8080/ab/company/Monowai

        Company callersCompany = getCompany(apiHeaderKey, apiKey);
        Company company = companyService.findByName(companyName);
        if (company == null)
            return new ResponseEntity<>(company, HttpStatus.NOT_FOUND);
        //ToDo figure out companyName strategy
        if (!callersCompany.getId().equals(company.getId())) {
            //Not Authorised
            throw new DatagioException("Company ["+companyName+"] could not be found");
        } else {
            return new ResponseEntity<>(company, HttpStatus.OK);
        }
    }


    /**
     * All documents in use by a company
     */
    @RequestMapping(value = "/documents", method = RequestMethod.GET)

    public Collection<DocumentResultBean> getDocumentsInUse(
            String apiKey, @RequestHeader(value = "Api-Key", required = false) String apiHeaderKey) throws DatagioException {

        // ToDo: figure out if the API Key can resolve to multiple companies
        Company company = getCompany(apiHeaderKey, apiKey);
        return schemaService.getDocumentsInUse(company);

    }

    private Company getCompany(String apiHeaderKey, String apiRequestKey) throws DatagioException {
        return registrationService.resolveCompany(ApiKeyHelper.resolveKey(apiHeaderKey, apiRequestKey));
    }


}