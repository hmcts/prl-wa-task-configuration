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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.gov.hmcts.reform.prl.taskconfiguration.DmnDecisionTable.WA_TASK_CANCEL;

class CamundaTaskCancellationTest extends DmnDecisionTableBaseUnitTest {
    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_CANCEL;
    }

    static Stream<Arguments> scenarioProvider() {

        return Stream.of(
            Arguments.of(
                "SUBMITTED_PAID",
                "WithdrawApplication_Event",
                "CASE_WITHDRAWN",
                List.of(
                    Map.of(
                        "action", "Cancel",
                        "processCategories", "applicationCheck"
                    )
                )
            ),
            Arguments.of(
                "DECISION_OUTCOME",
                "draftAnOrder",
                "DECISION_OUTCOME",
                List.of(
                    Map.of(
                        "action", "Cancel",
                        "processCategories", "requestSolicitorOrder"
                    )
                )
            ),
            Arguments.of(
                null,
                "statementOfService",
                null,
                List.of(
                    Map.of(
                        "action", "Cancel",
                        "processCategories", "statementOfServiceBySolicitor"
                    ),
                    Map.of(
                        "action", "Cancel",
                        "processCategories", "arrangeBailiffSOA"
                    )
                )
            ),
            Arguments.of(
                null,
                "amendApplicantsDetails",
                null,
                List.of(
                    Map.of(
                        "action", "Reconfigure"
                    )
                )
            ),
            Arguments.of(
                null,
                "amendCourtDetails",
                null,
                List.of(
                    Map.of(
                        "action", "Reconfigure"
                    )
                )
            ),
            Arguments.of(
                null,
                "hmcCaseUpdDecOutcome",
                null,
                List.of(
                    Map.of(
                        "action", "Reconfigure"
                    )
                )
            ),
            Arguments.of(
                null,
                "addCaseNote",
                null,
                List.of(
                    Map.of(
                        "action", "Reconfigure"
                    )
                )
            ),
            Arguments.of(
                null,
                "transferToAnotherCourt",
                null,
                List.of(
                    Map.of(
                        "action", "Reconfigure"
                    )
                )
            ),
            Arguments.of(
                null,
                "issueAndSendToLocalCourtCallback",
                null,
                List.of(
                    Map.of(
                        "action", "Reconfigure"
                    )
                )
            ),
            Arguments.of(
                null,
                "allAwPInReview",
                null,
                List.of(
                    Map.of(
                        "action", "Cancel",
                        "processCategories", "reviewAddtlApp"
                    )
                )
            ),
            Arguments.of(
                null,
                "hmcCaseUpdPrepForHearing",
                null,
                List.of(
                    Map.of(
                        "action", "Cancel",
                        "processCategories", "createHearingRequest"
                    )
                )
            ),
            Arguments.of(
                null,
                "hwfProcessCaseUpdate",
                null,
                List.of(
                    Map.of(
                        "action", "Cancel",
                        "processCategories", "applicationHwfCheck"
                    )
                )
            ),
            Arguments.of(
                null,
                "processHwfUpdateAwpStatus",
                null,
                List.of(
                    Map.of(
                        "action", "Cancel",
                        "processCategories", "checkAwpHwfCitizen"
                    )
                )
            ),
            Arguments.of(
                null,
                "processUrgentHelpWithFees",
                null,
                List.of(
                    Map.of(
                        "action", "Cancel",
                        "processCategories", "applicationHwfCheck"
                    ),
                    Map.of(
                        "action", "Cancel",
                        "processCategories", "checkAwpHwfCitizen"
                    )
                )
            )
        );
    }

    @ParameterizedTest(name = "from state: {0}, event id: {1}, state: {2}")
    @MethodSource("scenarioProvider")
    void given_multiple_event_ids_should_evaluate_dmn(String fromState,
                                                      String eventId,
                                                      String state,
                                                      List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("fromState", fromState);
        inputVariables.putValue("event", eventId);
        inputVariables.putValue("state", state);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {

        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getInputs().size(), is(3));
        assertThat(logic.getOutputs().size(), is(4));
        assertThat(logic.getRules().size(), is(16));

    }
}
