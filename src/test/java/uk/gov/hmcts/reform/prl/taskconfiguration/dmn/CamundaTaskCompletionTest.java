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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.gov.hmcts.reform.prl.taskconfiguration.DmnDecisionTable.WA_TASK_COMPLETION_FAMILY_PRL;

class CamundaTaskCompletionTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_COMPLETION_FAMILY_PRL;
    }

    static Stream<Arguments> scenarioProvider() {

        return Stream.of(
            Arguments.of(
                "issueAndSendToLocalCourtCallback",
                "CASE_ISSUE",
                singletonList(
                    Map.of(
                        "taskType", "checkApplication",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "fl401AddCaseNumber",
                "CASE_ISSUE",
                singletonList(
                    Map.of(
                        "taskType", "checkApplicationfl401",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "caseNumber",
                "CASE_ISSUE",
                singletonList(
                    Map.of(
                        "taskType", "addCaseNumber",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "returnApplication",
                null,
                singletonList(
                    Map.of(
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "sendToGateKeeper",
                "GATE_KEEPING",
                singletonList(
                    Map.of(
                        "taskType", "caseNumber",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "serviceOfApplication",
                "PREPARE_FOR_HEARING_CONDUCT_HEARING",
                singletonList(
                    Map.of(
                        "taskType", "sendToGateKeeper",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "manageOrders",
                "PREPARE_FOR_HEARING_CONDUCT_HEARING",
                singletonList(
                    Map.of(
                        "taskType", "sendToGateKeeper",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "manageOrders",
                "CASE_HEARING",
                asList(
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
                null,
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
    void given_event_ids_should_evaluate_dmn(String eventId, String postEventState, List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("state", postEventState);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {

        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getRules().size(), is(11));

    }


}
