package kanban.domain.usecase.card.cycleTime;

import kanban.domain.model.FlowEvent;
import kanban.domain.model.aggregate.workflow.Stage;
import kanban.domain.model.aggregate.workflow.Workflow;
import kanban.domain.usecase.flowEvent.IFlowEventRepository;
import kanban.domain.usecase.workflow.mapper.WorkflowEntityModelMapper;
import kanban.domain.usecase.workflow.IWorkflowRepository;

import java.util.*;

public class CalculateCycleTimeUseCase implements CalculateCycleTimeInput {
    private String workflowId;
    private String cardId;
    private String beginningStageId;
    private String endingStageId;

    private IWorkflowRepository workflowRepository;
    private IFlowEventRepository flowEventRepository;
    private List<FlowEventPair> flowEventPairs;

    public CalculateCycleTimeUseCase(IWorkflowRepository workflowRepository, IFlowEventRepository flowEventRepository) {
        this.workflowRepository = workflowRepository;
        this.flowEventRepository = flowEventRepository;
        flowEventPairs = new ArrayList<>();
    }

    public void execute(CalculateCycleTimeInput input, CalculateCycleTimeOutput output) {
        Stack<FlowEvent> stack = new Stack<>();

        for(FlowEvent flowEvent: flowEventRepository.getAll()) {
            if(!flowEvent.getCardId().equals(input.getCardId())) {
                continue;
            }
            if(stack.empty()) {
                // committedCard
                stack.push(flowEvent);
            }else {
                FlowEvent committed = stack.pop();
                flowEventPairs.add(new FlowEventPair(committed, flowEvent));
            }
        }

        if(!stack.empty()){
            flowEventPairs.add(new FlowEventPair(stack.pop()));
        }

        Workflow workflow = WorkflowEntityModelMapper.transformEntityToModel(
                workflowRepository.getWorkflowById(input.getWorkflowId()));
        boolean isInBoundary = false;
        List<String> stageIds = new ArrayList();
        for(Stage stage: workflow.getStages()){
            if(stage.getStageId().equals(input.getBeginningStageId())){
                isInBoundary = true;
            }else if(stage.getStageId().equals(input.getEndingStageId())){
                stageIds.add(stage.getStageId());
                break;
            }
            if(isInBoundary) {
                stageIds.add(stage.getStageId());
            }

        }

        long time = 0;
        for(String stageId: stageIds){
            for(FlowEventPair flowEventPair: flowEventPairs){
                if(flowEventPair.getCycleTimeInStage().getStageId().equals(stageId)){
                    time+= flowEventPair.getCycleTimeInStage().getDiff();
                }
            }
        }

        CycleTime cycleTime = new CycleTime(time);

        output.setCycleTime(cycleTime);

    }



    @Override
    public String getWorkflowId() {
        return workflowId;
    }

    @Override
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    @Override
    public String getCardId() {
        return cardId;
    }

    @Override
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    @Override
    public String getBeginningStageId() {
        return beginningStageId;
    }

    @Override
    public void setBeginningStageId(String beginningStageId) {
        this.beginningStageId = beginningStageId;
    }

    @Override
    public String getEndingStageId() {
        return endingStageId;
    }

    @Override
    public void setEndingStageId(String endingStageId) {
        this.endingStageId = endingStageId;
    }

}
