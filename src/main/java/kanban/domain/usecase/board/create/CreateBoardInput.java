package kanban.domain.usecase.board.create;

public class CreateBoardInput {
    private String boardName;

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }
}
