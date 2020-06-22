package kanban.domain.adapter.repository.card;

import kanban.domain.adapter.database.table.CardTable;
import kanban.domain.adapter.database.MySqlDatabaseHelper;
import kanban.domain.adapter.repository.card.data.CardData;
import kanban.domain.adapter.repository.card.mapper.CardEntityDataMapper;
import kanban.domain.usecase.card.CardEntity;
import kanban.domain.usecase.card.ICardRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlCardRepository implements ICardRepository {

    private MySqlDatabaseHelper sqlDatabaseHelper;

    public MySqlCardRepository() {
        sqlDatabaseHelper = new MySqlDatabaseHelper();
    }

    @Override
    public void add(CardEntity cardEntity) {
        sqlDatabaseHelper.connectToDatabase();
        PreparedStatement preparedStatement = null;
        try {
            sqlDatabaseHelper.transactionStart();

            String sql = String.format("Insert Into %s Values (?, ?, ?, ?, ?)",
                    CardTable.tableName);
            preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
            preparedStatement.setString(1, cardEntity.getCardId());
            preparedStatement.setString(2, cardEntity.getName());
            preparedStatement.setString(3, cardEntity.getDescription());
            preparedStatement.setString(4, cardEntity.getType());
            preparedStatement.setString(5, cardEntity.getSize());
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
    public CardEntity getCardById(String cardId) {
        if(!sqlDatabaseHelper.isTransacting()) {
            sqlDatabaseHelper.connectToDatabase();
        }
        ResultSet resultSet = null;
        CardData cardData = null;
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

                cardData = new CardData();
                cardData.setCardId(cardId);
                cardData.setName(name);
                cardData.setDescription(description);
                cardData.setType(type);
                cardData.setSize(size);

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

        if(cardData == null) {
            throw new RuntimeException("Card is not found,id=" + cardId);
        }

        return CardEntityDataMapper.transformDataToEntity(cardData);
    }

    @Override
    public void save(CardEntity cardEntity) {
        sqlDatabaseHelper.connectToDatabase();
        PreparedStatement preparedStatement = null;
        try {
            sqlDatabaseHelper.transactionStart();

            String sql = String.format("Insert Into %s Values (? , ?, ?, ?, ?) On Duplicate Key Update %s=? %s=? %s=? %s=?",
                    CardTable.tableName, CardTable.name, CardTable.description, CardTable.type, CardTable.size);
            preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
            preparedStatement.setString(1, cardEntity.getCardId());
            preparedStatement.setString(2, cardEntity.getName());
            preparedStatement.setString(3, cardEntity.getDescription());
            preparedStatement.setString(4, cardEntity.getType());
            preparedStatement.setString(5, cardEntity.getSize());
            preparedStatement.setString(6, cardEntity.getName());
            preparedStatement.setString(7, cardEntity.getDescription());
            preparedStatement.setString(8, cardEntity.getType());
            preparedStatement.setString(9, cardEntity.getSize());
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
