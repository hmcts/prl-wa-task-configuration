package uk.gov.hmcts.reform.prl.taskconfiguration.dmn;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.hmcts.reform.prl.taskconfiguration.DmnDecisionTable;
import uk.gov.hmcts.reform.prl.taskconfiguration.DmnDecisionTableBaseUnitTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class CamundaTaskInitiationTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = DmnDecisionTable.WA_TASK_INITIATION;
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getInputs().size(), is(4));
        assertThat(logic.getOutputs().size(), is(5));
        assertThat(logic.getRules().size(), is(23));
    }

    static Stream<Arguments> scenarioProvider() {

        return Stream.of(
            Arguments.of(
                "paymentSuccessCallback",
                "SUBMITTED_PAID",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "checkApplicationC100",
                        "name", "Check Application",
                        "workingDaysAllowed", 1,
                        "processCategories", "applicationCheck"
                    )
                )
            ),
            Arguments.of(
                "submit",
                "SUBMITTED_PAID",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "checkApplicationResubmittedC100",
                        "name", "Check Resubmitted Application",
                        "workingDaysAllowed", 1,
                        "processCategories", "applicationCheck"
                    )
                )
            ),
            Arguments.of(
                "fl401StatementOfTruthAndSubmit",
                "SUBMITTED_PAID",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "" + "\"\n"
                                      + "   }"
                                      + "}"),
                List.of(
                    Map.of(
                        "taskId", "checkApplicationFL401",
                        "name", "Check Application",
                        "workingDaysAllowed", 1,
                        "processCategories", "applicationCheck"
                    ),
                    Map.of(
                        "taskId", "sendToGateKeeperFL401",
                        "name", "Send to Gatekeeper",
                        "workingDaysAllowed", 1,
                        "processCategories", "localCourtGatekeepingFL401"
                    )
                )
            ),
            Arguments.of(
                "fl401resubmit",
                "SUBMITTED_PAID",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "" + "\"\n"
                                      + "   }"
                                      + "}"),
                List.of(
                    Map.of(
                        "taskId", "checkApplicationResubmittedFL401",
                        "name", "Check Resubmitted Application",
                        "workingDaysAllowed", 1,
                        "processCategories", "applicationCheck"
                    ),
                    Map.of(
                        "taskId", "sendToGateKeeperResubmittedFL401",
                        "name", "Send to Gatekeeper Resubmitted",
                        "workingDaysAllowed", 1,
                        "processCategories", "localCourtGatekeepingFL401Resubmit"
                    )
                )
            ),
            Arguments.of(
                "issueAndSendToLocalCourtCallback",
                "CASE_ISSUED",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "" + "\"\n"
                                      + "   }"
                                      + "}"),
                List.of(
                    Map.of(
                        "taskId", "addCaseNumber",
                        "name", "Add Case Number",
                        "workingDaysAllowed", 1,
                        "processCategories", "addCaseNumberC100"
                    ),
                    Map.of(
                        "taskId", "sendToGateKeeperC100",
                        "name", "Send to Gatekeeper",
                        "workingDaysAllowed", 1,
                        "processCategories", "localCourtGatekeepingC100"
                    )
                )
            ),
            Arguments.of(
                "submit",
                "CASE_ISSUED",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "" + "\"\n"
                                      + "   }"
                                      + "}"),
                List.of(
                    Map.of(
                        "taskId", "addCaseNumberResubmitted",
                        "name", "Add Case Number Resubmitted",
                        "workingDaysAllowed", 1,
                        "processCategories", "addCaseNumberC100Resubmit"
                    ),
                    Map.of(
                        "taskId", "sendToGateKeeperResubmittedC100",
                        "name", "Send to Gatekeeper Resubmitted",
                        "workingDaysAllowed", 1,
                        "processCategories", "localCourtGatekeepingResubmittedC100"
                    )
                )
            ),
            Arguments.of(
                "sendToGateKeeper",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "gateKeeping",
                        "name", "Gatekeeping",
                        "workingDaysAllowed", 2,
                        "processCategories", "gateKeeping"
                    )
                )
            ),
            Arguments.of(
                "submit",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "gateKeepingResubmitted",
                        "name", "Gatekeeping Resubmitted",
                        "workingDaysAllowed", 2,
                        "processCategories", "gateKeepingResubmitted"
                    )
                )
            ),
            Arguments.of(
                "hmcCaseUpdPrepForHearing",
                "PREPARE_FOR_HEARING_CONDUCT_HEARING",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "updateHearingActualsC100",
                        "name", "Update Hearing Actuals",
                        "workingDaysAllowed", 1,
                        "processCategories", "updateHearingActualsC100"
                    )
                )
            ),
            Arguments.of(
                "hmcCaseUpdPrepForHearing",
                "PREPARE_FOR_HEARING_CONDUCT_HEARING",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "updateHearingActualsFL100",
                        "name", "Update Hearing Actuals",
                        "workingDaysAllowed", 1,
                        "processCategories", "updateHearingActualsFL100"
                    )
                )
            ),
            Arguments.of(
                "hmcCaseUpdPrepForHearing",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "updateHearingActualsC100",
                        "name", "Update Hearing Actuals",
                        "workingDaysAllowed", 1,
                        "processCategories", "updateHearingActualsC100"
                    )
                )
            ),
            Arguments.of(
                "hmcCaseUpdPrepForHearing",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "updateHearingActualsFL100",
                        "name", "Update Hearing Actuals",
                        "workingDaysAllowed", 1,
                        "processCategories", "updateHearingActualsFL100"
                    )
                )
            ),
            Arguments.of(
                "hmcCaseUpdDecOutcome",
                "DECISION_OUTCOME",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "requestSolicitorOrderC100",
                        "name", "Request Solicitor Order",
                        "delayDuration", 5,
                        "workingDaysAllowed", 1,
                        "processCategories", "requestSolicitorOrder"
                    )
                )
            ),
            Arguments.of(
                "hmcCaseUpdDecOutcome",
                "DECISION_OUTCOME",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "requestSolicitorOrderFL100",
                        "name", "Request Solicitor Order",
                        "delayDuration", 5,
                        "workingDaysAllowed", 1,
                        "processCategories", "requestSolicitorOrder"
                    )
                )
            ),
            Arguments.of(
                "manageDocuments",
                null,
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "reviewCorrespondenceC100",
                        "name", "Review correspondence",
                        "workingDaysAllowed", 5,
                        "processCategories", "courtAdminCorrespondenceC100"
                    )
                )
            ),
            Arguments.of(
                "manageDocuments",
                null,
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "reviewCorrespondenceFL401",
                        "name", "Review correspondence",
                        "workingDaysAllowed", 5,
                        "processCategories", "courtAdminCorrespondenceFL401"
                    )
                )
            )
        );
    }

    @ParameterizedTest(name = "event id: {0} post event state: {1} additional data: {2}")
    @MethodSource("scenarioProvider")
    void given_multiple_event_ids_should_evaluate_dmn(String eventId,
                                                      String postEventState,
                                                      Map<String, Object> map,
                                                      List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postEventState);
        inputVariables.putValue("additionalData", map);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    private static Map<String, Object> mapAdditionalData(String additionalData) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            TypeReference<HashMap<String, Object>> typeRef = new TypeReference<>() {
            };
            return mapper.readValue(additionalData, typeRef);
        } catch (IOException exp) {
            return null;
        }
    }
}
