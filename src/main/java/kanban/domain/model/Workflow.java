package kanban.domain.model;

import java.util.UUID;

public class Workflow {
    private String workflowName;
    private String boardId;
    private String workflowId;

    public Workflow(String workflowName, String boardId) {
        this.workflowName = workflowName;
        this.boardId = boardId;
        workflowId = UUID.randomUUID().toString();
    }

    public String getName() {
        return workflowName;
    }
}
