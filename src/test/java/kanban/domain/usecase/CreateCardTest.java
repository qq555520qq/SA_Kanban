package kanban.domain.usecase;

import kanban.domain.Utility;
import kanban.domain.usecase.card.CardRepository;
import kanban.domain.usecase.card.CreateCardInput;
import kanban.domain.usecase.card.CreateCardOutput;
import kanban.domain.usecase.card.CreateCardUseCase;
import kanban.domain.usecase.workflow.WorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateCardTest {

    private String workflowId;
    private String stageId;
    private WorkflowRepository workflowRepository = new WorkflowRepository();
    private CardRepository cardRepository = new CardRepository();
    private Utility utility;

    @Before
    public void setup() {
        workflowRepository = new WorkflowRepository();
        utility = new Utility(workflowRepository);
        workflowId = utility.createWorkflow("boardId","workflowName");
        stageId = utility.createStage(workflowId,"stageName");
    }

    @Test
    public void Create_card_should_success() {

        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository);
        CreateCardInput input = new CreateCardInput();
        input.setCardName("card");
        input.setStageId(stageId);

        CreateCardOutput output = new CreateCardOutput();
        createCardUseCase.execute(input, output);
        assertEquals("card", output.getCardName());
        assertNotNull(output.getCardId());
    }

}
