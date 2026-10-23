package com.maya.cbs.ledger.domain.posting;

import java.util.UUID;

public interface LegAccountSource {

    UUID resolve (AccountBindings bindings);
}
