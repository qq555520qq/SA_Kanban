package kanban.domain.usecase.workflow;

import kanban.domain.Utility;
import kanban.domain.adapter.repository.board.InMemoryBoardRepository;
import kanban.domain.adapter.repository.workflow.InMemoryWorkflowRepository;
import kanban.domain.model.DomainEventBus;
import kanban.domain.model.aggregate.board.Board;
import kanban.domain.usecase.DomainEventHandler;
import kanban.domain.usecase.board.mapper.BoardEntityModelMapper;
import kanban.domain.usecase.board.repository.IBoardRepository;
import kanban.domain.usecase.workflow.commit.CommitWorkflowInput;
import kanban.domain.usecase.workflow.commit.CommitWorkflowOutput;
import kanban.domain.usecase.workflow.commit.CommitWorkflowUseCase;
import kanban.domain.usecase.workflow.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommitWorkflowTest {

    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;
    private Utility utility;

    @Before
    public void setUp() throws Exception {
        boardRepository = new InMemoryBoardRepository();
        workflowRepository = new InMemoryWorkflowRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(
                boardRepository,
                workflowRepository));

        utility = new Utility(boardRepository, workflowRepository, eventBus);
    }

    @Test
    public void Workflow_should_be_committed_in_its_board() {
        String boardId = utility.createBoard("board");
        Board board = BoardEntityModelMapper.transformEntityToModel(boardRepository.getBoardById(boardId));

        assertEquals(0,board.getWorkflowIds().size());

        CommitWorkflowUseCase commitWorkflowUseCase = new CommitWorkflowUseCase(boardRepository);
        CommitWorkflowInput input = new CommitWorkflowInput();
        CommitWorkflowOutput output = new CommitWorkflowOutput();

        input.setBoardId(boardId);
        input.setWorkflowId("workflowId");
        commitWorkflowUseCase.execute(input, output);

        board = BoardEntityModelMapper.transformEntityToModel(boardRepository.getBoardById(boardId));
        assertEquals(1,board.getWorkflowIds().size());
        assertEquals("workflowId",output.getWorkflowId());

    }
}
