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

package com.auditbucket.core.registration.endpoint;

import com.auditbucket.core.helper.SecurityHelper;
import com.auditbucket.core.registration.bean.FortressInputBean;
import com.auditbucket.core.registration.service.CompanyService;
import com.auditbucket.core.registration.service.FortressService;
import com.auditbucket.registration.model.IFortress;
import com.auditbucket.registration.model.IFortressUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * User: mike
 * Date: 1/05/13
 * Time: 8:23 PM
 */
@Controller
@RequestMapping("/fortress/")
public class FortressEP {

    @Autowired
    FortressService fortressService;

    @Autowired
    CompanyService companyService;

    @Autowired
    SecurityHelper securityHelper;

    @RequestMapping(value = "/{fortressName}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<IFortress> getFortresses(@PathVariable("fortressName") String fortressName) throws Exception {
        // curl -u mike:123 -X GET  http://localhost:8080/ab/fortress/ABC
        IFortress fortress = fortressService.find(fortressName);
        if (fortress == null)
            return new ResponseEntity<IFortress>(fortress, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<IFortress>(fortress, HttpStatus.OK);
    }

    @RequestMapping(value = "/new", produces = "application/json", consumes = "application/json", method = RequestMethod.PUT)
    @Transactional
    @ResponseBody
    public ResponseEntity<FortressInputBean> addFortresses(@RequestBody FortressInputBean fortressInputBean) throws Exception {
        IFortress fortress = fortressService.registerFortress(fortressInputBean);

        if (fortress == null) {
            fortressInputBean.setMessage("Unable to create fortress");
            return new ResponseEntity<FortressInputBean>(fortressInputBean, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            fortressInputBean.setFortressKey(fortress.getFortressKey());
            return new ResponseEntity<FortressInputBean>(fortressInputBean, HttpStatus.CREATED);
        }

    }


    @RequestMapping(value = "/{fortressName}/{user}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<IFortressUser> getFortressUsers(@PathVariable("fortressName") String fortressName, @PathVariable("userName") String userName) throws Exception {
        IFortressUser result = null;
        IFortress fortress = fortressService.find(fortressName);

        if (fortress == null) {
            return new ResponseEntity<IFortressUser>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<IFortressUser>(fortressService.getFortressUser(fortress, userName), HttpStatus.OK);
    }

}