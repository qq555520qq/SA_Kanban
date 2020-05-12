package kanban.domain.model.aggregate.board.event;

import kanban.domain.model.DomainEvent;
import kanban.domain.model.common.DateProvider;

import java.util.Date;

public class BoardCreated implements DomainEvent {

    private Date occurredOn;
    private String boardId;
    private String boardName;

    public BoardCreated(String boardId, String boardName) {
        this.occurredOn = DateProvider.now();
        this.boardId = boardId;
        this.boardName = boardName;
}

    @Override
    public Date getOccurredOn() {
        return occurredOn;
    }

    public String getBoardId() {
        return boardId;
    }
}
