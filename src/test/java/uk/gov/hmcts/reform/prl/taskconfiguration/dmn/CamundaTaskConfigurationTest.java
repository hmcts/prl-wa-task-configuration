package uk.gov.hmcts.reform.prl.taskconfiguration.dmn;


import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.gov.hmcts.reform.prl.taskconfiguration.DmnDecisionTable;
import uk.gov.hmcts.reform.prl.taskconfiguration.DmnDecisionTableBaseUnitTest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CamundaTaskConfigurationTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = DmnDecisionTable.WA_TASK_CONFIGS;
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getInputs().size(), is(2));
        assertThat(logic.getOutputs().size(), is(3));
        assertThat(logic.getRules().size(), is(86));
    }

    @Test
    void when_caseData_then_return_expected_appealType() {
        VariableMap inputVariables = new VariableMapImpl();
        Map<String, Object> caseData = new HashMap<>(); // allow null values
        caseData.put("appealType", "appealType");
        inputVariables.putValue("caseData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "appealType",
            "value", "appealType"
        )));
    }

    @Test
    void when_caseData_then_return_expected_caseManagementLocation() {
        String region = "2";
        String baseLocation = "283922";
        String baseLocationName = "STOKE ON TRENT TRIBUNAL HEARING CENTRE";
        Map<String, Object> caseManagementLocation = new HashMap<>();
        caseManagementLocation.put("baseLocation", baseLocation);
        caseManagementLocation.put("region", region);
        caseManagementLocation.put("baseLocationName", baseLocationName);
        Map<String, Object> caseData = new HashMap<>(); // allow null values
        caseData.put("caseManagementLocation", caseManagementLocation);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "region",
            "value", "2",
            "canReconfigure", true
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "location",
            "value", "283922",
            "canReconfigure", true
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "locationName",
            "value", "STOKE ON TRENT TRIBUNAL HEARING CENTRE",
            "canReconfigure", true
        )));
    }

    @Test
    void when_caseData_then_return_expected_caseManagementCategory() {
        VariableMap inputVariables = new VariableMapImpl();
        Map<String, Object> caseData = new HashMap<>(); // allow null values
        caseData.put("caseTypeOfApplication", "C100");
        inputVariables.putValue("caseData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "caseManagementCategory",
            "value", "Private Law - C100"
        )));
    }

    @Test
    void when_caseData_then_return_expected_nextHearing() {
        Map<String, Object> caseData = new HashMap<>(); // allow null values
        String hearingDateTime = "2023-04-13T09:00:00";
        String hearingID = "2000004862";
        Map<String, Object> nextHearingDetails = new HashMap<>();
        nextHearingDetails.put("hearingDateTime", hearingDateTime);
        nextHearingDetails.put("hearingId", hearingID);
        caseData.put("nextHearingDetails", nextHearingDetails);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "nextHearingDate",
            "value", "2023-04-13T09:00:00",
            "canReconfigure", true
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "nextHearingId",
            "value", "2000004862",
            "canReconfigure", true
        )));
    }

    @Test
    void when_caseData_then_return_expected_dueDateFields() {
        VariableMap inputVariables = new VariableMapImpl();
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "minorPriority",
            "value", "500"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "calculatedDates",
            "value", "nextHearingDate,dueDate,priorityDate",
            "canReconfigure", true
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "dueDateOrigin",
            "value", LocalDate.now().toString()
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "dueDateTime",
            "value", "17:00"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "dueDateNonWorkingCalendar",
            "value", "https://www.gov.uk/bank-holidays/england-and-wales.json"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "dueDateNonWorkingDaysOfWeek",
            "value", "SATURDAY,SUNDAY"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "dueDateSkipNonWorkingDays",
            "value", "true"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "dueDateMustBeWorkingDay",
            "value", "No"
        )));
    }

    @ParameterizedTest
    @CsvSource({
        "checkApplicationC100", "checkApplicationResubmittedC100", "checkApplicationFL401",
        "checkApplicationResubmittedFL401","addCaseNumber","addCaseNumberResubmitted",
        "sendToGateKeeperFL401","sendToGateKeeperResubmittedFL401","sendToGateKeeperC100",
        "sendToGateKeeperResubmittedC100","produceHearingBundleC100",
        "updateHearingActualsC100","updateHearingActualsFL401","requestSolicitorOrderC100",
        "requestSolicitorOrderFL401","confidentialCheckSOA","recreateApplicationPack",
        "replyToMessageForCourtAdminFL401","replyToMessageForCourtAdminC100","replyToMessageForLA",
        "completefl416AndServe","listWithoutNoticeHearingC100","listOnNoticeHearingFL401","reviewLangAndSmReq"
    })
    void when_given_task_type_then_return_dueDateIntervalDays_and_validate_description(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue(
            "taskAttributes",
            Map.of("taskId", "1234",
                   "taskType", taskType
            )
        );

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("dueDateIntervalDays"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "dueDateIntervalDays",
            "value", "1"
        )));

        assertDescriptionField(taskType, dmnDecisionTableResult);
    }

    @ParameterizedTest
    @CsvSource({
        "createHearingRequest","createMultipleHearingRequest","createHearingRequestReserveListAssist"
    })
    void when_given_task_type_then_return_dueDateIntervalDays_and_validate_description_for_hearing_request(
        String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue(
            "taskAttributes",
            Map.of("taskId", "1234",
                   "taskType", taskType,
                   "name", "Yolanda"
            )
        );

        Map<String, Object> caseData = new HashMap<>(); // allow null values
        caseData.put("orderNameForAdminCreatedOrder", "Cooper");
        inputVariables.putValue("caseData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("dueDateIntervalDays"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "dueDateIntervalDays",
            "value", "1"
        )));

        assertDescriptionField(taskType, dmnDecisionTableResult);
    }

    @ParameterizedTest
    @CsvSource({
        "directionOnIssue","directionOnIssueResubmitted","gateKeeping","gateKeepingResubmitted",
        "serviceOfApplicationC100","serviceOfApplicationFL401","serviceOfApplicationC100",
        "serviceOfApplicationFL401","reviewRaRequestsC100","reviewRaRequestsFL401",
        "reviewInactiveRaRequestsC100","reviewInactiveRaRequestsFL401"
    })
    void when_given_task_type_then_return_dueDateIntervalDaysIsTwo_and_validate_description(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue(
            "taskAttributes",
            Map.of("taskId", "1234",
                   "taskType", taskType
            )
        );

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("dueDateIntervalDays"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "dueDateIntervalDays",
            "value", "2"
        )));

        assertDescriptionField(taskType, dmnDecisionTableResult);
    }

    @ParameterizedTest
    @CsvSource({
        "adminServeOrderC100","adminServeOrderFL401"
    })
    void when_given_task_type_then_return_dueDateIntervalDays_and_validate_description_for_adminServeOrder(
        String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue(
            "taskAttributes",
            Map.of("taskId", "1234",
                   "taskType", taskType,
                   "name", "Yolanda"
            )
        );

        Map<String, Object> caseData = new HashMap<>(); // allow null values
        caseData.put("orderNameForJudgeApproved", "Cooper");
        inputVariables.putValue("caseData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("dueDateIntervalDays"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "dueDateIntervalDays",
            "value", "2"
        )));

        assertDescriptionField(taskType, dmnDecisionTableResult);
    }

    @ParameterizedTest
    @CsvSource({
        "reviewAdminOrderProvided","reviewAdminOrderByManager"
    })
    void when_given_task_type_then_return_dueDateIntervalDays_and_validate_description_for_reviewAdminOrder(
        String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue(
            "taskAttributes",
            Map.of("taskId", "1234",
                   "taskType", taskType,
                   "name", "Yolanda"
            )
        );

        Map<String, Object> caseData = new HashMap<>(); // allow null values
        caseData.put("orderNameForAdminCreatedOrder", "Cooper");
        inputVariables.putValue("caseData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("dueDateIntervalDays"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "dueDateIntervalDays",
            "value", "2"
        )));

        assertDescriptionField(taskType, dmnDecisionTableResult);
    }

    @ParameterizedTest
    @CsvSource({
        "reviewAdditionalApplication"
    })
    void when_given_task_type_then_return_dueDateIntervalDays_and_validate_description_for_reviewAdditionalApplication(
        String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue(
            "taskAttributes",
            Map.of("taskId", "1234",
                   "taskType", taskType,
                   "name", "awpWaTaskLastName"
            )
        );

        Map<String, Object> caseData = new HashMap<>(); // allow null values
        caseData.put("awpWaTaskName", "awpWaTaskFirstName");
        inputVariables.putValue("caseData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("dueDateIntervalDays"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "dueDateIntervalDays",
            "value", "2"
        )));

        assertDescriptionField(taskType, dmnDecisionTableResult);
    }

    @ParameterizedTest
    @CsvSource({
        "removeLegalRepresentativeC100","removeLegalRepresentativeFL401",
        "reviewDocumentsForSolAndCafcassC100", "reviewDocumentsForSolAndCafcassFL401"
    })
    void when_given_task_type_then_return_dueDateIntervalDaysIsThree_and_validate_description(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue(
            "taskAttributes",
            Map.of("taskId", "1234",
                   "taskType", taskType
            )
        );

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("dueDateIntervalDays"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "dueDateIntervalDays",
            "value", "3"
        )));

        assertDescriptionField(taskType, dmnDecisionTableResult);
    }

    @ParameterizedTest
    @CsvSource({
        "reviewSolicitorOrderProvided", "reviewCorrespondenceC100","reviewCorrespondenceFL401",
        "replyToMessageForJudiciary","appStatementOfServiceBySol","appStatementOfServiceByLiP",
        "appStatementOfServiceByBailiff","arrangeBailiffSOA","appStatementOfServiceByAdmin"
    })
    void when_given_task_type_then_return_dueDateIntervalDaysIsFive_and_validate_description(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue(
            "taskAttributes",
            Map.of("taskId", "1234",
                   "taskType", taskType,
                   "name", "Solicitor Name"
            )
        );

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("dueDateIntervalDays"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "dueDateIntervalDays",
            "value", "5"
        )));

        assertDescriptionField(taskType, dmnDecisionTableResult);
    }

    @ParameterizedTest
    @CsvSource({
        "checkApplicationFL401","checkApplicationResubmittedFL401","sendToGateKeeperFL401",
        "sendToGateKeeperResubmittedFL401","serviceOfApplicationFL401","adminServeOrderFL401",
        "updateHearingActualsFL401","requestSolicitorOrderFL401",
        "checkApplicationC100","checkApplicationResubmittedC100","addCaseNumber",
        "addCaseNumberResubmitted","sendToGateKeeperC100","sendToGateKeeperResubmittedC100",
        "serviceOfApplicationC100","adminServeOrderC100","updateHearingActualsC100",
        "requestSolicitorOrderC100","reviewAdminOrderProvided",
        "removeLegalRepresentativeC100","removeLegalRepresentativeFL401","confidentialCheckSOA",
        "replyToMessageForCourtAdminFL401","replyToMessageForCourtAdminC100",
        "replyToMessageForJudiciary","reviewDocumentsForSolAndCafcassC100",
        "reviewDocumentsForSolAndCafcassFL401","replyToMessageForLA","reviewAdminOrderByManager",
        "createHearingRequest","createMultipleHearingRequest","createHearingRequestReserveListAssist",
        "reviewRaRequestsC100","reviewRaRequestsFL401","reviewInactiveRaRequestsC100",
        "reviewInactiveRaRequestsFL401","appStatementOfServiceBySol","appStatementOfServiceByLiP",
        "appStatementOfServiceByBailiff","arrangeBailiffSOA","appStatementOfServiceByAdmin",
        "completefl416AndServe","reviewAdditionalApplication","reviewLangAndSmReq"
    })
    void when_given_task_type_then_return_priorityDateOriginRef_and_validate_description(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue(
            "taskAttributes",
            Map.of("taskId", "1234",
                   "taskType", taskType,
                   "name", "LastName"
            )
        );

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("priorityDateOriginRef"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "priorityDateOriginRef",
            "value", "dueDate",
            "canReconfigure", true
        )));

        assertDescriptionField(taskType, dmnDecisionTableResult);
    }

    @ParameterizedTest
    @CsvSource({
        "reviewSpecificAccessRequestAdmin", "reviewSpecificAccessRequestCTSC"
    })
    void when_given_task_type_then_return_priorityDateOriginRef_and_not_validate_description_for_reviewSpecificAccess(
        String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue(
            "taskAttributes",
            Map.of("taskId", "1234",
                   "taskType", taskType,
                   "name", "LastName"
            )
        );

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("priorityDateOriginRef"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "priorityDateOriginRef",
            "value", "dueDate",
            "canReconfigure", true
        )));
    }

    @ParameterizedTest
    @CsvSource({
        "reviewCorrespondenceFL401","produceHearingBundleFL401","reviewCorrespondenceC100",
        "produceHearingBundleC100","directionOnIssue","directionOnIssueResubmitted",
        "gateKeeping","gateKeepingResubmitted","reviewSolicitorOrderProvided",
        "replyToMessageForJudiciary"
    })
    void when_given_task_type_then_return_calculatedDates_and_validate_description(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue(
            "taskAttributes",
            Map.of("taskId", "1234",
                   "taskType", taskType,
                   "name", "LastName"
            )
        );

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("calculatedDates"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(2));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "calculatedDates",
            "value", "nextHearingDate,nextHearingDatePreDate,dueDate,priorityDate",
            "canReconfigure", true
        )));

        assertDescriptionField(taskType, dmnDecisionTableResult);
    }

    @ParameterizedTest
    @CsvSource({
        "reviewCorrespondenceFL401","produceHearingBundleFL401","reviewCorrespondenceC100",
        "produceHearingBundleC100","directionOnIssue","directionOnIssueResubmitted",
        "gateKeeping","gateKeepingResubmitted","reviewSolicitorOrderProvided"
    })
    void when_given_task_type_then_return_nextHearingDateFields_and_validate_description(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue(
            "taskAttributes",
            Map.of("taskId", "1234",
                   "taskType", taskType,
                   "name", "LastName"
            )
        );

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        //For nextHearingDatePreDateNonWorkingDaysOfWeek entry
        validateNextHearingDatePreDateNonWorkingDaysOfWeek(dmnDecisionTableResult);

        // For nextHearingDatePreDateNonWorkingCalendar entry
        validateNextHearingDatePreDateNonWorkingCalendar(dmnDecisionTableResult);

        //For nextHearingDatePreDateIntervalDays entry
        validateNextHearingDatePreDateIntervalDays(dmnDecisionTableResult);

        //For nextHearingDatePreDateOriginRef entry
        validateNextHearingDatePreDateOriginRef(dmnDecisionTableResult);

        //For calculatedDates entry
        validateCalculatedDates(dmnDecisionTableResult);

        //For nextHearingDatePreDateSkipNonWorkingDays entry
        validateNextHearingDatePreDateSkipNonWorkingDays(dmnDecisionTableResult);

        //For nextHearingDatePreDateMustBeWorkingDay entry
        validateNextHearingDatePreDateMustBeWorkingDay(dmnDecisionTableResult);

        //For priorityDateOriginEarliest entry
        validatePriorityDateOriginEarliest(dmnDecisionTableResult);

        assertDescriptionField(taskType, dmnDecisionTableResult);
    }

    @ParameterizedTest
    @CsvSource({
        "checkApplicationFL401","checkApplicationResubmittedFL401","sendToGateKeeperFL401",
        "sendToGateKeeperResubmittedFL401","directionOnIssue","directionOnIssueResubmitted",
        "serviceOfApplicationFL401","adminServeOrderFL401","updateHearingActualsFL401",
        "requestSolicitorOrderFL401", "reviewCorrespondenceFL401","produceHearingBundleFL401",
        "removeLegalRepresentativeFL401", "replyToMessageForCourtAdminFL401",
        "reviewDocumentsForSolAndCafcassFL401","listWithoutNoticeHearingC100","listOnNoticeHearingFL401"
    })
    void when_given_task_type_then_return_majorPriorityForValue1000_and_validate_description(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue(
            "taskAttributes",
            Map.of("taskId", "1234",
                   "taskType", taskType,
                   "name", "LastName"
            )
        );

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("majorPriority"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "majorPriority",
            "value", "1000"
        )));

        assertDescriptionField(taskType, dmnDecisionTableResult);
    }

    @ParameterizedTest
    @CsvSource({
        "reviewSpecificAccessRequestAdmin", "replyToMessageForLAFL401"
    })
    void when_given_task_type_then_return_priorityDateOriginRef_and_not_validate_description_for_replyToMessage(
        String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue(
            "taskAttributes",
            Map.of("taskId", "1234",
                   "taskType", taskType
            )
        );

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("majorPriority"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));
        assertTrue(workTypeResultList.contains(Map.of(
            "name", "majorPriority",
            "value", "1000"
        )));
    }

    @ParameterizedTest
    @CsvSource({
        "checkApplicationC100","checkApplicationResubmittedC100","sendToGateKeeperC100",
        "sendToGateKeeperResubmittedC100","gateKeeping","gateKeepingResubmitted"
    })
    void when_given_task_type_then_return_majorPriorityForUrgentCase_and_validate_description(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue(
            "taskAttributes",
            Map.of("taskId", "1234",
                   "taskType", taskType,
                   "name", "LastName"
            )
        );

        Map<String, Object> caseData = new HashMap<>(); // allow null values
        caseData.put("newAllegationsOfHarmYesNo", "Yes");
        inputVariables.putValue("caseData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("majorPriority"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "majorPriority",
            "value", "3000"
        )));

        assertDescriptionField(taskType, dmnDecisionTableResult);
    }

    @ParameterizedTest
    @CsvSource({
        "addCaseNumber","addCaseNumberResubmitted","serviceOfApplicationC100",
        "adminServeOrderC100","updateHearingActualsC100","requestSolicitorOrderC100",
        "reviewCorrespondenceC100", "produceHearingBundleC100",
        "removeLegalRepresentativeC100","replyToMessageForCourtAdminC100"
    })
    void when_given_task_type_then_return_majorPriorityForValue5000_and_validate_description(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue(
            "taskAttributes",
            Map.of("taskId", "1234",
                   "taskType", taskType,
                   "name", "LastName"
            )
        );

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("majorPriority"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "majorPriority",
            "value", "5000"
        )));

        assertDescriptionField(taskType, dmnDecisionTableResult);
    }

    @ParameterizedTest
    @CsvSource({
        "reviewSpecificAccessRequestCTSC"
    })
    void when_given_task_type_then_return_majorPriorityForValue5000_and_not_validate_desc_for_reviewSpecificAccess(
        String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue(
            "taskAttributes",
            Map.of("taskId", "1234",
                   "taskType", taskType
            )
        );

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("majorPriority"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "majorPriority",
            "value", "5000"
        )));
    }

    @ParameterizedTest
    @CsvSource({
        "reviewSolicitorOrderProvided","reviewAdminOrderProvided","confidentialCheckSOA",
        "recreateApplicationPack","replyToMessageForJudiciary","reviewAdminOrderByManager",
        "appStatementOfServiceBySol","appStatementOfServiceByLiP","appStatementOfServiceByBailiff",
        "arrangeBailiffSOA","appStatementOfServiceByAdmin","completefl416AndServe",
        "replyToMessageForLA","createHearingRequest","createMultipleHearingRequest",
        "createHearingRequestReserveListAssist"
    })
    void when_given_task_type_then_return_majorPriorityForCaseTypeOfApplication_and_validate_description(
        String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue(
            "taskAttributes",
            Map.of("taskId", "1234",
                   "taskType", taskType,
                   "name", "LastName"
            )
        );
        Map<String, Object> caseData = new HashMap<>(); // allow null values
        caseData.put("caseTypeOfApplication", "C100");
        inputVariables.putValue("caseData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("majorPriority"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "majorPriority",
            "value", "5000"
        )));

        assertDescriptionField(taskType, dmnDecisionTableResult);
    }

    @ParameterizedTest
    @CsvSource({
        "reviewAdditionalApplication"
    })
    void when_given_task_type_then_return_majorPriorityForReviewAdditionalApplication_and_validate_description(
        String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue(
            "taskAttributes",
            Map.of("taskId", "1234",
                   "taskType", taskType,
                   "name", "LastName"
            )
        );

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("majorPriority"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "majorPriority",
            "value", "1000"
        )));

        assertDescriptionField(taskType, dmnDecisionTableResult);
    }

    @ParameterizedTest
    @CsvSource({
        "reviewRaRequestsC100","reviewRaRequestsFL401",
        "reviewInactiveRaRequestsC100","reviewInactiveRaRequestsFL401","reviewLangAndSmReq"
    })
    void when_given_task_type_then_return_majorPriorityForValue3000_and_validate_description(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue(
            "taskAttributes",
            Map.of("taskId", "1234",
                   "taskType", taskType,
                   "name", "LastName"
            )
        );

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("majorPriority"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "majorPriority",
            "value", "3000"
        )));

        assertDescriptionField(taskType, dmnDecisionTableResult);
    }

    @ParameterizedTest
    @CsvSource({
        "adminServeOrderC100","adminServeOrderFL401"
    })
    void when_given_task_type_then_return_titleForOrderNameForJudgeApproved_and_validate_description(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue(
            "taskAttributes",
            Map.of("taskId", "1234",
                   "taskType", taskType,
                   "name", "FirstName"
            )
        );
        Map<String, Object> caseData = new HashMap<>(); // allow null values
        caseData.put("orderNameForJudgeApproved", "LastName");
        inputVariables.putValue("caseData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("title"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "title",
            "value", "FirstName - LastName",
            "canReconfigure", false
        )));

        assertDescriptionField(taskType, dmnDecisionTableResult);
    }

    @ParameterizedTest
    @CsvSource({
        "adminServeOrderCreatedByJudgeC100","adminServeOrderCreatedByJudgeFL401"
    })
    void when_given_task_type_then_return_titleForOrderNameForJudgeApproved_and_not_validate_description(
        String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue(
            "taskAttributes",
            Map.of("taskId", "1234",
                   "taskType", taskType,
                   "name", "FirstName"
            )
        );
        Map<String, Object> caseData = new HashMap<>(); // allow null values
        caseData.put("orderNameForJudgeApproved", "LastName");
        inputVariables.putValue("caseData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("title"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "title",
            "value", "FirstName - LastName",
            "canReconfigure", false
        )));
    }

    @ParameterizedTest
    @CsvSource({
        "reviewSolicitorOrderProvided"
    })
    void when_given_task_type_then_return_titleForOrderNameForSolicitorCreatedOrder_and_validate_description(
        String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue(
            "taskAttributes",
            Map.of("taskId", "1234",
                   "taskType", taskType,
                   "name", "FirstName"
            )
        );
        Map<String, Object> caseData = new HashMap<>(); // allow null values
        caseData.put("orderNameForSolicitorCreatedOrder", "LastName");
        inputVariables.putValue("caseData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("title"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "title",
            "value", "FirstName - LastName",
            "canReconfigure", false
        )));

        assertDescriptionField(taskType, dmnDecisionTableResult);
    }

    @ParameterizedTest
    @CsvSource({
        "reviewAdminOrderProvided","reviewAdminOrderByManager","createHearingRequest",
        "createMultipleHearingRequest","createHearingRequestReserveListAssist"
    })
    void when_given_task_type_then_return_titleForOrderNameForAdminCreatedOrder_and_validate_description(
        String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue(
            "taskAttributes",
            Map.of("taskId", "1234",
                   "taskType", taskType,
                   "name", "FirstName"
            )
        );
        Map<String, Object> caseData = new HashMap<>(); // allow null values
        caseData.put("orderNameForAdminCreatedOrder", "LastName");
        inputVariables.putValue("caseData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("title"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "title",
            "value", "FirstName - LastName",
            "canReconfigure", false
        )));

        assertDescriptionField(taskType, dmnDecisionTableResult);
    }

    @ParameterizedTest
    @CsvSource({
        "reviewAdditionalApplication"
    })
    void when_given_task_type_then_return_titleForAwpWaTaskName_and_validate_description(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue(
            "taskAttributes",
            Map.of("taskId", "1234",
                   "taskType", taskType,
                   "name", "FirstName"
            )
        );
        Map<String, Object> caseData = new HashMap<>(); // allow null values
        caseData.put("awpWaTaskName", "LastName");
        inputVariables.putValue("caseData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("title"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "title",
            "value", "FirstName - LastName",
            "canReconfigure", false
        )));

        assertDescriptionField(taskType, dmnDecisionTableResult);
    }

    private void validatePriorityDateOriginEarliest(DmnDecisionTableResult dmnDecisionTableResult) {
        List<Map<String, Object>> workTypeResultListPriorityDateOriginEarliest =
            dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("priorityDateOriginEarliest"))
            .collect(Collectors.toList());

        assertThat(workTypeResultListPriorityDateOriginEarliest.size(), is(1));
        assertTrue(workTypeResultListPriorityDateOriginEarliest.contains(Map.of(
            "name", "priorityDateOriginEarliest",
            "value", "nextHearingDatePreDate,dueDate",
            "canReconfigure", true
        )));
    }

    private void validateNextHearingDatePreDateMustBeWorkingDay(DmnDecisionTableResult dmnDecisionTableResult) {
        List<Map<String, Object>> workTypeResultListNextHearingDatePreDateMustBeWorkingDay =
            dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("nextHearingDatePreDateMustBeWorkingDay"))
            .collect(Collectors.toList());

        assertThat(workTypeResultListNextHearingDatePreDateMustBeWorkingDay.size(), is(1));
        assertTrue(workTypeResultListNextHearingDatePreDateMustBeWorkingDay.contains(Map.of(
            "name", "nextHearingDatePreDateMustBeWorkingDay",
            "value", "No",
            "canReconfigure", true
        )));
    }

    private static void validateNextHearingDatePreDateSkipNonWorkingDays(
        DmnDecisionTableResult dmnDecisionTableResult) {
        List<Map<String, Object>> workTypeResultListNextHearingDatePreDateSkipNonWorkingDays =
            dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("nextHearingDatePreDateSkipNonWorkingDays"))
            .collect(Collectors.toList());

        assertThat(workTypeResultListNextHearingDatePreDateSkipNonWorkingDays.size(), is(1));
        assertTrue(workTypeResultListNextHearingDatePreDateSkipNonWorkingDays.contains(Map.of(
            "name", "nextHearingDatePreDateSkipNonWorkingDays",
            "value", "true",
            "canReconfigure", true
        )));
    }

    private static void validateCalculatedDates(DmnDecisionTableResult dmnDecisionTableResult) {
        List<Map<String, Object>> workTypeResultListCalculatedDates =
            dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("calculatedDates"))
            .collect(Collectors.toList());

        assertThat(workTypeResultListCalculatedDates.size(), is(2));
        assertTrue(workTypeResultListCalculatedDates.contains(Map.of(
            "name", "calculatedDates",
            "value", "nextHearingDate,nextHearingDatePreDate,dueDate,priorityDate",
            "canReconfigure", true
        )));
    }

    private static void validateNextHearingDatePreDateOriginRef(DmnDecisionTableResult dmnDecisionTableResult) {
        List<Map<String, Object>> workTypeResultListNextHearingDatePreDateOriginRef =
            dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("nextHearingDatePreDateOriginRef"))
            .collect(Collectors.toList());

        assertThat(workTypeResultListNextHearingDatePreDateOriginRef.size(), is(1));
        assertTrue(workTypeResultListNextHearingDatePreDateOriginRef.contains(Map.of(
            "name", "nextHearingDatePreDateOriginRef",
            "value", "nextHearingDate",
            "canReconfigure", true
        )));
    }

    private static void validateNextHearingDatePreDateIntervalDays(DmnDecisionTableResult dmnDecisionTableResult) {
        List<Map<String, Object>> workTypeResultListNextHearingDatePreDateIntervalDays =
            dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("nextHearingDatePreDateIntervalDays"))
            .collect(Collectors.toList());

        assertThat(workTypeResultListNextHearingDatePreDateIntervalDays.size(), is(1));
        assertTrue(workTypeResultListNextHearingDatePreDateIntervalDays.contains(Map.of(
            "name", "nextHearingDatePreDateIntervalDays",
            "value", "-3",
            "canReconfigure", true
        )));
    }

    private static void validateNextHearingDatePreDateNonWorkingCalendar(
        DmnDecisionTableResult dmnDecisionTableResult) {
        List<Map<String, Object>> workTypeResultListNextHearingDatePreDateNonWorkingCalendar =
            dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("nextHearingDatePreDateNonWorkingCalendar"))
            .collect(Collectors.toList());

        assertThat(workTypeResultListNextHearingDatePreDateNonWorkingCalendar.size(), is(1));
        assertTrue(workTypeResultListNextHearingDatePreDateNonWorkingCalendar.contains(Map.of(
            "name", "nextHearingDatePreDateNonWorkingCalendar",
            "value", "https://www.gov.uk/bank-holidays/england-and-wales.json",
            "canReconfigure", true
        )));
    }

    private static void validateNextHearingDatePreDateNonWorkingDaysOfWeek(
        DmnDecisionTableResult dmnDecisionTableResult) {
        List<Map<String, Object>> workTypeResultListNextHearingDatePreDateNonWorkingDaysOfWeek =
            dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("nextHearingDatePreDateNonWorkingDaysOfWeek"))
            .collect(Collectors.toList());

        assertThat(workTypeResultListNextHearingDatePreDateNonWorkingDaysOfWeek.size(), is(1));
        assertTrue(workTypeResultListNextHearingDatePreDateNonWorkingDaysOfWeek.contains(Map.of(
            "name", "nextHearingDatePreDateNonWorkingDaysOfWeek",
            "value", "SATURDAY, SUNDAY",
            "canReconfigure", true
        )));
    }

    private void assertDescriptionField(String taskType, DmnDecisionTableResult dmnDecisionTableResult) {
        List<Map<String, Object>> descriptionResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("description"))
            .toList();
        assertThat(descriptionResultList.size(), is(1));

        String description = getDescriptionBasedOnTaskType(taskType);

        assertTrue(descriptionResultList.contains(Map.of(
            "name", "description",
            "value", description
        )));
    }

    private static String getDescriptionBasedOnTaskType(String taskType) {
        switch (taskType) {
            case "checkApplicationFL401":
            case "checkApplicationResubmittedFL401":
                return "[Add Case Number](/cases/case-details/${[CASE_REFERENCE]}/trigger/"
                    + "fl401AddCaseNumber/fl401AddCaseNumber1)";

            case "checkApplicationC100":
            case "checkApplicationResubmittedC100":
                return "[Issue and send to local Court](/cases/case-details/${[CASE_REFERENCE]}"
                    + "/trigger/issueAndSendToLocalCourtCallback/issueAndSendToLocalCourtCallback1)";

            case "addCaseNumber":
            case "addCaseNumberResubmitted":
                return "[Add Case Number](/cases/case-details/${[CASE_REFERENCE]}/trigger/caseNumber/caseNumber1)";

            case "sendToGateKeeperFL401":
            case "sendToGateKeeperResubmittedFL401":
                return "[Send To Gatekeeper](/cases/case-details/${[CASE_REFERENCE]}/trigger/"
                    + "fl401SendToGateKeeper/fl401SendToGateKeeper1)";

            case "sendToGateKeeperC100":
            case "sendToGateKeeperResubmittedC100":
                return "[Send To Gatekeeper](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                    + "/sendToGateKeeper/sendToGateKeeper1)";

            case "produceHearingBundleC100":
            case "produceHearingBundleFL401":
                return "[Create Bundle](/cases/case-details/${[CASE_REFERENCE]}/trigger/createBundle/createBundle1)";

            case "adminServeOrderFL401":
            case "adminServeOrderC100":
                return "[Complete the Order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                    + "/adminEditAndApproveAnOrder/adminEditAndApproveAnOrder1)";

            case "serviceOfApplicationFL401":
            case "serviceOfApplicationC100":
                return "[Service of Application](/cases/case-details/${[CASE_REFERENCE]}/"
                    + "trigger/serviceOfApplication/serviceOfApplication1)";

            case "reviewCorrespondenceFL401":
            case "reviewCorrespondenceC100":
                return "[Review Correspondence](/cases/case-details/${[CASE_REFERENCE]}#Casedocuments)";

            case "updateHearingActualsFL401":
            case "updateHearingActualsC100":
                return "[Update Hearing Actuals](/cases/case-details/${[CASE_REFERENCE]}/trigger/)";

            case "requestSolicitorOrderFL401":
            case "requestSolicitorOrderC100":
                return "[Request Solicitor to Submit the Order]";

            case "directionOnIssue":
            case "directionOnIssueResubmitted":
                return "[Directions on Issue]";

            case "gateKeeping":
            case "gateKeepingResubmitted":
                return "[Gatekeeping]";

            case "reviewSolicitorOrderProvided":
                return "[Review and Approve Legal rep Order](/cases/case-details/${[CASE_REFERENCE]}"
                    + "/trigger/editAndApproveAnOrder/editAndApproveAnOrder1)";

            case "reviewAdminOrderProvided":
            case "reviewAdminOrderByManager":
                return "[Review and Approve Admin Order](/cases/case-details/${[CASE_REFERENCE]}"
                    + "/trigger/editAndApproveAnOrder/editAndApproveAnOrder1)";

            case "removeLegalRepresentativeC100":
                return "[Remove Legal Representative](/cases/case-details/${[CASE_REFERENCE]}"
                    + "/trigger/adminRemoveLegalRepresentativeC100/adminRemoveLegalRepresentativeC1001)";

            case "removeLegalRepresentativeFL401":
                return "[Remove Legal Representative](/cases/case-details/${[CASE_REFERENCE]}"
                    + "/trigger/adminRemoveLegalRepresentativeFL401/adminRemoveLegalRepresentativeFL4011)";

            case "confidentialCheckSOA":
                return "[Confidential Check](/cases/case-details/${[CASE_REFERENCE]}"
                    + "/trigger/confidentialityCheck/confidentialityCheck1)";

            case "recreateApplicationPack":
                return "[Recreate Application Pack](/cases/case-details/${[CASE_REFERENCE]}"
                    + "/trigger/serviceOfApplication/serviceOfApplication1)";

            case "appStatementOfServiceBySol":
                return "[Waiting for Applicant's Solicitor to upload Statement of Service]";

            case "appStatementOfServiceByLiP":
                return "[Waiting for Unrepresented Applicant LiP to upload Statement of Service]";

            case "appStatementOfServiceByBailiff":
                return "[Upload Statement of Service provided by Court Bailiff](/cases"
                    + "/case-details/${[CASE_REFERENCE]}/trigger/statementOfService/statementOfService1)";

            case "arrangeBailiffSOA":
                return "[Arrange bailiff service of application]";

            case "appStatementOfServiceByAdmin":
                return "[Upload Statement of Service](/cases/case-details/${[CASE_REFERENCE]}"
                    + "/trigger/statementOfService/statementOfService1)";

            case "completefl416AndServe":
                return "[Complete FL416 and serve applicant only]";

            case "replyToMessageForCourtAdminFL401":
            case "replyToMessageForCourtAdminC100":
            case "replyToMessageForJudiciary":
            case "replyToMessageForLA":
                return "[Reply to Message](/cases/case-details/${[CASE_REFERENCE]}/trigger/sendOrReplyToMessages)";

            case "reviewDocumentsForSolAndCafcassC100":
            case "reviewDocumentsForSolAndCafcassFL401":
                return "[Review Documents](/cases/case-details/${[CASE_REFERENCE]}/trigger/reviewDocuments)";

            case "reviewRaRequestsC100":
                return "[Review RA request](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                    + "/c100ManageFlags/c100ManageFlags)";

            case "reviewRaRequestsFL401":
                return "[Review RA request](/cases/case-details/${[CASE_REFERENCE]}"
                    + "/trigger/fl401ManageFlags/fl401ManageFlags)";

            case "reviewInactiveRaRequestsC100":
            case "reviewInactiveRaRequestsFL401":
                return "[Review inactive case flags](/cases/case-details/${[CASE_REFERENCE]}#Case%20Flags)";

            case "createHearingRequestReserveListAssist":
                return "[Reserve Hearings in List Assist]";

            case "createHearingRequest":
            case "createMultipleHearingRequest":
                return "[Create Hearing Request](/cases/case-details/${[CASE_REFERENCE]}/hearings)";

            case "listWithoutNoticeHearingC100":
            case "listOnNoticeHearingFL401":
                return "";

            case "reviewAdditionalApplication":
                return "[Review other applications](/cases/case-details/${[CASE_REFERENCE]}#Other%20applications)";

            case "reviewLangAndSmReq":
                return "[Review case notes](/cases/case-details/${[CASE_REFERENCE]}#Case%20Notes)";

            default:
                break;
        }
        return null;
    }
}
