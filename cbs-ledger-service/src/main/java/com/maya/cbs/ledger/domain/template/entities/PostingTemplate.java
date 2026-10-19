package com.maya.cbs.ledger.domain.template.entities;

import java.util.List;
import java.util.UUID;

public class PostingTemplate {

    private UUID id;
    private String TransactionType;
    private boolean active;
    private int version;
    private List<PostingLeg> legs;
}
