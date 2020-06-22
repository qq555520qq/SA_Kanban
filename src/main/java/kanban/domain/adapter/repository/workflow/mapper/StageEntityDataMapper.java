package kanban.domain.adapter.repository.workflow.mapper;

import kanban.domain.adapter.repository.workflow.data.StageData;
import kanban.domain.usecase.stage.StageEntity;

import java.util.ArrayList;
import java.util.List;

public class StageEntityDataMapper {
    public static StageData transformEntityToData(StageEntity stageEntity) {
        StageData stageData = new StageData();

        stageData.setWorkflowId(stageEntity.getWorkflowId());
        stageData.setStageId(stageEntity.getStageId());
        stageData.setName(stageEntity.getName());
        stageData.setWipLimit(stageEntity.getWipLimit());
        stageData.setLayout(stageEntity.getLayout());

        List<StageData> stageDatas = new ArrayList<>();
        for(StageEntity _stageEntity: stageEntity.getStageEntities()) {
            stageDatas.add(StageEntityDataMapper.transformEntityToData(_stageEntity));
        }
        stageData.setStageDatas(stageDatas);

        stageData.setCardIds(stageEntity.getCardIds());

        return stageData;
    }
    public static StageEntity transformDataToEntity(StageData stageData) {
        StageEntity stageEntity = new StageEntity();

        stageEntity.setWorkflowId(stageData.getWorkflowId());
        stageEntity.setStageId(stageData.getStageId());
        stageEntity.setName(stageData.getName());
        stageEntity.setWipLimit(stageData.getWipLimit());
        stageEntity.setLayout(stageData.getLayout());

        List<StageEntity> stageEntities = new ArrayList<>();
        for(StageData _stageData: stageData.getStageDatas()) {
            stageEntities.add(StageEntityDataMapper.transformDataToEntity(_stageData));
        }
        stageEntity.setStageEntities(stageEntities);

        stageEntity.setCardIds(stageData.getCardIds());

        return stageEntity;
    }
}
