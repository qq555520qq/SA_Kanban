package kanban.domain.usecase.board.create;

import kanban.domain.model.DomainEventBus;
import kanban.domain.model.aggregate.board.Board;
import kanban.domain.usecase.board.mapper.BoardEntityModelMapper;
import kanban.domain.usecase.board.IBoardRepository;

public class CreateBoardUseCase implements CreateBoardInput {
    private IBoardRepository boardRepository;
    private DomainEventBus eventBus;
    private String userId;
    private String boardName;

    public CreateBoardUseCase(
            IBoardRepository boardRepository,
            DomainEventBus eventBus) {
        this.boardRepository = boardRepository;
        this.eventBus = eventBus;
    }

    public void execute(CreateBoardInput input, CreateBoardOutput output) {
        Board board = new Board(input.getUserId(), input.getBoardName());

        boardRepository.add(BoardEntityModelMapper.transformModelToEntity(board));
        eventBus.postAll(board);
        output.setBoardId(board.getBoardId());
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getBoardName() {
        return boardName;
    }

    @Override
    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }
}
