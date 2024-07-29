package uk.gov.hmcts.reform.prl.taskconfiguration.dmn;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.hmcts.reform.prl.taskconfiguration.DmnDecisionTableBaseUnitTest;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.gov.hmcts.reform.prl.taskconfiguration.DmnDecisionTable.WA_TASK_PERMISSION_EXCEPTION_RECORD;

class CamundaTaskPermissionForExceptionRecordTest extends DmnDecisionTableBaseUnitTest {


    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_PERMISSION_EXCEPTION_RECORD;
    }

    /*
        important: permissions rules in the DMN are in order, in case you can't find why your test fails.
     */
    @ParameterizedTest
    @MethodSource("scenarioProvider")
    void given_taskType_and_CaseData_when_evaluate_then_returns_expected_rules(
        String taskType,

        List<Map<String, Serializable>> expectedRules) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        Assertions.assertEquals(expectedRules, dmnDecisionTableResult);
    }

    static Stream<Arguments> scenarioProvider() {
        return Stream.of(
            Arguments.of(
                "reviewExceptionBulkScanRecord",
                List.of(
                    Map.of(
                        "authorisations","SKILL:ABA5:CHECKAPPLICATIONFL401",
                        "roleCategory", "ADMIN",
                        "name", "hearing-centre-admin",
                        "autoAssignable", false,
                        "value", "Read,Own,CompleteOwn,CancelOwn,UnclaimAssign,Claim,Unclaim,UnassignClaim"
                    ),
                    Map.of(
                        "roleCategory", "CTSC",
                        "authorisations","SKILL:ABA5:CHECKAPPLICATIONC100",
                        "autoAssignable", false,
                        "name", "ctsc",
                        "value", "Read,Own,CompleteOwn,CancelOwn,UnclaimAssign,Claim,Unclaim,UnassignClaim"

                    )
                )
            )
        );
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getInputs().size(), is(2));
        assertThat(logic.getOutputs().size(), is(7));
        assertThat(logic.getRules().size(), is(2));
    }
}
