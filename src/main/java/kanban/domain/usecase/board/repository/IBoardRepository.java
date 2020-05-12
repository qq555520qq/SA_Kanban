package kanban.domain.usecase.board.repository;


import kanban.domain.usecase.entity.BoardEntity;

public interface IBoardRepository {
    void add(BoardEntity board);

    BoardEntity getBoardById(String boardId);

    void save(BoardEntity board);
}
