package kanban.domain.adapter.repository.board;

import kanban.domain.adapter.repository.board.data.BoardData;
import kanban.domain.adapter.repository.board.mapper.BoardEntityDataMapper;
import kanban.domain.usecase.board.IBoardRepository;
import kanban.domain.usecase.board.BoardEntity;

import java.util.ArrayList;
import java.util.List;

public class InMemoryBoardRepository implements IBoardRepository {

    private List<BoardData> boardDatas;

    public InMemoryBoardRepository() {
        boardDatas = new ArrayList<BoardData>();
    }

    @Override
    public void add(BoardEntity boardEntity) {
        boardDatas.add(BoardEntityDataMapper.transformEntityToData(boardEntity));
    }

    @Override
    public BoardEntity getBoardById(String boardId) {
        for (BoardData each : boardDatas) {
            if (each.getBoardId().equalsIgnoreCase(boardId)) {
                return BoardEntityDataMapper.transformDataToEntity(each);
            }
        }
        throw new RuntimeException("Board is not found,id=" + boardId);
    }

    @Override
    public void save(BoardEntity board) {
        for (BoardData each : boardDatas) {
            if (each.getBoardId().equalsIgnoreCase(board.getBoardId())) {
                boardDatas.set(boardDatas.indexOf(each), BoardEntityDataMapper.transformEntityToData(board));
                break;
            }
        }
    }

    @Override
    public List<BoardEntity> getBoardsByUserId(String userId) {
        List<BoardEntity> boardEntities = new ArrayList<>();

        for (BoardData each : boardDatas) {
            if (each.getUserId().equalsIgnoreCase(userId)) {
                boardEntities.add(BoardEntityDataMapper.transformDataToEntity(each));
            }
        }

        return boardEntities;
    }
}
