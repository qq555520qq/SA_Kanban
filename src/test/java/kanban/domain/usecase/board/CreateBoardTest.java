package kanban.domain.usecase.board;


import kanban.domain.adapter.presenter.board.create.CreateBoardPresenter;
import kanban.domain.adapter.repository.board.InMemoryBoardRepository;
import kanban.domain.adapter.repository.domainEvent.InMemoryDomainEventRepository;
import kanban.domain.model.DomainEventBus;
import kanban.domain.model.aggregate.board.Board;
import kanban.domain.usecase.handler.domainEvent.DomainEventHandler;
import kanban.domain.usecase.board.create.CreateBoardInput;
import kanban.domain.usecase.board.create.CreateBoardOutput;
import kanban.domain.usecase.board.create.CreateBoardUseCase;
import kanban.domain.usecase.board.mapper.BoardEntityModelMapper;
import kanban.domain.usecase.workflow.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateBoardTest {

    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;

    private DomainEventBus eventBus;

    @Before
    public void setUp() {
        boardRepository = new InMemoryBoardRepository();
//        boardRepository = new MySqlBoardRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(new InMemoryDomainEventRepository()));

    }

    @Test
    public void Create_board_should_return_boardId() {
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository, eventBus);
        CreateBoardInput input = createBoardUseCase;
        CreateBoardOutput output = new CreateBoardPresenter();
        input.setUserId("1");
        input.setBoardName("Board");
        createBoardUseCase.execute(input, output);
        assertNotNull(output.getBoardId());

        Board board = BoardEntityModelMapper.transformEntityToModel(boardRepository.getBoardById(output.getBoardId()));

        assertEquals("1", board.getUserId());
        assertEquals(output.getBoardId(), board.getBoardId());
        assertEquals("Board", board.getBoardName());
    }
}
