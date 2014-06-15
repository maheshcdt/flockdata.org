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

package com.auditbucket.test.functional;

import com.auditbucket.helper.DatagioException;
import com.auditbucket.registration.bean.FortressInputBean;
import com.auditbucket.registration.bean.RegistrationBean;
import com.auditbucket.registration.bean.SystemUserResultBean;
import com.auditbucket.registration.model.*;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.TimeZone;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@Transactional
public class TestRegistration extends TestEngineBase {


    private Logger logger = LoggerFactory.getLogger(TestRegistration.class);

    @Test
    public void createPersonsTest() throws DatagioException {
        createCompanyUsers("", 3);

    }

    private void createCompanyUsers(String userNamePrefix, int count) throws DatagioException {
        setSecurity();
        SystemUserResultBean su = registrationEP.registerSystemUser(new RegistrationBean("CompanyA", "mike").setIsUnique(false)).getBody();

        int i = 1;
        while (i <= count) {
            CompanyUser test = regService.addCompanyUser(userNamePrefix + i + "@sunnybell.com", su.getCompanyName());
            test = companyService.save(test);
            assertNotNull(test);
            i++;
        }

    }

    @Test
    public void findByName() throws DatagioException {
        createCompanyUsers("MTest", 3);
        String name = "mtest2@sunnybell.com";
        setSecurity();
        CompanyUser p = companyService.getCompanyUser(name);
        assertNotNull(p);
        assertEquals(name, p.getName());

    }


    @Test
    public void companyFortressNameSearch() throws Exception {
        String companyName = "Monowai";
        String adminName = "mike";

        // Create the company.
        setSecurity();
        SystemUserResultBean systemUser = registrationEP.registerSystemUser(new RegistrationBean(companyName, adminName).setIsUnique(false)).getBody();
        assertNotNull(systemUser);

        fortressService.registerFortress("fortressA");
        fortressService.registerFortress("fortressB");
        fortressService.registerFortress("fortressC");
        fortressService.registerFortress("Fortress Space Name");

        int max = 100;
        for (int i = 0; i < max; i++) {
            Assert.assertNotNull(fortressService.findByName("fortressA"));
            Assert.assertNotNull(fortressService.findByName("fortressB"));
            Assert.assertNotNull(fortressService.findByName("fortressC"));
            Fortress fCode = fortressService.findByCode("fortressspacename");
            Assert.assertNotNull(fCode);
            assertEquals("Fortress Space Name", fCode.getName());
        }
    }

    @Test
    public void onlyOneCompanyCreatedWithMixedCase() throws Exception {
        String companyName = "Monowai";
        String adminName = "mike";

        // Create the company.
        setSecurity();
        SystemUserResultBean systemUser = registrationEP.registerSystemUser(new RegistrationBean(companyName, "password", adminName).setIsUnique(false)).getBody();
        assertNotNull(systemUser);
        Collection<Company>companies = companyEP.findCompanies(systemUser.getApiKey(), null);
        assertEquals(1, companies.size());
        String cKey = companies.iterator().next().getApiKey();

        SystemUserResultBean systemUserB = registrationEP.registerSystemUser(new RegistrationBean(companyName.toLowerCase(), "password", "xyz").setIsUnique(false)).getBody();
        assertNotNull(systemUserB);

        companyEP.findCompanies(systemUserB.getApiKey(), null);

        assertEquals(1, companies.size());
        assertEquals("Company keys should be the same irrespective of name case create with", companies.iterator().next().getApiKey(), cKey);
    }

    @Test
    public void testCompanyUsers() throws DatagioException {
        createCompanyUsers("mike", 10);
        Iterable<CompanyUser> users = companyService.getUsers();
        assertTrue(users.iterator().hasNext());
    }

