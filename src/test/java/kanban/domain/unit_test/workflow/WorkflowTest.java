package kanban.domain.unit_test.workflow;


import kanban.domain.model.aggregate.workflow.Workflow;
import kanban.domain.model.aggregate.workflow.event.CardCommitted;
import kanban.domain.model.aggregate.workflow.event.CardUnCommitted;
import kanban.domain.model.aggregate.workflow.event.StageCreated;
import kanban.domain.model.aggregate.workflow.event.WorkflowCreated;
import org.junit.Test;


import static org.junit.Assert.*;

public class WorkflowTest {

    @Test
    public void Create_workflow_should_have_the_WorkflowCreated_event_in_domainEvent_list() {
        Workflow workflow = new Workflow("boardId", "workflowName");

        assertEquals(1, workflow.getDomainEvents().size());
        assertEquals(WorkflowCreated.class, workflow.getDomainEvents().get(0).getClass());

        WorkflowCreated workflowCreated = (WorkflowCreated) workflow.getDomainEvents().get(0);

        assertEquals("boardId", workflowCreated.getBoardId());
        assertEquals(workflow.getName(), workflowCreated.getName());
        assertEquals(workflow.getWorkflowId(), workflowCreated.getWorkflowId());
    }

    @Test
    public void Create_stage_should_have_the_StageCreated_event_in_domainEvent_list() {
        Workflow workflow = new Workflow("workflowName", "boardId");

        assertEquals(0, workflow.getStages().size());

        workflow.createStage("stageName", 1, "Horizontal");

        assertEquals(1, workflow.getStages().size());
        assertEquals("stageName", workflow.getStages().get(0).getName());
        assertEquals(1, workflow.getStages().get(0).getWipLimit().toInt());
        assertEquals("Horizontal", workflow.getStages().get(0).getLayout().toString());

        assertEquals(2, workflow.getDomainEvents().size());
        assertEquals(StageCreated.class, workflow.getDomainEvents().get(1).getClass());

        StageCreated stageCreated = (StageCreated) workflow.getDomainEvents().get(1);

        assertEquals("stageName", stageCreated.getName());
        assertEquals(workflow.getStages().get(0).getStageId(), stageCreated.getStageId());
        assertEquals(workflow.getWorkflowId(), stageCreated.getWorkflowId());
        assertEquals(1, stageCreated.getWipLimit());
        assertEquals("Horizontal", stageCreated.getLayout());
    }

    @Test
    public void Commit_card_should_have_the_CardCommitted_event_in_domainEvent_list() {
        Workflow workflow = new Workflow("workflowName", "boardId");
        String stageId = workflow.createStage("stageName", 2, "Horizontal");

        assertEquals(0, workflow.getStageCloneById(stageId).getCardIds().size());

        workflow.commitCardInStage("cardId", stageId);

        assertEquals(1, workflow.getStageCloneById(stageId).getCardIds().size());
        assertEquals(3, workflow.getDomainEvents().size());
        assertEquals(CardCommitted.class, workflow.getDomainEvents().get(2).getClass());

        CardCommitted cardCommitted = (CardCommitted) workflow.getDomainEvents().get(2);

        assertEquals("cardId", cardCommitted.getCardId());
        assertEquals(stageId, cardCommitted.getStageId());
    }

    @Test
    public void Move_card_should_have_the_CardUnCommitted_event_and_CardCommitted_event_in_domainEvent_list() {
        Workflow workflow = new Workflow("workflowName", "boardId");
        String todoStageId = workflow.createStage("todoStage", 2, "Horizontal");
        String doingStageId = workflow.createStage("doingStage", 2, "Horizontal");
        String cardId = "cardId";
        workflow.commitCardInStage(cardId, todoStageId);

        workflow.moveCard(cardId, todoStageId, doingStageId);

        assertEquals(6, workflow.getDomainEvents().size());
        assertEquals(CardUnCommitted.class, workflow.getDomainEvents().get(4).getClass());

        CardUnCommitted cardUnCommitted = (CardUnCommitted) workflow.getDomainEvents().get(4);

        assertEquals(cardId, cardUnCommitted.getCardId());
        assertEquals(todoStageId, cardUnCommitted.getStageId());

        CardCommitted cardCommitted = (CardCommitted) workflow.getDomainEvents().get(5);

        assertEquals(cardId, cardCommitted.getCardId());
        assertEquals(doingStageId, cardCommitted.getStageId());
    }
}
