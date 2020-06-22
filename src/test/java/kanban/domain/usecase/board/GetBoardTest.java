package kanban.domain.usecase.board;

import kanban.domain.Utility;
import kanban.domain.adapter.presenter.board.get.GetBoardsPresenter;
import kanban.domain.adapter.repository.board.InMemoryBoardRepository;
import kanban.domain.adapter.repository.card.InMemoryCardRepository;
import kanban.domain.adapter.repository.domainEvent.InMemoryDomainEventRepository;
import kanban.domain.adapter.repository.flowEvent.InMemoryFlowEventRepository;
import kanban.domain.adapter.repository.workflow.InMemoryWorkflowRepository;
import kanban.domain.model.DomainEventBus;
import kanban.domain.usecase.card.ICardRepository;
import kanban.domain.usecase.flowEvent.IFlowEventRepository;
import kanban.domain.usecase.handler.domainEvent.DomainEventHandler;
import kanban.domain.usecase.board.get.GetBoardsInput;
import kanban.domain.usecase.board.get.GetBoardsOutput;
import kanban.domain.usecase.board.get.GetBoardsUseCase;
import kanban.domain.usecase.workflow.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GetBoardTest {
    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    private Utility utility;
    private IFlowEventRepository flowEventRepository;
    private ICardRepository cardRepository;

    @Before
    public void setUp() {
        workflowRepository = new InMemoryWorkflowRepository();
        boardRepository = new InMemoryBoardRepository();
        flowEventRepository = new InMemoryFlowEventRepository();
        cardRepository = new InMemoryCardRepository();
//        workflowRepository = new MySqlWorkflowRepository();
//        boardRepository = new MySqlBoardRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(new InMemoryDomainEventRepository()));

        utility = new Utility(boardRepository, workflowRepository, flowEventRepository, cardRepository,eventBus);
        utility.createBoard("boardName");
    }

    @Test
    public void Get_board_should_return_boardId() {

        GetBoardsUseCase getBoardsUseCase = new GetBoardsUseCase(boardRepository);
        GetBoardsInput input = getBoardsUseCase;
        input.setUserId("1");

        GetBoardsOutput output = new GetBoardsPresenter();

        getBoardsUseCase.execute(input, output);

        assertEquals(1, output.getBoardDTOs().size());
        assertEquals("1", output.getBoardDTOs().get(0).getUserId());
        assertEquals("boardName", output.getBoardDTOs().get(0).getBoardName());
        assertEquals(0, output.getBoardDTOs().get(0).getWorkflowIds().size());
    }
}
