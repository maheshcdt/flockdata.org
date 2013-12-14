package com.auditbucket.search.model;

import com.auditbucket.helper.AuditException;
import com.auditbucket.registration.model.Fortress;

/**
 * User: Mike Holdsworth
 * Since: 5/09/13
 */
public class AuditSearchSchema {
    // Storage schema used in a Search Document
    public static final String WHAT = "@what";
    public static final String WHEN = "@when";
    public static final String CALLER_REF = "@code";
    public static final String DESCRIPTION = "@description";
    public static final String TIMESTAMP = "@timestamp";
    public static final String FORTRESS = "@fortress";
    public static final String DOC_TYPE = "@docType";

    public static final String TAG = "@tag";
    public static final String LAST_EVENT = "@lastEvent";
    public static final String WHO = "@who";
    public static final String AUDIT_KEY = "@auditKey";
    public static String CREATED = "@whenCreated"; // Date the document was first created

    public static final String WHAT_CODE = "code";
    public static final String WHAT_NAME = "name";
    public static final String WHAT_DESCRIPTION = "description";

    // Analyzer NGram Config Settings
    public static final String NGRM_WHAT_DESCRIPTION = "ngram_what_description";
    public static final String NGRM_WHAT_CODE = "ngram_what_code";
    public static final String NGRM_WHAT_NAME = "ngram_what_name";

    public static final String NGRM_WHAT_DESCRIPTION_MIN = "3";
    public static final String NGRM_WHAT_DESCRIPTION_MAX = "10";

    public static final String NGRM_WHAT_CODE_MIN = "2";
    public static final String NGRM_WHAT_CODE_MAX = "5";

    public static final String NGRM_WHAT_NAME_MIN = "3";
    public static final String NGRM_WHAT_NAME_MAX = "10";

    public static String parseIndex(Fortress fortress) throws AuditException {
        if (fortress.getCompany().getCode() == null)
            throw new AuditException("Company code is null");
        return "ab." + fortress.getCompany().getCode() + "." + fortress.getCode();
    }

}
