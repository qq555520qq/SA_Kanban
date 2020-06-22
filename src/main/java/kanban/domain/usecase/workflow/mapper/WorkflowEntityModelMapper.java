package kanban.domain.usecase.workflow.mapper;

import kanban.domain.model.aggregate.workflow.Stage;
import kanban.domain.model.aggregate.workflow.Workflow;
import kanban.domain.usecase.stage.StageEntity;
import kanban.domain.usecase.stage.mapper.StageEntityModelMapper;
import kanban.domain.usecase.workflow.WorkflowEntity;

import java.util.ArrayList;
import java.util.List;

public class WorkflowEntityModelMapper {
    public static Workflow transformEntityToModel(WorkflowEntity workflowEntity) {
        Workflow workflow = new Workflow();

        workflow.setName(workflowEntity.getName());
        workflow.setWorkflowId(workflowEntity.getWorkflowId());

        List<Stage> stages = new ArrayList<>();
        for(StageEntity stageEntity: workflowEntity.getStageEntities()) {
            Stage _stage = StageEntityModelMapper.transformEntityToModel(stageEntity);

            stages.add(_stage);
        }
        workflow.setStages(stages);

        return workflow;
    }
    public static WorkflowEntity transformModelToEntity(Workflow workflow) {
        WorkflowEntity workflowEntity = new WorkflowEntity();

        workflowEntity.setName(workflow.getName());
        workflowEntity.setWorkflowId(workflow.getWorkflowId());

        List<StageEntity> stageEntities = new ArrayList<>();
        for(Stage stage: workflow.getStages()) {
            stageEntities.add(StageEntityModelMapper.transformModelToEntity(stage));
        }
        workflowEntity.setStageEntities(stageEntities);


        return workflowEntity;
    }
}
