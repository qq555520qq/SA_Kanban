package kanban.domain.adapter.repository.workflow;

import kanban.domain.adapter.repository.workflow.data.WorkflowData;
import kanban.domain.adapter.repository.workflow.mapper.WorkflowEntityDataMapper;
import kanban.domain.usecase.workflow.WorkflowEntity;
import kanban.domain.usecase.workflow.IWorkflowRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryWorkflowRepository implements IWorkflowRepository {

    private List<WorkflowData> workflowDatas;

    public InMemoryWorkflowRepository() {
        workflowDatas = new ArrayList<WorkflowData>();
    }

    @Override
    public void add(WorkflowEntity workflowEntity) {
        workflowDatas.add(WorkflowEntityDataMapper.transformEntityToData(workflowEntity));
    }

    @Override
    public WorkflowEntity getWorkflowById(String workflowId) {

        for (WorkflowData each : workflowDatas) {
            if (each.getWorkflowId().equalsIgnoreCase(workflowId)) {
                return WorkflowEntityDataMapper.transformDataToEntity(each);
            }
        }
        throw new RuntimeException("Workflow is not found,id=" + workflowId);
    }

    @Override
    public void save(WorkflowEntity workflowEntity) {

        for (WorkflowData each : workflowDatas) {
            if (each.getWorkflowId().equalsIgnoreCase(workflowEntity.getWorkflowId())) {
                workflowDatas.set(workflowDatas.indexOf(each), WorkflowEntityDataMapper.transformEntityToData(workflowEntity));
                break;
            }
        }
    }
}
