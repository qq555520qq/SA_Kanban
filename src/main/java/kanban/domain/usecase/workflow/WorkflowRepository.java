package kanban.domain.usecase.workflow;

import kanban.domain.model.Workflow;

import java.util.ArrayList;
import java.util.List;

public class WorkflowRepository {

    private List<Workflow> workflows;

    public WorkflowRepository() {
        workflows = new ArrayList<Workflow>();
    }

    public void add(Workflow workflow) {
        workflows.add(workflow);
    }

    public Workflow getWorkflowById(String workflowId) {

        for (Workflow each : workflows) {
            if (each.getWorkflowId().equalsIgnoreCase(workflowId)) {
                return each;
            }
        }
        throw new RuntimeException("Workflow is not found,id=" + workflowId);
    }

    public void save(Workflow workflow) {

        for (Workflow each : workflows) {
            if (each.getWorkflowId().equalsIgnoreCase(workflow.getWorkflowId())) {
                workflows.set(workflows.indexOf(each), workflow);
                break;
            }
        }
    }
}
