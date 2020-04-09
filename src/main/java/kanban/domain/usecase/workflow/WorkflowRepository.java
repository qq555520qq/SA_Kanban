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
}
