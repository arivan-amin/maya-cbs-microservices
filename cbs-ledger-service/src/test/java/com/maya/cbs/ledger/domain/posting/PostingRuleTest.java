package com.maya.cbs.ledger.domain.posting;

import com.maya.cbs.ledger.domain.account.entity.*;
import com.maya.cbs.ledger.domain.coa.entity.FinancialMovementType;
import com.maya.cbs.ledger.domain.posting.entity.*;
import com.maya.cbs.testing.architecture.bases.BaseUnitTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

@Slf4j
class PostingRuleTest implements BaseUnitTest {

    private UUID revenueGlId;
    private UUID feeGlId;
    private UUID taxGlId;

    @Test
    void _should_when () {
        // given
        PostingRule rule = PostingRule.builder()
            .id(UUID.randomUUID())
            .code("SHOP_PURCHASE_DEFAULT")
            .status(PostingRuleStatus.ACTIVE)
            .selector(PostingRuleSelector.builder()
                .movementType(FinancialMovementType.SHOP_PURCHASE)
                .build())
            .legs(List.of(LegTemplate.debit(AmountComponent.GROSS,
                    new BoundAccount(AccountRole.AGENT_WALLET)),
                LegTemplate.credit(AmountComponent.PRINCIPAL, new FixedAccount(revenueGlId)),
                LegTemplate.credit(AmountComponent.FEE, new FixedAccount(feeGlId)),
                LegTemplate.credit(AmountComponent.TAX, new FixedAccount(taxGlId))))
            .build();

        // when

        // then

    }
}
