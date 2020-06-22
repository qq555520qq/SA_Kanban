package kanban.domain.usecase.stage.mapper;

import kanban.domain.model.aggregate.workflow.Stage;
import kanban.domain.usecase.stage.StageDTO;

public class StageDTOModelMapper {
    public static StageDTO transformStageToDTO(Stage stage){
        StageDTO stageDTO = new StageDTO();
        stageDTO.setWorkflowId(stage.getWorkflowId());
        stageDTO.setStageId(stage.getStageId());
        stageDTO.setName(stage.getName());
        stageDTO.setCardIds(stage.getCardIds());
        return stageDTO;
    }


}
