package kanban.domain.adapter.repository.board;

import kanban.domain.model.aggregate.board.Board;
import kanban.domain.usecase.board.repository.IBoardRepository;
import kanban.domain.usecase.entity.BoardEntity;

import java.util.ArrayList;
import java.util.List;

public class InMemoryBoardRepository implements IBoardRepository {

    private List<BoardEntity> boards;

    public InMemoryBoardRepository() {
        boards = new ArrayList<BoardEntity>();
    }

    @Override
    public void add(BoardEntity board) {
        boards.add(board);
    }

    @Override
    public BoardEntity getBoardById(String boardId) {
        for (BoardEntity each : boards) {
            if (each.getBoardId().equalsIgnoreCase(boardId)) {
                return each;
            }
        }
        throw new RuntimeException("Board is not found,id=" + boardId);
    }

    @Override
    public void save(BoardEntity board) {
        for (BoardEntity each : boards) {
            if (each.getBoardId().equalsIgnoreCase(board.getBoardId())) {
                boards.set(boards.indexOf(each), board);
                break;
            }
        }
    }
}
