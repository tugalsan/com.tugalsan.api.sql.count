package com.tugalsan.api.sql.count.server;

import com.tugalsan.api.runnable.client.TGS_RunnableType1;
import com.tugalsan.api.sql.conn.server.TS_SQLConnAnchor;
import com.tugalsan.api.sql.where.server.TS_SQLWhereConditions;
import com.tugalsan.api.sql.where.server.TS_SQLWhereGroups;
import com.tugalsan.api.sql.where.server.TS_SQLWhereUtils;
import com.tugalsan.api.union.client.TGS_UnionExcuse;

public class TS_SQLCount {

    public TS_SQLCount(TS_SQLConnAnchor anchor, CharSequence tableName) {
        executor = new TS_SQLCountExecutor(anchor, tableName);
    }
    final private TS_SQLCountExecutor executor;

    public TGS_UnionExcuse<Long> whereGroupAnd(TGS_RunnableType1<TS_SQLWhereGroups> groups) {
        executor.where = TS_SQLWhereUtils.where();
        executor.where.groupsAnd(groups);
        return executor.run();
    }

    public TGS_UnionExcuse<Long> whereGroupOr(TGS_RunnableType1<TS_SQLWhereGroups> groups) {
        executor.where = TS_SQLWhereUtils.where();
        executor.where.groupsOr(groups);
        return executor.run();
    }

    public TGS_UnionExcuse<Long> whereConditionAnd(TGS_RunnableType1<TS_SQLWhereConditions> conditions) {
        return whereGroupAnd(where -> where.conditionsAnd(conditions));
    }

    public TGS_UnionExcuse<Long> whereConditionOr(TGS_RunnableType1<TS_SQLWhereConditions> conditions) {
        return whereGroupOr(where -> where.conditionsOr(conditions));
    }

    public TGS_UnionExcuse<Long> whereConditionNone() {
        return executor.run();
    }
}
