package kanban.domain.unit_test;


import kanban.domain.model.aggregate.workflow.Workflow;
import kanban.domain.model.aggregate.workflow.event.CardCommitted;
import kanban.domain.model.aggregate.workflow.event.StageCreated;
import kanban.domain.model.aggregate.workflow.event.WorkflowCreated;
import org.junit.Test;

import javax.smartcardio.Card;

import static org.junit.Assert.*;

public class WorkflowTest {

    @Test
    public void Create_workflow_should_have_the_WorkflowCreated_event_in_domainEvent_list() {
        Workflow workflow = new Workflow("workflowName", "boardId");

        assertEquals(1, workflow.getDomainEvents().size());
        assertEquals(WorkflowCreated.class, workflow.getDomainEvents().get(0).getClass());
    }

    @Test
    public void Create_stage_should_have_the_StageCreated_event_in_domainEvent_list() {
        Workflow workflow = new Workflow("workflowName", "boardId");

        assertEquals(0, workflow.getStages().size());

        workflow.createStage("stageName");

        assertEquals(1, workflow.getStages().size());
        assertEquals("stageName", workflow.getStages().get(0).getName());
        assertEquals(2, workflow.getDomainEvents().size());
        assertEquals(StageCreated.class, workflow.getDomainEvents().get(1).getClass());
    }

    @Test
    public void Commit_card_should_have_the_CardCommitted_event_in_domainEvent_list() {
        Workflow workflow = new Workflow("workflowName", "boardId");
        String stageId = workflow.createStage("stageName");

        assertEquals(0, workflow.getStageCloneById(stageId).getCardIds().size());

        workflow.commitCardInStage("cardId", stageId);

        assertEquals(1, workflow.getStageCloneById(stageId).getCardIds().size());
        assertEquals(3, workflow.getDomainEvents().size());
        assertEquals(CardCommitted.class, workflow.getDomainEvents().get(2).getClass());
    }
}
