package com.auditbucket.demo.services;

import com.auditbucket.bean.AuditResultBean;
import com.auditbucket.demo.domain.Account;
import com.auditbucket.demo.repository.AccountRepository;
import com.auditbucket.spring.AbClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    private AbClient abClient;

    public void saveAccount(Account account) throws IllegalAccessException {
        AuditResultBean auditResultBean = abClient.createAuditHeader(account);
        account.setAuditKey(auditResultBean.getAuditKey());
        accountRepository.save(account);

    }

}
