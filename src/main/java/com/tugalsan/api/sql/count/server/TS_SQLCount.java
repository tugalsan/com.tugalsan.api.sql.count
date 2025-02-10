package com.tugalsan.api.sql.count.server;


import com.tugalsan.api.function.client.maythrow.uncheckedexceptions.TGS_FuncMTUCE_In1;
import com.tugalsan.api.sql.conn.server.TS_SQLConnAnchor;
import com.tugalsan.api.sql.where.server.TS_SQLWhereConditions;
import com.tugalsan.api.sql.where.server.TS_SQLWhereGroups;
import com.tugalsan.api.sql.where.server.TS_SQLWhereUtils;

public class TS_SQLCount {

    public TS_SQLCount(TS_SQLConnAnchor anchor, CharSequence tableName) {
        executor = new TS_SQLCountExecutor(anchor, tableName);
    }
    final private TS_SQLCountExecutor executor;

    public long whereGroupAnd(TGS_FuncMTUCE_In1<TS_SQLWhereGroups> groups) {
        executor.where = TS_SQLWhereUtils.where();
        executor.where.groupsAnd(groups);
        return executor.run();
    }

    public long whereGroupOr(TGS_FuncMTUCE_In1<TS_SQLWhereGroups> groups) {
        executor.where = TS_SQLWhereUtils.where();
        executor.where.groupsOr(groups);
        return executor.run();
    }

    public long whereConditionAnd(TGS_FuncMTUCE_In1<TS_SQLWhereConditions> conditions) {
        return whereGroupAnd(where -> where.conditionsAnd(conditions));
    }

    public long whereConditionOr(TGS_FuncMTUCE_In1<TS_SQLWhereConditions> conditions) {
        return whereGroupOr(where -> where.conditionsOr(conditions));
    }

    public long whereConditionNone() {
        return executor.run();
    }
}
