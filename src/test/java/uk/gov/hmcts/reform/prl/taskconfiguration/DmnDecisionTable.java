package uk.gov.hmcts.reform.prl.taskconfiguration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DmnDecisionTable {

    WA_TASK_ALLOWED_DAYS_FAMILY_PRL("wa-task-allowed-days-family-prl", "wa-task-allowed-days-family-prl.dmn"),
    WA_TASK_CANCELLATION_FAMILY_PRL("wa-task-cancellation-family-prl", "wa-task-cancellation-family-prl.dmn"),
    WA_TASK_COMPLETION_FAMILY_PRL("wa-task-completion-family-prl", "wa-task-completion-family-prl.dmn"),
    WA_TASK_CONFIGURATION_FAMILY_PRL("wa-task-configuration-family-prl", "wa-task-configuration-family-prl.dmn"),
    WA_TASK_INITIATION_FAMILY_PRL("wa-task-initiation-family-prl", "wa-task-initiation-family-prl.dmn"),
    WA_TASK_PERMISSIONS_FAMILY_PRL("wa-task-permissions-family-prl", "wa-task-permissions-family-prl.dmn");


    @JsonValue
    private final String key;
    private final String fileName;

    DmnDecisionTable(String key, String fileName) {
        this.key = key;
        this.fileName = fileName;
    }

    public String getKey() {
        return key;
    }

    public String getFileName() {
        return fileName;
    }
}
