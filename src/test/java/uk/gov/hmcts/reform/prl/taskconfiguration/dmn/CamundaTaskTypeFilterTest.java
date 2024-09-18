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
import uk.gov.hmcts.reform.prl.taskconfiguration.DmnDecisionTable;
import uk.gov.hmcts.reform.prl.taskconfiguration.DmnDecisionTableBaseUnitTest;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class CamundaTaskTypeFilterTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = DmnDecisionTable.WA_TASK_TASKTYPEFILTER;
    }

    static Stream<Arguments> scenarioProvider() {
        List<Map<String, String>> taskTypes = List.of(
            Map.of(
                "taskTypeId", "checkApplicationC100",
                "taskTypeName", "Check application C100"
            ),
            Map.of(
                "taskTypeId", "checkApplicationResubmittedC100",
                "taskTypeName", "Check resubmitted application C100"
            ),
            Map.of(
                "taskTypeId", "checkApplicationFL401",
                "taskTypeName", "Check application FL401"
            ),
            Map.of(
                "taskTypeId", "checkApplicationResubmittedFL401",
                "taskTypeName", "Check resubmitted application FL401"
            ),
            Map.of(
                "taskTypeId", "addCaseNumber",
                "taskTypeName", "Add Case Number"
            ),
            Map.of(
                "taskTypeId", "AddCaseNumberResubmitted",
                "taskTypeName", "Add Case Number Resubmitted"
            ),
            Map.of(
                "taskTypeId", "sendToGateKeeperFL401",
                "taskTypeName", "Send To Gatekeeper FL401"
            ),
            Map.of(
                "taskTypeId", "sendToGateKeeperC100",
                "taskTypeName", "Send To Gatekeeper C100"
            ),
            Map.of(
                "taskTypeId", "sendToGateKeeperResubmittedFL401",
                "taskTypeName", "Send to Gatekeeper Resubmitted FL401"
            ),
            Map.of(
                "taskTypeId", "sendToGateKeeperResubmittedC100",
                "taskTypeName", "Send to Gatekeeper Resubmitted C100"
            ),
            Map.of(
                "taskTypeId", "directionOnIssue",
                "taskTypeName", "Directions on Issue"
            ),
            Map.of(
                "taskTypeId", "directionOnIssueResubmitted",
                "taskTypeName", "Directions on Issue Resubmitted"
            ),
            Map.of(
                "taskTypeId", "gateKeeping",
                "taskTypeName", "Gatekeeping"
            ),
            Map.of(
                "taskTypeId", "gateKeepingResubmitted",
                "taskTypeName", "Gatekeeping Resubmitted"
            ),
            Map.of(
                "taskTypeId", "serviceOfApplicationC100",
                "taskTypeName", "Service Of Application C100"
            ),
            Map.of(
                "taskTypeId", "serviceOfApplicationFL401",
                "taskTypeName", "Service Of Application FL401"
            ),
            Map.of(
                "taskTypeId", "serviceOfOrderC100",
                "taskTypeName", "Service Of Order C100"
            ),
            Map.of(
                "taskTypeId", "serviceOfOrderFL401",
                "taskTypeName", "Service Of Order FL401"
            ),
            Map.of(
                "taskTypeId", "produceHearingBundleC100",
                "taskTypeName", "Produce Hearing Bundle C100"
            ),
            Map.of(
                "taskTypeId", "produceHearingBundleFL401",
                "taskTypeName", "Produce Hearing Bundle FL401"
            ),
            Map.of(
                "taskTypeId", "createOrderC100",
                "taskTypeName", "Create Order C100"
            ),
            Map.of(
                "taskTypeId", "createOrderFL401",
                "taskTypeName", "Create Order FL401"
            ),
            Map.of(
                "taskTypeId", "reviewSolicitorOrderProvided",
                "taskTypeName", "Review Solicitor Order"
            ),
            Map.of(
                "taskTypeId", "requestSolicitorOrderC100",
                "taskTypeName", "Request Solicitor Order C100"
            ),
            Map.of(
                "taskTypeId", "requestSolicitorOrderFL401",
                "taskTypeName", "Request Solicitor Order FL401"
            ),
            Map.of(
                "taskTypeId", "updateHearingActualsC100",
                "taskTypeName", "Update Hearing Actuals C100"
            ),
            Map.of(
                "taskTypeId", "updateHearingActualsFL401",
                "taskTypeName", "Update Hearing Actuals FL401"
            ),
            Map.of(
                "taskTypeId", "reviewCorrespondenceC100",
                "taskTypeName", "Review Correspondence C100"
            ),
            Map.of(
                "taskTypeId", "reviewCorrespondenceFL401",
                "taskTypeName", "Review Correspondence FL401"
            ),
            Map.of(
                "taskTypeId", "removeLegalRepresentativeC100",
                "taskTypeName", "Remove legal representative C100"
            ),
            Map.of(
                "taskTypeId", "removeLegalRepresentativeFL401",
                "taskTypeName", "Remove legal representative FL401"
            ),
            Map.of(
                "taskTypeId", "confidentialCheckSOA",
                "taskTypeName", "C8 - Confidential details check"
            ),
            Map.of(
                "taskTypeId", "replyToMessageForCourtAdminFL401",
                "taskTypeName", "Reply To Message FL401"
            ),
            Map.of(
                "taskTypeId", "replyToMessageForCourtAdminC100",
                "taskTypeName", "Reply To Message C100"
            ),
            Map.of(
                "taskTypeId", "replyToMessageForLA",
                "taskTypeName", "Reply To Message C100"
            ),
            Map.of(
                "taskTypeId", "replyToMessageForJudiciary",
                "taskTypeName", "Reply To Message Judicial"
            ),
            Map.of(
                "taskTypeId", "reviewDocumentsForSolAndCafcassC100",
                "taskTypeName", "C100 Review Documents Submitted By Sol or Cafcass"
            ),
            Map.of(
                "taskTypeId", "reviewDocumentsForSolAndCafcassFL401",
                "taskTypeName", "FL401 Review Documents Submitted By Sol or Cafcass"
            ),
            Map.of(
                "taskTypeId", "reviewRaRequestsC100",
                "taskTypeName", "Review RA request"
            ),
            Map.of(
                "taskTypeId", "reviewRaRequestsFL401",
                "taskTypeName", "Review RA request"
            ),
            Map.of(
                "taskTypeId", "reviewInactiveRaRequestsC100",
                "taskTypeName", "Review inactive RA request"
            ),
            Map.of(
                "taskTypeId", "reviewInactiveRaRequestsFL401",
                "taskTypeName", "Review inactive RA request"
            ),
            Map.of(
                "taskTypeId", "listWithoutNoticeHearingC100",
                "taskTypeName", "List without notice hearing(see case notes)"
            ),
            Map.of(
                "taskTypeId", "listWithoutNoticeHearingFL401",
                "taskTypeName", "List without notice hearing(see case notes)"
            ),
            Map.of(
                "taskTypeId", "listOnNoticeHearingFL401",
                "taskTypeName", "Listing instructions (refer to case notes)"
            ),
            Map.of(
                "taskTypeId", "reviewAdditionalApplication",
                "taskTypeName", "Review additional application"
            ),
            Map.of(
                "taskTypeId", "reviewLangAndSmReq",
                "taskTypeName", "Review Language and SM requirements"
            )
        );
        return Stream.of(
            Arguments.of(
                taskTypes
            )
        );
    }

    @ParameterizedTest(name = "retrieve all task type data")
    @MethodSource("scenarioProvider")
    void should_evaluate_dmn_return_all_task_type_fields(List<Map<String, Object>> expectedTaskTypes) {

        VariableMap inputVariables = new VariableMapImpl();
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectedTaskTypes));

    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getInputs().size(), is(1));
        assertThat(logic.getOutputs().size(), is(2));
        assertThat(logic.getRules().size(), is(47));
    }
}
