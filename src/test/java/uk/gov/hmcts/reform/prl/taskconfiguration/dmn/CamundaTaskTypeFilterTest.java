package uk.gov.hmcts.reform.prl.taskconfiguration.dmn;

import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.gov.hmcts.reform.prl.taskconfiguration.DmnDecisionTable;
import uk.gov.hmcts.reform.prl.taskconfiguration.DmnDecisionTableBaseUnitTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class CamundaTaskTypeFilterTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = DmnDecisionTable.WA_TASK_TASKTYPEFILTER;
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getInputs().size(), is(1));
        assertThat(logic.getOutputs().size(), is(2));
        assertThat(logic.getRules().size(), is(46));
    }
}
