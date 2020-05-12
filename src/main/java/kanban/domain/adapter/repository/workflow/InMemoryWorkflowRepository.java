package kanban.domain.adapter.repository.workflow;

import kanban.domain.model.aggregate.workflow.Workflow;
import kanban.domain.usecase.workflow.repository.IWorkflowRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryWorkflowRepository implements IWorkflowRepository {

    private List<Workflow> workflows;

    public InMemoryWorkflowRepository() {
        workflows = new ArrayList<Workflow>();
    }

    @Override
    public void add(Workflow workflow) {
        workflows.add(workflow);
    }

    @Override
    public Workflow getWorkflowById(String workflowId) {

        for (Workflow each : workflows) {
            if (each.getWorkflowId().equalsIgnoreCase(workflowId)) {
                return each;
            }
        }
        throw new RuntimeException("Workflow is not found,id=" + workflowId);
    }

    @Override
    public void save(Workflow workflow) {

        for (Workflow each : workflows) {
            if (each.getWorkflowId().equalsIgnoreCase(workflow.getWorkflowId())) {
                workflows.set(workflows.indexOf(each), workflow);
                break;
            }
        }
    }
}
