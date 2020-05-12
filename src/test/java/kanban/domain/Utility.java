package kanban.domain;

import kanban.domain.model.DomainEventBus;
import kanban.domain.model.aggregate.board.Board;
import kanban.domain.usecase.DomainEventHandler;
import kanban.domain.usecase.board.create.CreateBoardInput;
import kanban.domain.usecase.board.create.CreateBoardOutput;
import kanban.domain.usecase.board.create.CreateBoardUseCase;
import kanban.domain.usecase.board.repository.IBoardRepository;
import kanban.domain.usecase.stage.create.CreateStageInput;
import kanban.domain.usecase.stage.create.CreateStageOutput;
import kanban.domain.usecase.stage.create.CreateStageUseCase;
import kanban.domain.usecase.workflow.create.CreateWorkflowInput;
import kanban.domain.usecase.workflow.create.CreateWorkflowOutput;
import kanban.domain.usecase.workflow.create.CreateWorkflowUseCase;
import kanban.domain.usecase.workflow.repository.IWorkflowRepository;

public class Utility {

    private IWorkflowRepository workflowRepository;
    private IBoardRepository boardRepository;
    private DomainEventBus eventBus;

    public Utility(
            IBoardRepository boardRepository,
            IWorkflowRepository workflowRepository,
            DomainEventBus eventBus) {
        this.workflowRepository = workflowRepository;
        this.boardRepository = boardRepository;
        this.eventBus = eventBus;
    }

    public String createBoard(String boardName){
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository);
        CreateBoardInput input = new CreateBoardInput();
        CreateBoardOutput output = new CreateBoardOutput();

        input.setBoardName("Board");
        createBoardUseCase.execute(input, output);
        return output.getBoardId();
    }

    public String createWorkflow(String boardId, String workflowName){
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(
                workflowRepository,
                eventBus);

        CreateWorkflowInput input = new CreateWorkflowInput();
        input.setBoardId(boardId);
        input.setWorkflowName(workflowName);

        CreateWorkflowOutput output = new CreateWorkflowOutput();
        createWorkflowUseCase.execute(input, output);
        return output.getWorkflowId();
    }

    public String createStage(String workflowId, String stageName) {

        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository);
        CreateStageInput input = new CreateStageInput();
        input.setStageName(stageName);
        input.setWorkflowId(workflowId);

        CreateStageOutput output = new CreateStageOutput();
        createStageUseCase.execute(input, output);
        return output.getStageId();
    }
}
