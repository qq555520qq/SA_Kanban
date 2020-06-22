package kanban.domain.adapter.presenter.card.create;

import kanban.domain.adapter.presenter.card.viewmodel.CreateCardViewModel;
import kanban.domain.usecase.card.create.CreateCardOutput;

public class CreateCardPresenter implements CreateCardOutput {
    private String cardName;
    private String cardId;

    public CreateCardViewModel build(){
        CreateCardViewModel viewModel = new CreateCardViewModel();
        viewModel.setCardId(getCardId());
        viewModel.setCardName(getCardName());
        return viewModel;
    }

    @Override
    public String getCardName() {
        return cardName;
    }

    @Override
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    @Override
    public String getCardId() {
        return cardId;
    }

    @Override
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
