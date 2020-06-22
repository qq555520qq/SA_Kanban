package kanban.domain.usecase.stage.mapper;


import kanban.domain.model.aggregate.workflow.Layout;
import kanban.domain.model.aggregate.workflow.Stage;
import kanban.domain.model.aggregate.workflow.WipLimit;
import kanban.domain.usecase.stage.StageEntity;

import java.util.ArrayList;
import java.util.List;

public class StageEntityModelMapper {
    public static Stage transformEntityToModel(StageEntity stageEntity) {
        Stage stage = new Stage();

        stage.setWorkflowId(stageEntity.getWorkflowId());
        stage.setStageId(stageEntity.getStageId());
        stage.setName(stageEntity.getName());
        stage.setWipLimit(new WipLimit(stageEntity.getWipLimit()));
        stage.setLayout(new Layout(stageEntity.getLayout()));

        List<Stage> stages = new ArrayList<>();
        for(StageEntity _stageEntity : stageEntity.getStageEntities()) {
            stages.add(StageEntityModelMapper.transformEntityToModel(_stageEntity));
        }
        stage.setStages(stages);

        stage.setCardIds(stageEntity.getCardIds());

        return stage;
    }
    public static StageEntity transformModelToEntity(Stage stage) {
        StageEntity stageEntity = new StageEntity();

        stageEntity.setWorkflowId(stage.getWorkflowId());
        stageEntity.setStageId(stage.getStageId());
        stageEntity.setName(stage.getName());
        stageEntity.setWipLimit(stage.getWipLimit().toInt());
        stageEntity.setLayout(stage.getLayout().toString());

        List<StageEntity> stageEntities = new ArrayList<>();
        for(Stage _stage : stage.getStages()) {
            stageEntities.add(StageEntityModelMapper.transformModelToEntity(_stage));
        }
        stageEntity.setStageEntities(stageEntities);

        stageEntity.setCardIds(stage.getCardIds());

        return stageEntity;
    }
}
