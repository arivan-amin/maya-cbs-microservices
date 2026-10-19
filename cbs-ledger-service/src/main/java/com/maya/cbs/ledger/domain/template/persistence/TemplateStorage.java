package com.maya.cbs.ledger.domain.template.persistence;

import com.maya.cbs.ledger.domain.template.entities.PostingTemplate;

public interface TemplateStorage {

    PostingTemplate findByTransactionType (String type);
}
