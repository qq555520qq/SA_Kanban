package kanban.domain.usecase;


import kanban.domain.adapter.repository.board.MySqlBoardRepository;
import kanban.domain.model.aggregate.board.Board;
import kanban.domain.usecase.board.create.CreateBoardInput;
import kanban.domain.usecase.board.create.CreateBoardOutput;
import kanban.domain.usecase.board.create.CreateBoardUseCase;
import kanban.domain.usecase.board.repository.IBoardRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateBoardTest {

    private IBoardRepository boardRepository;

    @Before
    public void setUp() {
//        boardRepository = new InMemoryBoardRepository();
        boardRepository = new MySqlBoardRepository();
    }

    @Test
    public void Create_board_should_return_boardId() {
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository);
        CreateBoardInput input = new CreateBoardInput();
        CreateBoardOutput output = new CreateBoardOutput();

        input.setBoardName("Board");
        createBoardUseCase.execute(input, output);
        assertNotNull(output.getBoardId());

        Board board = TransformToEntity.transform(boardRepository.getBoardById(output.getBoardId()));

        assertEquals(output.getBoardId(), board.getBoardId());
        assertEquals(output.getBoardName(), board.getBoardName());
    }
}
