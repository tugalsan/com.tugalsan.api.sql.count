package com.tugalsan.api.sql.count.server;

import com.tugalsan.api.log.server.*;
import com.tugalsan.api.sql.conn.server.*;
import com.tugalsan.api.sql.select.server.*;
import com.tugalsan.api.sql.where.server.*;
import com.tugalsan.api.union.client.TGS_UnionExcuse;
import com.tugalsan.api.union.client.TGS_UnionExcuseVoid;

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

    public TGS_UnionExcuse<Long> run() {
        var wrap = new Object() {
            TGS_UnionExcuse<Integer> u_fill;
            TGS_UnionExcuse<Boolean> u_empty;
            TGS_UnionExcuse<Long> u_lng;
            TGS_UnionExcuseVoid u_select;
        };
        wrap.u_select = TS_SQLSelectStmtUtils.select(anchor, toString(), fillStmt -> {
            if (where != null) {
                wrap.u_fill = where.fill(fillStmt, 0);
            }
        }, rs -> {
            wrap.u_empty = rs.row.isEmpty();
            if (wrap.u_empty.isExcuse() || wrap.u_empty.value()) {
                return;
            }
            wrap.u_lng = rs.lng.get(0, 0);
        });
        if (wrap.u_fill != null && wrap.u_fill.isExcuse()) {
            return wrap.u_fill.toExcuse();
        }
        if (wrap.u_empty != null && wrap.u_empty.isExcuse()) {
            return wrap.u_empty.toExcuse();
        }
        if (wrap.u_lng != null && wrap.u_lng.isExcuse()) {
            return wrap.u_lng.toExcuse();
        }
        if (wrap.u_select != null && wrap.u_select.isExcuse()) {
            return wrap.u_select.toExcuse();
        }
        return wrap.u_lng;
    }
}
