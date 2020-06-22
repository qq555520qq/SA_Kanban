package kanban.domain.adapter.presenter.card.viewmodel;

public class CreateCardViewModel {
    private String cardId;
    private String cardName;

    public void setCardId(String cardId){
        this.cardId = cardId;
    }
    public String getCardId(){
        return cardId;
    }

    public void setCardName(String cardName){
        this.cardName = cardName;
    }
    public String getCardName(){
        return cardName;
    }
}
