package kanban.domain.unit_test;

import kanban.domain.model.DomainEvent;
import kanban.domain.model.aggregate.board.Board;
import kanban.domain.model.aggregate.board.event.BoardCreated;
import kanban.domain.model.aggregate.board.event.WorkflowCommitted;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void Create_board_should_have_the_BoardCreated_event_in_domainEvent_list() {

        Board board = new Board("defaultBoard");

        assertEquals(1, board.getDomainEvents().size());
        assertEquals(BoardCreated.class, board.getDomainEvents().get(0).getClass());
    }

    @Test
    public void Commit_workflow_should_have_the_WorkflowCommitted_event_in_domainEvent_list() {

        Board board = new Board("defaultBoard");

        assertEquals(0, board.getWorkflowIds().size());

        board.commitWorkflow("SA1321-1321");

        assertEquals(1, board.getWorkflowIds().size());
        assertEquals("SA1321-1321", board.getWorkflowIds().get(0));
        assertEquals(2, board.getDomainEvents().size());
        assertEquals(WorkflowCommitted.class, board.getDomainEvents().get(1).getClass());

    }
}
