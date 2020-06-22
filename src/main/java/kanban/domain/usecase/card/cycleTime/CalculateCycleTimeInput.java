package kanban.domain.usecase.card.cycleTime;

public interface CalculateCycleTimeInput {
    public String getWorkflowId();

    public void setWorkflowId(String workflowId);

    public String getCardId();

    public void setCardId(String cardId);

    public String getBeginningStageId();

    public void setBeginningStageId(String beginningStageId);

    public String getEndingStageId();

    public void setEndingStageId(String endingStageId);
}
