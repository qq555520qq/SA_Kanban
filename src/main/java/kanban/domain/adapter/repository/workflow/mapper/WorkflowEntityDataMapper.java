package kanban.domain.adapter.repository.workflow.mapper;

import kanban.domain.adapter.repository.workflow.data.StageData;
import kanban.domain.adapter.repository.workflow.data.WorkflowData;
import kanban.domain.usecase.stage.StageEntity;
import kanban.domain.usecase.workflow.WorkflowEntity;

import java.util.ArrayList;
import java.util.List;

public class WorkflowEntityDataMapper {
    public static WorkflowData transformEntityToData(WorkflowEntity workflowEntity) {
        WorkflowData workflowData = new WorkflowData();

        workflowData.setName(workflowEntity.getName());
        workflowData.setWorkflowId(workflowEntity.getWorkflowId());

        List<StageData> stageDatas = new ArrayList<>();
        for(StageEntity stageEntity: workflowEntity.getStageEntities()) {
            stageDatas.add(StageEntityDataMapper.transformEntityToData(stageEntity));
        }
        workflowData.setStageDatas(stageDatas);

        return workflowData;
    }
    public static WorkflowEntity transformDataToEntity(WorkflowData workflowData) {
        WorkflowEntity workflowEntity = new WorkflowEntity();

        workflowEntity.setName(workflowData.getName());
        workflowEntity.setWorkflowId(workflowData.getWorkflowId());

        List<StageEntity> stageEntities = new ArrayList<>();
        for(StageData stageData: workflowData.getStageDatas()) {
            stageEntities.add(StageEntityDataMapper.transformDataToEntity(stageData));
        }
        workflowEntity.setStageEntities(stageEntities);

        return workflowEntity;
    }
}
