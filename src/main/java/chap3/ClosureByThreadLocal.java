package chap3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ClosureByThreadLocal {
    static String DB_URL = "jdbc:mysql://localhost/mydatabase";

    // 注意是静态的！
    // ThreadLocal变量类似于全局变量，可以理解成它对全局变量做了重新组织，每个线程拥有自己的一套全局变量，线程间互不影响
    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>() {
        @Override
        protected Connection initialValue() {
            try {
                return DriverManager.getConnection(DB_URL);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return null;
        }
    };

    public Connection getConnection() {
        // 原理：ThreadLocal类持有特定变量（它的泛型标识中的类，比如一个 Connection 连接对象），它的 get() 方法为每个使用特定变量的线程保存一份独立的副本
        // 因此，get() 方法总是返回的是当前执行线程在前一次调用 set() 方法时设置的最新值
        return connectionHolder.get();
    }

    /* 给出spring框架中事务上下文持有者的实现，它就使用了 ThreadLocal 的 get() set() remove()

package org.springframework.test.context.transaction;

import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.lang.Nullable;

final class TransactionContextHolder {
    private static final ThreadLocal<TransactionContext> currentTransactionContext = new NamedInheritableThreadLocal("Test Transaction Context");

    private TransactionContextHolder() {
    }

    static void setCurrentTransactionContext(TransactionContext transactionContext) {
        currentTransactionContext.set(transactionContext);
    }

    @Nullable
    static TransactionContext getCurrentTransactionContext() {
        return (TransactionContext)currentTransactionContext.get();
    }

    @Nullable
    static TransactionContext removeCurrentTransactionContext() {
        TransactionContext transactionContext = (TransactionContext)currentTransactionContext.get();
        currentTransactionContext.remove();
        return transactionContext;
    }
}

     */
}
