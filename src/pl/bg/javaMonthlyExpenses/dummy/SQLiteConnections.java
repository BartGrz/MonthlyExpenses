package pl.bg.javaMonthlyExpenses.dummy;

import java.sql.Statement;

public interface SQLiteConnections {

    void connect(Statement statement);
    void disconnect(Statement statement);
    boolean isConnected(Statement statement);
}
