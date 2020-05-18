package kanban.domain.adapter.repository.board;

import kanban.domain.adapter.database.MySqlDatabaseHelper;
import kanban.domain.adapter.database.table.BoardTable;
import kanban.domain.adapter.database.table.BoardWorkflowTable;
import kanban.domain.usecase.board.repository.IBoardRepository;
import kanban.domain.usecase.board.BoardEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlBoardRepository implements IBoardRepository {

    private MySqlDatabaseHelper sqlDatabaseHelper;

    public MySqlBoardRepository() {
        sqlDatabaseHelper = new MySqlDatabaseHelper();
    }

    @Override
    public void add(BoardEntity board) {
        sqlDatabaseHelper.connectToDatabase();
        PreparedStatement preparedStatement = null;
        try {
            sqlDatabaseHelper.transactionStart();

            for (String workflowId : board.getWorkflowIds()) {
                addBoardWorkflow(workflowId, board.getBoardId());
            }

            String sql = String.format("Insert Into %s Values (?, ?, ?)",
                    BoardTable.tableName);
            preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
            preparedStatement.setString(1, board.getUserId());
            preparedStatement.setString(2, board.getBoardId());
            preparedStatement.setString(3, board.getBoardName());
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
    public BoardEntity getBoardById(String boardId) {
        if (!sqlDatabaseHelper.isTransacting()) {
            sqlDatabaseHelper.connectToDatabase();
        }
        ResultSet resultSet = null;
        BoardEntity board = null;
        try {
            String query = String.format("Select * From %s Where %s = '%s'",
                    BoardTable.tableName,
                    BoardTable.boardId,
                    boardId);
            resultSet = sqlDatabaseHelper.getResultSet(query);
            if (resultSet.first()) {
                String userId = resultSet.getString(BoardTable.userId);
                String _boardId = resultSet.getString(BoardTable.boardId);
                String name = resultSet.getString(BoardTable.name);

                board = new BoardEntity();
                board.setUserId(userId);
                board.setBoardId(_boardId);
                board.setBoardName(name);
                board.setWorkflowIds(getWorkflowIdsByBoardId(boardId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            sqlDatabaseHelper.closeResultSet(resultSet);
            if (!sqlDatabaseHelper.isTransacting()) {
                sqlDatabaseHelper.closeConnection();
            }
        }
        return board;
    }

    @Override
    public void save(BoardEntity board) {
        sqlDatabaseHelper.connectToDatabase();
        PreparedStatement preparedStatement = null;
        try {
            sqlDatabaseHelper.transactionStart();

            for (String workflowId : board.getWorkflowIds()) {
                addBoardWorkflow(workflowId, board.getBoardId());
            }

            String sql = String.format("Insert Into %s Values (?, ?, ?) On Duplicate Key Update %s=?",
                    BoardTable.tableName, BoardTable.name);
            preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
            preparedStatement.setString(1, board.getUserId());
            preparedStatement.setString(2, board.getBoardId());
            preparedStatement.setString(3, board.getBoardName());
            preparedStatement.setString(4, board.getBoardName());
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
    public List<BoardEntity> getBoardsByUserId(String userId) {
        if (!sqlDatabaseHelper.isTransacting()) {
            sqlDatabaseHelper.connectToDatabase();
        }

        ResultSet resultSet = null;
        List<BoardEntity> boardEntities = new ArrayList<>();
        try {
            String query = String.format("Select * From %s Where %s = '%s'",
                    BoardTable.tableName,
                    BoardTable.userId,
                    userId);
            resultSet = sqlDatabaseHelper.getResultSet(query);
            while (resultSet.next()) {
                String boardId = resultSet.getString(BoardTable.boardId);
                String name = resultSet.getString(BoardTable.name);

                BoardEntity boardEntity = new BoardEntity();
                boardEntity.setUserId(userId);
                boardEntity.setBoardId(boardId);
                boardEntity.setBoardName(name);
                boardEntity.setWorkflowIds(getWorkflowIdsByBoardId(boardId));

                boardEntities.add(boardEntity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            sqlDatabaseHelper.closeResultSet(resultSet);
            if (!sqlDatabaseHelper.isTransacting()) {
                sqlDatabaseHelper.closeConnection();
            }
        }
        return boardEntities;
    }


    private void addBoardWorkflow(String workflowId, String boardId) throws SQLException {
        String sql = String.format("Insert Ignore Into %s Values (?, ?)",
                BoardWorkflowTable.tableName);
        PreparedStatement preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
        preparedStatement.setString(1, workflowId);
        preparedStatement.setString(2, boardId);
        preparedStatement.executeUpdate();
        sqlDatabaseHelper.closePreparedStatement(preparedStatement);
    }

    private List<String> getWorkflowIdsByBoardId(String boardId) {
        ResultSet resultSet = null;
        List<String> workflowIds = new ArrayList<>();
        try {
            String query = String.format("Select * From %s Where %s = '%s'",
                    BoardWorkflowTable.tableName,
                    BoardWorkflowTable.boardId,
                    boardId);
            resultSet = sqlDatabaseHelper.getResultSet(query);
            while (resultSet.next()) {
                String workFlowId = resultSet.getString(BoardWorkflowTable.workflowId);

                workflowIds.add(workFlowId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            sqlDatabaseHelper.closeResultSet(resultSet);
        }

        return workflowIds;
    }


}
