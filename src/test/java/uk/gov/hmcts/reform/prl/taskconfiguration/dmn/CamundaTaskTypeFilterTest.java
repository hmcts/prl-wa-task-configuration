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

import java.util.ArrayList;
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
        List<Map<String, String>> taskTypes = new ArrayList<>();
        taskTypes.add(Map.of("taskTypeId", "checkApplicationC100",
                "taskTypeName", "Check application C100"));
        taskTypes.add(Map.of("taskTypeId", "checkApplicationResubmittedC100",
                "taskTypeName", "Check resubmitted application C100"));
        taskTypes.add(Map.of("taskTypeId", "checkApplicationFL401",
                "taskTypeName", "Check application FL401"));
        taskTypes.add(Map.of("taskTypeId", "checkApplicationResubmittedFL401",
                "taskTypeName", "Check resubmitted application FL401"));
        taskTypes.add(Map.of("taskTypeId", "addCaseNumber",
                "taskTypeName", "Add Case Number"));
        taskTypes.add(Map.of("taskTypeId", "addCaseNumberResubmitted",
                "taskTypeName", "Add Case Number Resubmitted"));
        taskTypes.add(Map.of("taskTypeId", "sendToGateKeeperFL401",
                "taskTypeName", "Send To Gatekeeper FL401"));
        taskTypes.add(Map.of("taskTypeId", "sendToGateKeeperC100",
                "taskTypeName", "Send To Gatekeeper C100"));
        taskTypes.add(Map.of("taskTypeId", "sendToGateKeeperResubmittedFL401",
                "taskTypeName", "Send to Gatekeeper Resubmitted FL401"));
        taskTypes.add(Map.of("taskTypeId", "sendToGateKeeperResubmittedC100",
                "taskTypeName", "Send to Gatekeeper Resubmitted C100"));
        taskTypes.add(Map.of("taskTypeId", "directionOnIssue",
                "taskTypeName", "Directions on Issue"));
        taskTypes.add(Map.of("taskTypeId", "directionOnIssueResubmitted",
                "taskTypeName", "Directions on Issue Resubmitted"));
        taskTypes.add(Map.of("taskTypeId", "gateKeeping",
                "taskTypeName", "Gatekeeping"));
        taskTypes.add(Map.of("taskTypeId", "gateKeepingResubmitted",
                "taskTypeName", "Gatekeeping Resubmitted"));
        taskTypes.add(Map.of("taskTypeId", "serviceOfApplicationC100",
                "taskTypeName", "Service Of Application C100"));
        taskTypes.add(Map.of("taskTypeId", "serviceOfApplicationFL401",
                "taskTypeName", "Service Of Application FL401"));
        taskTypes.add(Map.of("taskTypeId", "produceHearingBundleC100",
                "taskTypeName", "Produce Hearing Bundle C100"));
        taskTypes.add(Map.of("taskTypeId", "produceHearingBundleFL100",
                "taskTypeName", "Produce Hearing Bundle FL401"));
        taskTypes.add(Map.of("taskTypeId", "reviewSolicitorOrderProvided",
                "taskTypeName", "Review and Approve Legal rep Order / Review resubmitted Order"));
        taskTypes.add(Map.of("taskTypeId", "requestSolicitorOrderC100",
                "taskTypeName", "Request Solicitor Order C100"));
        taskTypes.add(Map.of("taskTypeId", "requestSolicitorOrderFL401",
                "taskTypeName", "Request Solicitor Order FL401"));
        taskTypes.add(Map.of("taskTypeId", "updateHearingActualsC100",
                "taskTypeName", "Update Hearing Actuals C100"));
        taskTypes.add(Map.of("taskTypeId", "updateHearingActualsFL401",
                "taskTypeName", "Update Hearing Actuals FL401"));
        taskTypes.add(Map.of("taskTypeId", "reviewCorrespondenceC100",
                "taskTypeName", "Review Documents C100"));
        taskTypes.add(Map.of("taskTypeId", "reviewCorrespondenceFL401",
                "taskTypeName", "Review Documents FL401"));
        taskTypes.add(Map.of("taskTypeId", "removeLegalRepresentativeC100",
                "taskTypeName", "Remove legal representative C100"));
        taskTypes.add(Map.of("taskTypeId", "removeLegalRepresentativeFL401",
                "taskTypeName", "Remove legal representative FL401"));
        taskTypes.add(Map.of("taskTypeId", "confidentialCheckSOA",
                "taskTypeName", "C8 - Confidential details check"));
        taskTypes.add(Map.of("taskTypeId", "replyToMessageForCourtAdminFL401",
                "taskTypeName", "Reply To Message FL401"));
        taskTypes.add(Map.of("taskTypeId", "replyToMessageForCourtAdminC100",
                "taskTypeName", "Reply To Message C100"));
        taskTypes.add(Map.of("taskTypeId", "replyToMessageForLA",
                "taskTypeName", "Reply To Message C100"));
        taskTypes.add(Map.of("taskTypeId", "replyToMessageForJudiciary",
                "taskTypeName", "Reply To Message Judicial"));
        taskTypes.add(Map.of("taskTypeId", "reviewDocumentsForSolAndCafcassC100",
                "taskTypeName", "C100 Review Documents Submitted By Sol or Cafcass"));
        taskTypes.add(Map.of("taskTypeId", "reviewDocumentsForSolAndCafcassFL401",
                "taskTypeName", "FL401 Review Documents Submitted By Sol or Cafcass"));
        taskTypes.add(Map.of("taskTypeId", "reviewRaRequestsC100",
                "taskTypeName", "Review RA request"));
        taskTypes.add(Map.of("taskTypeId", "reviewRaRequestsFL401",
                "taskTypeName", "Review RA request"));
        taskTypes.add(Map.of("taskTypeId", "reviewInactiveRaRequestsC100",
                "taskTypeName", "Review inactive RA request"));
        taskTypes.add(Map.of("taskTypeId", "reviewInactiveRaRequestsFL401",
                "taskTypeName", "Review inactive RA request"));
        taskTypes.add(Map.of("taskTypeId", "listWithoutNoticeHearingC100",
                "taskTypeName", "List without notice hearing - C100"));
        taskTypes.add(Map.of("taskTypeId", "listWithoutNoticeHearingFL401",
                             "taskTypeName", "List without notice hearing - FL401"));
        taskTypes.add(Map.of("taskTypeId", "listOnNoticeHearingFL401",
                "taskTypeName", "Listing instructions (refer to case notes)"));
        taskTypes.add(Map.of("taskTypeId", "reviewAdditionalApplication",
                "taskTypeName", "Review additional application"));
        taskTypes.add(Map.of("taskTypeId", "reviewLangAndSmReq",
                "taskTypeName", "Review Language and SM requirements"));
        taskTypes.add(Map.of("taskTypeId", "adminServeOrderC100",
                "taskTypeName", "Complete the Order / Service Of Order C100"));
        taskTypes.add(Map.of("taskTypeId", "adminServeOrderFL401",
                "taskTypeName", "Complete the Order / Service Of Order FL401"));
        taskTypes.add(Map.of("taskTypeId", "appStatementOfServiceByAdmin",
                "taskTypeName", "Arrange personal service of application and upload statement of service"));
        taskTypes.add(Map.of("taskTypeId", "appStatementOfServiceByBailiff",
                "taskTypeName", "Application statement of service due"));
        taskTypes.add(Map.of("taskTypeId", "appStatementOfServiceByLiP",
                "taskTypeName", "Application statement of service due"));
        taskTypes.add(Map.of("taskTypeId", "appStatementOfServiceBySol",
                "taskTypeName", "Application statement of service due"));
        taskTypes.add(Map.of("taskTypeId", "arrangeBailiffSOA",
                "taskTypeName", "Arrange bailiff service of application"));
        taskTypes.add(Map.of("taskTypeId", "completefl416AndServe",
                "taskTypeName", "Complete FL416 and serve applicant only"));
        taskTypes.add(Map.of("taskTypeId", "createHearingRequest",
                "taskTypeName", "Create Hearing Request"));
        taskTypes.add(Map.of("taskTypeId", "createHearingRequestReserveListAssist",
                "taskTypeName", "Create Hearing Request"));
        taskTypes.add(Map.of("taskTypeId", "createMultipleHearingRequest",
                "taskTypeName", "Create Multiple Hearing Request"));
        taskTypes.add(Map.of("taskTypeId", "recreateApplicationPack",
                "taskTypeName", "Recreate Application Pack"));
        taskTypes.add(Map.of("taskTypeId", "reviewAdminOrderProvided",
                "taskTypeName", "Review and Approve Admin Order"));
        taskTypes.add(Map.of("taskTypeId", "reviewAdminOrderByManager",
                "taskTypeName", "Review and Approve Admin Order"));
        taskTypes.add(Map.of("taskTypeId", "checkHwfApplicationC100",
                             "taskTypeName", "Check HWF application"));
        taskTypes.add(Map.of("taskTypeId", "checkAwpHwfCitizen",
                             "taskTypeName", "Check HWF on additional application"));
        taskTypes.add(Map.of("taskTypeId", "confidentialCheckDocuments",
                             "taskTypeName", "Confidential check - Documents"));
        taskTypes.add(Map.of("taskTypeId", "checkAndReServeDocuments",
                             "taskTypeName", "Check and re-serve documents"));
        taskTypes.add(Map.of("taskTypeId", "newCaseTransferredToCourt",
                             "taskTypeName", "Case transferred to your court"));
        taskTypes.add(Map.of("taskTypeId", "reqSafeguardingLetterUpdate",
                             "taskTypeName", "Request to upload Safeguarding Letter from Cafcass / Cafcass Cymru"));
        taskTypes.add(Map.of("taskTypeId", "responseToInformationRequested",
                             "taskTypeName", "Response to information requested"));
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
        assertThat(logic.getRules().size(), is(64));
    }
}
