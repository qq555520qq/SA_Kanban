package kanban.domain.model.aggregate.board.event;

import kanban.domain.model.DomainEvent;
import kanban.domain.model.common.DateProvider;

import java.util.Date;

public class BoardCreated implements DomainEvent {

    private Date occurredOn;
    private String userId;
    private String boardId;
    private String boardName;

    public BoardCreated(String userId, String boardId, String boardName) {
        this.occurredOn = DateProvider.now();
        this.userId = userId;
        this.boardId = boardId;
        this.boardName = boardName;
}

    @Override
    public Date getOccurredOn() {
        return occurredOn;
    }

    public String getUserId() {
        return userId;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getBoardName() {
        return boardName;
    }
}
