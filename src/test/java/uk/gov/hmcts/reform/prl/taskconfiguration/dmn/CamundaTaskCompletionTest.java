package uk.gov.hmcts.reform.prl.taskconfiguration.dmn;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.hmcts.reform.prl.taskconfiguration.DmnDecisionTableBaseUnitTest;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.gov.hmcts.reform.prl.taskconfiguration.DmnDecisionTable.WA_TASK_COMPLETION;

class CamundaTaskCompletionTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_COMPLETION;
    }

    static Stream<Arguments> scenarioProvider() {

        return Stream.of(
            Arguments.of(
                "issueAndSendToLocalCourtCallback",
                singletonList(
                    Map.of(
                        "taskType", "checkApplication",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "fl401AddCaseNumber",
                singletonList(
                    Map.of(
                        "taskType", "checkApplicationfl401",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "caseNumber",
                singletonList(
                    Map.of(
                        "taskType", "addCaseNumber",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "returnApplication",
                singletonList(
                    Map.of(
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "sendToGateKeeper",
                singletonList(
                    Map.of(
                        "taskType", "caseNumber",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "serviceOfApplication",
                singletonList(
                    Map.of(
                        "taskType", "sendToGateKeeper",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "manageOrders",
                asList(
                    Map.of(
                         "taskType", "sendToGateKeeper",
                         "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "produceHearingBundle",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "gateKeeping",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "checkSolicitorOrderProvided",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "sendAndReplyToMessages",
                singletonList(
                    Map.of(
                        "taskType", "reviewOrder",
                        "completionMode", "Auto"
                    )
                )
            )
        );
    }

    @ParameterizedTest(name = "event id: {0} post event state: {1}")
    @MethodSource("scenarioProvider")
    void event_ids_should_evaluate_dmn(String eventId,List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {

        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getInputs().size(), is(1));
        assertThat(logic.getOutputs().size(), is(2));
        assertThat(logic.getRules().size(), is(11));
    }


}
