package kanban.domain.usecase.workflow.mapper;


import kanban.domain.model.aggregate.workflow.Stage;
import kanban.domain.model.aggregate.workflow.Workflow;
import kanban.domain.usecase.stage.StageDTO;
import kanban.domain.usecase.stage.mapper.StageDTOModelMapper;
import kanban.domain.usecase.workflow.WorkflowDTO;

import java.util.ArrayList;
import java.util.List;

public class WorkflowDTOModelMapper {
    public static WorkflowDTO transformModelToDTO(Workflow workflow){
        WorkflowDTO workflowDTO = new WorkflowDTO();
        workflowDTO.setWorkflowId(workflow.getWorkflowId());
        workflowDTO.setName(workflow.getName());

        List<StageDTO> stageDTOs = new ArrayList<>();
        for(Stage stage : workflow.getStages()) {
            stageDTOs.add(StageDTOModelMapper.transformStageToDTO(stage));
        }

        workflowDTO.setStageDTOs(stageDTOs);
        return workflowDTO;
    }
}
