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
                asList(
                    Map.of(
                        "taskType", "checkApplicationC100",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "checkApplicationResubmittedC100",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "returnApplication",
                asList(
                    Map.of(
                        "taskType", "checkApplicationC100",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "checkApplicationResubmittedC100",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "checkApplicationFL401",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "checkApplicationResubmittedFL401",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "addCaseNumber",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "sendToGateKeeperFL401",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "sendToGateKeeperC100",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "sendToGateKeeperResubmittedC100",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "sendToGateKeeperResubmittedFL401",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "directionOnIssue",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "directionOnIssueResubmitted",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "gateKeeping",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "gateKeepingResubmitted",
                        "completionMode", "Auto"
                    ),
                    Map.of()
                )
            ),
            Arguments.of(
                "manageOrders",
                asList(
                    Map.of(
                        "taskType", "sendToGateKeeperFL401",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "sendToGateKeeperC100",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "sendToGateKeeperResubmittedC100",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "sendToGateKeeperResubmittedFL401",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "directionOnIssue",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "directionOnIssueResubmitted",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "gateKeeping",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "gateKeepingResubmitted",
                        "completionMode", "Auto"
                    ),
                    Map.of()
                )
            ),
            Arguments.of(
                "fl401AddCaseNumber",
                asList(
                    Map.of(
                        "taskType", "checkApplicationFL401",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "checkApplicationResubmittedFL401",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "caseNumber",
                asList(
                    Map.of(
                        "taskType", "addCaseNumber",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "addCaseNumberResubmitted",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "sendToGateKeeper",
                asList(
                    Map.of(
                        "taskType", "sendToGateKeeperC100",
                        "completionMode", "Auto"
                    ), Map.of(
                        "taskType", "sendToGateKeeperResubmittedC100",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "fl401SendToGateKeeper",
                asList(
                    Map.of(
                        "taskType", "sendToGateKeeperFL401",
                        "completionMode", "Auto"
                    ), Map.of(
                        "taskType", "sendToGateKeeperResubmittedFL401",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "createBundle",
                asList(
                    Map.of(
                        "taskType", "produceHearingBundleC100",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "produceHearingBundleFL401",
                        "completionMode", "Auto"
                    ),
                    Map.of()
                )
            ),
            Arguments.of(
                "editAndApproveAnOrder",
                asList(
                    Map.of(
                        "taskType", "reviewSolicitorOrderProvided",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "reviewAdminOrderProvided",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "reviewAdminOrderByManager",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "adminEditAndApproveAnOrder",
                asList(
                    Map.of(
                        "taskType", "adminServeOrderC100",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "adminServeOrderFL401",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "requestSolicitorOrderC100",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "requestSolicitorOrderFL401",
                        "completionMode", "Auto"
                    ),
                    Map.of()
                )
            ),
            Arguments.of(
                "updateHearingActuals",
                asList(
                    Map.of(
                        "taskType", "updateHearingActualsC100",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "updateHearingActualsFL401",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "adminRemoveLegalRepresentativeC100",
                asList(
                    Map.of(
                        "taskType", "removeLegalRepresentativeC100",
                        "completionMode", "Auto"
                    ),
                    Map.of()
                )
            ),
            Arguments.of(
                "adminRemoveLegalRepresentativeFL401",
                asList(
                    Map.of(
                        "taskType", "removeLegalRepresentativeFL401",
                        "completionMode", "Auto"
                    ),
                    Map.of()
                )
            ),
            Arguments.of(
                "serviceOfApplication",
                asList(
                    Map.of(
                        "taskType", "recreateApplicationPack",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "serviceOfApplicationC100",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "serviceOfApplicationFL401",
                        "completionMode", "Auto"
                    ),
                    Map.of()
                )
            ),
            Arguments.of(
                "statementOfService",
                asList(
                    Map.of(
                        "taskType", "appStatementOfServiceByBailiff",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "arrangeBailiffSOA",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "appStatementOfServiceByAdmin",
                        "completionMode", "Auto"
                    ),
                    Map.of()
                )
            ),
            Arguments.of(
                "confidentialityCheck",
                asList(
                    Map.of(
                        "taskType", "confidentialCheckSOA",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "c100ManageFlags",
                asList(
                    Map.of(
                        "taskType", "reviewRaRequestsC100",
                        "completionMode", "Auto"
                    ),
                    Map.of()
                )
            ),
            Arguments.of(
                "fl401ManageFlags",
                asList(
                    Map.of(
                        "taskType", "reviewRaRequestsFL401",
                        "completionMode", "Auto"
                    ),
                    Map.of()
                )
            ),
            Arguments.of(
                "listWithoutNotice",
                asList(
                    Map.of(
                        "taskType", "directionOnIssue",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "directionOnIssueResubmitted",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "fl401ListOnNotice",
                asList(
                    Map.of(
                        "taskType", "directionOnIssue",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "directionOnIssueResubmitted",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "serviceOfDocumentsConfCheck",
                List.of(
                    Map.of(
                        "taskType", "confidentialCheckDocuments",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "serviceOfDocuments",
                List.of(
                    Map.of(
                        "taskType", "checkAndReServeDocuments",
                        "completionMode", "Auto"
                    )
                )
            )
        );
    }

    @ParameterizedTest(name = "event id: {0} post event state: {1}")
    @MethodSource("scenarioProvider")
    void event_ids_should_evaluate_dmn(String eventId, List<Map<String, String>> expectation) {

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
        assertThat(logic.getRules().size(), is(67));
    }


}
