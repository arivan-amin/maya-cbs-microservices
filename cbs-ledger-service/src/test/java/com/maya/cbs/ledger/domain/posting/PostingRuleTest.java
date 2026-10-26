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

    private UUID revenueGeneralLedgerId;
    private UUID feeGeneralLedgerId;
    private UUID taxGeneraLedgerId;

    @Test
    void _should_when () {
        // given
        PostingRule rule = PostingRule.createPostingRule(UUID.randomUUID(), "SHOP_PURCHASE_DEFAULT",
            PostingRuleStatus.ACTIVE, PostingRuleSelector.builder()
                .movementType(FinancialMovementType.SHOP_PURCHASE)
                .build(), List.of(LegTemplate.debit(AmountComponent.GROSS,
                    new BoundAccount(AccountRole.AGENT_WALLET)),
                LegTemplate.credit(AmountComponent.PRINCIPAL,
                    new FixedAccount(revenueGeneralLedgerId)),
                LegTemplate.credit(AmountComponent.FEE, new FixedAccount(feeGeneralLedgerId)),
                LegTemplate.credit(AmountComponent.TAX, new FixedAccount(taxGeneraLedgerId))));

        // when

        // then

    }
}
