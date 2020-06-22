package kanban.domain.usecase.board.get;

import kanban.domain.model.aggregate.board.Board;
import kanban.domain.usecase.board.BoardDTO;
import kanban.domain.usecase.board.mapper.BoardDTOModelMapper;
import kanban.domain.usecase.board.mapper.BoardEntityModelMapper;
import kanban.domain.usecase.board.IBoardRepository;
import kanban.domain.usecase.board.BoardEntity;

import java.util.ArrayList;
import java.util.List;

public class GetBoardsUseCase implements GetBoardsInput{

    private IBoardRepository boardRepository;
    private String userId;

    public GetBoardsUseCase(IBoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public void execute(GetBoardsInput input, GetBoardsOutput output) {
        List<BoardEntity> boardEntities = boardRepository.getBoardsByUserId(input.getUserId());
        List<BoardDTO> BoardDTOs = new ArrayList<>();
        for(BoardEntity boardEntity: boardEntities){
            Board board = BoardEntityModelMapper.transformEntityToModel(boardEntity);
            BoardDTOs.add(BoardDTOModelMapper.transformModelToDTO(board));
        }
        output.setBoardDTOs(BoardDTOs);
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
