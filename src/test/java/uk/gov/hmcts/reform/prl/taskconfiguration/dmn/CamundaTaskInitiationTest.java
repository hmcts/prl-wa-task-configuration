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
        assertThat(logic.getInputs().size(), is(21));
        assertThat(logic.getOutputs().size(), is(4));
        assertThat(logic.getRules().size(), is(107));
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
                        "processCategories", "applicationCheck"
                    )
                )
            ),
            Arguments.of(
                "submitAndPay",
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
                        "processCategories", "applicationCheck"
                    )
                )
            ),
            Arguments.of(
                "citizen-case-submit",
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
                        "processCategories", "applicationCheck"
                    ),
                    Map.of(
                        "taskId", "sendToGateKeeperFL401",
                        "name", "Send to Gatekeeper",
                        "processCategories", "localCourtGatekeepingFL401"
                    )
                )
            ),
            Arguments.of(
                "courtnav-case-creation",
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
                        "processCategories", "applicationCheck"
                    ),
                    Map.of(
                        "taskId", "sendToGateKeeperFL401",
                        "name", "Send to Gatekeeper",
                        "processCategories", "localCourtGatekeepingFL401"
                    )
                )
            ),
            Arguments.of(
                "testingSupportDummySolicitorCreateCourtNav",
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
                        "processCategories", "applicationCheck"
                    ),
                    Map.of(
                        "taskId", "sendToGateKeeperFL401",
                        "name", "Send to Gatekeeper",
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
                        "processCategories", "applicationCheck"
                    ),
                    Map.of(
                        "taskId", "sendToGateKeeperResubmittedFL401",
                        "name", "Send to Gatekeeper Resubmitted",
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
                        "processCategories", "addCaseNumberC100"
                    ),
                    Map.of(
                        "taskId", "sendToGateKeeperC100",
                        "name", "Send to Gatekeeper",
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
                        "processCategories", "addCaseNumberC100Resubmit"
                    ),
                    Map.of(
                        "taskId", "sendToGateKeeperResubmittedC100",
                        "name", "Send to Gatekeeper Resubmitted",
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
                        "processCategories", "gateKeepingResubmitted"
                    )
                )
            ),
            Arguments.of(
                "fl401SendToGateKeeper",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "directionOnIssue",
                        "name", "Directions on Issue",
                        "processCategories", "directionOnIssue"
                    )
                )
            ),
            Arguments.of(
                "fl401resubmit",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "directionOnIssueResubmitted",
                        "name", "Directions on Issue Resubmitted",
                        "processCategories", "directionOnIssue"
                    )
                )
            ),
            Arguments.of(
                "hmcCaseUpdPrepForHearing",
                "PREPARE_FOR_HEARING_CONDUCT_HEARING",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"performingUser\":\"" + "TO BE REMOVED LATER" + "\"\n,"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "updateHearingActualsC100",
                        "name", "Update Hearing Actuals",
                        "processCategories", "updateHearingActualsC100"
                    )
                )
            ),
            Arguments.of(
                "draftAnOrder",
                null,
                null,
                singletonList(
                    Map.of(
                        "taskId", "reviewSolicitorOrderProvided",
                        "name", "Review and Approve Legal rep Order",
                        "processCategories", "reviewSolicitorOrderByJudge"
                    )
                )
            ),
            Arguments.of(
                "editReturnedOrder",
                null,
                null,
                singletonList(
                    Map.of(
                        "taskId", "reviewSolicitorOrderProvided",
                        "name", "Review resubmitted Order",
                        "processCategories", "reviewReturnedSolicitorOrderByJudge"
                    )
                )
            ),
            Arguments.of(
                "editAndApproveAnOrder",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n,"
                                      + "      \"isOrderCompleteToServe\":\"" + "true" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "serviceOfApplicationC100",
                        "name", "Service of Application",
                        "processCategories", "serviceOfApplication"
                    )
                )
            ),
            Arguments.of(
                "editAndApproveAnOrder",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n,"
                                      + "      \"isOrderCompleteToServe\":\"" + "true" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "serviceOfApplicationFL401",
                        "name", "Service of Application",
                        "processCategories", "serviceOfApplication"
                    )
                )
            ),
            Arguments.of(
                "editAndApproveAnOrder",
                "CASE_ISSUED",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n,"
                                      + "      \"isOrderApproved\":\"" + "Yes" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "adminServeOrderC100",
                        "name", "Complete the Order",
                        "processCategories", "completeTheOrder"
                    )
                )
            ),
            Arguments.of(
                "editAndApproveAnOrder",
                "CASE_ISSUED",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n,"
                                      + "      \"isOrderApproved\":\"" + "Yes" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "adminServeOrderFL401",
                        "name", "Complete the Order",
                        "processCategories", "completeTheOrder"
                    )
                )
            ),
            Arguments.of(
                "manageOrders",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n,"
                                      + "      \"performingUser\":\"" + "JUDGE" + "\"\n,"
                                      + "      \"performingAction\":\"" + "Create an order" + "\"\n,"
                                      + "      \"isOrderCompleteToServe\":\"" + "true" + "\"\n"
                                      + "   }"
                                      + "}"),
                List.of(
                    Map.of(
                        "processCategories", "serviceOfApplication",
                        "taskId", "serviceOfApplicationC100",
                        "name", "Service of Application"
                    ),
                    Map.of(
                        "processCategories", "completeTheOrder",
                        "taskId", "adminServeOrderC100",
                        "name", "Complete the Order"
                    )
                )
            ),
            Arguments.of(
                "manageOrders",
                "PREPARE_FOR_HEARING_CONDUCT_HEARING",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n,"
                                      + "      \"performingUser\":\"" + "JUDGE" + "\"\n,"
                                      + "      \"performingAction\":\"" + "Create an order" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "processCategories", "completeTheOrder",
                        "taskId", "adminServeOrderC100",
                        "name", "Service Of Order"
                    )
                )
            ),
            Arguments.of(
                "manageOrders",
                "DECISION_OUTCOME",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n,"
                                      + "      \"performingUser\":\"" + "JUDGE" + "\"\n,"
                                      + "      \"performingAction\":\"" + "Upload an order" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "adminServeOrderFL401",
                        "name", "Service Of Order",
                        "processCategories", "completeTheOrder"
                    )
                )
            ),
            Arguments.of(
                "editAndApproveAnOrder",
                null,
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n,"
                                      + "      \"performingUser\":\"" + "JUDGE" + "\"\n,"
                                      + "      \"judgeLaManagerReviewRequired\":\"" + "null" + "\"\n,"
                                      + "      \"isOrderApproved\":\"" + "Yes" + "\"\n,"
                                      + "      \"isHearingTaskNeeded\":\"" + "Yes" + "\"\n,"
                                      + "      \"isMultipleHearingSelected\":\"" + "No" + "\"\n,"
                                      + "      \"hearingOptionSelected\":\"" + "dateReservedWithListAssit" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "createHearingRequestReserveListAssist",
                        "name", "Create Hearing Request - Reserved in List Assist",
                        "processCategories", "createHearingRequest"
                    )
                )
            ),
            Arguments.of(
                "manageOrders",
                null,
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n,"
                                      + "      \"performingUser\":\"" + "JUDGE" + "\"\n,"
                                      + "      \"performingAction\":\"" + "Create an order" + "\"\n,"
                                      + "      \"judgeLaManagerReviewRequired\":\"" + "null" + "\"\n,"
                                      + "      \"isHearingTaskNeeded\":\"" + "Yes" + "\"\n,"
                                      + "      \"isMultipleHearingSelected\":\"" + "No" + "\"\n,"
                                      + "      \"hearingOptionSelected\":\"" + "dateReservedWithListAssit" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "createHearingRequestReserveListAssist",
                        "name", "Create Hearing Request - Reserved in List Assist",
                        "processCategories", "createHearingRequest"
                    )
                )
            ),
            Arguments.of(
                "editAndApproveAnOrder",
                null,
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n,"
                                      + "      \"performingUser\":\"" + "JUDGE" + "\"\n,"
                                      + "      \"judgeLaManagerReviewRequired\":\"" + "null" + "\"\n,"
                                      + "      \"isOrderApproved\":\"" + "Yes" + "\"\n,"
                                      + "      \"isHearingTaskNeeded\":\"" + "Yes" + "\"\n,"
                                      + "      \"isMultipleHearingSelected\":\"" + "No" + "\"\n,"
                                      + "      \"hearingOptionSelected\":\"" + "dateReservedWithListAssit" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "createHearingRequestReserveListAssist",
                        "name", "Create Hearing Request - Reserved in List Assist",
                        "processCategories", "createHearingRequest"
                    )
                ),
                Arguments.of(
                    "editAndApproveAnOrder",
                    null,
                    mapAdditionalData("{\n"
                                          + "   \"Data\":{\n"
                                          + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n,"
                                          + "      \"performingUser\":\"" + "JUDGE" + "\"\n,"
                                          + "      \"performingAction\":\"" + "Create an order" + "\"\n,"
                                          + "      \"isOrderApproved\":\"" + "Yes" + "\"\n,"
                                          + "      \"isHearingTaskNeeded\":\"" + "Yes" + "\"\n,"
                                          + "      \"isMultipleHearingSelected\":\"" + "No" + "\"\n,"
                                          + "      \"hearingOptionSelected\":\"" + "dateConfirmedByListingTeam" + "\"\n"
                                          + "   }"
                                          + "}"),
                    singletonList(
                        Map.of(
                            "taskId", "createHearingRequest",
                            "name", "Create Hearing Request",
                            "processCategories", "createHearingRequest"
                        )
                    )
                ),
                Arguments.of(
                    "editAndApproveAnOrder",
                    null,
                    mapAdditionalData("{\n"
                                          + "   \"Data\":{\n"
                                          + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n,"
                                          + "      \"performingUser\":\"" + "JUDGE" + "\"\n,"
                                          + "      \"performingAction\":\"" + "Create an order" + "\"\n,"
                                          + "      \"isOrderApproved\":\"" + "Yes" + "\"\n,"
                                          + "      \"isHearingTaskNeeded\":\"" + "Yes" + "\"\n,"
                                          + "      \"isMultipleHearingSelected\":\"" + "No" + "\"\n,"
                                          + "      \"hearingOptionSelected\":\"" + "dateToBeFixed" + "\"\n"
                                          + "   }"
                                          + "}"),
                    singletonList(
                        Map.of(
                            "taskId", "createHearingRequest",
                            "name", "Create Hearing Request",
                            "processCategories", "createHearingRequest"
                        )
                    )
                ),
                Arguments.of(
                    "editAndApproveAnOrder",
                    null,
                    mapAdditionalData("{\n"
                                          + "   \"Data\":{\n"
                                          + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n,"
                                          + "      \"performingUser\":\"" + "JUDGE" + "\"\n,"
                                          + "      \"performingAction\":\"" + "Create an order" + "\"\n,"
                                          + "      \"isOrderApproved\":\"" + "Yes" + "\"\n,"
                                          + "      \"isHearingTaskNeeded\":\"" + "Yes" + "\"\n,"
                                          + "      \"isMultipleHearingSelected\":\"" + "Yes" + "\"\n,"
                                          + "      \"hearingOptionSelected\":\"" + "multipleOptionSelected" + "\"\n"
                                          + "   }"
                                          + "}"),
                    singletonList(
                        Map.of(
                            "taskId", "createMultipleHearingRequest",
                            "name", "Create Multiple Hearing Request",
                            "processCategories", "createHearingRequest"
                        )
                    )
                )
            ),
            Arguments.of(
                "hmcCaseUpdPrepForHearing",
                "PREPARE_FOR_HEARING_CONDUCT_HEARING",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"performingUser\":\"" + "TO BE REMOVED LATER" + "\"\n,"
                                      + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "updateHearingActualsFL100",
                        "name", "Update Hearing Actuals",
                        "processCategories", "updateHearingActualsFL100"
                    )
                )
            ),
            Arguments.of(
                "hmcCaseUpdPrepForHearing",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"performingUser\":\"" + "TO BE REMOVED LATER" + "\"\n,"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "updateHearingActualsC100",
                        "name", "Update Hearing Actuals",
                        "processCategories", "updateHearingActualsC100"
                    )
                )
            ),
            Arguments.of(
                "hmcCaseUpdPrepForHearing",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"performingUser\":\"" + "TO BE REMOVED LATER" + "\"\n,"
                                      + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "updateHearingActualsFL100",
                        "name", "Update Hearing Actuals",
                        "processCategories", "updateHearingActualsFL100"
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
                        "processCategories", "courtAdminCorrespondenceFL401"
                    )
                )
            ),
            Arguments.of(
                "citizenRemoveLegalRepresentative",
                null,
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "removeLegalRepresentativeC100",
                        "name", "Remove legal representative",
                        "processCategories", "citizenNoC"
                    )
                )
            ),
            Arguments.of(
                "citizenRemoveLegalRepresentative",
                null,
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "name", "Remove legal representative",
                        "processCategories", "citizenNoC",
                        "taskId", "removeLegalRepresentativeFL401"
                    )
                )
            ),
            Arguments.of(
                "c100RequestSupport",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "reviewRaRequestsC100",
                        "name", "Review RA request",
                        "processCategories", "reviewRAC100"
                    )
                )
            ),
            Arguments.of(
                "fl401RequestSupport",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "reviewRaRequestsFL401",
                        "name", "Review RA request",
                        "processCategories", "reviewRAFL401"
                    )
                )
            ),
            Arguments.of(
                "c100ManageSupport",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "reviewInactiveRaRequestsC100",
                        "name", "Review inactive RA request",
                        "processCategories", "reviewInactiveRAC100"
                    )
                )
            ),
            Arguments.of(
                "fl401ManageSupport",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "reviewInactiveRaRequestsFL401",
                        "name", "Review inactive RA request",
                        "processCategories", "reviewInactiveRAFL401"
                    )
                )
            ),
            Arguments.of(
                "serviceOfApplication",
                "PREPARE_FOR_HEARING_CONDUCT_HEARING",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"isC8CheckNeeded\":\"" + "Yes" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "name", "C8 - Confidential details check",
                        "processCategories", "confidentialCheck",
                        "taskId", "confidentialCheckSOA"
                    )
                )
            ),
            Arguments.of(
                "confidentialityCheck",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"isC8CheckApproved\":\"" + "No" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "name", "Recreate Application Pack",
                        "processCategories", "recreateApplicationPack",
                        "taskId", "recreateApplicationPack"
                    )
                )
            ),
            Arguments.of(
                "serviceOfApplication",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"isC8CheckNeeded\":\"" + "No" + "\"\n,"
                                      + "      \"responsibleForService\":\"" + "applicantLegalRepresentative" + "\"\n"
                                      + "   }"
                                      + "}"),
                List.of(
                    Map.of(
                        "name", "Application statement of service due",
                        "processCategories", "statementOfServiceBySolicitor",
                        "taskId", "appStatementOfServiceBySol"
                    )
                )
            ),
            Arguments.of(
                "serviceOfApplication",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"isC8CheckNeeded\":\"" + "No" + "\"\n,"
                                      + "      \"responsibleForService\":\"" + "unrepresentedApplicant" + "\"\n"
                                      + "   }"
                                      + "}"),
                List.of(
                    Map.of(
                        "name", "Application statement of service due",
                        "processCategories", "statementOfServiceByCitizen",
                        "taskId", "appStatementOfServiceByLiP"
                    )
                )
            ),
            Arguments.of(
                "serviceOfApplication",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"isC8CheckNeeded\":\"" + "No" + "\"\n,"
                                      + "      \"responsibleForService\":\"" + "courtBailiff" + "\"\n"
                                      + "   }"
                                      + "}"),
                List.of(
                    Map.of(
                        "name", "Application statement of service due",
                        "processCategories", "statementOfServiceByBailiff",
                        "taskId", "appStatementOfServiceByBailiff"
                    ),
                    Map.of(
                        "name", "Arrange bailiff service of application",
                        "processCategories", "arrangeBailiffSOA",
                        "taskId", "arrangeBailiffSOA"
                    )
                )
            ),
            Arguments.of(
                "serviceOfApplication",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"isC8CheckNeeded\":\"" + "No" + "\"\n,"
                                      + "      \"responsibleForService\":\"" + "courtAdmin" + "\"\n"
                                      + "   }"
                                      + "}"),
                List.of(
                    Map.of(
                        "name", "Arrange personal service of application and upload statement of service",
                        "processCategories", "statementOfServiceByAdmin",
                        "taskId", "appStatementOfServiceByAdmin"
                    )
                )
            ),
            Arguments.of(
                "serviceOfApplication",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n,"
                                      + "      \"isC8CheckNeeded\":\"" + "No" + "\"\n,"
                                      + "      \"isOccupationOrderSelected\":\"" + "Yes" + "\"\n"
                                      + "   }"
                                      + "}"),
                List.of(
                    Map.of(
                        "name", "Complete FL416 and serve applicant only",
                        "processCategories", "completefl416AndServe",
                        "taskId", "completefl416AndServe"
                    )
                )
            ),
            Arguments.of(
                "confidentialityCheck",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n,"
                                      + "      \"isC8CheckApproved\":\"" + "Yes" + "\"\n,"
                                      + "      \"isOccupationOrderSelected\":\"" + "Yes" + "\"\n"
                                      + "   }"
                                      + "}"),
                List.of(
                    Map.of(
                        "name", "Complete FL416 and serve applicant only",
                        "processCategories", "completefl416AndServe",
                        "taskId", "completefl416AndServe"
                    )
                )
            ),
            Arguments.of(
                "confidentialityCheck",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"isC8CheckApproved\":\"" + "Yes" + "\"\n,"
                                      + "      \"responsibleForService\":\"" + "applicantLegalRepresentative" + "\"\n"
                                      + "   }"
                                      + "}"),
                List.of(
                    Map.of(
                        "name", "Application statement of service due",
                        "processCategories", "statementOfServiceBySolicitor",
                        "taskId", "appStatementOfServiceBySol"
                    )
                )
            ),
            Arguments.of(
                "confidentialityCheck",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"isC8CheckApproved\":\"" + "Yes" + "\"\n,"
                                      + "      \"responsibleForService\":\"" + "unrepresentedApplicant" + "\"\n"
                                      + "   }"
                                      + "}"),
                List.of(
                    Map.of(
                        "name", "Application statement of service due",
                        "processCategories", "statementOfServiceByCitizen",
                        "taskId", "appStatementOfServiceByLiP"
                    )
                )
            ),
            Arguments.of(
                "confidentialityCheck",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"isC8CheckApproved\":\"" + "Yes" + "\"\n,"
                                      + "      \"responsibleForService\":\"" + "courtBailiff" + "\"\n"
                                      + "   }"
                                      + "}"),
                List.of(
                    Map.of(
                        "name", "Application statement of service due",
                        "processCategories", "statementOfServiceByBailiff",
                        "taskId", "appStatementOfServiceByBailiff"
                    ),
                    Map.of(
                        "name", "Arrange bailiff service of application",
                        "processCategories", "arrangeBailiffSOA",
                        "taskId", "arrangeBailiffSOA"
                    )
                )
            ),
            Arguments.of(
                "confidentialityCheck",
                "JUDICIAL_REVIEW",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"isC8CheckApproved\":\"" + "Yes" + "\"\n,"
                                      + "      \"responsibleForService\":\"" + "courtAdmin" + "\"\n"
                                      + "   }"
                                      + "}"),
                List.of(
                    Map.of(
                        "name", "Arrange personal service of application and upload statement of service",
                        "processCategories", "statementOfServiceByAdmin",
                        "taskId", "appStatementOfServiceByAdmin"
                    )
                )
            ),
            Arguments.of(
                "c100listWithoutNotice",
                null,
                null,
                List.of(
                    Map.of(
                        "name", "List without notice hearing (see case notes)",
                        "processCategories", "listWithoutNoticeHearingC100",
                        "taskId", "listWithoutNoticeHearingC100"
                    )
                )
            ),
            Arguments.of(
                "awpPaymentSuccessCallback",
                null,
                null,
                singletonList(
                    Map.of(
                        "taskId", "reviewAdditionalApplication",
                        "name", "Review additional application",
                        "processCategories", "reviewAddtlApp"
                    )
                )
            ),
            Arguments.of(
                "uploadAdditionalApplications",
                null,
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"awpWaTaskToBeCreated\":\"" + "Yes" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "reviewAdditionalApplication",
                        "name", "Review additional application",
                        "processCategories", "reviewAddtlApp"
                    )
                )
            ),
            Arguments.of(
                "createWaTaskForCtscCaseFlags",
                null,
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "C100" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "reviewRaRequestsC100",
                        "name", "Review RA request",
                        "processCategories", "reviewRAC100"
                    )
                )
            ),
            Arguments.of(
                "createWaTaskForCtscCaseFlags",
                null,
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"caseTypeOfApplication\":\"" + "FL401" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "reviewRaRequestsFL401",
                        "name", "Review RA request",
                        "processCategories", "reviewRAFL401"
                    )
                )
            ),
            Arguments.of(
                "citizenLanguageSupportNotes",
                null,
                null,
                singletonList(
                    Map.of(
                        "taskId", "reviewLangAndSmReq",
                        "name", "Review Language and SM requirements",
                        "processCategories", "reviewLangAndSmReqIdent"
                    )
                )
            ),
            Arguments.of(
                "citizenCaseSubmitWithHWF",
                "SUBMITTED_NOT_PAID",
                null,
                singletonList(
                    Map.of(
                        "taskId", "checkHwfApplicationC100",
                        "name", "Check HWF application",
                        "processCategories", "applicationHwfCheck"
                    )
                )
            ),
            Arguments.of(
                "hwfProcessCaseUpdate",
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
                        "processCategories", "applicationCheck"
                    )
                )
            ),
            Arguments.of(
                "processUrgentHelpWithFees",
                "SUBMITTED_PAID",
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"isTheCaseInDraftState\":\"" + "Yes" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "checkApplicationC100",
                        "name", "Check Application",
                        "processCategories", "applicationCheck"
                    )
                )
            ),
            Arguments.of(
                "citizenAwpCreate",
                null,
                null,
                singletonList(
                    Map.of(
                        "taskId", "reviewAdditionalApplication",
                        "name", "Review additional application",
                        "processCategories", "reviewAddtlAppCitizen"
                    )
                )
            ),
            Arguments.of(
                "citizenAwpHwfCreate",
                null,
                null,
                singletonList(
                    Map.of(
                        "taskId", "checkAwpHwfCitizen",
                        "name", "Check HWF on additional application",
                        "processCategories", "checkAwpHwfCitizen"
                    )
                )
            ),
            Arguments.of(
                "processHwfUpdateAwpStatus",
                null,
                null,
                singletonList(
                    Map.of(
                        "taskId", "reviewAdditionalApplication",
                        "name", "Review additional application",
                        "processCategories", "reviewAddtlAppCitizen"
                    )
                )
            ),
            Arguments.of(
                "processUrgentHelpWithFees",
                null,
                mapAdditionalData("{\n"
                                      + "   \"Data\":{\n"
                                      + "      \"isTheCaseInDraftState\":\"" + "No" + "\"\n"
                                      + "   }"
                                      + "}"),
                singletonList(
                    Map.of(
                        "taskId", "reviewAdditionalApplication",
                        "name", "Review additional application",
                        "processCategories", "reviewAddtlAppCitizen"
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
