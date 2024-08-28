package uk.gov.hmcts.reform.prl.taskconfiguration.dmn;


import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.gov.hmcts.reform.prl.taskconfiguration.DmnDecisionTable;
import uk.gov.hmcts.reform.prl.taskconfiguration.DmnDecisionTableBaseUnitTest;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CamundaTaskConfigurationForExceptionRecordTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = DmnDecisionTable.WA_TASK_CONFIGS_EXCEPTION_RECORD;
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getInputs().size(), is(2));
        assertThat(logic.getOutputs().size(), is(3));
        assertThat(logic.getRules().size(), is(6));
    }

    @Test
    void when_given_task_type_then_return_dueDateIntervalDays() {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskType", "reviewExceptionBulkScanRecord");

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "dueDateIntervalDays",
            "value", "1"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "majorPriority",
            "value", "1000"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "workType",
            "value", "routine_work"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "roleCategory",
            "value", "ADMIN"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "roleCategory",
            "value", "CTSC"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "description",
            "value", "[Review Exception Record Documents](/cases/case-details/${[CASE_REFERENCE]}"
                + "/trigger/attachToExistingCase/attachToExistingCase1)"
        )));
    }
}
