package kanban.domain.usecase.workflow;

import kanban.domain.Utility;
import kanban.domain.adapter.presenter.workflow.commit.CommitWorkflowPresenter;
import kanban.domain.adapter.repository.board.InMemoryBoardRepository;
import kanban.domain.adapter.repository.card.InMemoryCardRepository;
import kanban.domain.adapter.repository.domainEvent.InMemoryDomainEventRepository;
import kanban.domain.adapter.repository.flowEvent.InMemoryFlowEventRepository;
import kanban.domain.adapter.repository.workflow.InMemoryWorkflowRepository;
import kanban.domain.model.DomainEventBus;
import kanban.domain.model.aggregate.board.Board;
import kanban.domain.usecase.card.ICardRepository;
import kanban.domain.usecase.flowEvent.IFlowEventRepository;
import kanban.domain.usecase.handler.domainEvent.DomainEventHandler;
import kanban.domain.usecase.board.mapper.BoardEntityModelMapper;
import kanban.domain.usecase.board.IBoardRepository;
import kanban.domain.usecase.workflow.commit.CommitWorkflowInput;
import kanban.domain.usecase.workflow.commit.CommitWorkflowOutput;
import kanban.domain.usecase.workflow.commit.CommitWorkflowUseCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommitWorkflowTest {

    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;
    private Utility utility;
    private IFlowEventRepository flowEventRepository;
    private ICardRepository cardRepository;

    @Before
    public void setUp() throws Exception {
        boardRepository = new InMemoryBoardRepository();
        workflowRepository = new InMemoryWorkflowRepository();
        flowEventRepository = new InMemoryFlowEventRepository();
        cardRepository = new InMemoryCardRepository();
//        boardRepository = new MySqlBoardRepository();
//        workflowRepository = new MySqlWorkflowRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(new InMemoryDomainEventRepository()));

        utility = new Utility(boardRepository, workflowRepository, flowEventRepository, cardRepository,eventBus);
    }

    @Test
    public void Workflow_should_be_committed_in_its_board() {
        String boardId = utility.createBoard("board");
        Board board = BoardEntityModelMapper.transformEntityToModel(boardRepository.getBoardById(boardId));

        assertEquals(0,board.getWorkflowIds().size());

        CommitWorkflowUseCase commitWorkflowUseCase = new CommitWorkflowUseCase(boardRepository, eventBus);
        CommitWorkflowInput input = commitWorkflowUseCase;
        CommitWorkflowOutput output = new CommitWorkflowPresenter();

        input.setBoardId(boardId);
        input.setWorkflowId("workflowId");
        commitWorkflowUseCase.execute(input, output);

        board = BoardEntityModelMapper.transformEntityToModel(boardRepository.getBoardById(boardId));
        assertEquals(1,board.getWorkflowIds().size());
        assertEquals("workflowId",output.getWorkflowId());

    }
}
