package kanban.domain.adapter.database;

import kanban.domain.adapter.database.table.*;

import java.sql.*;

public class MySqlDatabaseHelper {

    private String serverUrl;
    private String databaseName;
    private String account;
    private String password;
    private Connection connection;
    private boolean transacting;

    public MySqlDatabaseHelper() {
        serverUrl = "127.0.0.1";
        databaseName = "kanban";
        account = "root";
        password = "root";
        transacting = false;

        createKanbanDatabase();
        createBoardTable();
        createBoardWorkflowTable();

        createWorkflowTable();
        createStageTable();
        createStageCardTable();

        createCardTable();
    }

    private void createBoardTable() {
        connectToDatabase();
        Statement statement = null;
        String sql = "Create Table If Not Exists " + BoardTable.tableName + " ("
                + BoardTable.userId + " Varchar(50) Not Null, "
                + BoardTable.boardId + " Varchar(50) Not Null, "
                + BoardTable.name + " Varchar(50) Not Null, "
                + "Primary Key (" + BoardTable.boardId + ") "
                + ")";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
            closeConnection();
        }
    }

    private void createBoardWorkflowTable() {
        connectToDatabase();
        Statement statement = null;
        String sql = "Create Table If Not Exists " + BoardWorkflowTable.tableName + " ("
                + BoardWorkflowTable.workflowId + " Varchar(50) Not Null, "
                + BoardWorkflowTable.boardId + " Varchar(50) Not Null, "
                + "Primary Key (" + BoardWorkflowTable.workflowId + ")"
                + ")";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
            closeConnection();
        }
    }

    private void createWorkflowTable() {
        connectToDatabase();
        Statement statement = null;
        String sql = "Create Table If Not Exists " + WorkflowTable.tableName + " ("
                + WorkflowTable.workflow_id + " Varchar(50) Not Null, "
                + WorkflowTable.title + " Varchar(50) Not Null, "
                + "Primary Key (" + WorkflowTable.workflow_id + ")"
                + ")";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
            closeConnection();
        }
    }

    private void createStageTable() {
        connectToDatabase();
        Statement statement = null;
        String sql = "Create Table If Not Exists " + StageTable.tableName + " ("
                + StageTable.stage_id + " Varchar(50) Not Null, "
                + StageTable.workflowId + " Varchar(50) Not Null, "
                + StageTable.name + " Varchar(50) Not Null, "
                + "Primary Key (" + StageTable.stage_id + ")"
                + ")";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
            closeConnection();
        }
    }

    private void createStageCardTable() {
        connectToDatabase();
        Statement statement = null;
        String sql = "Create Table If Not Exists " + StageCardTable.tableName + " ("
                + StageCardTable.cardId + " Varchar(50) Not Null, "
                + StageCardTable.stageId + " Varchar(50) Not Null, "
                + "Primary Key (" + StageCardTable.cardId + ")"
                + ")";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
            closeConnection();
        }
    }

    private void createCardTable() {
        connectToDatabase();
        Statement statement = null;
        String sql = "Create Table If Not Exists " + CardTable.tableName + " ("
                + CardTable.cardId + " Varchar(50) Not Null, "
                + CardTable.name + " Varchar(256) Not Null, "
                + CardTable.description + " Varchar(256) Not Null, "
                + CardTable.type + " Varchar(50) Not Null, "
                + CardTable.size + " Varchar(50) Not Null, "
                + "Primary Key (" + CardTable.cardId + ") "
                + ")";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
            closeConnection();
        }
    }

    public void connectToDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://"+serverUrl+":3306/" + databaseName + "?useSSL=false&autoReconnect=true", account, password);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void createKanbanDatabase() {
        Statement statement = null;
        String sql = "Create Database If Not Exists " + databaseName;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + serverUrl + ":3306?useSSL=false", account, password);
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            closeConnection();
            connectToDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
            closeConnection();
        }
    }


    public boolean isTransacting() {
        return transacting;
    }

    public void transactionStart() throws SQLException {
        connection.setAutoCommit(false);
        transacting = true;
    }

    public void transactionError() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void transactionEnd() throws SQLException {
        connection.setAutoCommit(false);
        connection.commit();
        transacting = false;
    }

    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        return preparedStatement;
    }

    public ResultSet getResultSet(String query) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        return resultSet;
    }

    public void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {

            }
        }
    }


    public void closePreparedStatement(PreparedStatement preparedStatement) {
        if(preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeResultSet(ResultSet resultSet) {
        if(resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeConnection() {
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
