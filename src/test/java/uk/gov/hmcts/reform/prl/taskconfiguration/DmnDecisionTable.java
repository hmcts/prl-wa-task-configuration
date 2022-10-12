package uk.gov.hmcts.reform.prl.taskconfiguration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DmnDecisionTable {

    WA_TASK_CANCEL("wa-task-cancellation-family-prl", "wa-task-cancellation-privatelaw-prlapps.dmn"),
    WA_TASK_COMPLETION("wa-task-completion-family-prl", "wa-task-completion-privatelaw-prlapps.dmn"),
    WA_TASK_CONFIGS("wa-task-configuration-family-prl", "wa-task-configuration-privatelaw-prlapps.dmn"),
    WA_TASK_INITIATION("wa-task-initiation-family-prl", "wa-task-initiation-privatelaw-prlapps.dmn"),
    WA_TASK_PERMISSION("wa-task-permissions-family-prl", "wa-task-permissions-privatelaw-prlapps.dmn");


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
