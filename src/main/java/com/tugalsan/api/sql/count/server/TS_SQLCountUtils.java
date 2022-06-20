package com.tugalsan.api.sql.count.server;

import com.tugalsan.api.sql.conn.server.TS_SQLConnAnchor;
import com.tugalsan.api.sql.where.server.TS_SQLWhereUtils;

public class TS_SQLCountUtils {

    public static TS_SQLCount count(TS_SQLConnAnchor anchor, CharSequence tableName) {
        return new TS_SQLCount(anchor, tableName);
    }

    public static void setInfo(boolean enabled) {
        TS_SQLCountExecutor.d.infoEnable = enabled;
        TS_SQLWhereUtils.setInfo(enabled);
    }

//    public static void test() {
//        var count = TS_SQLCountUtils
//                .count(null, "ya")
//                .whereConditionAnd(conditions -> conditions.lngEq("", 0));
//    }
}
