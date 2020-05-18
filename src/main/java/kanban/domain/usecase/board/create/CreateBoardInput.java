package kanban.domain.usecase.board.create;

public interface CreateBoardInput {
    public String getUserId();

    public void setUserId(String userId);

    public String getBoardName();

    public void setBoardName(String boardName);
}
