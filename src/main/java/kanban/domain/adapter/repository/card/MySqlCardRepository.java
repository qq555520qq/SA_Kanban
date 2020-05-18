package kanban.domain.adapter.repository.card;

import kanban.domain.adapter.database.table.CardTable;
import kanban.domain.adapter.database.MySqlDatabaseHelper;
import kanban.domain.model.aggregate.card.Card;
import kanban.domain.usecase.card.repository.ICardRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlCardRepository implements ICardRepository {

    private MySqlDatabaseHelper sqlDatabaseHelper;

    public MySqlCardRepository() {
        sqlDatabaseHelper = new MySqlDatabaseHelper();
    }

    @Override
    public void add(Card card) {
        sqlDatabaseHelper.connectToDatabase();
        PreparedStatement preparedStatement = null;
        try {
            sqlDatabaseHelper.transactionStart();

            String sql = String.format("Insert Into %s Values (?, ?, ?, ?, ?)",
                    CardTable.tableName);
            preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
            preparedStatement.setString(1, card.getCardId());
            preparedStatement.setString(2, card.getName());
            preparedStatement.setString(3, card.getDescription());
            preparedStatement.setString(4, card.getType());
            preparedStatement.setString(5, card.getSize());
            preparedStatement.executeUpdate();
            sqlDatabaseHelper.transactionEnd();
        } catch (SQLException e) {
            sqlDatabaseHelper.transactionError();
            e.printStackTrace();
        } finally {
            sqlDatabaseHelper.closePreparedStatement(preparedStatement);
            sqlDatabaseHelper.closeConnection();
        }
    }

    @Override
    public Card getCardById(String cardId) {
        if(!sqlDatabaseHelper.isTransacting()) {
            sqlDatabaseHelper.connectToDatabase();
        }
        ResultSet resultSet = null;
        Card card = null;
        try {
            String query = String.format("Select * From %s Where %s = '%s'",
                    CardTable.tableName,
                    CardTable.cardId,
                    cardId);
            resultSet = sqlDatabaseHelper.getResultSet(query);
            if (resultSet.first()) {
                String name = resultSet.getString(CardTable.name);
                String description = resultSet.getString(CardTable.description);
                String type = resultSet.getString(CardTable.type);
                String size = resultSet.getString(CardTable.size);

                card = new Card();
                card.setCardId(cardId);
                card.setName(name);
                card.setDescription(description);
                card.setType(type);
                card.setSize(size);

            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            sqlDatabaseHelper.closeResultSet(resultSet);
            if(!sqlDatabaseHelper.isTransacting()) {
                sqlDatabaseHelper.closeConnection();
            }
        }

        if(card == null) {
            throw new RuntimeException("Card is not found,id=" + cardId);
        }

        return card;
    }

    @Override
    public void save(Card card) {
        sqlDatabaseHelper.connectToDatabase();
        PreparedStatement preparedStatement = null;
        try {
            sqlDatabaseHelper.transactionStart();

            String sql = String.format("Insert Into %s Values (? , ?, ?, ?, ?) On Duplicate Key Update %s=? %s=? %s=? %s=?",
                    CardTable.tableName, CardTable.name, CardTable.description, CardTable.type, CardTable.size);
            preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
            preparedStatement.setString(1, card.getCardId());
            preparedStatement.setString(2, card.getName());
            preparedStatement.setString(3, card.getDescription());
            preparedStatement.setString(4, card.getType());
            preparedStatement.setString(5, card.getSize());
            preparedStatement.setString(6, card.getName());
            preparedStatement.setString(7, card.getDescription());
            preparedStatement.setString(8, card.getType());
            preparedStatement.setString(9, card.getSize());
            preparedStatement.executeUpdate();
            sqlDatabaseHelper.transactionEnd();
        } catch (SQLException e) {
            sqlDatabaseHelper.transactionError();
            e.printStackTrace();
        } finally {
            sqlDatabaseHelper.closePreparedStatement(preparedStatement);
            sqlDatabaseHelper.closeConnection();
        }
    }
}
