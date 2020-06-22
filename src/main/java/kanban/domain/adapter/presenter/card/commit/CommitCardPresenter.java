package kanban.domain.adapter.presenter.card.commit;

import kanban.domain.adapter.presenter.card.viewmodel.CommitCardViewModel;
import kanban.domain.usecase.card.commit.CommitCardOutput;

public class CommitCardPresenter implements CommitCardOutput {
    private String cardId;
    public CommitCardViewModel build(){
        CommitCardViewModel viewModel = new CommitCardViewModel();
        viewModel.setCardId(getCardId());
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
