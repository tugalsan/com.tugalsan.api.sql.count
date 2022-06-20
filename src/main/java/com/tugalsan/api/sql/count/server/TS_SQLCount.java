package com.tugalsan.api.sql.count.server;

import com.tugalsan.api.executable.client.TGS_ExecutableType1;
import com.tugalsan.api.sql.conn.server.TS_SQLConnAnchor;
import com.tugalsan.api.sql.where.server.TS_SQLWhereConditions;
import com.tugalsan.api.sql.where.server.TS_SQLWhereGroups;
import com.tugalsan.api.sql.where.server.TS_SQLWhereUtils;

public class TS_SQLCount {

    public TS_SQLCount(TS_SQLConnAnchor anchor, CharSequence tableName) {
        executor = new TS_SQLCountExecutor(anchor, tableName);
    }
    final private TS_SQLCountExecutor executor;

    public long whereGroupAnd(TGS_ExecutableType1<TS_SQLWhereGroups> groups) {
        executor.where = TS_SQLWhereUtils.where();
        executor.where.groupsAnd(groups);
        return executor.execute();
    }

    public long whereGroupOr(TGS_ExecutableType1<TS_SQLWhereGroups> groups) {
        executor.where = TS_SQLWhereUtils.where();
        executor.where.groupsOr(groups);
        return executor.execute();
    }

    public long whereConditionAnd(TGS_ExecutableType1<TS_SQLWhereConditions> conditions) {
        return whereGroupAnd(where -> where.conditionsAnd(conditions));
    }

    public long whereConditionOr(TGS_ExecutableType1<TS_SQLWhereConditions> conditions) {
        return whereGroupOr(where -> where.conditionsOr(conditions));
    }

    public long whereConditionNone() {
        return executor.execute();
    }
}