    @Test
    public void companiesForUser() throws DatagioException {
        setSecurity("mike");
        registrationEP.registerSystemUser(new RegistrationBean("CompanyAA", "mike").setIsUnique(false));
        Fortress fA = fortressService.registerFortress("FortressA");
        Fortress fB = fortressService.registerFortress("FortressB");
        Fortress fC = fortressService.registerFortress("FortressC");
        fortressService.registerFortress("FortressC");// Forced duplicate should be ignored

        Collection<Company> companies = companyService.findCompanies();
        assertEquals(1, companies.size());

        Collection<Fortress> fortresses = fortressService.findFortresses();
        assertFalse(fortresses.isEmpty());
        assertEquals(3, fortresses.size());

        setSecurity(harry);
        registrationEP.registerSystemUser(new RegistrationBean("CompanyBB", harry));

        //Should be seeing different fortresses
        assertNotSame(fA.getId(), fortressService.registerFortress("FortressA").getId());
        assertNotSame(fB.getId(), fortressService.registerFortress("FortressB").getId());
        assertNotSame(fC.getId(), fortressService.registerFortress("FortressC").getId());

    }

    @Test
    public void testRegistration() throws DatagioException {
        String companyName = "testReg";
        String adminName = "mike";
        String userName = "gina@hummingbird.com";

        // Create the company.
        SecurityContextHolder.getContext().setAuthentication(null);
        try {
            // Unauthenticated users can't register accounts
            SystemUserResultBean systemUser = registrationEP.registerSystemUser(new RegistrationBean(companyName, "password", adminName)).getBody();
            assertNotNull(systemUser);
        } catch (Exception e){
            // this is good
        }

        // Assume the user has now logged in.
        setSecurity();
        SystemUserResultBean systemUser = registrationEP.registerSystemUser(new RegistrationBean(companyName, adminName)).getBody();
        assertNotNull(systemUser);

        CompanyUser nonAdmin = regService.addCompanyUser(userName, companyName);
        assertNotNull(nonAdmin);

        Fortress fortress = fortressService.registerFortress("auditbucket");
        assertNotNull(fortress);

        List<Fortress> fortressList = fortressService.findFortresses(companyName);
        assertNotNull(fortressList);
        assertEquals(1, fortressList.size());


        Company company = companyService.findByName(companyName);
        assertNotNull(company);
        assertNotNull(company.getApiKey());
        Long companyId = company.getId();
        company = companyService.findByApiKey(company.getApiKey());
        assertNotNull(company);
        assertEquals(companyId, company.getId());
        assertNotNull(systemUserService.findByLogin(adminName));
        assertNull(systemUserService.findByLogin(userName));
        // SystemNode registration are not automatically company registration
        //assertEquals(1, company.getCompanyUserCount());
        assertNotNull(companyService.getAdminUser(company, adminName));
        assertNull(companyService.getAdminUser(company, userName));

        // Add fortress User
        FortressUser fu = fortressService.getFortressUser(fortress, "useRa");
        assertNotNull(fu);
        fu = fortressService.getFortressUser(fortress, "uAerb");
        assertNotNull(fu);
        fu = fortressService.getFortressUser(fortress, "Userc");
        assertNotNull(fu);

        fortress = fortressService.findByName("auditbucket");
        assertNotNull(fortress);

        fu = fortressService.getFortressUser(fortress, "useRax", false);
        assertNull(fu);
        fu = fortressService.getFortressUser(fortress, "userax");
        assertNotNull(fu);
        fu = fortressService.getFortressUser(fortress, "useRax");
        assertNotNull(fu);
    }

    @Test
    public void twoDifferentCompanyFortressSameName() throws Exception {
        regService.registerSystemUser(new RegistrationBean("companya", "mike").setIsUnique(false));
        Fortress fortressA = fortressService.registerFortress("fortress-same");
        FortressUser fua = fortressService.getFortressUser(fortressA, "mike");

        setSecurity("harry");
        regService.registerSystemUser(new RegistrationBean("companyb", "harry").setIsUnique(false));
        Fortress fortressB = fortressService.registerFortress("fortress-same");
        FortressUser fub = fortressService.getFortressUser(fortressB, "mike");
        FortressUser fudupe = fortressService.getFortressUser(fortressB, "mike");

        assertNotSame("Fortress should be different", fortressA.getId(), fortressB.getId());
        assertNotSame("FortressUsers should be different", fua.getId(), fub.getId());
        assertEquals("FortressUsers should be the same", fub.getId(), fudupe.getId());
    }

