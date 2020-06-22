package kanban.domain.usecase.workflow;

import kanban.domain.usecase.stage.StageDTO;

import java.util.List;

public class WorkflowDTO {
    private String name;
    private String workflowId;
    private List<StageDTO> stageDTOs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public List<StageDTO> getStageDTOs() {
        return stageDTOs;
    }

    public void setStageDTOs(List<StageDTO> stageDTOs) {
        this.stageDTOs = stageDTOs;
    }
}
