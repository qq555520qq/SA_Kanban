package kanban.domain.unit_test.board;

import kanban.domain.model.DomainEvent;
import kanban.domain.model.aggregate.board.Board;
import kanban.domain.model.aggregate.board.event.BoardCreated;
import kanban.domain.model.aggregate.board.event.WorkflowCommitted;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    private String userId;

    @Before
    public void setUp() throws Exception {
        userId = "1";
    }

    @Test
    public void Create_board_should_have_the_BoardCreated_event_in_domainEvent_list() {

        Board board = new Board(userId, "defaultBoard");

        assertEquals(1, board.getDomainEvents().size());
        assertEquals(BoardCreated.class, board.getDomainEvents().get(0).getClass());

        BoardCreated boardCreated =  ((BoardCreated) board.getDomainEvents().get(0));

        assertEquals("1", boardCreated.getUserId());
        assertEquals(board.getBoardId(), boardCreated.getBoardId());
        assertEquals(board.getBoardName(), boardCreated.getBoardName());
    }

    @Test
    public void Commit_workflow_should_have_the_WorkflowCommitted_event_in_domainEvent_list() {

        Board board = new Board(userId,"defaultBoard");

        assertEquals(0, board.getWorkflowIds().size());

        board.commitWorkflow("SA1321-1321");

        assertEquals(1, board.getWorkflowIds().size());
        assertEquals("SA1321-1321", board.getWorkflowIds().get(0));
        assertEquals(2, board.getDomainEvents().size());
        assertEquals(WorkflowCommitted.class, board.getDomainEvents().get(1).getClass());

        WorkflowCommitted workflowCommitted =  ((WorkflowCommitted) board.getDomainEvents().get(1));
        assertEquals(board.getBoardId(), workflowCommitted.getBoardId());
        assertEquals("SA1321-1321", workflowCommitted.getWorkflowId());

    }
}