    @Test
    public void companyNameCodeWithSpaces() throws DatagioException {
        String uid = "mike";
        String name = "Monowai Developments";
        SystemUser su = regService.registerSystemUser(new RegistrationBean(name, uid));
        assertNotNull(su);
        Company company = companyService.findByName(name);
        Assert.assertNotNull(company);
        assertEquals(name.replaceAll("\\s", "").toLowerCase(), company.getCode());

        Company comp = companyService.findByCode(company.getCode());
        assertNotNull(comp);
        assertEquals(comp.getId(), company.getId());

    }

    @Test
    public void fortressTZLocaleChecks() throws DatagioException {
        String uid = "mike";
        regService.registerSystemUser(new RegistrationBean("Monowai", uid));
        setSecurity(uid);
        // Null fortress
        Fortress fortressNull = fortressService.registerFortress(new FortressInputBean("wportfolio", true));
        assertNotNull(fortressNull.getLanguageTag());
        assertNotNull(fortressNull.getTimeZone());

        String testTimezone = TimeZone.getTimeZone("GMT").getID();
        assertNotNull(testTimezone);

        String languageTag = "en-GB";
        FortressInputBean fib = new FortressInputBean("uk-wp", true);
        fib.setLanguageTag(languageTag);
        fib.setTimeZone(testTimezone);
        Fortress custom = fortressService.registerFortress(fib);
        assertEquals(languageTag, custom.getLanguageTag());
        assertEquals(testTimezone, custom.getTimeZone());

        try {
            FortressInputBean fibError = new FortressInputBean("uk-wp", true);
            fibError.setTimeZone("Rubbish!");
            fail("No exception thrown for an illegal timezone");
        } catch (IllegalArgumentException e) {
            // This is what we expected
        }

        try {
            FortressInputBean fibError = new FortressInputBean("uk-wp", true);
            fibError.setLanguageTag("Rubbish!");
            fail("No exception thrown for an illegal languageTag");
        } catch (IllegalArgumentException e) {
            // This is what we expected
        }
        FortressInputBean fibNullSetter = new FortressInputBean("uk-wp", true);
        fibNullSetter.setLanguageTag(null);
        fibNullSetter.setTimeZone(null);
        Fortress fResult = fortressService.registerFortress(fibNullSetter);
        assertNotNull("Language not set to the default", fResult.getLanguageTag());
        assertNotNull("TZ not set to the default", fResult.getTimeZone());


    }

    @Test
    public void duplicateRegistrationFails() throws Exception{
        String companyA = "companya";
        String companyB = "companyb";
        try {
            registrationEP.registerSystemUser(new RegistrationBean(companyA, "mike"));
            registrationEP.registerSystemUser(new RegistrationBean(companyB, "mike"));
            Assert.fail("You can't have a duplicate registration");
        } catch ( DatagioException e ){
            // Expected
        }

    }

    @Test
    public void multipleFortressUserErrors() throws Exception {
        Long uid;
        String uname = "mike";
        // Assume the user has now logged in.
        String company = "MultiFortTest";
        regService.registerSystemUser(new RegistrationBean(company, uname));
        setSecurity(uname);
        CompanyUser nonAdmin = regService.addCompanyUser(uname, company);
        assertNotNull(nonAdmin);

        Fortress fortress = fortressService.registerFortress("auditbucket");
        assertNotNull(fortress);
        FortressUser fu = fortressService.getFortressUser(fortress, uname);
        assertNotNull(fu);
        uid = fu.getId();
        fu = fortressService.getFortressUser(fortress, "MIKE");
        assertEquals(uid, fu.getId());
        fu = fortressService.getFortressUser(fortress, "MikE");
        assertEquals(uid, fu.getId());
    }

    @Test
    public void findCompanyByNullApiKey() throws Exception {
        String uname = "mike";
        // Assume the user has now logged in.
        String company = "MultiFortTest";
        regService.registerSystemUser(new RegistrationBean(company, uname));
        setSecurity();
        Collection<Company> co = companyEP.findCompanies(null, null);
        Assert.assertFalse(co.isEmpty());
    }


}
