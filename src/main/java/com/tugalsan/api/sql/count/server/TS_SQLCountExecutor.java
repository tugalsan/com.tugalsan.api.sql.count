package com.tugalsan.api.sql.count.server;

import com.tugalsan.api.log.server.*;
import com.tugalsan.api.tuple.client.*;
import com.tugalsan.api.sql.conn.server.*;
import com.tugalsan.api.sql.select.server.*;
import com.tugalsan.api.sql.where.server.*;

public class TS_SQLCountExecutor {

    final public static TS_Log d = TS_Log.of(TS_SQLCountExecutor.class);

    public TS_SQLCountExecutor(TS_SQLConnAnchor anchor, CharSequence tableName) {
        this.anchor = anchor;
        this.tableName = tableName;
    }
    final public TS_SQLConnAnchor anchor;
    final public CharSequence tableName;

    public TS_SQLWhere where = null;

    @Override
    public String toString() {
        var sb = new StringBuilder("SELECT COUNT(*) ").append(" FROM ").append(tableName);
        if (where != null) {
            sb.append(" ").append(where);
        }
        var sql = sb.toString();
        d.ci("toString", sql);
        return sql;
    }

    public Long run() {
        TGS_Tuple1<Long> pack = new TGS_Tuple1();
        TS_SQLSelectStmtUtils.select(anchor, toString(), fillStmt -> {
            if (where != null) {
                where.fill(fillStmt, 0);
            }
        }, rs -> {
            if (rs.row.isEmpty()) {
                return;
            }
            pack.value0 = rs.lng.get(0, 0);
        });
        return pack.value0;
    }
}
