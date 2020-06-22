package kanban.domain.adapter.presenter.card.move;

import kanban.domain.adapter.presenter.card.viewmodel.MoveCardViewModel;
import kanban.domain.usecase.card.move.MoveCardOutput;

public class MoveCardPresenter implements MoveCardOutput {
    private String cardId;

    public MoveCardViewModel build() {
        MoveCardViewModel viewModel  = new MoveCardViewModel();
        viewModel.setCardId(cardId);
        return viewModel;
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
