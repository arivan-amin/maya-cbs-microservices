package com.maya.cbs.ledger.domain.posting;

import com.maya.cbs.ledger.domain.coa.entity.FinancialMovementType;
import com.maya.cbs.testing.architecture.bases.BaseUnitTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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
            .version(1)
            .status(PostingRuleStatus.ACTIVE)
            .selector(PostingRuleSelector.builder()
                .movementType(FinancialMovementType.SHOP_PURCHASE)
                .build())
            .effectiveFrom(LocalDate.of(2026, 1, 1))
            .legs(List.of(LegTemplate.debit(AmountComponent.GROSS,
                    new BoundAccount(AccountRole.CUSTOMER_WALLET)),
                LegTemplate.credit(AmountComponent.PRINCIPAL, new FixedAccount(revenueGlId)),
                LegTemplate.credit(AmountComponent.FEE, new FixedAccount(feeGlId)),
                LegTemplate.credit(AmountComponent.TAX, new FixedAccount(taxGlId))))
            .build();

        // when

        // then

    }
}
