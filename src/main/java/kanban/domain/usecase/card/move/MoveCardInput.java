package kanban.domain.usecase.card.move;

public interface MoveCardInput {

    public String getWorkflowId();

    public void setWorkflowId(String workflowId);

    public String getCardId();

    public void setCardId(String cardId);

    public String getOriginStageId();

    public void setOriginStageId(String originStageId);

    public String getNewStageId();

    public void setNewStageId(String newStageId);
}
